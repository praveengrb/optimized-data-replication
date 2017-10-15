package praveen.odr.encrypt.exceptions;

/**
 * This exception indicates that a decryption operation failed. This
 * may be because the ciphertext has been corrupted or because the
 * wrong key was used. This exception is not used if the corrupt
 * ciphertext prevents the decryption calculation from even being
 * performed (for example, if the NtruEncrypt ciphertext is the wrong
 * length). It is used only if the decryption can proceed but fails
 * due to an internal error check, such as a CCM MAC verification
 * failure or an NtruEncrypt decryption candidate having the wrong
 * format.
 */
public class DecryptionFailureException extends NtruException
{
    /**
     * Constructs a new exception a default message.
     */
    public DecryptionFailureException()
    {
        super("Input ciphretext is not encrypted with this key");
    }
}
