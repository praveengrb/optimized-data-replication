package praveen.odr.model;

/**
 *
 * @author Praveen
 */
public class User {
    int id;
    String name;
    String emailAddress;
    String password;    
    boolean admin;
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public void setAdmin(boolean admin){
        this.admin = admin;
    }
    
    public boolean isAdmin(){
        return admin;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName (){
        return this.name;
    }
    
    public void setEmailAddress (String emailAddress){
        this.emailAddress = emailAddress;
    }
     public String getEmailAddress (){
        return this.emailAddress;
    }
     
     public void setPassword(String password){
        this.password = password;
    }
    
    public String getPassword (){
        return this.password;
    }
}
