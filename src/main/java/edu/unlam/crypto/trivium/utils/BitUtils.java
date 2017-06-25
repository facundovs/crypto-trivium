package edu.unlam.crypto.trivium.utils;


public class BitUtils
{
    private BitUtils()
    {
    }

    public static int getBitFromPosition (byte byteValue, int position)
    {
        position = 7 - position;
        return (byteValue >> position) & 1;
    }

    public static byte  setBitPosition(int bitValue, byte byteValue, int position)
    {
        position = 7 - position;
        if(bitValue == 1)
        {
            return (byte) (byteValue | (1 << position)) ;
        }
        else
        {
            return (byte) (byteValue & ~(1 << position));
        }
    }
}
