package praveen.odr.encrypt.encoder;

import praveen.odr.encrypt.exceptions.ParamSetNotSupportedException;
import praveen.odr.encrypt.math.FullPolynomial;
import praveen.odr.encrypt.KeyParams;

interface PrivKeyFormatter {

    public byte[] encode(
            KeyParams keyParams,
            FullPolynomial h,
            FullPolynomial f);

    public RawKeyData decode(
            byte keyBlob[])
            throws ParamSetNotSupportedException;
}
