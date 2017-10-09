/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ord;

import com.securityinnovation.jNeo.NtruException;
import com.securityinnovation.jNeo.OID;
import com.securityinnovation.jNeo.Random;
import com.securityinnovation.jNeo.ntruencrypt.NtruEncryptKey;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
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

/**
 *
 * @author Praveen Sankarasubramanian
 */
@WebServlet(urlPatterns = {"/download"})
public class Download extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException, NtruException {

        String name = request.getParameter("filelist");

        String path = request.getParameter("myText");
        String u = request.getParameter("userid");
        System.out.println(name);

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }

        Connection con;

        con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/odr", "root", "root");
        String sa = "select * from fileplaceing where id='" + name + "'";
        PreparedStatement pr = con.prepareStatement(sa);
        ResultSet rs = pr.executeQuery();
        String kk = "";
        String decrypted = "";
        PrintWriter writer1 = new PrintWriter("F:\\Project\\ODR\\Project\\ODR\\web\\Download\\" + name + ".txt");//F:\Project\ODR\Project\ODR\web
        writer1.print("");
        writer1.close();
        FileWriter writer = new FileWriter("F:\\Project\\ODR\\Project\\ODR\\web\\Download\\" + name + ".txt", true);

        if (rs.next()) {
            kk = rs.getString(2);
        }
        String[] hj = kk.split(",");
        int size = hj.length;
        System.out.println(size);
        int i = 0;
        for (String k : hj) {

            String PART_NAME = name + "data" + i + ".txt";

            Path path1 = Paths.get("F:\\Project\\ODR\\Project\\ODR//Key//" + name + ".txt");
            byte[] data = Files.readAllBytes(path1);
            System.out.println(new String(data));
            System.out.println(k);
            Path path3 = Paths.get("F:\\Project\\ODR\\Project\\ODR\\web\\Server\\" + k + "\\" + PART_NAME);
            encryptedBuf = Files.readAllBytes(path3);

            System.out.println(new String(encryptedBuf));

            String pubkeyFile1 = "public_Key";
            String privkeyFile1 = "private_Key";

            OID oid1 = parseOIDName("ees1499ep1");

            Random prng1 = new Random(data);

            NtruEncryptKey ntruKeys1 = setupNtruEncryptKey(prng1, oid1, pubkeyFile1, privkeyFile1);

            String k1 = decryptMessage(ntruKeys1, new String(encryptedBuf), name, i, k);
            System.out.println("Decrypted: " + k1);

            decrypted = decrypted + k1;

            i = i + 1;

        }
        writer.write(decrypted);
        System.out.println(decrypted);

        writer.close();

        String param1 = "F:\\Project\\ODR\\Project\\ODR\\web\\Download\\" + name + ".txt";
        // reads input file from an absolute path
        String filePath = param1;
        File downloadFile = new File(filePath);
        FileInputStream inStream = new FileInputStream(downloadFile);

        // if you want to use a relative path to context root:
        String relativePath = getServletContext().getRealPath("");
        System.out.println("relativePath = " + relativePath);

        // obtains ServletContext
        ServletContext context = getServletContext();

        // gets MIME type of the file
        String mimeType = context.getMimeType(filePath);
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

            String PART_NAME = id + "data" + i + ".txt";
            Path path1 = Paths.get("F:\\Project\\ODR\\Project\\ODR//Key//" + id + ".txt");
            byte[] data = Files.readAllBytes(path1);
            Path path = Paths.get("F:\\Project\\ODR\\Project\\ODR\\web\\Server\\" + server + "\\" + PART_NAME);
            byte[] data1 = Files.readAllBytes(path);
            byte encFileContents[] = data1;
            Random prng1 = new Random(data);
            Path aes = Paths.get("F:\\Project\\ODR\\Project\\ODR//Key//" + id + "aes" + i + ".txt");
            byte[] da = Files.readAllBytes(aes);
            wrappedAESKey = ntruKey.encrypt(da, prng1);
            Path path3 = Paths.get("F:\\Project\\ODR\\Project\\ODR//Key//" + id + "iv" + i + ".txt");
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
        } catch (ClassNotFoundException | SQLException | NtruException ex) {
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
        } catch (ClassNotFoundException | SQLException | NtruException ex) {
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
