/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.business;

import java.util.logging.Level;
import java.util.logging.Logger;
import praveen.odr.constants.Constants;
import praveen.odr.dao.UserManagementDAO;
import praveen.odr.dao.impl.UserManagementDAOImpl;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.Status;
import praveen.odr.model.User;

/**
 *
 * @author Praveen
 */
public class UserManagement {
    private UserManagementDAO userManagementDAO = new UserManagementDAOImpl();
    public Status registerUser(User user){
        Status status = new Status(false,Constants.SUCCESS);
        try {
            userManagementDAO.registerUser(user);
        } catch (ODRDataAccessException ex) {
            Logger.getLogger(UserManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public boolean isUserExists(String emailAddress) throws ODRDataAccessException{
       return userManagementDAO.isUserExists(emailAddress);
    }
    
}
