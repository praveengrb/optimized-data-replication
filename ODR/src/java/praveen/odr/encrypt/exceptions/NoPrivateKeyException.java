package praveen.odr.encrypt.exceptions;

/**
 * This exception indicates that a private key operation (decrypt)
 * was attempted on a key that only contains public key material.
 */
public class NoPrivateKeyException extends NtruException
{
    /**
     * Constructs a new exception a default message.
     */
    public NoPrivateKeyException()
    {
        super("The key can only be used for public key operations");
    }
}
