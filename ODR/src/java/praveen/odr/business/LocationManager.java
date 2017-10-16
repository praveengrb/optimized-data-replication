/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.business;

import praveen.odr.dao.LocationManagerDAO;
import praveen.odr.dao.impl.LocationManagerDAOImpl;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.ServerNode;

/**
 *
 * @author Praveen
 */
public class LocationManager {
    LocationManagerDAO locationManagerDAO = new LocationManagerDAOImpl();
    public int getLocation() throws  ODRDataAccessException{
       return locationManagerDAO.getLocation();
    }

    public void createServer(ServerNode node) throws ODRDataAccessException{
        locationManagerDAO.createLocation(node);
    }
    
}
