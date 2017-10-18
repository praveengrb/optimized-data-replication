/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.business;

import praveen.odr.dao.UniqueFileManagerDAO;
import praveen.odr.dao.impl.UniqueFileManagerDAOImpl;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.UFile;

/**
 *
 * @author Praveen
 */
public class UniqueFileManager {

    UniqueFileManagerDAO uniqueDAO = new UniqueFileManagerDAOImpl();

    public void insertUniqueFile(UFile ufile) throws ODRDataAccessException {
        uniqueDAO.insertUniqueFile(ufile);
    }

    public int getLUID() throws ODRDataAccessException{
        return uniqueDAO.getLUID();
    }
    
 
}
