/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import praveen.odr.constants.Constants;
import praveen.odr.dao.UserManagementDAO;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.Status;
import praveen.odr.model.User;

/**
 *
 * @author Praveen
 */
public class UserManagementDAOImpl implements UserManagementDAO {
    ConnectionManagerDAOImpl connectionManager = new ConnectionManagerDAOImpl();
    @Override
    public boolean isUserExists(String emailId) throws ODRDataAccessException {
        boolean isExists=false;
        try (Connection con = connectionManager.getConnection()) {
            //String sa = "select * from account where emailid='" + emailId + "'";
            String sa = "select * from account where emailid=?";
            PreparedStatement pr = con.prepareStatement(sa);
            pr.setString(1, emailId);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                System.out.println("User already Exists");
                isExists=true;
            }
        } catch (ClassNotFoundException |IllegalAccessException |SQLException | InstantiationException ex) {
            Logger.getLogger(UserManagementDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
           throw new ODRDataAccessException(ex.getMessage(),ex.getCause());
        } 

        return isExists;
    }

    @Override
    public Status registerUser(User user) throws ODRDataAccessException {
        Status status = new Status(true, Constants.SUCCESS);
        PreparedStatement st=null;
        try (Connection con = connectionManager.getConnection()) {
            String sql = "insert into account (name,emailid,password)values(?,?,?)";
            st = con.prepareStatement(sql);
            st.setString(1, user.getName());
            st.setString(2, user.getEmailAddress());
            st.setString(3, user.getPassword());
            st.executeUpdate();
        } catch (ClassNotFoundException |IllegalAccessException |SQLException | InstantiationException ex) {
            Logger.getLogger(UserManagementDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            status.setDone(false);
            status.setStatusMessage(ex.getMessage());
            throw new ODRDataAccessException(ex.getMessage(),ex.getCause());
        }
        return status;
    }
}
