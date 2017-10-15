package com.securityinnovation.jNeo.ntruencrypt.encoder;

import com.securityinnovation.jNeo.math.FullPolynomial;
import com.securityinnovation.jNeo.ntruencrypt.KeyParams;

/**
 * This class holds the result of parsing a key blob. The
 * result contains the parameter set, the public key, and the
 * private key (which will be null if the input blob was a public
 * key blob).
 */
public class RawKeyData
{
    public KeyParams      keyParams;
    public FullPolynomial h;
    public FullPolynomial f;

    public RawKeyData(
        KeyParams      _keyParams,
        FullPolynomial _h)
    {
        keyParams = _keyParams;
        h = _h;
        f = null;
    }
    public RawKeyData(
        KeyParams      _keyParams,
        FullPolynomial _h,
        FullPolynomial _f)
    {
        keyParams = _keyParams;
        h = _h;
        f = _f;
    }
}


