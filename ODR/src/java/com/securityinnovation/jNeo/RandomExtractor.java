package com.securityinnovation.jNeo;

import com.securityinnovation.jNeo.inputstream.X982Drbg;

/**
 * This class provides hooks to extract the X982 DRBG from a
 * com.securityinnovation.jNeo.Random object. 
 */
public class RandomExtractor
{
    public static X982Drbg extractRNG(
        Random r)
    {
        return r.rng;
    }
}
