package praveen.odr.dao;

import java.util.List;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.ServerNode;

/**
 *
 * @author Praveen
 */
public interface LocationManagerDAO {

    public int getLocation() throws ODRDataAccessException;

    public void createLocation(ServerNode node) throws ODRDataAccessException;
    
    /**
     *
     * @param node
     * @throws ODRDataAccessException
     */
    public void updateLocation(ServerNode node) throws ODRDataAccessException;
    
    public ServerNode getLocationById(int id) throws ODRDataAccessException;
    
    public List<ServerNode> getLocations() throws ODRDataAccessException; 
}
