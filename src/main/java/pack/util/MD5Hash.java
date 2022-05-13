package pack.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {

    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // Always present...
        }
    }

    public static String hash(String input) {
        return Base16Encoder.encode(md.digest(Base16Encoder.decode(input)));
    }
}
