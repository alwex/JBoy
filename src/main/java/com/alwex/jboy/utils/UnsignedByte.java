package com.alwex.jboy.utils;

/**
 *
 * @author alex
 */
public class UnsignedByte
{
    byte value;
    public UnsignedByte(byte aValue)
    {
        this.value = aValue;
    }

    public void shiftLeft(int shift)
    {
        this.value = (byte) (this.value << shift);
    }

    public void shiftRight(int shift)
    {
        this.value = (byte) (this.value >> shift);
    }

    public void bitwise()
    {
        
    }

    @Override
    public String toString()
    {
        String out = Integer.toHexString(this.value & 0xff).toUpperCase();
        while (out.length() < 2)
        {
            out = "0" + out;
        }
        return out;
    }

    public int getValue()
    {
        return this.value & 0xff;
    }
}
