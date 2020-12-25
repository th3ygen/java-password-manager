package models;

import java.util.Base64;

import utils.SymmetricCrypto;

public class Password {
    private long createdAt;
    private byte[] encrypted;

    /* 
        constructor
         - automatically encrypts the password once it were given
    */
    public Password(String password, Owner owner) {
        this.createdAt = System.currentTimeMillis();

        this.encrypted = SymmetricCrypto.encrypt(owner.getKey(), password);
    }

    public long getCreatedAt() {
        return createdAt;
    }

    /* read with key */
    public String read(String key) {
        return SymmetricCrypto.decrypt(key, this.encrypted);
    }

    /* raw read */
    public String read() {
        return Base64.getEncoder().encodeToString(this.encrypted);
    }
}
