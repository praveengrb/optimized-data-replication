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
import praveen.odr.constants.Queries;
import praveen.odr.dao.mapper.UserDAOMapper;
import praveen.odr.dao.UserManagementDAO;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.Status;
import praveen.odr.model.User;

/**
 *
 * @author Praveen
 */
public class UserManagementDAOImpl implements UserManagementDAO<User> {
    ConnectionManagerDAOImpl connectionManager = new ConnectionManagerDAOImpl();
    @Override
    public Status<User> isUserExists(User user) throws ODRDataAccessException {
        Status<User> status = new Status(false, Constants.FAILED);
        try (Connection con = connectionManager.getConnection()) {           
            PreparedStatement pr = con.prepareStatement(Queries.SELECT_USER_BY_EMAIL);
            pr.setString(1, user.getEmailAddress());
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                
                user.setName(rs.getString(UserDAOMapper.NAME));
                user.setEmailAddress(rs.getString(UserDAOMapper.EMAIL_ADDRESS));
                user.setAdmin(rs.getBoolean(UserDAOMapper.ADMINPERSON));
                status.setDone(true);
                status.setResultObject(user);
                status.setStatusMessage("User already Exists");
                System.out.println(status.getStatusMessage());
                
            }
        } catch (ClassNotFoundException |IllegalAccessException |SQLException | InstantiationException ex) {
            Logger.getLogger(UserManagementDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            status.setDone(false);
            status.setStatusMessage(ex.getMessage());            
           throw new ODRDataAccessException(ex.getMessage(),ex.getCause());
        } 

        return status;
    }
    
    
    
   @Override
    public Status<User> isValidUser(User user) throws ODRDataAccessException {
        Status<User> status = new Status(false, Constants.FAILED);
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement pr = con.prepareStatement(Queries.SELECT_USER_BY_CREDENTIALS);
            pr.setString(1, user.getEmailAddress());
            pr.setString(2, user.getPassword());
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                user.setName(rs.getString(UserDAOMapper.NAME));
                user.setEmailAddress(rs.getString(UserDAOMapper.EMAIL_ADDRESS));
                user.setAdmin(rs.getBoolean(UserDAOMapper.ADMINPERSON));
                status.setDone(true);
                status.setResultObject(user);
                status.setStatusMessage("User is valid");
                System.out.println(status.getStatusMessage());
            }
        } catch (ClassNotFoundException |IllegalAccessException |SQLException | InstantiationException ex) {
            Logger.getLogger(UserManagementDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            status.setDone(false);
            status.setStatusMessage(ex.getMessage()); 
           throw new ODRDataAccessException(ex.getMessage(),ex.getCause());
        } 

        return status;
    }
    

    @Override
    public Status<User> registerUser(User user) throws ODRDataAccessException {
        Status<User> status = new Status(true, Constants.SUCCESS);
        PreparedStatement st=null;
        try (Connection con = connectionManager.getConnection()) {
            st = con.prepareStatement(Queries.INSERT_USER);
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
