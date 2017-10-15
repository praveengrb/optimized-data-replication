/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.exception;

/**
 *
 * @author Praveen
 */
public class ODRDataAccessException extends Exception {

    public ODRDataAccessException() {
        super();
    }

    public ODRDataAccessException(String message, Throwable ex) {
        super(message, ex);
    }
    public ODRDataAccessException(String message) {
        super(message);
    }

    public ODRDataAccessException(Throwable ex) {
        super(ex);
    }

}
