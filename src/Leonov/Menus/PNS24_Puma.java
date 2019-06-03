/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Leonov.Menus;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 *
 * @author gines
 */
public class PNS24_Puma {
    public static void bloquejarPantalla() {
        Scanner in = new Scanner(System.in);
        System.out.print("Toca 'C' per a continuar ");
        while (in.hasNext()) {
            if ("C".equalsIgnoreCase(in.next())) break;
        }
    }
    
    
    static void menuPNS24Puma() throws IOException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        String opcio;
        StringBuilder menu = new StringBuilder("");
        Scanner sc = new Scanner(System.in);
        
        do {/*
            System.out.println("---------------- PNS-24 Puma ----------------");
            System.out.println("1. Sistema de navegació Krona");
            System.out.println("2. Sistema d'atac Orion");
            System.out.println("3. Sistema de defensa Sirena");
            System.out.println("4. Sistema de comunicacions Evkalipt");
            System.out.println("5. Sistema d'identificació Khom-Nikel");
            System.out.println("6. Sistema de visualització PPV");
            System.out.println("7. Sistema de grabació Kristall");
            System.out.println();
            System.out.println("10. Control de sondes Delta");
            System.out.println();
            System.out.println("50. Tancar el sistema");
            System.out.println();
            System.out.print("opció?: ");*/
            
            menu.delete(0, menu.length());
                     
            menu.append(System.getProperty("line.separator"));
            menu.append("PNS-24 Puma");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));
            menu.append("1. Sistema de navegació Krona");
            menu.append(System.getProperty("line.separator"));
            menu.append("2. Sistema d'atac Orion");
            menu.append(System.getProperty("line.separator"));
            menu.append("3. Sistema de defensa Sirena");
            menu.append(System.getProperty("line.separator"));
            menu.append("4. Sistema de comunicacions Evkalipt");
            menu.append(System.getProperty("line.separator"));
            menu.append("5. Sistema d'identificació Khom-Nikel");
            menu.append(System.getProperty("line.separator"));
            menu.append("6. Sistema de visualització PPV");
            menu.append(System.getProperty("line.separator"));
            menu.append("7. Sistema de grabació Kristall");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));
            menu.append("10. Control de sondes Delta");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));
            menu.append("50. Tancar el sistema");
            menu.append(System.getProperty("line.separator"));
            
            System.out.print(MenuConstructorPantalla.constructorPantalla(menu));
            
            opcio = sc.next();
            
            switch (opcio) {
                case "1":
                    Krona.menuKrona();
                    bloquejarPantalla();
                    break;
                case "2":
                    
                    bloquejarPantalla();
                    break;
                case "3":
                    
                    bloquejarPantalla();
                    break;
                case "4":

                    bloquejarPantalla();
                    break;
                case "5":
                    
                    bloquejarPantalla();
                    break;
                case "6":
                    
                    bloquejarPantalla();
                    break;
                case "7":
                    Kristall.menuKristall();
                    
                    bloquejarPantalla();
                    break;
                case "10":

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
