package edu.unlam.crypto.trivium.images;


public class Image
{

    private byte [] body;
    private byte [] header;
    private byte [] allBytes;

    public Image(byte [] allBytes, byte[] body, byte[] header)
    {
        this.body = body;
        this.header = header;
        this.allBytes = allBytes;
    }

    public byte[] getBody()
    {
        return body;
    }

    public void setBody(byte[] body)
    {
        this.body = body;
    }

    public byte[] getHeader()
    {
        return header;
    }

    public void setHeader(byte[] header)
    {
        this.header = header;
    }

    public byte [] getAllBytes ()
    {
        return allBytes;
    }

}
