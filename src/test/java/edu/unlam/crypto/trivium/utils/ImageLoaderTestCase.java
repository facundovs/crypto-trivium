package edu.unlam.crypto.trivium.utils;

import edu.unlam.crypto.trivium.loader.ImageLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static edu.unlam.crypto.trivium.loader.ImageLoader.HEADER_LENGTH;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(Parameterized.class)
public class ImageLoaderTestCase {

    private ImageLoader imageLoader = new ImageLoader();
    private byte[] bytesImage = null;
    private final String path;

    public ImageLoaderTestCase(String path)
    {
        this.path = path;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> imagesPaths() {
        List imagesPaths = new ArrayList();
        imagesPaths.add("src/test/resources/testJPEGImage.jpg");
        imagesPaths.add("src/test/resources/testJPEGImage2.jpg");
        return imagesPaths;
    }

    @Before
    public void setUp() throws Exception {
        bytesImage = imageLoader.getBytes(path);
    }

    @Test
    public void testGetBytesImage() throws Exception
    {
        assertThat(bytesImage, instanceOf(byte[].class));
    }


    // JPEG Header: http://www.onicos.com/staff/iz/formats/jpeg.html
    @Test
    public void testGetBytesHeaderImage() throws Exception
    {
        assertThat(bytesImage, instanceOf(byte[].class));

        byte [] bytesHeader = imageLoader.getBytesHeader(bytesImage);
        assertThat(bytesImage, instanceOf(byte[].class));

        StringBuilder hexadecimalHeader = new StringBuilder();

        for (int i= 0; i < 4 ; i ++) {
            hexadecimalHeader.append(String.format("%02X", bytesHeader[i]));
        }

        assertThat(hexadecimalHeader.toString(), is("FFD8FFE0"));

        assertThat(bytesHeader.length, is(HEADER_LENGTH));

        byte [] characters = new byte [5];

        for(int i = 6, j = 0; i < 11; i ++, j++)
        {
            characters[j] = bytesHeader[i];
        }

        assertThat((char) characters[0], is('J'));
        assertThat((char) characters[1], is('F'));
        assertThat((char) characters[2], is('I'));
        assertThat((char) characters[3], is('F'));
        assertThat((char) characters[4], is('\0'));
    }

    @Test
    public void getBytesBodyImage() throws Exception
    {
        byte [] bodyImage = imageLoader.getBytesBody(bytesImage);
        assertThat(bodyImage, instanceOf(byte[].class));
        assertThat(bodyImage.length, is(bytesImage.length - HEADER_LENGTH));
        for(int i = HEADER_LENGTH, j = 0; i < bytesImage.length && j < bodyImage.length; i ++, j ++)
        {
            assertThat("Error in byte number " + j, bodyImage[j], is(bytesImage[i]));
        }
    }

}
