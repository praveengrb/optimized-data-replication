package praveen.odr.servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import praveen.odr.business.UserManagement;

import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.Status;
import praveen.odr.model.User;

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
            UserManagement userManagement = new UserManagement();
            User user = new User();
            user.setEmailAddress(eid);
            user.setPassword(pass);
            Status<User> userDetails = userManagement.isValidUser(user);
            if (eid.equalsIgnoreCase("Admin@gmail.com") && pass.equalsIgnoreCase("Admin")) {
            //else if (eid.equalsIgnoreCase("Admin@gmail.com") && pass.equalsIgnoreCase("Admin")) {
                request.setAttribute("id", "Welcome Admin");
                RequestDispatcher rd = request.getRequestDispatcher("Admin.html");
                rd.forward(request, response);

            } else if (userDetails.isDone()) {
                request.setAttribute("id", user.getEmailAddress());
                RequestDispatcher rd = request.getRequestDispatcher("user.jsp");
                rd.forward(request, response);
            } 
          else {
                request.setAttribute("mess", "Invalid Account");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);

            }
        }   catch (ODRDataAccessException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
