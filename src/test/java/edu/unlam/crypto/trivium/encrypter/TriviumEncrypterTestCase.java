package edu.unlam.crypto.trivium.encrypter;

import edu.unlam.crypto.trivium.images.Image;
import edu.unlam.crypto.trivium.loader.ImageLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Math.random;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class TriviumEncrypterTestCase
{

    private final String path ;
    private TriviumImageEncrypter triviumEncrypter = null;
    private ImageLoader imageLoader = new ImageLoader();
    private byte [] bytesImage = null;
    private byte [] bytesBody = null;
    private byte [] bytesHeader = null;
    private Image image = null;
    private File fileEncryptedImage = null;
    private File fileDecryptedImage = null;
    private File filePartialDecryptedImage = null;

    private final int index;
    public TriviumEncrypterTestCase(String path, int index)
    {
        this.path = path;
        this.index = index;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> imagesPaths()
    {
        List imagesPaths = new ArrayList <Object[]>();
        imagesPaths.add(new Object [] {"src/test/resources/testImage.bmp", 1});
        imagesPaths.add(new Object [] { "src/test/resources/testImage2.bmp", 2});
        imagesPaths.add(new Object [] { "src/test/resources/testImage3.bmp", 3});

        //   imagesPaths.add("src/test/resources/testJPEGImage2.jpg");
        return imagesPaths;
    }

    @Before
    public void setUp() throws Exception
    {
        fileEncryptedImage = new File("src/test/resources/encryptedImage" + index + ".bmp");
        fileDecryptedImage = new File("src/test/resources/decryptedImage" + index + ".bmp");
        filePartialDecryptedImage = new File("src/test/resources/partialDecryptedImage" + index + ".bmp");
        triviumEncrypter = new TriviumImageEncrypter("1160398827".getBytes() , "1161688572".getBytes());
        triviumEncrypter.initialize();
        bytesImage = imageLoader.getBytes(path);
        bytesHeader = imageLoader.getBytesHeader(bytesImage);
        bytesBody = imageLoader.getBytesBody(bytesImage);
        image = new Image(bytesImage, bytesBody, bytesHeader);
    }

    @Test
    public void testEncrypt() throws Exception
    {
        byte result [] = triviumEncrypter.encrypt(image);
        assertThat(result.length, is (bytesImage.length));
        writeByteArrayToFile(fileEncryptedImage, result);
    }

    @Test
    public void testDecrypt() throws Exception
    {
        bytesImage = imageLoader.getBytes("src/test/resources/encryptedImage" + index + ".bmp");
        bytesHeader = imageLoader.getBytesHeader(bytesImage);
        bytesBody = imageLoader.getBytesBody(bytesImage);
        image = new Image(bytesImage, bytesBody, bytesHeader);
        byte result [] = triviumEncrypter.decrypt(image);
        assertThat(result.length, is (bytesImage.length));
        writeByteArrayToFile(fileDecryptedImage, result);
    }

    @Test
    public void testPartialDecrypt() throws Exception
    {
        bytesImage = imageLoader.getBytes("src/test/resources/encryptedImage" + index + ".bmp");
        bytesHeader = imageLoader.getBytesHeader(bytesImage);
        bytesBody = imageLoader.getBytesBody(bytesImage);
        for(int i = 0; i < bytesBody.length / 4; i ++)
        {
            bytesBody[i] = (byte) random();
        }
        image = new Image(bytesImage, bytesBody, bytesHeader);
        byte result [] = triviumEncrypter.decrypt(image);
        assertThat(result.length, is (bytesImage.length));
        writeByteArrayToFile(filePartialDecryptedImage, result);
    }

}
