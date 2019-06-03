package Leonov.Menus;

import Leonov.Controllers.Encriptacio;

import java.io.IOException;
import java.security.KeyPair;
import java.util.LinkedList;
import java.util.Scanner;
import javax.crypto.SecretKey;

/**
 *
 * @author gmartinez
 */
public class Kristall {
    public static void bloquejarPantalla() {
        Scanner in = new Scanner(System.in);
        System.out.print("Toca 'C' per a continuar ");
        while (in.hasNext()) {
            if ("C".equalsIgnoreCase(in.next())) break;
        }
        System.out.println();
    }


    static void menuKristall() throws IOException  {
        SecretKey clauSecretaSimetrica = null;
        byte[] dadesEncriptadesEnByte = null;
        KeyPair clauPublicaIPrivada = null;
        byte[][] dadesIClauEncriptadesEnByte = new byte[2][];

        String opcio;
        Scanner sc = new Scanner(System.in);
        Encriptacio objEncriptacio = new Encriptacio();

        do {
            System.out.println("---------------- Kristall ----------------");
            System.out.println("   ENCRIPTACIÓ SIMÈTRICA (AES 128 bits)");
            System.out.println("1. Generar clau simètrica");
            System.out.println("2. Encriptar cadena de text amb AES de 128 bits");
            System.out.println("3. Desencriptar cadena de text amb AES de 128 bits");
            System.out.println();
            System.out.println("    ENCRIPTACIÓ ASIMÈTRICA (RSA amb clau embolcallada)");
            System.out.println("11. Generar clau simètrica i público-privades");
            System.out.println("12. Encriptar cadena de text amb RSA amb clau embolcallada");
            System.out.println("13. Desencriptar cadena de text amb RSA amb clau embolcallada");
            System.out.println("14. Emmagatzemar clau asimètrica en fitxer de claus");
            System.out.println();
            System.out.println("    ENCRIPTACIÓ DE DADES (cap a fitxer)");
            System.out.println("21. Encriptar amb AES 128 bits els tripulants");
            System.out.println("22. Encriptar amb RSA amb clau embolcallada els tripulants");
            System.out.println();
            System.out.println("    DESENCRIPTACIÓ DE DADES (desde fitxer)");
            System.out.println("31. Desencriptar amb AES 128 bits els tripulants");
            System.out.println("32. Desencriptar amb RSA amb clau embolcallada els tripulants");
            System.out.println();
            System.out.println("    EMMAGATZEMATGE/DESEMAGATZEMATGE DE LES CLAUS (a/desde fitxer)");
            System.out.println("41. Emmagatzemar una clau simètrica en fitxer de claus encriptat amb clau a partir de contrasenya");
            System.out.println("42. Visualitza per pantalla les claus simètriques del fitxer de claus encriptat");
            System.out.println();
            System.out.println("50. Tornar al menú pare (PNS-24 Puma)");
            System.out.println();
            System.out.print("opció?: ");
            opcio = sc.next();

            switch (opcio) {
                case "1":
                    clauSecretaSimetrica = Encriptacio.generadorDeClausSimetriques(128);
                    bloquejarPantalla();
                    break;
                case "2":
                    dadesEncriptadesEnByte = Encriptacio.menu2(clauSecretaSimetrica);
                    bloquejarPantalla();
                    break;
                case "3":
                    Encriptacio.menu3(clauSecretaSimetrica, dadesEncriptadesEnByte);
                    bloquejarPantalla();
                    break;
                case "11":
                    clauSecretaSimetrica = objEncriptacio.generadorDeClausSimetriques(128);
                    System.out.println("clauSecretaSimetrica?: " + clauSecretaSimetrica);
                    clauPublicaIPrivada = objEncriptacio.generadorDeClausAsimetriques(1024);
                    System.out.println("clauPublicaIPrivada?: " + clauPublicaIPrivada);
                    bloquejarPantalla();
                    break;
                case "12":
                    dadesIClauEncriptadesEnByte = objEncriptacio.menu12(clauSecretaSimetrica, clauPublicaIPrivada);
                    bloquejarPantalla();
                    break;
                case "13":
                    objEncriptacio.menu13(clauPublicaIPrivada, dadesIClauEncriptadesEnByte);
                    bloquejarPantalla();
                    break;
                case "14":
                    bloquejarPantalla();
                    break;
                case "21":
                    if (clauSecretaSimetrica != null) {
                        objEncriptacio.menu21(clauSecretaSimetrica);
                    }
                    bloquejarPantalla();
                    break;
                case "22":
                    if ((clauSecretaSimetrica != null) && (clauPublicaIPrivada != null)) {
                        objEncriptacio.menu22(clauSecretaSimetrica, clauPublicaIPrivada);
                    }
                    bloquejarPantalla();
                    break;
                case "31":
                    if (clauSecretaSimetrica != null) {
                        objEncriptacio.menu31(clauSecretaSimetrica);
                    }
                    bloquejarPantalla();
                    break;
                case "32":
                    if (clauPublicaIPrivada != null) {
                        //objEncriptacio.menu32(clauPublicaIPrivada);
                    }
                    bloquejarPantalla();
                    break;
                case "41":
                    if (clauSecretaSimetrica != null) {
                        //objEncriptacio.menu41(clauSecretaSimetrica);
                    }
                    bloquejarPantalla();
                    break;
                case "42":
                    //objEncriptacio.menu42();
                    bloquejarPantalla();
                    break;
                case "50":
                    break;
                default:
                    System.out.println("COMANDA NO RECONEGUDA");
            }
        } while (!opcio.equals("50"));
    }
}
