/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alwex.jboy.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alex
 */
public class ByteUtilTest
{

    public ByteUtilTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Test
    public void testCombinationOfByte()
    {
        byte a = 0x10;
        byte b = 0x40;

        int c = ByteUtil.combine(a, b);

        assertEquals(0x1040, c);
    }

    @Test
    public void testSplitOfInt()
    {
        int value = 0xFF06;
        byte[] result = ByteUtil.split(value);

        System.out.println(ByteUtil.toHex(result[0]) + " " + ByteUtil.toHex(result[1]));

        assertEquals((byte) 0xFF, result[0]);
        assertEquals(0x06, result[1]);
    }
}
