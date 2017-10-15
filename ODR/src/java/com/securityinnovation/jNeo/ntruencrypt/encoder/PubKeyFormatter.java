package com.securityinnovation.jNeo.ntruencrypt.encoder;

import com.securityinnovation.jNeo.ParamSetNotSupportedException;
import com.securityinnovation.jNeo.math.FullPolynomial;
import com.securityinnovation.jNeo.ntruencrypt.KeyParams;

interface PubKeyFormatter
{
    public byte[] encode(
        KeyParams      keyParams,
        FullPolynomial h);

    public RawKeyData decode(
        byte keyBlob[])
        throws ParamSetNotSupportedException;
}
