/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Leonov.Menus;

import Leonov.Controllers.Ruta;
import Leonov.Controllers.Waypoint;
import Leonov.Models.Waypoint_Dades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author gmartinez
 */
public class Krona {
    /*
    SISTEMA DE NAVEGACIÓ BASAT EN WAYPOINTS.
    ES DONEN D'ALTA DIVERSOS WAYPOINTS DE L'ESPAI (ORBITA MARCIANA, PUNT LAGRANGE TERRA-LLUNA, PHOBOS, SATURN, LLUNA,...).
    ES PODEN MEMORITZAR DIVERSES RUTES AFEGINT DIVERSOS WAYPOINTS A CADA RUTA.
    
    */

    public static void bloquejarPantalla() {
        Scanner in = new Scanner(System.in);
        System.out.print("Toca 'C' per a continuar ");
        while (in.hasNext()) {
            if ("C".equalsIgnoreCase(in.next())) break;
        }
    }


    public static void menuKrona() throws IOException {
        String opcio;
        Scanner sc = new Scanner(System.in);
        StringBuilder menu = new StringBuilder("");

        ArrayList<Waypoint_Dades> llistaWaypoints = new ArrayList<Waypoint_Dades>();

        do {
            menu.delete(0, menu.length());

            menu.append(System.getProperty("line.separator"));
            menu.append("RV-18A Krona");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));

            menu.append("1. Carregar en memòria els waypoints");
            menu.append(System.getProperty("line.separator"));
            menu.append("2. Carregar en la BD els waypoints carregats en memòria");
            menu.append(System.getProperty("line.separator"));
            menu.append("3. LListar els waypoints");
            menu.append(System.getProperty("line.separator"));
            menu.append("4. Afegir un nou waypoint");
            menu.append(System.getProperty("line.separator"));
            menu.append("5. Modificar un waypoint");
            menu.append(System.getProperty("line.separator"));
            menu.append("6. Esborrar de la BD tots els waypoints");
            menu.append(System.getProperty("line.separator"));
            menu.append("7. Ordena i visualitzar els waypoints");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));

            menu.append("10. Carregar en la BD les rutes");
            menu.append(System.getProperty("line.separator"));
            menu.append("11. Carregar en la BD les rutes carregades en memòria");
            menu.append(System.getProperty("line.separator"));
            menu.append("12. LListar les rutes (només amb els ID dels waypoints)");
            menu.append(System.getProperty("line.separator"));
            menu.append("13. LListar les rutes(amb tota la informació dels waypoints)");
            menu.append(System.getProperty("line.separator"));
            menu.append("14. LListar els waypoints d'una ruta");
            menu.append(System.getProperty("line.separator"));
            menu.append("15. Crear una ruta");
            menu.append(System.getProperty("line.separator"));
            menu.append("16. Modificar una ruta");
            menu.append(System.getProperty("line.separator"));
            menu.append("17. LListar les rutes que tinguin un waypoint concret");
            menu.append(System.getProperty("line.separator"));
            menu.append("18. Donar de baixa una ruta");
            menu.append(System.getProperty("line.separator"));
            menu.append("19. Esborrar de la BD una ruta");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));

            menu.append("20. Fer una copia de seguretat de la BD");
            menu.append(System.getProperty("line.separator"));
            menu.append("21. Esborrar tota la BD");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));

            menu.append("50. Tornar al menú pare (PNS-24 Puma)");
            menu.append(System.getProperty("line.separator"));


            System.out.print(MenuConstructorPantalla.constructorPantalla(menu));

            opcio = sc.next();

            switch (opcio) {
                case "1":
                    llistaWaypoints = Waypoint.menu1();
                    bloquejarPantalla();
                    break;
                case "2":
                    Waypoint.menu2(llistaWaypoints);
                    bloquejarPantalla();
                    break;
                case "3":
                    Waypoint.menu3();
                    bloquejarPantalla();
                    break;
                case "4":

                    bloquejarPantalla();
                    break;
                case "5":
                    Waypoint.menu5();
                    bloquejarPantalla();
                    break;
                case "6":

                    bloquejarPantalla();
                    break;
                case "7":
                    Waypoint.menu7();
                    bloquejarPantalla();
                    break;
                case "10":

                    bloquejarPantalla();
                    break;
                case "12":
                    Ruta.menu12();
                    bloquejarPantalla();
                    break;
                case "13":
                    Ruta.menu13();
                    bloquejarPantalla();
                    break;
                case "15":
                    Ruta.menu15();
                    bloquejarPantalla();
                    break;
                case "19":
                    Ruta.menu19();
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
