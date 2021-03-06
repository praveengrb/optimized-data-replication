/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ord;

import com.securityinnovation.jNeo.NtruException;
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

/**
 *
 * @author Praveen Sankarasubramanian
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet(urlPatterns = {"/upload"})
public class Upload extends HttpServlet {

    String workingDir = System.getProperty("user.dir");

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
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
     * @throws com.securityinnovation.jNeo.NtruException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, NtruException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String des = request.getParameter("des");
        String webAppPath = getServletContext().getRealPath(File.separator);
        String path = request.getParameter("myText");
        String u = request.getParameter("userid");
        String ll = path.length() < 2 ? path : path.substring(0, 2);
        path = ll + "//Project//ODR//Project//ODR\\web\\Files\\";//F://Project//ODR//Project//ODR

        System.out.println(webAppPath);
        Part filePart = request.getPart("files");

        String photo = "";

        System.out.println(path);

        File file = new File(path);
        file.mkdir();
        String fileName = getFileName(filePart);

        OutputStream out1 = null;

        InputStream filecontent = null;

        out1 = new FileOutputStream(new File(path + File.separator
                + fileName));

        filecontent = filePart.getInputStream();

        int read = 0;
        final byte[] bytes = new byte[1024];

        while ((read = filecontent.read(bytes)) != -1) {
            out1.write(bytes, 0, read);

            photo = path + fileName;

        }

        try {
            try {
                try {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InstantiationException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }

            Connection con;

            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/odr", "root", "root");

            String hh = ll + "//Project//ODR//Project//ODR//web//Files//" + fileName;
            String sql = "insert into ufile (uid,fname,des,file)values('" + u + "','" + name + "','" + des + "','" + hh + "')";
            Statement st = con.createStatement();
            int s = st.executeUpdate(sql);
            Statement st2 = con.createStatement();
            ResultSet rs = st2.executeQuery("SELECT id FROM ufile");
            int id = 0;
            if (rs.last()) {
                id = rs.getInt("id");
            }
            //  Algorithm for fragment placement
            //Intialization
            ArrayList<String> nameList = new ArrayList<>();
            ArrayList<String> nameList1 = new ArrayList<>();
           // ArrayList<String> fragmentsize = new ArrayList<>();
            ArrayList<String> color = new ArrayList<>();
            //ArrayList<String> centrality1 = new ArrayList<>();
            color.add("open color");
            color.add("close color");
            File willBeRead = new File(hh);
            int FILE_SIZE = (int) willBeRead.length();
            System.out.println(FILE_SIZE);
            int filesize = 1;
            if (FILE_SIZE > 10) {
                filesize = FILE_SIZE / 10;
            }

            Centrality.fragmentation(15, id);

            nameList = ord.Fragmentation.readAndFragment(hh, filesize, id);
            String placeing = "";
            String replaceing = "";
            for (int i = 0; i < nameList.size(); i++) {
                if (nameList.size() - 1 == i) {
                    placeing = placeing + nameList.get(i);
                } else {
                    placeing = placeing + nameList.get(i) + ",";
                }

            }
            nameList1 = ord.Fragmentation.readAndFragment1(hh, filesize, id);
            for (int i = 0; i < nameList1.size(); i++) {
                if (nameList1.size() - 1 == i) {
                    replaceing = replaceing + nameList1.get(i);
                } else {
                    replaceing = replaceing + nameList1.get(i) + ",";
                }

            }

            String query = "update  fileplaceing set location = '" + placeing + "' , replacing = '" + replaceing + "' where id ='" + id + "'";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.executeUpdate();
            // request.setAttribute("id",u);
            //   RequestDispatcher rd=request.getRequestDispatcher("user.jsp");
            //   rd.forward(request, response);

        } finally {
            out.close();

        }

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
            try {
                try {
                    processRequest(request, response);
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NtruException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                try {
                    processRequest(request, response);
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NtruException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
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
