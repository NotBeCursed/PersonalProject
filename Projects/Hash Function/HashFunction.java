// Hash Function 
// Java project

import java.util.Scanner;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

class Main {
	
	// java method to get input user password
	public static String GetPasswd() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your password");
		String passwd = input.nextLine();
		return passwd;
	}
	
	// java method which it encrypts a password in MD5
	public static String MD5Layer(String passwd) {
		try {
			// start a MessageDigest instance with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			// calculate message digest of passwd return array of byte
			byte[] digest = md.digest(passwd.getBytes());
			// convert byte array into signum representation
			BigInteger digest_converted = new BigInteger(1, digest);
			
			// convert into hex value
			String hash = digest_converted.toString(16);
			while (hash.length() < 32) {hash = "0" + hash;}
			return hash;
		}
		
		// exception treatment
		catch (NoSuchAlgorithmException error) {
			throw new RuntimeException(error);
		}
	}
	
	public static String SHA256Layer(String passwd) {
		try {
			// start a MessageDigest instance with hashing SHA256
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			// calculate message digest of passwd return array of byte
			byte[] digest = md.digest(passwd.getBytes(StandardCharsets.UTF_8));
			// convert byte array into signum representation
			BigInteger digest_converted = new BigInteger(1, digest);
			
			// convert into hex value
			String hash = digest_converted.toString(16);
			while (hash.length() < 64) {hash = "0" + hash;}
			return hash;
		}
		
		// exception treatment
		catch (NoSuchAlgorithmException error) {
			throw new RuntimeException(error);
		}
	}
	
	// java main method which it is execute when the script is running 
	public static void main(String[] args){
		
		// get input password
		String passwd = GetPasswd();
		System.out.println("Your password is : " + passwd);
		
		// encrypt input password with MD5
		String MD5hash = MD5Layer(passwd);
		System.out.println("Your password hash is (MD5) : " + MD5hash);
		
		// encrypt input password with SHA-256
		String SHA256hash = SHA256Layer(passwd);
		System.out.println("Your password hash is (SHA-256): " + SHA256hash);
		
		// encrypt input password with multi layer
		String Layer1 = MD5Layer(passwd);
		String Layer2 = SHA256Layer(Layer1);
		System.out.println("Multi layer : " + Layer2);
	}
}
