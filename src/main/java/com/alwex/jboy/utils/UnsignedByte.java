package com.alwex.jboy.utils;

/**
 *
 * @author alex
 */
public class UnsignedByte
{
    /**
     * valeur de l'octet
     */
    byte value;

    public UnsignedByte(byte aValue)
    {
        this.value = aValue;
    }

    public byte shiftLeft(int shift)
    {
        return (byte) (this.value << shift);
    }

    public byte shiftRight(int shift)
    {
        return (byte) (this.value >> shift);
    }

    public void bitwise()
    {
        
    }

    public int combine(byte a, byte b)
    {
        int result = ((((a << 8) & 0xffff | b & 0xff) & 0x0000ffff) & 0xffff);
        return result;
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
