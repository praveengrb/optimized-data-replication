/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.dao;

import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.FilePlacing;

/**
 *
 * @author Praveen
 */
public interface FilePlacementDAO {
     public void insertFilePlacement(FilePlacing filePlacement) throws ODRDataAccessException;
}
