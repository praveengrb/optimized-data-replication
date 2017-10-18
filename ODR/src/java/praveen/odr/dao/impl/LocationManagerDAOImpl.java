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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import praveen.odr.constants.Queries;
import praveen.odr.dao.LocationManagerDAO;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.ServerNode;

/**
 *
 * @author Praveen
 */
public class LocationManagerDAOImpl implements LocationManagerDAO {

    @Override
    public int getLocation() throws ODRDataAccessException {
        int id = 0;
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(Queries.SERVERNODE_ID);
            if (rs.last()) {
                id = rs.getInt("id") + 1;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocationManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(), ex.getCause());
        }
        return id;
    }

    @Override
    public void createLocation(ServerNode node) throws ODRDataAccessException {
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            PreparedStatement st1 = con.prepareStatement(Queries.INSERT_SERVERNODE);
            st1.setString(1, node.getDist());
            st1.setString(2, node.getCapacity());
            st1.setString(3, node.getColor());
             st1.setString(4, node.getNodeName());
            st1.executeUpdate();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocationManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(), ex.getCause());
        }

    }

    @Override
    public void updateLocation(ServerNode node) throws ODRDataAccessException {
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            PreparedStatement preparedStmt = con.prepareStatement(Queries.UPDATE_SERVERNODE_BYID);
            preparedStmt.setString(1, node.getCapacity());
            preparedStmt.setInt(2, node.getId());
            preparedStmt.executeUpdate();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocationManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public ServerNode getLocationById(int id) throws ODRDataAccessException {
        ServerNode serverNode = new ServerNode();
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            PreparedStatement pr1 = con.prepareStatement(Queries.SELECT_SERVERNODE_BYID);
            pr1.setInt(1, id);
            ResultSet rs1 = pr1.executeQuery();
            if (rs1.next()) {
                //select * from servernode
                serverNode.setId(rs1.getInt("id"));
                serverNode.setColor(rs1.getString("color"));
                serverNode.setDist(rs1.getString("dis"));
                serverNode.setCapacity(rs1.getString("capacity"));
                //capacity = Integer.parseInt(rs1.getString(3));
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocationManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(), ex.getCause());
        }
        return serverNode;
    }
    
    
       @Override
    public List<ServerNode> getLocations() throws ODRDataAccessException {
        List<ServerNode> serverNodes = new ArrayList<ServerNode>();
        try (Connection con = new ConnectionManagerDAOImpl().getConnection()) {
            PreparedStatement pr = con.prepareStatement(Queries.SELECT_SERVERNODE);
            ResultSet rs1 = pr.executeQuery();
            while (rs1.next()) {
                //select * from servernode
                ServerNode serverNode = new ServerNode();
                serverNode.setId(rs1.getInt("id"));
                serverNode.setColor(rs1.getString("color"));
                serverNode.setDist(rs1.getString("dis"));
                serverNode.setCapacity(rs1.getString("capacity"));
                serverNode.setNodeName(rs1.getString("node_name"));
                //capacity = Integer.parseInt(rs1.getString(3));
                serverNodes.add(serverNode);
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocationManagerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ODRDataAccessException(ex.getMessage(), ex.getCause());
        }
        return serverNodes;
    }
}
