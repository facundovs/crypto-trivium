package edu.unlam.crypto.trivium.utils;

import org.junit.Test;

import static edu.unlam.crypto.trivium.utils.ImageUtils.isJPEGImage;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ImageUtilsTestCase {

    @Test
    public void testJPEGImage() throws Exception
    {
        boolean isJPEGImage = isJPEGImage("src/test/resources/testJPEGImage.jpg");
        assertThat(isJPEGImage, is(true));
    }

    @Test
    public void testNotJPEGImage() throws Exception
    {
        boolean isJPEGImage = isJPEGImage("src/test/resources/testPNGImage.png");
        assertThat(isJPEGImage, is(false));
    }
}
