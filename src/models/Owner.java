package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.PasswordAuthentication;
import utils.SymmetricCrypto;

public class Owner {
    /*
        List of owners/accounts, for login functionality
        only temporary, will be removed once database is implemented
    */
    private static List<Owner> owners = new ArrayList<Owner>();

    private String username;
    private String password;

    private String key;

    /* 
        login
        - returns true if the password hash matches
    */
    public static boolean login(String username, String password) {
        Iterator<Owner> it = owners.iterator();
        while(it.hasNext()) {
            Owner o = it.next();
            if (o.getUsername() == username && o.verifyPassword(password)) {
                return true;
            }
        }
        return false;
    }

    /* 
        constructor
        - save username but save hashed password
        - add the new owner in the list for login function
    */
    public Owner(String username, String password) {
        this.username = username;
        // passwords are hashed using MD5 (weak but simple)
        this.password = PasswordAuthentication.hash(password);

        this.key = SymmetricCrypto.createKey();

        owners.add(this);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getKey() {
        return key;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    /*
        verify the password by using the verify
        function provided in PasswordAuthentication class
    */
    public boolean verifyPassword(String password) {
        return PasswordAuthentication.verify(this.password, password);
    }
}
