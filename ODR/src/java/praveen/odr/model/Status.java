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
public class Status {
    private boolean done;
    private String statusMessage;
    
    public Status(boolean done, String statusMessage){
        this.setDone(done);
        this.setStatusMessage(statusMessage);
    }

    public final void setDone(boolean done) {
       this.done=done;
    }

    public final void setStatusMessage(String statusMessage) {
       this.statusMessage = statusMessage;
    }
    public final String getStatusMessage(){
        return this.statusMessage;
    }
    public final boolean isDone(){
        return this.done;
    }
    
}
