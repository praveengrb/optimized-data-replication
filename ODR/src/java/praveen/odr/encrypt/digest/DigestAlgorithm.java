package praveen.odr.encrypt.digest;


/**
 * This class provides an enumeration of hash algorithms that can
 * be used throughout the code. Each enumeration has a utility
 * function for creating a new instance of a Digest object
 * for that algorithm.
 */
public enum DigestAlgorithm
{
    /**
     * The enum for SHA1.
     */
    sha1(Sha1.class),

    /**
     * The enum for SHA256.
     */
    sha256(Sha256.class);



    /**
     * Constructor.
     */
    private DigestAlgorithm(
        Class _clss)
    {
        clss = _clss;
    }

    /**
     * The class used to generate objects
     */
    private Class clss;

    /**
     * Return the byte array identifying the OID.
     */
    public Digest newInstance()
    {
        try {return (Digest) clss.newInstance();}
        // By construction this shouldn't happen,
        // except perhaps an out-of-memory error.
        catch (Exception e) {return null;}
    }
};
