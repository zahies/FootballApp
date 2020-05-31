package DataAccess;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHash {

    private static PasswordHash single_instance = null;
    private PasswordHash(){

    }
    public static PasswordHash getInstance()
    {
        if (single_instance == null)
            single_instance = new PasswordHash();

        return single_instance;
    }
    public String hash(String passwordToHash) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));

        return new String(hashedPassword);
    }

}
