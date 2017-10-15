package com.securityinnovation.jNeo;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.jar.*;

public class ManifestBBTestCase
{
    static String jarfileName = "jars/jNeo.jar";

    String getAttribute(
        String attrName)
        throws java.io.IOException
    {
        JarFile jarfile = new JarFile(jarfileName);
        Manifest manifest = jarfile.getManifest();
        Attributes att = manifest.getMainAttributes();
        return att.getValue(attrName);
    }

    @Test public void test_implementation_vendor()
        throws java.io.IOException
    {
        assertEquals("Security Innovation",
                     getAttribute("Implementation-Vendor"));
    }
    
    @Test public void test_implementation_title()
        throws java.io.IOException
    {
        assertEquals("jNeo",
                     getAttribute("Implementation-Title"));
    }
    
    @Test public void test_implementation_version()
        throws java.io.IOException
    {
        assertEquals("1.0rc1",
                     getAttribute("Implementation-Version"));
    }
    
}
