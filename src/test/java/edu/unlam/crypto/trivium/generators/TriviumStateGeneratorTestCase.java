package edu.unlam.crypto.trivium.generators;

import org.junit.Before;
import org.junit.Test;

import static edu.unlam.crypto.trivium.constants.TriviumConstants.KEY_LENGTH;
import static edu.unlam.crypto.trivium.constants.TriviumConstants.STATE_BYTE_LENGTH;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TriviumStateGeneratorTestCase
{
    private final String key = "0123456789";
    private final String iv = "1234567890";
    private TriviumStateGenerator triviumStateGenerator;

    @Before
    public void setUp() throws Exception
    {
        triviumStateGenerator = new TriviumStateGenerator(key.getBytes(),iv.getBytes());
    }

    @Test
    public void testGenerateFirstBlock() throws Exception
    {
        triviumStateGenerator.generateState();
        byte [] state = triviumStateGenerator.getState();
        verifyFirstBlockIsCorrect(state);
        verifySecondBlockIsCorrect(state);
        byte resultLastByte = 7;
        assertThat(state[STATE_BYTE_LENGTH - 1], is(resultLastByte));
    }

    private void verifyFirstBlockIsCorrect (byte [] firstBlock)
    {
        byte [] keyInBytes = key.getBytes();


        for(int i = 0; i < KEY_LENGTH; i ++)
        {
            assertThat(firstBlock[i],  is(keyInBytes[i]));
        }
    }
    private void verifySecondBlockIsCorrect (byte [] secondBlock)
    {
        byte zeroByte = 0;
        byte elevenByte = 1;
        byte twelveByte =  -119;
        byte thirteenByte = -111;
        assertThat(secondBlock[10], is(zeroByte));
        assertThat(secondBlock[11], is(elevenByte));
        assertThat(secondBlock[12], is(twelveByte));
        assertThat(secondBlock[13], is(thirteenByte));
    }

}
