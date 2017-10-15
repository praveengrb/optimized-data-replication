package praveen.odr.encrypt.encoder;

import praveen.odr.encrypt.exceptions.ParamSetNotSupportedException;
import praveen.odr.encrypt.math.BitPack;
import praveen.odr.encrypt.math.FullPolynomial;
import praveen.odr.encrypt.KeyParams;


class PubKeyFormatter_PUBLIC_KEY_v1 implements PubKeyFormatter
{
    private final static byte tag = NtruEncryptKeyNativeEncoder.PUBLIC_KEY_v1;

    public byte[] encode(
        KeyParams      keyParams,
        FullPolynomial h)
    {
        if (h.p.length != keyParams.N)
          return null;

        int len = (KeyFormatterUtil.fillHeader(tag, keyParams.OIDBytes, null) +
                   BitPack.pack(keyParams.N, keyParams.q));
        byte ret[] = new byte[len];

        int offset = KeyFormatterUtil.fillHeader(tag, keyParams.OIDBytes, ret);
        BitPack.pack(keyParams.N, keyParams.q, h.p, 0, ret, offset);
        return ret;
    }

    public RawKeyData decode(
        byte keyBlob[])
        throws ParamSetNotSupportedException
    {
        // Parse the header, recover the key parameters.
        if (keyBlob[0] != tag)
          throw new IllegalArgumentException("key blob tag not recognized");
        KeyParams keyParams = KeyFormatterUtil.parseOID(keyBlob, 1, 3);

        // Make sure the input will be fully consumed
        int headerLen = KeyFormatterUtil.getHeaderEndOffset(keyBlob);
        int packedHLen = BitPack.unpack(keyParams.N, keyParams.q);
        if (headerLen + packedHLen != keyBlob.length)
          throw new IllegalArgumentException(
              "Input public key blob is " + keyBlob.length + " bytes, not " +
              "the expected " + (headerLen + packedHLen));

        // Recover h
        FullPolynomial h = new FullPolynomial(keyParams.N);
        BitPack.unpack(keyParams.N, keyParams.q, keyBlob, headerLen, h.p, 0);

        // Return the key material
        return new RawKeyData(keyParams, h);
    }
}
