package com.alwex.jboy.utils;

/**
 *
 * @author Alex
 */
public class ByteUtil
{
    public static int UByte(byte value)
    {
        return value & 0xff;
    }

    public static int UShort(short value)
    {
        return value & 0xffff;
    }

    public static String toBin(byte value)
    {
        return Integer.toBinaryString(value);
    }

    public static String toBin(short value)
    {
        return Integer.toBinaryString(value);
    }
    
    public static String toHex(byte value)
    {
        String out = Integer.toHexString(UByte(value)).toUpperCase();
        while (out.length() < 2)
        {
            out = "0" + out;
        }
        return out;
    }

    public static String toHex(short value)
    {
        String out = Integer.toHexString(UShort(value)).toUpperCase();
        while (out.length() < 4)
        {
            out = "0" + out;
        }
        return out;
    }
    
    /**
     * combine 2 octets tels que
     * 0x12 | 0x34 => 0x1234
     *
     * @param a
     * @param b
     * @return
     */
    public static int combine(byte a, byte b)
    {
        int result = ((((a << 8) & 0xffff | b & 0xff) & 0x0000ffff) & 0xffff);
        return result;
    }
}
