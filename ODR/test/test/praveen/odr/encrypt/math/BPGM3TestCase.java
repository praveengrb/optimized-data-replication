package test.praveen.odr.encrypt.math;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import test.praveen.odr.encrypt.NtruEncryptTestVector;
import com.securityinnovation.jNeo.ParamSetNotSupportedException;
import com.securityinnovation.jNeo.ntruencrypt.KeyParams;
import com.securityinnovation.jNeo.inputstream.IGF2;
import com.securityinnovation.jNeo.math.BPGM3;
import com.securityinnovation.jNeo.math.FullPolynomial;


public class BPGM3TestCase {

    // Test polynomial generation with a fixed input that will
    // produce no collisions.
    @Test public void test_oddeven()
    {
        byte igfSequence[] = {0,0,  0,2,  0,4,  0,6,  0,8,
                              0,1,  0,3,  0,5,  0,7,  0,9};
        ByteArrayInputStream is = new ByteArrayInputStream(igfSequence);
        IGF2 igf = new IGF2(0x7fff, 16, is);
        
        short polyCoeffs[] = {1, -1, 1, -1, 1, -1, 1, -1, 1, -1};
        FullPolynomial expected = new FullPolynomial(polyCoeffs);

        FullPolynomial out = BPGM3.genTrinomial(10, 5, 5, igf);

        assertTrue(out.equals(expected));
    }


    // Test polynomial generation with a fixed input that will
    // produce collisions.
    @Test public void test_collisions()
    {
        byte igfSequence[] = {0,0,  0,2,  0,4,  0,6,  
                              0,0,  0,2,  0,4,  0,6,  0,8,
                              0,0,  0,2,  0,4,  0,6,  0,8,
                              0,1,  0,3,  0,5,  0,7,  0,9};
        ByteArrayInputStream is = new ByteArrayInputStream(igfSequence);
        IGF2 igf = new IGF2(0x7fff, 16, is);
        
        short polyCoeffs[] = {1, -1, 1, -1, 1, -1, 1, -1, 1, -1};
        FullPolynomial expected = new FullPolynomial(polyCoeffs);

        FullPolynomial out = BPGM3.genTrinomial(10, 5, 5, igf);

        assertTrue(out.equals(expected));
    }


    // See if we can reproduce the generation of r for each NtruEncrypt
    // test vector
    // r = BGPM3(MGF1(dr, seed=sData));
    @Test public void test_genr()
        throws ParamSetNotSupportedException
    {
        NtruEncryptTestVector tests[] = NtruEncryptTestVector.getTestVectors();
        for (int t=0; t<tests.length; t++)
        {
            KeyParams keyParams = KeyParams.getKeyParams(tests[t].oid);
            FullPolynomial expected = new FullPolynomial(tests[t].r);
            IGF2 igf = new IGF2(
                keyParams.N, keyParams.c, keyParams.igfHash, 1,
                tests[t].sData, 0, tests[t].sData.length);
            FullPolynomial out = BPGM3.genTrinomial(
                keyParams.N, keyParams.dr, keyParams.dr, igf);

            assertTrue(out.equals(expected));
        }
    }
}

