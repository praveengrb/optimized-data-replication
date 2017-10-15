/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.dao;

import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.Status;

/**
 *
 * @author Praveen
 * @param <T>
 */
public interface UserManagementDAO<T> {

    public Status<T> isUserExists(T user) throws ODRDataAccessException;

    public Status<T> isValidUser(T user) throws ODRDataAccessException;

    public Status<T> registerUser(T user) throws ODRDataAccessException;

}
