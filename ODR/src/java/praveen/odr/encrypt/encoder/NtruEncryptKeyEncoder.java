package praveen.odr.encrypt.encoder;

import praveen.odr.encrypt.exceptions.FormatNotSupportedException;
import praveen.odr.encrypt.exceptions.ParamSetNotSupportedException;
import praveen.odr.encrypt.math.FullPolynomial;
import praveen.odr.encrypt.KeyParams;


public interface NtruEncryptKeyEncoder
{
    /**
     * Encode a public key as a byte array.
     * @param keyParams
     * @param h
     * @return 
     */
    public byte[] encodePubKey(
        KeyParams      keyParams,
        FullPolynomial h);

    /**
     * Encode a private key as a byte array.
     * @param keyParams
     * @param h
     * @param f
     * @return 
     */
    public byte[] encodePrivKey(
        KeyParams      keyParams,
        FullPolynomial h,
        FullPolynomial f);


    /**
     * Parse a public or private key blob.
     * @param keyBlob
     * @return 
     * @throws praveen.odr.encrypt.exceptions.FormatNotSupportedException 
     * @throws praveen.odr.encrypt.exceptions.ParamSetNotSupportedException 
     */
    public RawKeyData decodeKeyBlob(
        byte keyBlob[])
        throws FormatNotSupportedException, ParamSetNotSupportedException;
}
