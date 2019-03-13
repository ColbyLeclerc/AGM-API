package io.colby.modules.auth;



import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

public class Token {

    private String token = "-1";

    public Token(String token){

        if (token == null || token.isEmpty()){
            throw new InvalidParameterException("Token cannot be empty or null");
        } else if (token.toUpperCase().contains("BEARER")){

            String[] tokenSplit = token.toUpperCase().split("BEARER");

            if (tokenSplit.length != 2){
                throw new InvalidParameterException("Token invalid");
            }

            this.token = tokenSplit[1].trim().toUpperCase();

        } else {
            this.token = token.trim().toUpperCase();
        }
    }

    public static Token createToken() {

        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.init(256); // for example
        SecretKey secretKey = keyGen.generateKey();

        String aesKeyGen = new BigInteger(1, secretKey.getEncoded()).toString(36);

        System.out.println("Token " + new Token(aesKeyGen.substring(0, 28)) + " generated");

        return new Token(aesKeyGen.substring(0, 29));
    }

    @Override
    public String toString(){
        return this.token;
    }

}