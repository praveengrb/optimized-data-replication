package praveen.odr.encrypt.exceptions;

/**
 * This exception indicates that the header tag of an input key blob 
 * is not recognized.
 */
public class FormatNotSupportedException extends NtruException
{
    /**
     * Constructs a new exception with the supplied key blob tag as
     * the detail message.
     */
    public FormatNotSupportedException(
        byte tag)
    {
        super("Blob format (" + Integer.toHexString(0xff&tag) + 
              ") is not supported");
    }
}
