package test.praveen.odr.encrypt.encoder;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

import praveen.odr.encrypt.exceptions.NtruException;
import praveen.odr.encrypt.exceptions.ParamSetNotSupportedException;
import praveen.odr.encrypt.math.BitPack;
import praveen.odr.encrypt.math.FullPolynomial;
import praveen.odr.encrypt.KeyParams;
import praveen.odr.encrypt.encoder.NtruEncryptKeyNativeEncoder;
import com.securityinnovation.jNeo.ntruencrypt.encoder.PubKeyFormatter;
import praveen.odr.encrypt.encoder.PubKeyFormatter_PUBLIC_KEY_v1;
import praveen.odr.encrypt.encoder.RawKeyData;
import test.praveen.odr.encrypt.NtruEncryptTestVector;


public class PubKeyFormatter_PUBLIC_KEY_v1TestCase {

    // The master list of test vectors
    NtruEncryptTestVector tests[] = NtruEncryptTestVector.getTestVectors();

    byte[] buildPubBlob(
        KeyParams keyParams,
        byte[] packedH)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(NtruEncryptKeyNativeEncoder.PUBLIC_KEY_v1);
        os.write(keyParams.OIDBytes, 0, keyParams.OIDBytes.length);
        os.write(packedH, 0, packedH.length);
        return os.toByteArray();
    }

    @Test public void test_encodePubKey_v1()
        throws NtruException
    {
        for (int t=0; t<tests.length; t++)
        {
            KeyParams keyParams = KeyParams.getKeyParams(tests[t].oid);
            FullPolynomial h = new FullPolynomial(tests[t].h);
            
            // Build a blob using the code being tested.
            PubKeyFormatter encoder = new PubKeyFormatter_PUBLIC_KEY_v1();
            byte pubBlob[] = encoder.encode(keyParams,h);

            // Build the expected blob:
            byte expected[] = buildPubBlob(keyParams, tests[t].packedH);

            assertTrue(java.util.Arrays.equals(pubBlob, expected));
        }
    }


    @Test public void test_decodePubKey()
        throws NtruException
    {
        for (int t=0; t<tests.length; t++)
        {
            KeyParams keyParams = KeyParams.getKeyParams(tests[t].oid);

            // Build a blob
            byte blob[] = buildPubBlob(keyParams, tests[t].packedH);

            // Parse the test blob
            PubKeyFormatter encoder = new PubKeyFormatter_PUBLIC_KEY_v1();
            RawKeyData key = encoder.decode(blob);

            // Check results;
            assertEquals(key.keyParams, KeyParams.getKeyParams(tests[t].oid));
            assertTrue(java.util.Arrays.equals(key.h.p, tests[t].h));
            assertEquals(key.f, null);
        }
    }


    @Test(expected=ParamSetNotSupportedException.class)
    public void test_decodePubKey_bad_oid()
        throws NtruException
    {
        byte            blob[] = null;
        PubKeyFormatter encoder = null;
        try {
            int t = 0;
            KeyParams keyParams = KeyParams.getKeyParams(tests[t].oid);

            // Build a blob
            blob = buildPubBlob(keyParams, tests[t].packedH);
            // Change the oid
            blob[2] = (byte)0xff;
        
            encoder = new PubKeyFormatter_PUBLIC_KEY_v1();
        }
        catch (Throwable t)
        {
            fail("Unexpected exception " + t);
        }

        // Parse the test blob
        encoder.decode(blob);
    }


    @Test(expected=IllegalArgumentException.class)
    public void test_decodePubKey_shortBuf()
        throws NtruException
    {
        byte blob2[] = null;
        PubKeyFormatter encoder = null;

        // Do the setup inside a try statement so we don't accidentally
        // trigger a false positive.
        try
        {
            int t = 0;
            KeyParams keyParams = KeyParams.getKeyParams(tests[t].oid);

            // Build a blob and make a short copy of it
            byte blob[] = buildPubBlob(keyParams, tests[t].packedH);
            blob2 = new byte[blob.length-2];
            System.arraycopy(blob, 0, blob2, 0, blob2.length);
        
            // Create the decoder
            encoder = new PubKeyFormatter_PUBLIC_KEY_v1();
        }
        catch (Throwable t)
        {
            fail("setup for decoding short blob nexpectedly threw " + t.toString());
        }

        // Parse the test blob
        RawKeyData key = encoder.decode(blob2);
        fail("decoding short blob didn't throw an exception");
    }


    @Test(expected=IllegalArgumentException.class)
    public void test_decodePubKey_longBuf()
        throws NtruException
    {
        byte blob2[] = null;
        PubKeyFormatter encoder = null;
        
        // Do the setup inside a try statement so we don't accidentally
        // trigger a false positive.
        try {
            int t = 0;
            KeyParams keyParams = KeyParams.getKeyParams(tests[t].oid);

            // Build a blob and make a long copy of it
            byte blob[] = buildPubBlob(keyParams, tests[t].packedH);
            blob2 = new byte[blob.length+2];
            System.arraycopy(blob, 0, blob2, 0, blob.length);
            blob2[blob.length] = blob2[blob.length+1] = 0;
        
            // Create the decoder
            encoder = new PubKeyFormatter_PUBLIC_KEY_v1();
        }
        catch (Throwable t)
        {
            fail("setup for decoding short blob nexpectedly threw " + t.toString());
        }

        // Parse the test blob
        RawKeyData key = encoder.decode(blob2);
        fail("decoding short blob didn't throw an exception");
    }
}
