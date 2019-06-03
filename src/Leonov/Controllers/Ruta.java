package Leonov.Controllers;

import Leonov.Auxiliars.MetodesAuxiliars;
import Leonov.Models.Ruta_Dades;
import Leonov.Models.Waypoint_Dades;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
/*
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
*/

import java.util.*;

public class Ruta {


    public static void menu12() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");

        try {
            List<Ruta_Dades> allRoutes = MetodesAuxiliars.listRoutes(db);

            for (Ruta_Dades ruta : allRoutes) {
                System.out.println(ruta);
            }
        } finally {
            db.close();
        }

        System.out.println("menu12(): FINAL");

    }

    public static void menu13() {

        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");
        List<Ruta_Dades> allRoutes = null;
        try {
            allRoutes = MetodesAuxiliars.listRoutes(db);
            System.out.println(allRoutes.size());

            for (Ruta_Dades ruta : allRoutes) {
                System.out.println("yep");
                String rutaString = "Ruta " + ruta.getId() + "\n" +
                        "\t nom = " + ruta.getNom() + "\n" +
                        "\t actiu = " + ruta.isActiu() + "\n" +
                        "\t dataCreacio = " + MetodesAuxiliars.formatCalendar(ruta.getDataCreacio()) + "\n" +
                        "\t dataAnulacio = " + MetodesAuxiliars.formatCalendar(ruta.getDataAnulacio()) + "\n" +
                        "\t dataModificacio = " + MetodesAuxiliars.formatCalendar(ruta.getDataModificacio()) + "\n";
                System.out.println(rutaString);


                for (Integer integer : ruta.getWaypoints()) {

                    String rutaString2 = "\t " + visualitzarDadesDUnWaypoint(integer, db) + "\n";
                    System.out.println(rutaString2);
                }
            }
        } finally {
            db.close();
        }

        System.out.println("menu13(): FINAL");


    }

    public static void menu15() {
        int id = trobarNouIDRuta() + 1;

        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");

        Scanner in = new Scanner(System.in);
        try {

            Map<Integer, Waypoint_Dades> map = new HashMap<>();
            for (Waypoint_Dades tipusMissatges_dades : MetodesAuxiliars.listWaypont(db)) {
                map.put(tipusMissatges_dades.getId(), tipusMissatges_dades);
            }

            System.out.println("Introdueix el nom de la ruta");
            String nom = in.nextLine();
            System.out.println("Llista de tots waypoints:");
            map.forEach((k, v) -> System.out.println(v));


            System.out.println("Introdueix els waypoint que formarán part de la ruta separatas per espais (1 2 3)");
            ArrayList<Integer> waypoints = new ArrayList<>();

            try {
                String[] ids = in.nextLine().split(" ");

                for (String string : ids) {

                    if (!map.containsKey(Integer.parseInt(string))) {
                        throw new Exception();
                    } else {
                        waypoints.add(Integer.valueOf(string));
                    }
                }

            } catch (Exception e) {
                System.out.println("Error: no has inserit uns IDs vàlids");
                return;
            }

            boolean actiu = false;

            boolean acituGood = false;
            do {
                System.out.println("Actiu (S/N)");
                String actiuString = in.nextLine();

                if (actiuString.equalsIgnoreCase("s")) {
                    acituGood = true;
                    actiu = true;
                } else if (actiuString.equalsIgnoreCase("n")) {
                    acituGood = true;
                    actiu = false;
                } else {
                    System.out.println("Escriu S/N, no continuarem fins que ho faci");
                }
            } while (!acituGood);


            Ruta_Dades ruta_dades = new Ruta_Dades(id, nom, waypoints, actiu, Calendar.getInstance(), null, null);
            db.store(ruta_dades);
            System.out.println("Ruta " + ruta_dades.getNom() + " amb ID = " + ruta_dades.getId() + " INSERTADA EN LA BD.");

        } finally {
            db.close();
        }

        System.out.println("menu15(): FINAL");

    }

    public static void menu19() {


        Scanner in = new Scanner(System.in);


        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");
        try {

            Map<Integer, Ruta_Dades> map = new HashMap<>();
            for (Ruta_Dades ruta_dades : MetodesAuxiliars.listRoutes(db)) {
                map.put(ruta_dades.getId(), ruta_dades);
            }
            map.forEach((k, v) -> System.out.println(v));

            Ruta_Dades ruta_dades;

            // Recull un id i si no existeix retorna error
            System.out.println("Quina ruta vols esborrar de la base de dades?");
            int id2 = -1;
            try {
                id2 = Integer.parseInt(in.nextLine());
                if (!map.containsKey(id2)) throw new Exception();
                ruta_dades = map.get(id2);
            } catch (Exception e) {
                System.out.println("Error: no has inserit un ID vàlid");
                return;
            }


            boolean segur = false;
            do {
                System.out.println("Estas segur d'esborrar la ruta " + id2 + " de la BD (S/N) ");
                String resposta = in.nextLine();
                if (resposta.equalsIgnoreCase("n")) {
                    System.out.println("Adeu!");
                    return;
                } else if (resposta.equalsIgnoreCase("s")) {
                    segur = true;
                    db.delete(ruta_dades);
                    System.out.println("\t RUTA ESBORRADA");
                }

            } while (!segur);
        } finally {
            db.close();
        }

        System.out.println("menu19(): FINAL");

    }

    public static int trobarNouIDRuta() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");

        int maxID = -1;
        ObjectSet<Ruta_Dades> result = null;

        try {
            Predicate p = new Predicate<Ruta_Dades>() {
                @Override
                public boolean match(Ruta_Dades c) {
                    return true;
                }
            };


            result = db.query(p);
            while (result.hasNext()) {
                Ruta_Dades cli = result.next();
                if (cli.getId() > maxID) {
                    maxID = cli.getId();
                }
            }
        } finally {
            db.close();
        }


        return maxID;
    }

    public static void llistarWaypoints() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Calendar.class).callConstructor(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, "baseDeDades/Leonov.db4o");


        try {
            Predicate p = new Predicate<Waypoint_Dades>() {
                @Override
                public boolean match(Waypoint_Dades c) {
                    return true;
                }
            };


            ObjectSet<Waypoint_Dades> result = db.query(p);
            while (result.hasNext()) {
                System.out.println(result.next());
            }
        } finally {
            db.close();
        }

    }

    public static String visualitzarDadesDUnWaypoint(int id, ObjectContainer db) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Llista de waypoints\n");


        Predicate p = new Predicate<Waypoint_Dades>() {
            @Override
            public boolean match(Waypoint_Dades c) {
                return c.getId() == id;
            }
        };
        ObjectSet<Waypoint_Dades> result = db.query(p);
        stringBuilder.append(result.next() + "\n");


        return stringBuilder.toString();

    }

}
