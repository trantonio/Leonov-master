/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Leonov.Controllers;

import Leonov.Auxiliars.MetodesAuxiliars;
import Leonov.Comparators.WaypointComparator;
import Leonov.Models.Waypoint_Dades;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
/*
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
*/

import java.util.*;

/**
 * @author gmartinez
 */
public class Waypoint {

    //1. Carregar en memòria els waypoints 
    public static ArrayList<Waypoint_Dades> menu1() {
        int[] coordenadesTmp = null;
        Calendar dataCreacio = Calendar.getInstance();
        ArrayList<Waypoint_Dades> llistaWaypoints = new ArrayList<Waypoint_Dades>();


        //coordenadesTmp[0] = 0;
        //coordenadesTmp[1] = 0;
        //coordenadesTmp[2] = 0;
        coordenadesTmp = new int[]{0, 0, 0};
        llistaWaypoints.add(new Waypoint_Dades(0, "Òrbita de la Terra", coordenadesTmp, true, dataCreacio, null, null));

        coordenadesTmp = new int[]{1, 1, 0};
        llistaWaypoints.add(new Waypoint_Dades(1, "Punt Lagrange Terra-LLuna", coordenadesTmp, true, dataCreacio, null, null));

        coordenadesTmp = new int[]{2, 2, 0};
        llistaWaypoints.add(new Waypoint_Dades(2, "Òrbita de la LLuna", coordenadesTmp, true, dataCreacio, null, null));

        coordenadesTmp = new int[]{10, 10, 5};
        llistaWaypoints.add(new Waypoint_Dades(3, "Òrbita de Mart", coordenadesTmp, true, dataCreacio, null, null));

        coordenadesTmp = new int[]{100, 20, 10};
        llistaWaypoints.add(new Waypoint_Dades(4, "Òrbita de Júpiter", coordenadesTmp, true, dataCreacio, null, null));

        coordenadesTmp = new int[]{101, 21, 10};
        llistaWaypoints.add(new Waypoint_Dades(5, "Punt Lagrange Júpiter-Europa", coordenadesTmp, true, dataCreacio, null, null));

        coordenadesTmp = new int[]{101, 21, 10};
        llistaWaypoints.add(new Waypoint_Dades(6, "Òrbita de Europa", coordenadesTmp, true, dataCreacio, null, null));

        coordenadesTmp = new int[]{10, 10, 5};
        llistaWaypoints.add(new Waypoint_Dades(7, "Òrbita de Venus", coordenadesTmp, true, dataCreacio, null, null));

        return llistaWaypoints;
    }

    public static void menu2(ArrayList<Waypoint_Dades> llistaWaypoints) {

        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");


        try {


            for (Waypoint_Dades waypointDades : llistaWaypoints) {
                Predicate p = new Predicate<Waypoint_Dades>() {
                    @Override
                    public boolean match(Waypoint_Dades c) {
                        return (c.getId() == waypointDades.getId());
                    }
                };

                ObjectSet<Waypoint_Dades> result = db.query(p);


                if (result.size() != 0) {

                    Waypoint_Dades tipus = result.next();
                    tipus.setId(waypointDades.getId());
                    tipus.setNom(waypointDades.getNom());
                    tipus.setActiu(waypointDades.isActiu());
                    tipus.setDataCreacio(waypointDades.getDataCreacio());
                    tipus.setDataAnulacio(waypointDades.getDataAnulacio());
                    db.store(tipus);
                } else {

                    db.store(waypointDades);
                }
            }
        } finally {
            db.close();
        }

        System.out.println("menu2(): FINAL");


    }

    public static void menu3() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");

        try {
            List<Waypoint_Dades> allWaypoint = MetodesAuxiliars.listWaypont(db);

            for (Waypoint_Dades waypointDades : allWaypoint) {
                System.out.println(waypointDades);
            }
        } finally {
            db.close();
        }

        System.out.println("menu3(): FINAL");

    }

    public static void menu5() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");

        Scanner in = new Scanner(System.in);

        try {

            Map<Integer, Waypoint_Dades> map = new HashMap<>();
            for (Waypoint_Dades tipusMissatges_dades : MetodesAuxiliars.listWaypont(db)) {
                map.put(tipusMissatges_dades.getId(), tipusMissatges_dades);
            }
            System.out.println("Llista de tots waypoints:");
            map.forEach((k, v) -> System.out.println(v));

            // Recull un id i si no existeix retorna error
            System.out.print("Sel.lecciona un waypoint (ID): ");
            int id = -1;
            try {
                id = Integer.parseInt(in.nextLine());
                if (!map.containsKey(id)) throw new Exception();
            } catch (Exception e) {
                System.out.println("Error: no has inserit un ID vàlid");
                return;
            }

            // Inicia edició

            Waypoint_Dades tmp = map.get(id);

            System.out.println("Nom : ");
            String nom = in.nextLine();
            tmp.setNom(nom);
            System.out.println("Coordenada X");
            int[] coordenadas = new int[3];
            coordenadas[0] = Integer.parseInt(in.nextLine());
            System.out.println("Coordenada Y");
            coordenadas[1] = Integer.parseInt(in.nextLine());
            System.out.println("Coordenada Z");
            coordenadas[2] = Integer.parseInt(in.nextLine());
            tmp.setCoordenades(coordenadas);

            boolean actiu;

            boolean acituGood = false;
            do {
                System.out.println("Actiu (S/N)");
                String actiuString = in.nextLine();

                if (actiuString.equalsIgnoreCase("s")) {
                    acituGood = true;
                    actiu = true;
                    tmp.setActiu(actiu);
                } else if (actiuString.equalsIgnoreCase("n")) {
                    acituGood = true;
                    actiu = false;
                    tmp.setActiu(actiu);
                } else {
                    System.out.println("Escriu S/N, no continuarem fins que ho faci");
                }
            } while (!acituGood);

            db.store(tmp);
            System.out.println("\t WAYPOINT " + tmp.getId() + ", UPDATEJAT");

        } finally {
            db.close();
        }

        System.out.println("menu5(): FINAL");

    }

    public static void menu7() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");

        System.out.println("1. En funció de la distància a la qual es troben de la Terra. \n" +
                "2. Alfabèticament." +
                "\n3. Per data de modificació (descendent).");

        Scanner in = new Scanner(System.in);
        System.out.println("Escolliu el tipus d'ordenacio");

        int ordenacio = Integer.parseInt(in.nextLine());

        try {
            List<Waypoint_Dades> allWaypoint = MetodesAuxiliars.listWaypont(db);
            ArrayList<Waypoint_Dades> arrayList = new ArrayList<>(allWaypoint);
            WaypointComparator waypointComparator = new WaypointComparator(ordenacio);
            Collections.sort(arrayList, waypointComparator);

            for (Waypoint_Dades waypointDades : arrayList) {
                System.out.println(waypointDades);
            }
        } finally {
            db.close();
        }


        System.out.println("menu7(): FINAL");

    }


}
