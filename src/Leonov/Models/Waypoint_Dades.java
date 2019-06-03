package Leonov.Models;

import Leonov.Auxiliars.MetodesAuxiliars;

import java.util.Arrays;
import java.util.Calendar;

public class Waypoint_Dades {
    int id;                     //Clau primaria. Es crea automàticament pel sistema (el vostre programa, no l'usuari) i és intocable.
    String nom;
    int[] coordenades;
    boolean actiu;              //TRUE si està actiu i es pot fer servir per afegir-lo a alguna ruta.
    Calendar dataCreacio;
    Calendar dataAnulacio;      //Quan actiu passi a valdre FALSE.
    Calendar dataModificacio;


    public Waypoint_Dades(int id, String nom, int[] coordenades, boolean actiu, Calendar dataCreacio, Calendar dataAnulacio, Calendar dataModificacio) {
        this.id = id;
        this.nom = nom;
        this.coordenades = coordenades;
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

    public int[] getCoordenades() {
        return coordenades;
    }

    public void setCoordenades(int[] coordenades) {
        this.coordenades = coordenades;
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
        return "\tWAYPONT " + id + "\n"+
                "\tnom = " + nom + "\n" +
                "\tcoordenades (x,y,z) = " + Arrays.toString(coordenades) + "\n" +
                "\tactiu = " + actiu + "\n" +
                "\tdataCreacio = " + MetodesAuxiliars.formatCalendar(dataCreacio) + "\n" +
                "\tdataAnulacio = " + MetodesAuxiliars.formatCalendar(dataAnulacio) + "\n" +
                "\tdataModificacio = " + MetodesAuxiliars.formatCalendar(dataModificacio) + "\n";
    }
}
