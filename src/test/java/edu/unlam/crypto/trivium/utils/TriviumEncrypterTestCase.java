package edu.unlam.crypto.trivium.utils;

import edu.unlam.crypto.trivium.encrypter.TriviumEncrypter;
import edu.unlam.crypto.trivium.loader.ImageLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by facundo on 18/6/17.
 */

@RunWith(Parameterized.class)
public class TriviumEncrypterTestCase
{

    private final String path ;
    private TriviumEncrypter triviumEncrypter = null;
    private ImageLoader imageLoader = new ImageLoader();
    private byte [] bytesImage = null;
    private byte [] bytesBody = null;
    private byte [] bytesHeader = null;

    public TriviumEncrypterTestCase(String path)
    {
        this.path = path;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> imagesPaths()
    {
        List imagesPaths = new ArrayList();
        imagesPaths.add("src/test/resources/testJPEGImage.jpg");
        imagesPaths.add("src/test/resources/testJPEGImage2.jpg");
        return imagesPaths;
    }

    @Before
    public void setUp() throws Exception
    {
        triviumEncrypter = new TriviumEncrypter(path);
        triviumEncrypter.initialize();
        bytesImage = imageLoader.getBytes(path);
        bytesHeader = imageLoader.getBytesHeader(bytesImage);
        bytesBody = imageLoader.getBytesBody(bytesImage);
    }

    @Test
    public void testInitialize() throws Exception
    {
        assertThat(triviumEncrypter.getImage().getBody(), is(bytesBody));
        assertThat(triviumEncrypter.getImage().getHeader(), is(bytesHeader));
    }
}
