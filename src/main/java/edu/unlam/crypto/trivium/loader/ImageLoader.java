package edu.unlam.crypto.trivium.loader;

import java.io.*;

import static org.apache.commons.io.IOUtils.toByteArray;


public class ImageLoader
{

    public static Integer HEADER_LENGTH = 54;

    public byte [] getBytes(String fileName) throws IOException
    {
        DataInputStream imageStream = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
        byte [] bytesOfImage = toByteArray(imageStream);
        imageStream.close();
        return bytesOfImage;
    }

    public byte [] getBytesHeader (byte [] bytesImage)
    {
        byte bytesHeader [] = new byte[HEADER_LENGTH];

        for (int i = 0; i < HEADER_LENGTH; i ++)
        {
            bytesHeader[i] = bytesImage[i];
        }

        return bytesHeader;
    }

    public byte [] getBytesBody (byte [] bytesImage)
    {
        byte bytesBody [] = new byte[bytesImage.length - HEADER_LENGTH];

        for (int i = 0, j = HEADER_LENGTH; i < bytesImage.length - HEADER_LENGTH ; i ++, j++)
        {
            bytesBody[i] = bytesImage[j];
        }

        return bytesBody ;
    }




}


