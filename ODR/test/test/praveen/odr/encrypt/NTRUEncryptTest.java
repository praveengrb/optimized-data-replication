package test.praveen.odr.encrypt;

import praveen.odr.encrypt.exceptions.NtruException;
import praveen.odr.encypt.OID;
import praveen.odr.encypt.Random;
import praveen.odr.encypt.RandomExtractor;
import praveen.odr.encrypt.inputstream.X982Drbg;
import praveen.odr.encrypt.NtruEncryptKey;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author d_win
 */
public class NTRUEncryptTest {

    private static byte ivBytes[];
    private static byte ivBytes1[];
    private static byte encryptedBuf[];
    private static byte wrappedAESKey[];
    private static Random Random1;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NtruException {
        String decrypted = "";
        String pubkeyFile = "public_Key";
        String privkeyFile = "private_Key";
        Random prng = createSeededRandom();
        X982Drbg j = RandomExtractor.extractRNG(prng);
        System.out.println(j);
        int CHUNK_SIZE = 0;
        OID oid = parseOIDName("ees1499ep1");
        System.out.println(oid);
        Random1 = prng;

        System.out.println(Random1);
        NtruEncryptKey ntruKeys = setupNtruEncryptKey(Random1, oid, pubkeyFile, privkeyFile);
        //log ("File Is Reading "+ SourceFileName );
        File willBeRead = new File("D://weather.txt");
        int FILE_SIZE = (int) willBeRead.length();

        ArrayList<String> nameList = new ArrayList<String>();

        System.out.println("Total File Size: " + FILE_SIZE);

        int NUMBER_OF_CHUNKS = 0;
        byte[] temporary = null;
        CHUNK_SIZE = FILE_SIZE / 10;

        InputStream inStream = null;
        int totalBytesRead = 0;

        try {
            inStream = new BufferedInputStream(new FileInputStream(willBeRead));

            while (totalBytesRead < FILE_SIZE) {
                String PART_NAME = "data" + NUMBER_OF_CHUNKS + ".txt";
                int bytesRemaining = FILE_SIZE - totalBytesRead;
                if (bytesRemaining < CHUNK_SIZE) // Remaining Data Part is Smaller Than CHUNK_SIZE
                // CHUNK_SIZE is assigned to remain volume
                {
                    CHUNK_SIZE = bytesRemaining;
                    System.out.println("CHUNK_SIZE: " + CHUNK_SIZE);
                }

                temporary = new byte[CHUNK_SIZE]; //Temporary Byte Array
                int bytesRead = inStream.read(temporary, 0, CHUNK_SIZE);

                if (bytesRead > 0) // If bytes read is not empty
                {
                    totalBytesRead += bytesRead;
                    NUMBER_OF_CHUNKS++;
                }

                String msg = new String(temporary);

                encryptMessage(ntruKeys, prng, msg);
                System.out.println("Encrypted: " + new String(encryptedBuf));
                FileOutputStream fos = new FileOutputStream("E://sam//" + PART_NAME);
                fos.write(encryptedBuf);
                fos.close();
                Path path = Paths.get("D://example.txt");
                byte[] data = Files.readAllBytes(path);

                Random prng1 = new Random(data);
                NtruEncryptKey ntruKeys1 = setupNtruEncryptKey(prng1, oid, pubkeyFile, privkeyFile);
                Path path1 = Paths.get("E://sam//" + PART_NAME);
                byte[] data1 = Files.readAllBytes(path1);

                String kk = decryptMessage(ntruKeys1, new String(data1));
                System.out.println("Decrypted: " + kk);
                decrypted = decrypted + kk;
            }

        } catch (IOException | NtruException ex) {
            Logger.getLogger(NTRUEncryptTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(decrypted);
    }

    static Random createSeededRandom() throws FileNotFoundException, IOException {
        byte seed[] = new byte[32];
        java.util.Random sysRand = new java.util.Random();
        sysRand.nextBytes(seed);
        System.out.println(seed);
        FileOutputStream fos = new FileOutputStream("D://example.txt");
        fos.write(seed);
        fos.close();
        Random prng = new Random(seed);
        return prng;
    }

    static OID parseOIDName(
            String requestedOid) {
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

    public static NtruEncryptKey setupNtruEncryptKey(Random prng, OID oid, String pubFileName, String privFileName) throws IOException, NtruException {
        NtruEncryptKey k = NtruEncryptKey.genKey(oid, prng);

        FileOutputStream pubFile = new FileOutputStream(pubFileName);
        pubFile.write(k.getPubKey());
        pubFile.close();

        FileOutputStream privFile = new FileOutputStream(privFileName);
        privFile.write(k.getPrivKey());
        privFile.close();

        return k;
    }

    public static String encryptMessage(NtruEncryptKey ntruKey, Random prng, String message) throws NtruException, FileNotFoundException, IOException {
        String output = "";
        byte buf[] = message.getBytes();

        try {
            // Get an AES key
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128);
            SecretKey aesKey = keygen.generateKey();
            byte[] encoded = aesKey.getEncoded();
            System.out.println("key: " + encoded);
            FileOutputStream fos = new FileOutputStream("D://encrypt.txt");
            fos.write(encoded);
            fos.close();
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
        ivBytes1 = ivBytes;
        return output;
    }

    public static String decryptMessage(NtruEncryptKey ntruKey, String cipherText) throws NtruException, IOException {

        byte encFileContents[] = encryptedBuf;
        byte fileContents[] = null;
        try {

            Path path = Paths.get("D://encrypt.txt");
            byte[] data = Files.readAllBytes(path);
            Path path1 = Paths.get("D://example.txt");
            byte[] data1 = Files.readAllBytes(path1);

            Random prng1 = new Random(data1);
            wrappedAESKey = ntruKey.encrypt(data, prng1);
            byte wrappedKey[] = wrappedAESKey;
            // Unwrap the AES key
            byte aesKeyBytes[] = ntruKey.decrypt(wrappedKey);
            SecretKeySpec aesKey = new SecretKeySpec(aesKeyBytes, "AES");
            java.util.Arrays.fill(aesKeyBytes, (byte) 0);

            // Decrypt the file contents
            IvParameterSpec iv = new IvParameterSpec(ivBytes1);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, aesKey, iv);
            fileContents = cipher.doFinal(encFileContents);
        } catch (java.security.GeneralSecurityException e) {
            System.out.println("AES error: " + e);
        }
        return new String(fileContents);
    }

}
