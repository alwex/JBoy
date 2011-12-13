package com.alwex.jboy.sandbox;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.alwex.jboy.utils.ByteUtil.*;

/**
 *
 * @author Alex
 */
public class UnsignedByte
{

    public UnsignedByte()
    {
    }

    @Test
    public void usignedByteUse()
    {
        byte b = 0;

        for (int i = 0; i <= 512; i++)
        {
            b++;
            assertTrue(b <= 255);
        }
    }

    @Test
    public void unsignedByteCustom()
    {
        byte b = 127;
        for (int i = 0; i<= 512; i++)
        {
            b++;
            assertTrue(UByte(b) <= 255);
            assertTrue(UByte(b) >= 0);
        }
    }

    @Test
    public void unsignedShortCustom()
    {
        short s = 32767;
        for (int i = 0; i<= 52767; i++)
        {
            s++;
            assertTrue(UShort(s) <= 65535);
            assertTrue(UShort(s) >= 0);
        }
    }
}
