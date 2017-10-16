/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.constants;

/**
 *
 * @author Praveen
 */
public class Queries {

    public static final String INSERT_USER = "insert into account (name,emailid,password)values(?,?,?)";

    public static final String SELECT_USER_BY_EMAIL = "select name,emailid,password,adminperson from account where emailid=?";

    public static final String SELECT_USER_BY_CREDENTIALS = "select name,emailid,password,adminperson from account where emailid=? and password=?";

    public static final String SELECT_SERVERNODE = "select * from servernode";

    public static final String SELECT_SERVERNODE_BYID = SELECT_SERVERNODE + " where id=?";

    public static final String UPDATE_SERVERNODE_BYID = "update servernode set capacity = ? where id =?";

    public static final String INSERT_FILEPLACING = "insert into fileplaceing (id,location,replacing)values(?,?,?)";

    public static final String SELECT_FILEPLACING_ID = "select * from fileplaceing where id=?";
    
    public static String SERVERNODE_ID = "SELECT id FROM servernode";
    
    public static String INSERT_SERVERNODE = "insert into servernode (dis,capacity,color)values(?,?,?)";
    
    public static String INSERT_UNIQUEFILE = "insert into ufile (uid,fname,des,file)values(?,?,?,?)"; 
   
    public static String ID_UIDFILE="SELECT id FROM ufile";
    
    public static String UPDATE_FILEPLACING_BYID = "update  fileplaceing set location = ? , replacing = ? where id =?";
    
    public static String SELECT_UFILE_BYID="select * from ufile where uid=?";

}
