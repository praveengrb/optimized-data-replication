/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.dao;

import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.Status;
import praveen.odr.model.User;

/**
 *
 * @author Praveen
 */
public interface UserManagementDAO {

    public boolean isUserExists(String emailId) throws ODRDataAccessException;

    public Status registerUser(User user) throws ODRDataAccessException;

}
