package praveen.odr.encrypt.exceptions;

import praveen.odr.encypt.OID;

/**
 * This exception indicates that the input key blob contains a key that
 * uses an NtruEncrypt or NtruSign parameter set that is not supported
 * by this implementation.
 */
public class ParamSetNotSupportedException extends NtruException
{
    /**
     * Constructs a new exception with the supplied OID as the detail message,
     * formatted as "w.x.y.z".
     */
    public ParamSetNotSupportedException(
        byte oid[])
    {
        super("Ntru key parameter set (" + oidToString(oid) +
              ") is not supported");
    }


    /**
     * Constructs a new exception with the supplied OID's name as
     * the detail message.
     */
    public ParamSetNotSupportedException(
        OID oid)
    {
        super("Ntru key parameter set (" + oid + ") is not supported");
    }


    /**
     * Create a string containing the OID as "w.x.y.z".
     */
    private static String oidToString(
        byte oid[])
    {
        String s = "";
        if (oid.length > 0)
          s += oid[0];
        for (int i=1; i<oid.length; i++)
          s += "." + (0xff & oid[i]);
        return s;
    }
}
