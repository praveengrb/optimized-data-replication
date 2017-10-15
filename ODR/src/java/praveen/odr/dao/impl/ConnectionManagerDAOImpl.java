/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.dao.impl;

import praveen.odr.dao.ConnectionManagerDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import praveen.odr.constants.Constants;
import praveen.odr.servlets.Register;

/**
 *
 * @author Praveen
 */
public class ConnectionManagerDAOImpl implements ConnectionManagerDAO {

    @Override
    public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            try {
                Class.forName(Constants.DRIVER_NAME).newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        try {
            con = (Connection) DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(UserManagementDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return con;
    }
}
