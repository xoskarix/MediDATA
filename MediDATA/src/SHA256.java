package MediDATA;

import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class SHA256
{
    public static byte[] hashuj(String s) throws NoSuchAlgorithmException
    {
        MessageDigest msgDgst = MessageDigest.getInstance("SHA-256");
        return msgDgst.digest(s.getBytes(StandardCharsets.UTF_8));
    }

    public static String hash_str(byte[] hash)
    {

        BigInteger no = new BigInteger(1, hash);
        StringBuilder hexStr = new StringBuilder(no.toString(16));
        while (hexStr.length() < 32)
        {
            hexStr.insert(0, '0');
        }
        return hexStr.toString();
    }
}