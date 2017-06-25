package edu.unlam.crypto.trivium.encrypter;

import edu.unlam.crypto.trivium.generators.TriviumStateGenerator;
import edu.unlam.crypto.trivium.images.Image;

import java.io.IOException;

import static edu.unlam.crypto.trivium.utils.BitUtils.getBitFromPosition;
import static edu.unlam.crypto.trivium.utils.BitUtils.setBitPosition;


public class TriviumImageEncrypter implements Encrypter
{
    private final TriviumStateGenerator triviumStateGenerator;
    private final byte[] key;
    private final byte[] iv;

    public TriviumImageEncrypter(byte[] key, byte[] iv)
    {
        this.key = key;
        this.iv = iv;
        this.triviumStateGenerator = new TriviumStateGenerator(key, iv);
    }

    public void initialize() throws IOException
    {
        triviumStateGenerator.generateState();
    }

    public byte[] encrypt(Image image)
    {
        return process(image);
    }

    public byte[] decrypt(Image image)
    {
        return process(image);
    }

    private byte [] process (Image image)
    {
        int[] encrpytedBitsImage = new int[image.getBody().length * 8];
        int encryptedIndex = 0;
        for (byte imageByte : image.getBody())
        {
            for (int i = 0; i < 8; i++)
            {
                int bitImage = getBitFromPosition(imageByte, i);
                int triviumBit = triviumStateGenerator.generateBit();
                int encryptedBit = bitImage ^ triviumBit;
                encrpytedBitsImage[encryptedIndex] = encryptedBit;
                encryptedIndex++;
            }
        }

        return generateEncryptedImage(encrpytedBitsImage, image);
    }

    private byte[] generateEncryptedImage(int[] intArray, Image image)
    {
        byte[] encryptedImage = new byte[image.getHeader().length + image.getBody().length];
        byte[] header = image.getHeader();
        byte[] body = image.getBody();
        int i;
        int indexIntArray = 0;

        for (i = 0; i < header.length; i++)
        {
            encryptedImage[i] = header[i];
        }
        for (; i < header.length + body.length; i++)
        {
            byte encryptedByte = 0;
            for (int j = 0; j < 8 && indexIntArray  <  intArray.length; j++)
            {
                encryptedByte = setBitPosition(intArray[indexIntArray], encryptedByte, j);
                indexIntArray++;
            }
            encryptedImage[i] = encryptedByte;
        }

        return encryptedImage;
    }

}
