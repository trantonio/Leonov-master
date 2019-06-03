package Leonov.Models;

import Leonov.Auxiliars.MetodesAuxiliars;

import java.util.ArrayList;
import java.util.Calendar;

public class Ruta_Dades {
    int id;                             //Clau primaria. Es crea automàticament pel sistema (el vostre programa, no l'usuari) i és intocable.
    String nom;
    ArrayList<Integer> waypoints;       //Conté una llista de a tots els waypoints que formen part de la ruta (els ID's i no pas el objecte waypoint).
    boolean actiu;                      //TRUE si està activa.
    Calendar dataCreacio;
    Calendar dataAnulacio;              //Quan actiu passi a valdre FALSE.
    Calendar dataModificacio;

    public Ruta_Dades(int id, String nom, ArrayList<Integer> waypoints, boolean actiu, Calendar dataCreacio, Calendar dataAnulacio, Calendar dataModificacio) {
        this.id = id;
        this.nom = nom;
        this.waypoints = waypoints;
        this.actiu = actiu;
        this.dataCreacio = dataCreacio;
        this.dataAnulacio = dataAnulacio;
        this.dataModificacio = dataModificacio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Integer> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ArrayList<Integer> waypoints) {
        this.waypoints = waypoints;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public Calendar getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(Calendar dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public Calendar getDataAnulacio() {
        return dataAnulacio;
    }

    public void setDataAnulacio(Calendar dataAnulacio) {
        this.dataAnulacio = dataAnulacio;
    }

    public Calendar getDataModificacio() {
        return dataModificacio;
    }

    public void setDataModificacio(Calendar dataModificacio) {
        this.dataModificacio = dataModificacio;
    }

    @Override
    public String toString() {
        return "Ruta " + id + "\n"+
                "\t nom = " + nom + "\n" +
                "\t waypoints = " + waypoints + "\n" +
                "\t actiu = " + actiu + "\n" +
                "\t dataCreacio = " + MetodesAuxiliars.formatCalendar(dataCreacio) + "\n" +
                "\t dataAnulacio = " + MetodesAuxiliars.formatCalendar(dataAnulacio) + "\n" +
                "\t dataModificacio = " + MetodesAuxiliars.formatCalendar(dataModificacio) + "\n";
    }
}
