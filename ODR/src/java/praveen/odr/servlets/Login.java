package praveen.odr.servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import praveen.odr.constants.Constants;

/**
 *
 * @author Praveen Sankarasubramanian
 */
@WebServlet(urlPatterns = {"/login1"})
public class Login extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3669311768403445354L;

	/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String eid = request.getParameter("email");

        String pass = request.getParameter("pass");
        /* TODO output your page here. You may use following sample code. */
        try {
            try {
                try {
                    Class.forName(Constants.DRIVER_NAME).newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }

            Connection con = (Connection) DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
					Constants.DATABASE_PASSWORD);
            String sa = "select * from account where emailid='" + eid + "' and password='" + pass + "'";
            PreparedStatement pr = con.prepareStatement(sa);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                request.setAttribute("id", eid);
                RequestDispatcher rd = request.getRequestDispatcher("user.jsp");
                rd.forward(request, response);
            } else if (eid.equalsIgnoreCase("Admin@gmail.com") && pass.equalsIgnoreCase("Admin")) {
                request.setAttribute("id", "Welcome Admin");
                RequestDispatcher rd = request.getRequestDispatcher("Admin.html");
                rd.forward(request, response);

            } else {
                request.setAttribute("mess", "Invalid Account");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);

            }
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
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
