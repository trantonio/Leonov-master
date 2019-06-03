package Leonov.Controllers;

import Leonov.Auxiliars.MetodesAuxiliars;
import Leonov.Models.Waypoint_Dades;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Calendar;
import java.util.List;

public class Encriptacio {

    public static SecretKey generadorDeClausSimetriques(int clauTamany) {
        SecretKey sKey = null;
        if ((clauTamany == Info.SIZE_128) || (clauTamany == Info.SIZE_192) || (clauTamany == Info.SIZE_256)) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(clauTamany);
                sKey = kgen.generateKey();
            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador de claus simetriques no disponible.");
                ex.printStackTrace();
            }
        }
        System.out.println("Clau generada amb éxit.\n");
        return sKey;
    }

    public KeyPair generadorDeClausAsimetriques(int clauTamany) {
        KeyPair keys = null;

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(clauTamany);
            keys = keyGen.genKeyPair();
        } catch (Exception ex) {
            System.err.println("Generador de claus asimètriques no disponible.");
            ex.printStackTrace();
        }
        return keys;
    }

    // Encriptar cadena de text amb AES de 128 bits
    public static byte[] menu2(SecretKey sKey) {
        byte[] encryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(Info.IV_PARAM);
            cipher.init(Cipher.ENCRYPT_MODE, sKey, iv);
            encryptedData = cipher.doFinal(Utils.stringToBytes(Info.dadesAEncriptarEnString));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            System.out.println("Encriptador AES de 128 bits no disponible");
            e.printStackTrace();
        }

        System.out.println("Dades a encriptar en String:\n" + Info.dadesAEncriptarEnString);
        System.out.println("\nDades encriptades en String:\n" + Utils.bytesToString(encryptedData));
        return encryptedData;
    }

    // Desencriptar una cadena de text encriptada amb AES de 128 bits
    public static void menu3(SecretKey sKey, byte[] bytes) {
        byte[] decriptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(Info.IV_PARAM);
            cipher.init(Cipher.DECRYPT_MODE, sKey, iv);
            decriptedData = cipher.doFinal(bytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Desencriptador AES de 128 bits no disponible");
            e.printStackTrace();
        }

        System.out.println("Dades desencriptades:\n" + Utils.bytesToString(decriptedData));
    }

    // Encriptar cadena de text amb RSA amb clau embolcallada
    public static byte[][] menu12(SecretKey sKey, KeyPair keyPair) {
        byte[][] encWrappedData = new byte[2][];

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(Info.IV_PARAM);
            cipher.init(Cipher.ENCRYPT_MODE, sKey, iv);
            byte[] encMsg = cipher.doFinal(Utils.stringToBytes(Info.dadesAEncriptarEnString));

            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.WRAP_MODE, keyPair.getPublic());
            byte[] encKey = cipher.wrap(sKey);
            encWrappedData[0] = encMsg;
            encWrappedData[1] = encKey;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        System.out.println("Dades a encriptar en String:\n" + Info.dadesAEncriptarEnString);
        System.out.println("\nDades encriptades en String:\n" + Utils.bytesToString(encWrappedData[0]));
        return encWrappedData;
    }


    public void menu13(KeyPair keyPair, byte[][] encWrappedData) {
        byte[] decriptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.UNWRAP_MODE, keyPair.getPrivate());
            Key decKey = cipher.unwrap(encWrappedData[1], "AES", Cipher.SECRET_KEY);

            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(Info.IV_PARAM);
            cipher.init(Cipher.DECRYPT_MODE, decKey, iv);
            decriptedData = cipher.doFinal(encWrappedData[0]);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        System.out.println("Dades desencriptades:\n"+Utils.bytesToString(decriptedData));
    }

    public void menu21(SecretKey sKey) {
        byte[] data = Info.getCrewDB();

        Utils.checkPath(Info.DATA_PATH);
        String encData = Info.DATA_PATH+"/encData_AES.txt";

        try {
            // Preparem l'encriptació de les dades.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(Info.IV_PARAM);
            cipher.init(Cipher.ENCRYPT_MODE, sKey, iv);

            // Encriptem les dades amb AES en mode CBC directament a un fitxer.
            CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(encData), cipher);
            cos.write(data);
            cos.flush();
            cos.close();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IOException e) {
            e.printStackTrace();
        }
    }

    public void menu22(SecretKey sKey, KeyPair keyPair) {
        byte[] data = Info.getCrewDB();
        byte[] clauAESEncriptadaEnByte;

        Utils.checkPath(Info.DATA_PATH);
        String encData = Info.DATA_PATH+"/encData_RSA.txt";
        String encKey = Info.DATA_PATH+"/encKey_RSA.txt";

        try {
            // Preparem l'encriptació de les dades.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(Info.IV_PARAM);
            cipher.init(Cipher.ENCRYPT_MODE, sKey, iv);

            // Encriptem les dades amb AES en mode CBC directament a un fitxer.
            CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(encData), cipher);
            cos.write(data);
            cos.close();

            // Encriptem la clau d'encriptació que s'ha fet servir amb RSA.
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.WRAP_MODE, keyPair.getPublic());

            clauAESEncriptadaEnByte = cipher.wrap(sKey);

            // Guardem la clau xifrada a un fitxer diferent a les dades.
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(encKey));
            bos.write(clauAESEncriptadaEnByte);
            bos.close();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IOException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    public void menu31(SecretKey sKey) throws FileNotFoundException {
        byte[] data;
        String encData = Info.DATA_PATH+"/encData_AES.txt";
        String decData = Info.DATA_PATH+"/decData_AES.txt";

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(Info.IV_PARAM);
            cipher.init(Cipher.DECRYPT_MODE, sKey, iv);

            CipherInputStream cis = new CipherInputStream(new FileInputStream(encData), cipher);
            BufferedReader br = new BufferedReader(new InputStreamReader(cis));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                result.append(line).append("\n"); // TODO Improve
            }
            result.deleteCharAt(result.length() - 1);
            System.out.println(result.toString());

            FileOutputStream fos = new FileOutputStream(decData);
            fos.write(Utils.stringToBytes(result.toString()));
            fos.close();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | IOException e) {
            e.printStackTrace();
        }
    }
}

