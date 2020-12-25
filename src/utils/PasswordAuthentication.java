/* 
    @author - Muhd. Aidil Syazwan, CA19144 UMP

    PasswordAuthentication.hash(String password) returns String
     - hash a plain password using MD5 algorithm

    PasswordAuthentication.verify(String main, String target) return boolean
     - verify whether two given hashed password matches
*/

package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordAuthentication {
    /* hash using MD5 */
    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return new String(Base64.getEncoder().encode(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /* verify two hash */
    public static boolean verify(String main, String target) {
        return main.equals(target);
    }
}