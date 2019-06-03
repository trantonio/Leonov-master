package Leonov.Auxiliars;

import Leonov.Models.Ruta_Dades;
import Leonov.Models.Waypoint_Dades;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MetodesAuxiliars {

    private static HashMap<Integer, String> days = new HashMap<>();

    static {

        days.put(1, "DIUMENGE");
        days.put(2, "DILLUNS");
        days.put(3, "DIMARTS");
        days.put(4, "DIMECRES");
        days.put(5, "DIJOUS");
        days.put(6, "DIVENDRES");
        days.put(7, "DISSABTE");
    }

    public static String formatCalendar(Calendar calendar) {
        if(calendar == null){
            return "null";
        }
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String nameDay = days.get(calendar.get(Calendar.DAY_OF_WEEK));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String hora = sdf.format(calendar.getTime());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(nameDay);
        stringBuilder.append(" ");
        stringBuilder.append(day);
        stringBuilder.append("-");
        stringBuilder.append(month);
        stringBuilder.append("-");
        stringBuilder.append(year);
        stringBuilder.append(" a les ");
        stringBuilder.append(hora);


        return stringBuilder.toString();
    }

    public static List<Waypoint_Dades> listWaypont(ObjectContainer db) {
        List<Waypoint_Dades> result = null;

        Predicate p = new Predicate<Waypoint_Dades>() {
            @Override
            public boolean match(Waypoint_Dades c) {
                return true;
            }
        };
        result = db.query(p);
        return result;
    }

    public static List<Ruta_Dades> listRoutes(ObjectContainer db) {
        List<Ruta_Dades> result = null;

        Predicate p = new Predicate<Ruta_Dades>() {
            @Override
            public boolean match(Ruta_Dades c) {
                return true;
            }
        };
        result = db.query(p);
        return result;
    }
    public static List<Ruta_Dades> listRoutes(ObjectContainer db, int id) {
        List<Ruta_Dades> result = null;

        Predicate p = new Predicate<Ruta_Dades>() {
            @Override
            public boolean match(Ruta_Dades c) {
                return c.getId() == id;
            }
        };
        result = db.query(p);
        return result;
    }
}
