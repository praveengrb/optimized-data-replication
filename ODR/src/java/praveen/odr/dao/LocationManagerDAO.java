package praveen.odr.dao;

import praveen.odr.exception.ODRDataAccessException;

/**
 *
 * @author Praveen
 */
public interface LocationManagerDAO {

    public int getLocation() throws ODRDataAccessException;

    public void createLocation(int disf, int capa, String color) throws ODRDataAccessException;
}
