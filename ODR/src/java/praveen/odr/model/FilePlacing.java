/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.model;

/**
 *
 * @author Praveen
 */
public class FilePlacing {
    private String id;
    private String location;
    private String replacing;

    public FilePlacing() {
        super();
    }

    public FilePlacing(String id, String location, String replacing) {
        this.id = id;
        this.location = location;
        this.replacing = replacing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReplacing() {
        return replacing;
    }

    public void setReplacing(String replacing) {
        this.replacing = replacing;
    }

    
}
