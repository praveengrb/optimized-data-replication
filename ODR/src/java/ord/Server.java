package ord;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
@WebServlet(urlPatterns = { "/server1" })
public class Server extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3559178625372853952L;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws java.sql.SQLException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String dis = request.getParameter("dis");
		String cap = request.getParameter("cap");

		try {
			try {
				try {
					Class.forName(Constants.DRIVER_NAME).newInstance();
				} catch (InstantiationException | IllegalAccessException ex) {
					Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
				}
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}

			Connection con = (Connection) DriverManager.getConnection(Constants.DATABASE_URL,
					Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT id FROM servernode");
			int id = 0;
			if (rs.last()) {
				id = rs.getInt("id") + 1;
			}
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(100);
			int disf = Integer.parseInt(dis) + randomInt;
			int capa = Integer.parseInt(cap) + randomInt;
			String color = "open color";
			File file = new File("F:\\Project\\ODR\\Project\\ODR\\web\\Server\\" + id);
			if (!file.exists()) {
				if (file.mkdir()) {

				}
			}
			String sql = "insert into servernode (dis,capacity,color)values('" + disf + "','" + capa + "','" + color
					+ "')";
			Statement st1 = con.createStatement();
			st1.executeUpdate(sql);
			response.sendRedirect("server.jsp");
		} finally {
			out.close();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on
	// the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (SQLException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (SQLException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
