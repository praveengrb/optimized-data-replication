package praveen.odr.servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Praveen Sankarasubramanian
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.securityinnovation.jNeo.NtruException;
import com.securityinnovation.jNeo.OID;
import com.securityinnovation.jNeo.Random;
import com.securityinnovation.jNeo.RandomExtractor;
import com.securityinnovation.jNeo.inputstream.X982Drbg;
import com.securityinnovation.jNeo.ntruencrypt.NtruEncryptKey;

import praveen.odr.constants.Constants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Fragmentation {

	private static byte ivBytes[];
	private static byte encryptedBuf[];
	private static byte wrappedAESKey[];
	private static Random Random1;

	static Random createSeededRandom(int id) throws FileNotFoundException, IOException {
		byte seed[] = new byte[32];
		java.util.Random sysRand = new java.util.Random();
		sysRand.nextBytes(seed);
		System.out.println(Arrays.toString(seed));
		try (FileOutputStream fos = new FileOutputStream(Constants.KEY_FILE_LOCATION + id + ".txt")) {
			fos.write(seed);
		}
		Random prng = new Random(seed);
		return prng;
	}

	static Random createSeededRandom1(int id) throws FileNotFoundException, IOException {
		byte seed[] = new byte[32];
		java.util.Random sysRand = new java.util.Random();
		sysRand.nextBytes(seed);
		System.out.println(Arrays.toString(seed));
		Random prng = new Random(seed);
		return prng;
	}

	static OID parseOIDName(String requestedOid) {
		try {
			return OID.valueOf(requestedOid);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid OID! Valid values are:");
			for (OID oid : OID.values()) {
				System.out.println("  " + oid);
			}
			System.exit(1);
		}
		return null;
	}

	public static NtruEncryptKey setupNtruEncryptKey(Random prng, OID oid, String pubFileName, String privFileName)
			throws IOException, NtruException {
		NtruEncryptKey k = NtruEncryptKey.genKey(oid, prng);

		try (FileOutputStream pubFile = new FileOutputStream(pubFileName)) {
			pubFile.write(k.getPubKey());
		}

		try (FileOutputStream privFile = new FileOutputStream(privFileName)) {
			privFile.write(k.getPrivKey());
		}

		return k;
	}

	public static String encryptMessage(NtruEncryptKey ntruKey, Random prng, String message, int id, int count)
			throws NtruException, FileNotFoundException, IOException {
		String output = "";
		byte buf[] = message.getBytes();

		try {
			// Get an AES key
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			keygen.init(128);
			SecretKey aesKey = keygen.generateKey();
			byte[] encoded = aesKey.getEncoded();
			// System.out.println("key: " + encoded);
			try (FileOutputStream fos = new FileOutputStream(
					Constants.KEY_FILE_LOCATION + id + "aes" + count + ".txt")) {
				fos.write(encoded);
			}
			// Get an IV
			ivBytes = new byte[16];
			prng.read(ivBytes);
			IvParameterSpec iv = new IvParameterSpec(ivBytes);

			// Encrypt the plaintext, then zero it out
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
			encryptedBuf = cipher.doFinal(buf);

			java.util.Arrays.fill(buf, (byte) 0);

			// Wrap the AES key with the NtruEncrypt key
			byte aesKeyBytes[] = aesKey.getEncoded();
			wrappedAESKey = ntruKey.encrypt(aesKeyBytes, prng);

			java.util.Arrays.fill(aesKeyBytes, (byte) 0);

		} catch (java.security.GeneralSecurityException e) {
			System.out.println("AES error: " + e);
		}
		output += ivBytes.length + "\n";
		output += ivBytes + "\n";
		output += wrappedAESKey.length + "\n";
		output += wrappedAESKey + "\n";
		output += encryptedBuf.length + "\n";
		output += encryptedBuf + "\n";
		FileOutputStream fos = new FileOutputStream(Constants.KEY_FILE_LOCATION + id + "iv" + count + ".txt");
		fos.write(ivBytes);
		fos.close();
		return output;
	}

	public static String encryptMessage1(NtruEncryptKey ntruKey, Random prng, String message, int id)
			throws NtruException, FileNotFoundException, IOException {
		String output = "";
		byte buf[] = message.getBytes();

		try {
			// Get an AES key
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			keygen.init(128);
			SecretKey aesKey = keygen.generateKey();

			// Get an IV
			ivBytes = new byte[16];
			prng.read(ivBytes);
			IvParameterSpec iv = new IvParameterSpec(ivBytes);

			// Encrypt the plaintext, then zero it out
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
			encryptedBuf = cipher.doFinal(buf);
			java.util.Arrays.fill(buf, (byte) 0);

			// Wrap the AES key with the NtruEncrypt key
			byte aesKeyBytes[] = aesKey.getEncoded();
			wrappedAESKey = ntruKey.encrypt(aesKeyBytes, prng);

			java.util.Arrays.fill(aesKeyBytes, (byte) 0);

		} catch (java.security.GeneralSecurityException e) {
			System.out.println("AES error: " + e);
		}
		output += ivBytes.length + "\n";
		output += ivBytes + "\n";
		output += wrappedAESKey.length + "\n";
		output += wrappedAESKey + "\n";
		output += encryptedBuf.length + "\n";
		output += encryptedBuf + "\n";
		return output;
	}

	public static ArrayList<String> readAndFragment(String SourceFileName, int CHUNK_SIZE, int id)
			throws IOException, NtruException, ClassNotFoundException, SQLException {
		ArrayList<String> lo = new ArrayList<>();

		try {

			Class.forName(Constants.DRIVER_NAME).newInstance();

		} catch (IllegalAccessException | InstantiationException ex) {
			Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
		}

		Connection con;

		con = (Connection) DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
				Constants.DATABASE_PASSWORD);
		String sa = "select * from fileplaceing where id='" + id + "'";
		PreparedStatement pr = con.prepareStatement(sa);
		ResultSet rs = pr.executeQuery();
		String kk = "";
		String decrypted = "";

		if (rs.next()) {
			kk = rs.getString(2);
		}
		String ji[] = kk.split(",");
		for (String k : ji) {
			lo.add(k);

		}
		int count = 0;
		String pubkeyFile = "public_Key";
		String privkeyFile = "private_Key";
		Random prng = createSeededRandom(id);
		X982Drbg j = RandomExtractor.extractRNG(prng);
		System.out.println(j);
		CHUNK_SIZE = 0;
		OID oid = parseOIDName("ees1499ep1");
		Random1 = prng;

		System.out.println(Random1);
		NtruEncryptKey ntruKeys = setupNtruEncryptKey(Random1, oid, pubkeyFile, privkeyFile);
		// log ("File Is Reading "+ SourceFileName );
		File willBeRead = new File(SourceFileName);
		int FILE_SIZE = (int) willBeRead.length();

		ArrayList<String> nameList = new ArrayList<>();

		System.out.println("Total File Size: " + FILE_SIZE);

		int NUMBER_OF_CHUNKS = 0;
		byte[] temporary = null;
		CHUNK_SIZE = FILE_SIZE / 10;

		InputStream inStream = null;
		int totalBytesRead = 0;

		try {
			inStream = new BufferedInputStream(new FileInputStream(willBeRead));

			while (totalBytesRead < FILE_SIZE) {
				String PART_NAME = id + "data" + NUMBER_OF_CHUNKS + ".txt";
				int bytesRemaining = FILE_SIZE - totalBytesRead;
				if (bytesRemaining < CHUNK_SIZE) // Remaining Data Part is
													// Smaller Than CHUNK_SIZE
				// CHUNK_SIZE is assigned to remain volume
				{
					CHUNK_SIZE = bytesRemaining;
					System.out.println("CHUNK_SIZE: " + CHUNK_SIZE);
				}

				temporary = new byte[CHUNK_SIZE]; // Temporary Byte Array
				int bytesRead = inStream.read(temporary, 0, CHUNK_SIZE);

				if (bytesRead > 0) // If bytes read is not empty
				{
					totalBytesRead += bytesRead;
					NUMBER_OF_CHUNKS++;
				}

				String msg = new String(temporary);

				String i = id + "data" + count + ".txt";

				encryptMessage(ntruKeys, prng, msg, id, count);
				System.out.println("Encrypted: " + new String(encryptedBuf));
				String server = (String) lo.get(count);
				try (FileOutputStream fos = new FileOutputStream(
						"F:/Project/ODR/Project/ODR/web/Server/" + server + "/" + PART_NAME)) {
					fos.write(encryptedBuf);
				}

				nameList.add(server);
				System.out.println("Total Bytes Read: " + totalBytesRead);

				// decryption:
				Path path1 = Paths.get(Constants.KEY_FILE_LOCATION + id + ".txt");
				byte[] data = Files.readAllBytes(path1);
				System.out.println(new String(data));

				Path path3 = Paths.get("F:/Project/ODR/Project/ODR/web/Server/" + server + "/" + PART_NAME);
				encryptedBuf = Files.readAllBytes(path3);

				System.out.println(new String(encryptedBuf));

				String pubkeyFile1 = "public_Key";
				String privkeyFile1 = "private_Key";

				OID oid1 = parseOIDName("ees1499ep1");

				Random prng1 = new Random(data);

				NtruEncryptKey ntruKeys1 = setupNtruEncryptKey(prng1, oid1, pubkeyFile1, privkeyFile1);

				String k1 = decryptMessage(ntruKeys1, new String(encryptedBuf), id, count, server);
				System.out.println("Decrypted: " + k1);

				count = count + 1;

			}

		} finally {
			inStream.close();
		}

		return nameList;
	}

	public static String decryptMessage(NtruEncryptKey ntruKey, String cipherText, int id, int i, String server)
			throws NtruException, IOException {

		byte fileContents[] = null;
		try {
			String PART_NAME = id + "data" + i + ".txt";
			Path path1 = Paths.get(Constants.KEY_FILE_LOCATION + id + ".txt");
			byte[] data = Files.readAllBytes(path1);
			Path path = Paths.get("F:/Project/ODR/Project/ODR/web/Server/" + server + "/" + PART_NAME);
			byte[] data1 = Files.readAllBytes(path);
			byte encFileContents[] = data1;
			Random prng1 = new Random(data);
			Path aes = Paths.get(Constants.KEY_FILE_LOCATION + id + "aes" + i + ".txt");
			byte[] da = Files.readAllBytes(aes);
			wrappedAESKey = ntruKey.encrypt(da, prng1);

			byte wrappedKey[] = wrappedAESKey;
			// Unwrap the AES key
			byte aesKeyBytes[] = ntruKey.decrypt(wrappedKey);
			SecretKeySpec aesKey = new SecretKeySpec(aesKeyBytes, "AES");
			java.util.Arrays.fill(aesKeyBytes, (byte) 0);

			// Decrypt the file contents
			IvParameterSpec iv = new IvParameterSpec(ivBytes);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, aesKey, iv);
			fileContents = cipher.doFinal(encFileContents);
		} catch (java.security.GeneralSecurityException e) {
			System.out.println("AES error: " + e);
		}
		return new String(fileContents);
	}

	public static ArrayList<String> readAndFragment1(String SourceFileName, int CHUNK_SIZE, int id) throws IOException,
			NtruException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		ArrayList<String> lo = new ArrayList<>();

		Class.forName(Constants.DRIVER_NAME).newInstance();

		Connection con;

		con = (Connection) DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
				Constants.DATABASE_PASSWORD);
		String sa = "select * from fileplaceing where id='" + id + "'";
		PreparedStatement pr = con.prepareStatement(sa);
		ResultSet rs = pr.executeQuery();
		String kk = "";
		String decrypted = "";

		if (rs.next()) {
			kk = rs.getString(3);
		}
		String ji[] = kk.split(",");
		for (String k : ji) {
			lo.add(k);
		}
		int count = 0;
		String pubkeyFile = "public_Key";
		String privkeyFile = "private_Key";
		Random prng = createSeededRandom1(id);
		X982Drbg j = RandomExtractor.extractRNG(prng);
		System.out.println(j);
		CHUNK_SIZE = 0;
		OID oid = parseOIDName("ees1499ep1");
		Random1 = prng;

		System.out.println(Random1);
		NtruEncryptKey ntruKeys = setupNtruEncryptKey(Random1, oid, pubkeyFile, privkeyFile);
		// log ("File Is Reading "+ SourceFileName );
		File willBeRead = new File(SourceFileName);
		int FILE_SIZE = (int) willBeRead.length();

		ArrayList<String> nameList = new ArrayList<>();

		System.out.println("Total File Size: " + FILE_SIZE);

		int NUMBER_OF_CHUNKS = 0;
		byte[] temporary = null;
		CHUNK_SIZE = FILE_SIZE / 10;

		InputStream inStream = null;
		int totalBytesRead = 0;

		try {
			inStream = new BufferedInputStream(new FileInputStream(willBeRead));

			while (totalBytesRead < FILE_SIZE) {
				String PART_NAME = id + "data" + NUMBER_OF_CHUNKS + ".txt";
				int bytesRemaining = FILE_SIZE - totalBytesRead;
				if (bytesRemaining < CHUNK_SIZE) // Remaining Data Part is
													// Smaller Than CHUNK_SIZE
				// CHUNK_SIZE is assigned to remain volume
				{
					CHUNK_SIZE = bytesRemaining;
					System.out.println("CHUNK_SIZE: " + CHUNK_SIZE);
				}

				temporary = new byte[CHUNK_SIZE]; // Temporary Byte Array
				int bytesRead = inStream.read(temporary, 0, CHUNK_SIZE);

				if (bytesRead > 0) // If bytes read is not empty
				{
					totalBytesRead += bytesRead;
					NUMBER_OF_CHUNKS++;
				}

				String msg = new String(temporary);

				encryptMessage1(ntruKeys, prng, msg, id);
				System.out.println("Encrypted: " + new String(encryptedBuf));
				String server = (String) lo.get(count);
				try (FileOutputStream fos = new FileOutputStream(
						"F:/Project/ODR/Project/ODR/web/Server/" + server + "/" + PART_NAME)) {
					fos.write(encryptedBuf);
				}

				nameList.add(server);
				System.out.println("Total Bytes Read: " + totalBytesRead);

				count = count + 1;

			}

		} catch (IOException ex) {
			System.err.println("" + ex.getMessage());
		}
		return nameList;
	}

	static void write(String DataByteArray, String DestinationFileName) {
		try {
			OutputStream output = null;
			FileWriter writer = new FileWriter(DestinationFileName, true);

			try {
				output = new BufferedOutputStream(new FileOutputStream(DestinationFileName));
				writer.write(DataByteArray);
				System.out.println("Writing Process Was Performed");
			} finally {
				output.close();
				writer.close();
			}
		} catch (IOException ex) {
			System.err.println("" + ex.getMessage());
		}
	}

}
