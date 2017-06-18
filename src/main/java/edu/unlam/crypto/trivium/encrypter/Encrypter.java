package edu.unlam.crypto.trivium.encrypter;

import java.io.IOException;

/**
 * Created by facundo on 18/6/17.
 */
public interface Encrypter
{
    public void initialize() throws IOException;
    public byte [] encrypt (byte [] bytesImage);
    public byte [] decrypt (byte [] bytesImage);
}
