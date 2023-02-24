import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

public class Decrypt {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static String keyString = "";
    private static String ivString = "";

    public static void main(String[] args) {
        System.out.println("This decrypter will decrypt the files ending in .encrypted versions");
        System.out.println("If the program finds key.txt it will automatically decrypt files, else you will enter the key and the IV in string format (16 characters each)");
        System.out.println("And to be extra secure you can save the encrypted files as it is hard to recover them if you decrypt with the wrong key.");
        boolean keyfile = false;
        byte[] keyBytes = null;
        byte[] ivBytes = null;
        boolean permissions = true;
        Scanner in = new Scanner(System.in);

        try {
            File file = new File("key.txt");
            if(file.isFile()) {
                keyfile = true;
                FileInputStream inputStream = new FileInputStream(file);
                Scanner scanner = new Scanner(inputStream);
                String line = scanner.nextLine();
                keyString = line.substring(13);
                line = scanner.nextLine();
                ivString = line.substring(4);
                scanner.close();
                inputStream.close();

                keyBytes = keyString.getBytes();
                ivBytes = ivString.getBytes();
                System.out.println("key.txt found. Key: " + keyString + " IV: " + ivString);
                System.out.println("Write \"n\" to input key and iv yourself everything else will use the above key-IV.");

                String x = in.nextLine();
                if  (x.equals("n"))
                    permissions = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!(keyfile && permissions)){
            System.out.print("Enter encryption key: ");
            keyString = in.nextLine();
            System.out.print("Enter IV: ");
            ivString = in.nextLine();
            keyBytes = keyString.getBytes();
            ivBytes = ivString.getBytes();
        }
        in.close();
        File folder = new File(".");
        processFolder(folder, keyBytes, ivBytes);
    }

    public static void processFolder(File folder, byte[] keyBytes, byte[] ivBytes) {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && !file.getName().endsWith(".java") && !file.getName().endsWith(".class")) {
                decryptFile(file, keyBytes, ivBytes);
                
            }
            if (file.isDirectory()) {
                processFolder(file, keyBytes, ivBytes);
            }
            if(file.getName().endsWith(".class"))
                file.delete();
        }
    }

    public static void decryptFile(File file, byte[] keyBytes, byte[] ivBytes) {
        try {
            Key key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));

            FileInputStream inputStream = new FileInputStream(file);
            byte[] inputBytes = new byte[(int) file.length()];
            inputStream.read(inputBytes);

            byte[] decodedBytes = Base64.getDecoder().decode(inputBytes);
            byte[] outputBytes = cipher.doFinal(decodedBytes);

            String outputFilepath = file.getAbsolutePath().replace(".encrypted", "");
            FileOutputStream outputStream = new FileOutputStream(outputFilepath);
            outputStream.write(outputBytes);
            outputStream.close();
            inputStream.close();

            System.out.println("File decrypted: " + outputFilepath);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
