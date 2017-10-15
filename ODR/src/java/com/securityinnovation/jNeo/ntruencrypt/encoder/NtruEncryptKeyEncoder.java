package com.securityinnovation.jNeo.ntruencrypt.encoder;

import com.securityinnovation.jNeo.FormatNotSupportedException;
import com.securityinnovation.jNeo.ParamSetNotSupportedException;
import com.securityinnovation.jNeo.math.FullPolynomial;
import com.securityinnovation.jNeo.ntruencrypt.KeyParams;


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
     * @throws com.securityinnovation.jNeo.FormatNotSupportedException 
     * @throws com.securityinnovation.jNeo.ParamSetNotSupportedException 
     */
    public RawKeyData decodeKeyBlob(
        byte keyBlob[])
        throws FormatNotSupportedException, ParamSetNotSupportedException;
}
