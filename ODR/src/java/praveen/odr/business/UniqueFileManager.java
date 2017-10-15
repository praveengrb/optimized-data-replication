/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.business;

import praveen.odr.dao.UniqueFileManagerDAO;
import praveen.odr.dao.impl.UniqueFileManagerDAOImpl;
import praveen.odr.exception.ODRDataAccessException;

/**
 *
 * @author Praveen
 */
public class UniqueFileManager {

    UniqueFileManagerDAO uniqueDAO = new UniqueFileManagerDAOImpl();

    public void insertUniqueFile(String uniqueId, String name, String description, String fileName) throws ODRDataAccessException {
        uniqueDAO.insertUniqueFile(uniqueId, name, description, fileName);
    }

    public int getLUID() throws ODRDataAccessException{
        return uniqueDAO.getLUID();
    }
    
    public void updateFilePlacing(String placing,String replacing, String id)throws ODRDataAccessException{
         uniqueDAO.updateFilePlacing(placing,replacing,id);
    }
}
