import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


class Main {
    
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 16;
    private static final int AES_KEY_BIT = 256;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
 
    public static SecretKey getSecretKey(byte[] salt) {
        // get input user password
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your password");
        String StrPasswd = input.nextLine();
        char[] passwd = StrPasswd.toCharArray();
        
        // generate random salt
        // byte[] salt = new  byte[IV_LENGTH_BYTE];
        // new SecureRandom().nextBytes(salt);
        
        // generate AES KeySpec
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            // iterationCount = 65536
            // keyLength = 256
            KeySpec spec = new PBEKeySpec(passwd, salt, 65536, AES_KEY_BIT);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            return secret; 
        }
        catch(InvalidKeySpecException e) {throw new RuntimeException(e);}
        catch(NoSuchAlgorithmException e) {throw new RuntimeException(e);}
    }
	
	// print hex with block size split
    public static String hexWithBlockSize(byte[] bytes, int blockSize) {

        String hex = hex(bytes);

        // one hex = 2 chars
        blockSize = blockSize * 2;

        // better idea how to print this?
        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < hex.length()) {
            result.add(hex.substring(index, Math.min(index + blockSize, hex.length())));
            index += blockSize;
        }

        return result.toString();

    }
	
	// AES-GCM needs GCMParameterSpec
    public static byte[] encrypt(byte[] pText, SecretKey secret, byte[] iv) throws Exception {

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        byte[] encryptedText = cipher.doFinal(pText);
        return encryptedText;
    }
	
	// prefix IV length + IV bytes to cipher text
    public static byte[] encryptWithPrefixIV(byte[] pText, SecretKey secret, byte[] iv){
		try {
			byte[] cipherText = encrypt(pText, secret, iv);

			byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length + cipherText.length)
					.put(iv)
					.put(cipherText)
					.array();
			return cipherTextWithIv;
		}
		catch(Exception e) {throw new RuntimeException(e);}
    }
    
	// hex representation
    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
	
	public static String decryptWithPrefixIV(byte[] cText, SecretKey secret){
		try {
			ByteBuffer bb = ByteBuffer.wrap(cText);

			byte[] iv = new byte[IV_LENGTH_BYTE];
			bb.get(iv);
			//bb.get(iv, 0, iv.length);

			byte[] cipherText = new byte[bb.remaining()];
			bb.get(cipherText);

			String plainText = decrypt(cipherText, secret, iv);
			return plainText;
		}
		catch(Exception e) {throw new RuntimeException(e);}
    }
	
	public static String decrypt(byte[] cText, SecretKey secret, byte[] iv) throws Exception {

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        byte[] plainText = cipher.doFinal(cText);
        return new String(plainText, UTF_8);
	}
	
	
	public static void main(String[] args) {
	    
	    String OUTPUT_FORMAT = "%-30s:%s";
        String pText = "Hello World AES-GCM, Welcome to Cryptography!";

        // encrypt and decrypt need the same key.
        // get AES 256 bits (32 bytes) key

        // encrypt and decrypt need the same IV.
        // AES-GCM needs IV 96-bit (12 bytes)
        byte[] iv = getRandomNonce(IV_LENGTH_BYTE);
        SecretKey secretKey = getSecretKey(iv);
        byte[] encryptedText = encryptWithPrefixIV(pText.getBytes(UTF_8), secretKey, iv);

        System.out.println("\n------ AES GCM Encryption ------");
        System.out.println(String.format(OUTPUT_FORMAT, "Input (plain text)", pText));
        System.out.println(String.format(OUTPUT_FORMAT, "Key", hex(secretKey.getEncoded())));
		System.out.println(String.format(OUTPUT_FORMAT, "IV  (hex)", hex(iv)));
        System.out.println(String.format(OUTPUT_FORMAT, "Encrypted ", hex(encryptedText)));
		 System.out.println(String.format(OUTPUT_FORMAT, "Encrypted (hex) (block = 16)", hexWithBlockSize(encryptedText, 16)));
	    System.out.println("\n------ AES GCM Decryption ------");
		System.out.println(String.format(OUTPUT_FORMAT, "Input (hex)", hex(encryptedText)));
        System.out.println(String.format(OUTPUT_FORMAT, "Input (hex) (block = 16)", hexWithBlockSize(encryptedText, 16)));
        System.out.println(String.format(OUTPUT_FORMAT, "Key (hex)", hex(secretKey.getEncoded())));

        String decryptedText = decryptWithPrefixIV(encryptedText, secretKey);
        System.out.println(String.format(OUTPUT_FORMAT, "Decrypted (plain text)", decryptedText));
	}
}