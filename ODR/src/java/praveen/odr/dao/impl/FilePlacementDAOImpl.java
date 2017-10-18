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
    @Override
    public FilePlacing getFilePlacedById(String id)throws ODRDataAccessException{
        FilePlacing filePlacement = new FilePlacing();
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            PreparedStatement st = con.prepareStatement(Queries.SELECT_FILEPLACING_ID);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                //id,location,replacing
                filePlacement.setId(rs.getString("id"));
                filePlacement.setLocation(rs.getString("location"));
                filePlacement.setReplacing(rs.getString("replacing"));
            //locationOfFragments = rs.getString(2);
            }
         } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocationManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(), ex.getCause());
        }
        return filePlacement;
    }
    
    
    @Override
    public void updateFilePlacing(FilePlacing filePlacing) throws ODRDataAccessException {
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            PreparedStatement preparedStmt = con.prepareStatement(Queries.UPDATE_FILEPLACING_BYID);
            preparedStmt.setString(1, filePlacing.getLocation());
            preparedStmt.setString(2, filePlacing.getReplacing());
            preparedStmt.setString(3, filePlacing.getId());
            preparedStmt.executeUpdate();
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(UniqueFileManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(), ex.getCause());
        }

    }
}
