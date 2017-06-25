package edu.unlam.crypto.trivium.encrypter;

import edu.unlam.crypto.trivium.images.Image;

import java.io.IOException;


public interface Encrypter
{
    public void initialize() throws IOException;
    public byte [] encrypt (Image image);
    public byte [] decrypt (Image image);
}
