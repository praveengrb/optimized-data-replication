package com.securityinnovation.jNeo.digest;


/**
 * <p>This class holds utility methods for manipulating
 * <code>Digest</code> objects. These methods are public to
 * the com.securityinnovation.jNeo.digest package.
 */
public class DigestUtil
{
    /**
     * Create an instance of the specified Hash subclass using the 
     * default constructor.
     */
    public static Digest create(
        Class clss)
    {
        // The newInstance can fail if clss is not a subclass of Digest
        // or the clss is missing the default constructor.
        try {return (Digest) clss.newInstance();}
        catch (Exception e) {return null;}
    }
}

