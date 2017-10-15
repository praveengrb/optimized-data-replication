package com.securityinnovation.jNeo;

/**
 * This class is the base class for all exceptions thrown by the jNeo
 * toolkit except for standard exceptions such as NullPointerException
 * or IllegalArgumentException for invalid array bounds or lengths.
 */
public class NtruException extends Exception
{
    public NtruException(String msg)
    {
        super(msg);
    }
}
