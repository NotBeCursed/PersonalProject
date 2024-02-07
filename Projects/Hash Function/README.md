# Hash Function

---

**How does it work ?**
A hash function is a mathematical function that converts a numerical input value into another compressed numerical value. In the hash function, he input is always of arbitrary length but the output is always of fixed length.
Hash functions take **data** as an input and return an **integer** in the range of possible values into a hash table and then it consistently distributes the data across the entire set of possible hash values. The hash function generates completely different hash values even for similar strings.

**Function Development**
Some of the popular programming languages used for implementing HashMaps or HashTables include **Java, C++, Python, and Ruby**. These languages provide built-in support for HashMaps or HashTables, making it relatively straightforward to work with this data structure.
I'm used to developing programs in **Python**, so for this project I'll be using another language. 

This hash function will allow to generate a hash string from a input password. This function could be use in a web server to save users' passwords. For password security, we store the password hash in a database, not the password itself. Storing the hashes of our passwords enables a web server to authenticate a user by comparing the hash of the password entered by the user during authentication with the hash of the password entered during registration, for example. Since a hash is unique, the only way to discover a password is to test passwords in the hope of finding an identical hash. This is known as **brute force**.

**Java**
This code generates the hash of a password entered by the user. To increase the security of our hash without actually creating our own algorithm, we'll run our password through several algorithms. This makes brute force more difficult to achieve. In our case, we run our password through a first algorithm, MD5, then a second, SHA-256. We can easily multiply the layers if we wish. 

```java
import java.util.Scanner;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

class Main {
	
	// java method to get input user password
	public static String GetPasswd() {
		Scanner inputPassword = new Scanner(System.in);
		System.out.println("Enter your password");
		String passwd = inputPassword.nextLine();
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
```

