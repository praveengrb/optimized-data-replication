/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import praveen.odr.constants.Queries;
import praveen.odr.dao.FilePlacementDAO;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.FilePlacing;

/**
 *
 * @author Praveen
 */
public class FilePlacementDAOImpl implements FilePlacementDAO {
    @Override
    public void insertFilePlacement(FilePlacing filePlacement) throws ODRDataAccessException {
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            //String sql = "insert into fileplaceing (id,location,replacing)values('" + fid + "','" + placeing + "','" + replaceing + "')";
            PreparedStatement st = con.prepareStatement(Queries.INSERT_FILEPLACING);
            //int size, int fid,String placeing = "";String replaceing = "";
            st.setString(1, filePlacement.getId());
            st.setString(2, filePlacement.getLocation());
            st.setString(3, filePlacement.getReplacing());
            st.executeUpdate();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocationManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(), ex.getCause());
        }
    }
}
