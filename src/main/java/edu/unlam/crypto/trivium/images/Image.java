package edu.unlam.crypto.trivium.images;


public class Image
{

    private byte [] body;
    private byte [] header;

    public Image(byte[] body, byte[] header)
    {
        this.body = body;
        this.header = header;
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

    public void getAllBytes ()
    {
        //
    }
}
