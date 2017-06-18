package edu.unlam.crypto.trivium.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class ImageUtils {

    public static Boolean isJPEGImage(String filename) throws Exception {
        DataInputStream ins = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
        try {
            if (ins.readInt() == 0xffd8ffe0) {
                return true;
            } else {
                return false;
            }
        } finally {
            ins.close();
        }
    }
}
