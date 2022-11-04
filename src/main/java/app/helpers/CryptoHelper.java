package app.helpers;

import org.apache.commons.codec.digest.DigestUtils;

public class CryptoHelper {
    public static String encryptSHA2(String clearString){
        return DigestUtils.sha256Hex(clearString).toUpperCase();
    }
    public static boolean checkSHA2(String encryptedString, String clearString){
         return encryptedString.equals(encryptSHA2(clearString).toUpperCase());
    }

}
