/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.servlets;

import praveen.odr.business.Centrality;
import praveen.odr.encrypt.exceptions.NtruException;

import praveen.odr.constants.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import praveen.odr.business.FilePlacingManager;
import praveen.odr.business.UniqueFileManager;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.FilePlacing;
import praveen.odr.model.UFile;

/**
 *
 * @author Praveen Sankarasubramanian
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
@WebServlet(urlPatterns = {"/upload"})
public class FileUpload extends HttpServlet {

    static final Logger log = Logger.getLogger(FileUpload.class.getName());
    UniqueFileManager uniqueFileManager = new UniqueFileManager();
    /**
     *
     */
    private static final long serialVersionUID = -5078537553022154818L;
    String workingDir = System.getProperty("user.dir");

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws praveen.odr.encrypt.exceptions.NtruException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, NtruException,
            InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String description = request.getParameter("des");
        String webAppPath = getServletContext().getRealPath(File.separator);
        String uniqueId = request.getParameter("userid");
        Part filePart = request.getPart("files");
        String fileName = getFileName(filePart);
        String backupLocation = Constants.FILE_BKP_LOCATION;
        String backupFileName = Constants.FILE_BKP_LOCATION + fileName;
        System.out.println(webAppPath);
        //String photo = "";
        System.out.println(backupLocation);
        File file = new File(backupLocation);
        file.mkdir();
        //InputStream filecontent = null;
        OutputStream backUpFileOPStream = new FileOutputStream(new File(backupFileName));
        InputStream copyToBackupFile = filePart.getInputStream();
        int read = 0;
        final byte[] bytes = new byte[1024];

        while ((read = copyToBackupFile.read(bytes)) != -1) {
            backUpFileOPStream.write(bytes, 0, read);
            //photo = backupLocation + fileName;
        }

        try {
            File willBeRead = new File(backupFileName);
            int FILE_SIZE = (int) willBeRead.length();
            System.out.println(FILE_SIZE);
            int filesize = 1;
            if (FILE_SIZE > 10) {
                filesize = FILE_SIZE / 10;
            }
            uniqueFileManager.insertUniqueFile(new UFile(uniqueId, name, description, backupFileName));
            int fileId = uniqueFileManager.getLUID();
            Centrality.fragmentation(15, fileId);

            ArrayList<String> locationList = praveen.odr.business.Fragmentation.readAndFragment(backupFileName, filesize, fileId);
            ArrayList<String> replacementList = praveen.odr.business.Fragmentation.readAndFragment1(backupFileName, filesize, fileId);
            if (locationList != null && replacementList != null) {
                String location = "";
                String replacing = "";
                for (int i = 0; i < locationList.size(); i++) {
                    location = (locationList.size() - 1 == i)
                            ? (location + locationList.get(i))
                            : (location + locationList.get(i) + ",");

                }

                for (int i = 0; i < replacementList.size(); i++) {
                    replacing = (replacementList.size() - 1 == i)
                            ? (replacing + replacementList.get(i))
                            : (replacing + replacementList.get(i) + ",");
                }
                //String fileId, String location, String replacing
                new FilePlacingManager().updateFilePlacing(new FilePlacing(fileId + "", location, replacing));
            }
        } catch (ODRDataAccessException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on
    // the + sign on the left to edit the code.">
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
        } catch (NtruException | InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (NtruException | InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
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