class Info {
    public static final int SIZE_128 = 128;
    public static final int SIZE_192 = 192;
    public static final int SIZE_256 = 256;
    public static final int SIZE_512 = 512;
    public static final int SIZE_1024 = 1024;
    public static final int SIZE_2048 = 2048;
    public static final File DATA_PATH = new File("data/encriptacio/");
    public static final String dadesAEncriptarEnString = "El RT-2UTTH Tópol-M (en ruso Тополь-М) es un misil balístico intercontinental de fabricación rusa.\n" +
            "Según expertos es capaz de evadir el Sistema Antimisiles de EE. UU. debido a su fase de impulsión ultrarrápida, la pronta liberación de\n" +
            "sus cabezas nucleares, la capacidad de sus ojivas para maniobrar en la fase terminal y otras técnicas especiales.\n" +
            "El Bulava (SS-NX-30) es la versión del Topol-M para submarinos estratégicos, puede ser lanzado desde los nuevos submarinos de Rusia.\n" +
            "El misil se halla montado sobre un vehículo que se construye en la fábrica de tractores de Minsk de Ucrania y Rusia. El peso del\n" +
            "vehículo de transporte con el misil supera las 90 toneladas. El vehículo es de ocho puentes de tracción con un motor diésel eléctrico\n" +
            "de 800 CV. La velocidad máxima del vehículo es 75 km/h y la autonomía es de 500 kilómetros.\n" +
            "Según datos correspondientes a enero de 2015, estaban en servicio 78 misiles «Topol-M» emplazados en silos subterráneos, hangares con\n" +
            "el techo removible y camiones de transporte, en diferentes lugares de Rusia. A partir de 2010, se pasó a su sucesor el RS-24 Yars.";
    public static final byte[] IV_PARAM = {
            0x00, 0x04, 0x08, 0x0C,
            0x01, 0x05, 0x09, 0x0D,
            0x02, 0x06, 0x0A, 0x0E,
            0x03, 0x07, 0x0B, 0x0F};
    public static byte[] getCrewDB() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");
        StringBuilder result = new StringBuilder();

        try {
            List<Waypoint_Dades> waypoints = MetodesAuxiliars.listWaypont(db);
            for (Waypoint_Dades waypoint : waypoints) {
                result.append(waypoint).append("\n");
            }
        } finally {
            db.close();
        }

        return Utils.stringToBytes(result.toString());
    }
}

class Utils {
    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] stringToBytes(String string) {
        return string.getBytes();
    }

    public static void checkPath(File path) {
        if (!path.exists()) {
            if (path.mkdirs()) {
                System.out.println("PATH CREAT!");
            }
        }
    }
}