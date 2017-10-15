package praveen.odr.encrypt.exceptions;

/**
 * This exception indicates that an input ciphertext was the
 * wrong length based on the key parameter set.
 */
public class CiphertextBadLengthException extends NtruException
{
    /**
     * Constructs a new exception a default message.
     *
     * @param ctLen the length of the input ciphertext.
     * @param reqCtLen the required length of the input ciphertext.
     */
    public CiphertextBadLengthException(
        int ctLen,
        int reqCtLen)
    {
        super("Input ciphertext is wrong length (is " + ctLen + 
              "bytes, should be " + reqCtLen + " bytes)");
    }
}
