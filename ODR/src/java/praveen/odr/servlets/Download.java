/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import praveen.odr.encrypt.exceptions.NtruException;
import praveen.odr.encypt.OID;
import praveen.odr.encypt.Random;
import praveen.odr.encrypt.NtruEncryptKey;

import praveen.odr.constants.Constants;
import praveen.odr.constants.Queries;
import praveen.odr.dao.impl.ConnectionManagerDAOImpl;

/**
 *
 * @author Praveen Sankarasubramanian
 */
@WebServlet(urlPatterns = {"/download"})
public class Download extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -1158354971869912836L;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static byte ivBytes[];
    private static byte encryptedBuf[];
    private static byte wrappedAESKey[];

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, NtruException, InstantiationException, IllegalAccessException {

        String uniqueFileId = request.getParameter("filelist");
        String path = request.getParameter("myText");
        String u = request.getParameter("userid");
        System.out.println(uniqueFileId);

        Connection con = new ConnectionManagerDAOImpl().getConnection();
       // String sa = "select * from fileplaceing where id='" + uniqueFileId + "'";
        PreparedStatement pr = con.prepareStatement(Queries.SELECT_FILEPLACING_ID);
        pr.setString(1,uniqueFileId);
        ResultSet rs = pr.executeQuery();
        String locationOfFragments = "";
        if (rs.next()) {
            locationOfFragments = rs.getString(2);
        }
        String decrypted = "";
        //PrintWriter writer1 = new PrintWriter("F:/Project/ODR/Project/ODR/web/Download/" + uniqueFileId + ".txt");

        PrintWriter writer1 = new PrintWriter(String.format(Constants.DOWNLOAD_FILE_LOCATION, uniqueFileId));//F:\Project\ODR\Project\ODR\web
        writer1.print("");
        writer1.close();
        // FileWriter writer = new FileWriter("F:/Project/ODR/Project/ODR/web/Download/" + uniqueFileId + ".txt", true);
        FileWriter writer = new FileWriter(String.format(Constants.DOWNLOAD_FILE_LOCATION, uniqueFileId), true);

       
        String[] fragmentLocations = locationOfFragments.split(",");
        int numberOfFragments = fragmentLocations.length;
        System.out.println(numberOfFragments);
        int fragIncrementer = 0;
        for (String fragmentLocation : fragmentLocations) {

            String PART_NAME = uniqueFileId + "data" + fragIncrementer + Constants.TXT_FILE_EXTENSION;

            Path encryptedKeyFIle = Paths.get(Constants.KEY_FILE_LOCATION + uniqueFileId + Constants.TXT_FILE_EXTENSION);
            byte[] bytsOfEKeyFile = Files.readAllBytes(encryptedKeyFIle);
            System.out.println(new String(bytsOfEKeyFile));
            System.out.println(fragmentLocation);
            String partFileName = Constants.FRAGMENT_SERVER_LOCATION
                    .replaceAll(Constants.LOCATION, fragmentLocation)
                    .replaceAll(Constants.PART, PART_NAME);
            Path partFilePath = Paths.get(partFileName);
            encryptedBuf = Files.readAllBytes(partFilePath);

            System.out.println(new String(encryptedBuf));

            String publicKeyFile = "public_Key";
            String privateKeyFile = "private_Key";

            OID oid1 = parseOIDName("ees1499ep1");

            Random prng1 = new Random(bytsOfEKeyFile);

            NtruEncryptKey ntruKeys1 = setupNtruEncryptKey(prng1, oid1, publicKeyFile, privateKeyFile);

            String deCryptedMessage = decryptMessage(ntruKeys1, new String(encryptedBuf), uniqueFileId, fragIncrementer, fragmentLocation);
            System.out.println("Decrypted: " + deCryptedMessage);

            decrypted = decrypted + deCryptedMessage;

            fragIncrementer = fragIncrementer + 1;

        }
        writer.write(decrypted);
        System.out.println(decrypted);

        writer.close();

        //String downloadFilePath = "F:/Project/ODR/Project/ODR/web/Download/" + uniqueFileId + ".txt";
        //String downloadFilePath = String.format(Constants.DOWNLOAD_FILE_LOCATION, uniqueFileId);
        // reads input file from an absolute path
        String downloadFilePath = String.format(Constants.DOWNLOAD_FILE_LOCATION, uniqueFileId);
        File downloadFile = new File(downloadFilePath);
        FileInputStream inStream = new FileInputStream(downloadFile);

        // if you want to use a relative path to context root:
        String relativePath = getServletContext().getRealPath("");
        System.out.println("relativePath = " + relativePath);

        // obtains ServletContext
        ServletContext context = getServletContext();

        // gets MIME type of the file
        String mimeType = context.getMimeType(downloadFilePath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // forces Download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("id", u);
        RequestDispatcher rd = request.getRequestDispatcher("user.jsp");
        rd.forward(request, response);

    }

    static OID parseOIDName(
            String requestedOid) {
        try {
            return OID.valueOf(requestedOid);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid OID! Valid values are:");
            for (OID oid : OID.values()) {
                System.out.println("  " + oid);
            }
            System.exit(1);
        }
        return null;
    }

    public static NtruEncryptKey setupNtruEncryptKey(Random prng, OID oid, String pubFileName, String privFileName) throws IOException, NtruException {
        NtruEncryptKey k = NtruEncryptKey.genKey(oid, prng);

        try (FileOutputStream pubFile = new FileOutputStream(pubFileName)) {
            pubFile.write(k.getPubKey());
        }

        try (FileOutputStream privFile = new FileOutputStream(privFileName)) {
            privFile.write(k.getPrivKey());
        }

        return k;
    }

    public static String decryptMessage(NtruEncryptKey ntruKey, String cipherText, String id, int i, String server) throws NtruException, IOException {

        byte fileContents[] = null;
        try {

            String PART_NAME = id + "data" + i + Constants.TXT_FILE_EXTENSION;
            Path path1 = Paths.get(Constants.KEY_FILE_LOCATION + id + Constants.TXT_FILE_EXTENSION);
            byte[] data = Files.readAllBytes(path1);
            String partFileName = Constants.FRAGMENT_SERVER_LOCATION
                    .replaceAll(Constants.LOCATION, server)
                    .replaceAll(Constants.PART, PART_NAME);
            Path path = Paths.get(partFileName);
            byte[] data1 = Files.readAllBytes(path);
            byte encFileContents[] = data1;
            Random prng1 = new Random(data);
            Path aes = Paths.get(Constants.KEY_FILE_LOCATION + id + "aes" + i + Constants.TXT_FILE_EXTENSION);
            byte[] da = Files.readAllBytes(aes);
            wrappedAESKey = ntruKey.encrypt(da, prng1);
            Path path3 = Paths.get(Constants.KEY_FILE_LOCATION + id + "iv" + i + Constants.TXT_FILE_EXTENSION);
            ivBytes = Files.readAllBytes(path3);

            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            byte wrappedKey[] = wrappedAESKey;
            // Unwrap the AES key
            byte aesKeyBytes[] = ntruKey.decrypt(wrappedKey);
            SecretKeySpec aesKey = new SecretKeySpec(aesKeyBytes, "AES");
            java.util.Arrays.fill(aesKeyBytes, (byte) 0);

            // Decrypt the file contents
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, aesKey, iv);
            fileContents = cipher.doFinal(encFileContents);
        } catch (java.security.GeneralSecurityException e) {
            System.out.println("AES error: " + e);
        }
        return new String(fileContents);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | NtruException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | NtruException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
