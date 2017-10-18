/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.business;

import praveen.odr.dao.FilePlacementDAO;
import praveen.odr.dao.impl.FilePlacementDAOImpl;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.FilePlacing;

/**
 *
 * @author Praveen
 */
public class FilePlacingManager {

    FilePlacementDAO filePlacementsDAO = new FilePlacementDAOImpl();

    public FilePlacing getFilePlacedById(String id) throws ODRDataAccessException {
        return filePlacementsDAO.getFilePlacedById(id);
    }

    public void updateFilePlacing(FilePlacing fplacing) throws ODRDataAccessException {
        filePlacementsDAO.updateFilePlacing(fplacing);
    }
}
