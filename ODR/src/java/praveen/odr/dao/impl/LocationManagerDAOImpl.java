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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import praveen.odr.constants.Queries;
import praveen.odr.dao.LocationManagerDAO;
import praveen.odr.exception.ODRDataAccessException;

/**
 *
 * @author Praveen
 */
public class LocationManagerDAOImpl implements LocationManagerDAO {

    @Override
    public int getLocation() throws ODRDataAccessException {
        int id = 0;
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()){
             Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(Queries.SERVERNODE_ID);
        if (rs.last()) {
            id = rs.getInt("id") + 1;
        }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocationManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
             throw new ODRDataAccessException(ex.getMessage(),ex.getCause());
        }
        return id;
    }

    @Override
    public void createLocation(int disf, int capa, String color) throws ODRDataAccessException {
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            PreparedStatement st1 = con.prepareStatement(Queries.INSERT_SERVERNODE);
            st1.setString(1, disf + "");
            st1.setString(2, capa + "");
            st1.setString(3, color + "");
            st1.executeUpdate();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocationManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(),ex.getCause());
        }

    }
}
