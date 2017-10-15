/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.dao;

import praveen.odr.exception.ODRDataAccessException;

/**
 *
 * @author Praveen
 */
public interface UniqueFileManagerDAO {
     public void insertUniqueFile(String uniqueId,String name,String description,String fileName) throws ODRDataAccessException;

    public int getLUID()throws ODRDataAccessException;

    public void updateFilePlacing(String placing, String replacing, String id) throws ODRDataAccessException;
}
