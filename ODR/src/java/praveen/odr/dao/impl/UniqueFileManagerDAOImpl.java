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
import praveen.odr.dao.UniqueFileManagerDAO;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.UFile;

/**
 *
 * @author Praveen
 */
public class UniqueFileManagerDAOImpl implements UniqueFileManagerDAO {

    @Override
    public void insertUniqueFile(UFile ufile) throws ODRDataAccessException {
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            PreparedStatement st = con.prepareStatement(Queries.INSERT_UNIQUEFILE);
            st.setString(1, ufile.getUid());
            st.setString(2, ufile.getFname());
            st.setString(3, ufile.getDescription());
            st.setObject(4, ufile.getFile());
            st.executeUpdate();
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(UniqueFileManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public int getLUID() throws ODRDataAccessException {
        int id = 0;
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            Statement st2 = con.createStatement();
            ResultSet rs = st2.executeQuery(Queries.ID_UIDFILE);
            if (rs.last()) {
                id = rs.getInt("id");
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(UniqueFileManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(), ex.getCause());
        }
        return id;
    }

}
