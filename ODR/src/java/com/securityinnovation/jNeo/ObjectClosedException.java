package com.securityinnovation.jNeo;

/**
 * This exception indicates that a key was used after it was closed.
 */
public class ObjectClosedException extends NtruException
{
    /**
     * Constructs a new exception a default message.
     */
    public ObjectClosedException()
    {
        super("Object has been closed");
    }
}
