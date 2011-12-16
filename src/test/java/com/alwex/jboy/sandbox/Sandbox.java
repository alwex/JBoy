/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alwex.jboy.sandbox;

import com.alwex.jboy.utils.Debug;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class Sandbox
{

    public Sandbox()
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
    public void bitRotation()
    {
        System.out.println(Integer.toHexString(0xFF << 8 | 0x50));
        System.out.println(Integer.toHexString(0x01 << 8 | 0x50));
        System.out.println(Integer.toHexString(0xFF << 8 | 0x00));
        System.out.println(Integer.toHexString((0xff & 0x00ff) | (0x05 & 0xff00)));

        byte b = (byte) 0x01;
        byte c = (byte) 0x85;
        short result = (short) (((b << 8) | c) & 0x0000ffff);
        result = (short) (b << 8 & 0xffff | c & 0xff);
        System.out.println(Integer.toHexString(result & 0xffff));
    }

    @Test
    public void bitSetting()
    {
        byte b = 0, c = 0;
        System.out.println(Debug.toBin(b));
        b = (byte) (b | (1 << 7));
        System.out.println(Debug.toBin(b));
        b = (byte) (b & ~(1 << 7));
        System.out.println(Debug.toBin(b));
        
        b = 0x40; c = (byte) 0xE0;
        
        System.out.println(Integer.toHexString(b^c & 0xff));
        
        System.out.println(Integer.toHexString(0xE0^0x20));
    }

    @Test
    public void writeOpDesc()
    {
        for (int i = 0; i <= 0xff; i++)
        {
//            System.out.println("case 0x" + Debug.toHex((byte)i) + ": break;");
            if (i % 16 == 0)
            {
                System.out.println("");
            }
            System.out.print("/*0x" + Debug.toHex((byte) i) + "*/ \"\", ");
        }
    }
    
    @Test 
    public void testCharByte()
    {
        byte b = (byte) 0xff;
        
        System.out.println(Debug.toHex(b));
        System.out.println(b);
         b = (byte) 255;
        System.out.println(b); 
        int i = 0xff;
        System.out.println(i);
    }
    
    @Test
    public void testMask()
    {
        short s = (short) 0xDFFE;
        System.out.println(Debug.toHex(s));
        
        byte b = (byte) ((s & 0xFF00) >> 8 );
        
        System.out.println(Debug.toHex(b));
        
        b = (byte) 0xFC;
        s = 0x216;
        
        System.out.println(Debug.toHex( (short)(b+s) ));
    }

    @Test
    public void testStringBuilder()
    {
        StringBuilder s = new StringBuilder();
        s.append("test");
        assertEquals("test", s.toString());

        s.append("truc");
        assertEquals("testtruc", s.toString());

        String a = String.format("LD %s, %s, in %s", "0x00", "0x12", "0xFC");
        System.out.println(a);
    }
}
