package edu.unlam.crypto.trivium.generators;

import static edu.unlam.crypto.trivium.constants.TriviumConstants.*;
import static edu.unlam.crypto.trivium.utils.BitUtils.getBitFromPosition;
import static edu.unlam.crypto.trivium.utils.BitUtils.setBitPosition;

public class TriviumStateGenerator
{

    private final byte [] key ;
    private final byte [] iv ;
    private byte [] state = new byte [STATE_BYTE_LENGTH];
    private int [] blockOne = new int [93];
    private int [] blockTwo = new int [84];
    private int [] blockThree = new int [111];

    public TriviumStateGenerator(byte[] key, byte[] iv)
    {
        this.key = key;
        this.iv = iv;
    }

    public void generateState()
    {
        generateFirstBlock();
        generateSecondBlock();
        convertByteToInt();
        mixBytes();
    }

    public int generateBit()
    {
        int t1;
        int t2;
        int t3;
        int z1;
        t1 = getBitValueFromIntArray(66 -1) ^ getBitValueFromIntArray(93 -1);
        t2 = getBitValueFromIntArray(162 -1) ^ getBitValueFromIntArray(177 -1);
        t3 = getBitValueFromIntArray(243 -1) ^ getBitValueFromIntArray(288 -1);
        z1 = t1 ^ t2 ^ t3 ;
        t1 = t1 ^ (getBitValueFromIntArray(91 -1 ) & getBitValueFromIntArray(92 -1 ))  ^ getBitValueFromIntArray(171 -1 );
        t2 = t2 ^ ( getBitValueFromIntArray(175 -1)  & getBitValueFromIntArray(176 -1))  ^ getBitValueFromIntArray(264 -1);
        t3 = t3 ^ ( getBitValueFromIntArray(286 -1 )  & getBitValueFromIntArray(287 -1))  ^ getBitValueFromIntArray(69 -1);
        blockOne = shiftOnePosition(t3, blockOne);
        blockTwo = shiftOnePosition(t1, blockTwo);
        blockThree = shiftOnePosition(t2, blockThree);
        return z1;
    }

    private void generateFirstBlock()
    {
        if(key.length != KEY_LENGTH)
        {
            throw new RuntimeException("Trivium Key must have 80 bits length");
        }

        for(int i = 0; i < KEY_LENGTH; i++)
        {
            state[i] = key [i];
        }

        state[10] = 0;

    }

    private void generateSecondBlock()
    {
        if(iv.length != IV_LENGTH)
        {
            throw new RuntimeException("Trivium IV must have 80 bits length");
        }

        int index = 11;
        int ivPosition = 0;
        int ivIndex = 0;

        for (int i = 0; i < 5; i++)
        {
            setBitPosition(0, state[index], i);
        }

        for(int i = 5; i < 8; i++, ivPosition++)
        {
            state[index] = (byte) setBitPosition(getBitFromPosition(iv[0], ivPosition), state[index], i);
        }

        index ++;

        int i ;
        int statePosition = 0;
        for (i = ivPosition; i < IV_BIT_LENGTH && ivIndex < IV_LENGTH; i ++)
        {
            int auxPosition = (i ) % 8 ;
            int bitValue = getBitFromPosition(iv[ivIndex], auxPosition) ;
            state[index] = setBitPosition(bitValue, state[index], statePosition);
            if (auxPosition == 7)
            {
                ivIndex ++;
            }
            if(statePosition == 7)
            {
                index++;
                statePosition = 0;
            }
            else
            {
                statePosition ++;
            }
        }

        for(int j= 5; j < 8; j++)
        {
            state[STATE_BYTE_LENGTH - 1] = setBitPosition(1, state[STATE_BYTE_LENGTH -1 ], j);
        }

    }

    public byte[] getState()
    {
        return state;
    }

    private void mixBytes()
    {
        int t1, t2, t3;
        for (int i = 0; i < STATE_BIT_LENGTH * NUMBER_OF_ITERATIONS ; i ++)
        {
            t1 = getBitValueFromIntArray(66 -1 ) ^ (getBitValueFromIntArray(91 -1 ) & getBitValueFromIntArray(92 -1 )) ^ getBitValueFromIntArray(93 -1)  ^ getBitValueFromIntArray(171 -1);
            t2 = getBitValueFromIntArray(162 -1) ^ (getBitValueFromIntArray(175 -1) & getBitValueFromIntArray(176 -1)) ^ getBitValueFromIntArray(177 -1) ^ getBitValueFromIntArray(264 -1);
            t3 = getBitValueFromIntArray(243 -1) ^ (getBitValueFromIntArray(286 -1) & getBitValueFromIntArray(287 -1)) ^ getBitValueFromIntArray(288 -1) ^ getBitValueFromIntArray(69-1);
            blockOne = shiftOnePosition(t3, blockOne);
            blockTwo = shiftOnePosition(t1, blockTwo);
            blockThree = shiftOnePosition(t2, blockThree);
        }
    }

    private int getBitValueFromBytePosition (int position)
    {
        int byteIndex = (position / 8) ;
        int bitPosition = (position % 8);
        return getBitFromPosition(state[byteIndex], bitPosition);
    }

    private int getBitValueFromIntArray (int position)
    {
        if (position < 93)
        {
            return blockOne[position];
        }
        else if (position < 177)
        {
            return blockTwo[position - 93];
        }

        return blockThree[position - 177];
    }

    private int [] shiftOnePosition (int value, int [] vector)
    {
        for(int i = vector.length - 1 ;  i > 0; i --)
        {
            vector[i] = vector [i-1];
        }
        vector [0] = value;

        return vector;
    }



    private void convertByteToInt()
    {
        int i = 0;
        for(int j = 0; i < STATE_BIT_LENGTH && j < blockOne.length; i ++, j++)
        {
            blockOne[j] = getBitValueFromBytePosition(i);
        }

        for(int j = 0; i < STATE_BIT_LENGTH && j < blockTwo.length; i ++, j++)
        {
            blockTwo[j] = getBitValueFromBytePosition(i);
        }

        for(int j = 0; i < STATE_BIT_LENGTH && j < blockThree.length; i ++, j++)
        {
            blockThree[j] = getBitValueFromBytePosition(i);
        }
    }

}
