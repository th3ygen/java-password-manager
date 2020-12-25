package models;

public class Access {
    protected String key;

    /* 
        array of passwords to be accessed
    */
    protected Password[] passwords;

    /* 
        contructor
        - takes String key, access key in base64 encoding
    */
    public Access(String key) {
        this.key = key;
    }

    public boolean validate() {
        return false;
    }

    /*
        execute either read or write operation
        but only available by using any of the role classes
    */
    public String authorize() {
        return "Access denied";
    }
}
