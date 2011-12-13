package com.alwex.jboy.utils;

/**
 *
 * @author Alex
 */
public class Debug
{

    public static String toHex(short value)
    {
        String out = Integer.toHexString(value & 0xffff).toUpperCase();
        while (out.length() < 4)
        {
            out = "0" + out;
        }
        return out;
    }

    public static String toHex(byte value)
    {
        String out = Integer.toHexString(value & 0xff).toUpperCase();
        while (out.length() < 2)
        {
            out = "0" + out;
        }
        return out;
    }

    public static String toBin(byte value)
    {
        String out = Integer.toBinaryString(value & 0xff);
        while (out.length() < 8)
        {
            out = "0" + out;
        }
        return out;
    }

    public static void dumpRom(byte[] m, int from, int to)
    {
        String dump = "";
        for (int i = from; i < to; i++)
        {
            if ((i) % 16 == 0)
            {
                System.out.println("");
                System.out.print("(" + Integer.toHexString(i) + ") ");
            }
            System.out.print(toHex(m[i]) + " ");
        }
        System.out.println("");
    }
}
