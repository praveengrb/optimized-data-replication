package com.securityinnovation.jNeo;

/**
 * This exception indicates that the plaintext input to an
 * encryption operation cannot be encrypted because of length
 * restrictions.
 */
public class PlaintextBadLengthException extends NtruException
{
    /**
     * Constructs a new exception a default message.
     *
     * @param msgLen the data length supplied to the encrypt routine.
     * @param maxMsgLen the actual maximum allowed plaintext length.
     */
    public PlaintextBadLengthException(
        int msgLen,
        int maxMsgLen)
    {
        super("Input plaintext too long (" + msgLen + 
              " bytes, should be less than " + maxMsgLen + " bytes)");
    }
}
