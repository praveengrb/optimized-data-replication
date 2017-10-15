package praveen.odr.encrypt.encoder;

import praveen.odr.encrypt.exceptions.ParamSetNotSupportedException;
import praveen.odr.encrypt.math.FullPolynomial;
import praveen.odr.encrypt.KeyParams;

interface PubKeyFormatter
{
    public byte[] encode(
        KeyParams      keyParams,
        FullPolynomial h);

    public RawKeyData decode(
        byte keyBlob[])
        throws ParamSetNotSupportedException;
}
