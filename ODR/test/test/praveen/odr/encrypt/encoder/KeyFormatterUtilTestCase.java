package test.praveen.odr.encrypt.encoder;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

import praveen.odr.encypt.OID;
import praveen.odr.encypt.OIDMap;
import praveen.odr.encrypt.exceptions.NtruException;
import praveen.odr.encrypt.exceptions.ParamSetNotSupportedException;
import praveen.odr.encrypt.math.FullPolynomial;
import praveen.odr.encrypt.KeyParams;
import praveen.odr.encrypt.encoder.KeyFormatterUtil;
import test.praveen.odr.encrypt.NtruEncryptTestVector;


public class KeyFormatterUtilTestCase {

    // The master list of test vectors
    NtruEncryptTestVector tests[] = NtruEncryptTestVector.getTestVectors();

    @Test public void test_fillHeader()
        throws NtruException
    {
        byte oid[] = {2};
        byte out[] = new byte[oid.length+1];
        java.util.Arrays.fill(out, (byte)0);
        assertEquals(2, KeyFormatterUtil.fillHeader((byte) 3, oid, out));
        assertEquals(out[0], (byte)3);
        assertEquals(out[1], (byte)2);
    }

    @Test public void test_fillHeader2()
        throws NtruException
    {
        byte oid[] = {2, 9, 6};
        byte out[] = new byte[oid.length+1];
        java.util.Arrays.fill(out, (byte)0);
        assertEquals(4, KeyFormatterUtil.fillHeader((byte) 3, oid, out));
        assertEquals(out[0], (byte)3);
        assertEquals(out[1], (byte)2);
        assertEquals(out[2], (byte)9);
        assertEquals(out[3], (byte)6);
    }

    @Test public void test_fillHeader_noOutput()
        throws NtruException
    {
        byte oid[] = {2, 9, 6};
        assertEquals(4, KeyFormatterUtil.fillHeader((byte) 3, oid, null));
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_short_buffer()
        throws ParamSetNotSupportedException
    {
        byte inData[] = new byte[3];
        java.util.Arrays.fill(inData, (byte)0);
        KeyFormatterUtil.parseOID(inData, 2, 3);
        fail();
    }
    
    @Test(expected=ParamSetNotSupportedException.class)
    public void test_parseOID_bad_oid()
        throws ParamSetNotSupportedException
    {
        byte inData[] = new byte[10];
        java.util.Arrays.fill(inData, (byte)0);
        KeyFormatterUtil.parseOID(inData, 1, 2);
    }


    boolean checkOID(
        OID oid)
        throws NtruException
    {
        byte inData[] = new byte[10];
        java.util.Arrays.fill(inData, (byte)0);
        byte oidBytes[] = OIDMap.getOIDBytes(oid);
        System.arraycopy(oidBytes, 0, inData, 2, oidBytes.length);
        KeyParams p = KeyFormatterUtil.parseOID(inData, 2, oidBytes.length);
        return (p == KeyParams.getKeyParams(oid));
    }

    @Test public void test_parseOID_ok()
        throws NtruException
    {
        assertTrue(checkOID(OID.ees401ep1));
        assertTrue(checkOID(OID.ees449ep1));
        assertTrue(checkOID(OID.ees677ep1));
        assertTrue(checkOID(OID.ees1087ep2));
        assertTrue(checkOID(OID.ees541ep1));
        assertTrue(checkOID(OID.ees613ep1));
        assertTrue(checkOID(OID.ees887ep1));
        assertTrue(checkOID(OID.ees1171ep1));
        assertTrue(checkOID(OID.ees659ep1));
        assertTrue(checkOID(OID.ees761ep1));
        assertTrue(checkOID(OID.ees1087ep1));
        assertTrue(checkOID(OID.ees1499ep1));
    }



    @Test public void test_recoverF()
        throws NtruException
    {
        for (int t=0; t<tests.length; t++)
        {
            KeyParams keyParams = KeyParams.getKeyParams(tests[t].oid);
            FullPolynomial f = new FullPolynomial(tests[t].f);
            // get f into the expected form:
            // The test vectors have coefficients of f in [0..q-1]
            // recoverF expects them in the range [-q/2..q/2)
            for (int i=0; i<f.p.length; i++)
            {
                if (f.p[i] > keyParams.q)
                  f.p[i] %= keyParams.q;
                if (f.p[i] > keyParams.q/2)
                  f.p[i] -= keyParams.q;
            }
            FullPolynomial F = new FullPolynomial(tests[t].F);
            FullPolynomial Fret = KeyFormatterUtil.recoverF(f);
            assertTrue(Arrays.equals(F.p, Fret.p));
        }
    }
}
