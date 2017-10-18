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
public class UFile {
    private int id;
    private String uid;
    private String fname;
    private String description;
    private String file;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public UFile() {
        super();
    }

    public UFile(String uid, String fname,String description, String file) {
        this.uid = uid;
        this.fname = fname;
        this.uid = uid;
        this.description = description;
        this.file = file;
    }
    
    



    public void setFile(String file) {
        this.file = file;
    }
}
