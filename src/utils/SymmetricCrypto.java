/* 
    @author - Muhd. Aidil Syazwan, CA19144 UMP
*/

package utils;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricCrypto {
    private static final String ALGORITHM = "DES";
    private static final int KEY_SIZE = 56;

    /* create 56bit DES key */
    public static String createKey() {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
            keygen.init(KEY_SIZE, new SecureRandom());

            return Base64.getEncoder().encodeToString(keygen.generateKey().getEncoded());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /* encrypt with 0 as initialization vector, returns bytes */
    public static byte[] encrypt(String key, String txt) {
        try {
            byte[] encodedKey = Base64.getDecoder().decode(key);
            SecretKey sk = new SecretKeySpec(Base64.getDecoder().decode(key), 0, encodedKey.length, ALGORITHM);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
            cipher.init(Cipher.ENCRYPT_MODE, sk, zeroIv);
            
            return cipher.doFinal(txt.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /* decrypt with 0 as initialization vector, returns string */
    public static String decrypt(String key, byte[] cp) {
        
        try {
            byte[] encodedKey = Base64.getDecoder().decode(key);
            SecretKey sk = new SecretKeySpec(encodedKey, 0, encodedKey.length, ALGORITHM);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
            cipher.init(Cipher.DECRYPT_MODE, sk, zeroIv);

            return new String(cipher.doFinal(cp));
        } catch(Exception e) {
            System.out.println("Invalid key!");
        }

        return "";
    }
}
