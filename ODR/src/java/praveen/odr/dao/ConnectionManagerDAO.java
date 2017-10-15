/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Praveen
 */
public interface ConnectionManagerDAO {
     public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
}
