package edu.unlam.crypto.trivium.encrypter;

import edu.unlam.crypto.trivium.images.Image;
import edu.unlam.crypto.trivium.loader.ImageLoader;

import java.io.IOException;



public class TriviumEncrypter implements Encrypter
{
    private final ImageLoader imageLoader = new ImageLoader();
    private final String path ;
    private Image image = null;

    public TriviumEncrypter(String path)
    {
        this.path = path;
    }

    public void initialize() throws IOException
    {
        byte [] bytesImage =  imageLoader.getBytes(path);
        byte [] bytesHeader = imageLoader.getBytesHeader(bytesImage);
        byte [] bytesBody = imageLoader.getBytesBody(bytesImage);
        image = new Image(bytesBody, bytesHeader);
    }

    public byte[] encrypt(byte [] bytesImage)
    {
        return new byte[0];
    }

    public byte[] decrypt(byte [] bytesImage)
    {
        return new byte[0];
    }

    public Image getImage()
    {
        return image;
    }
}
