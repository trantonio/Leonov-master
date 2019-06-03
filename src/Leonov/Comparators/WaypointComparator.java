package Leonov.Comparators;

import Leonov.Models.Waypoint_Dades;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class WaypointComparator implements Comparator<Waypoint_Dades>{
    int opcio;

    public WaypointComparator(int opcio) {
        this.opcio = opcio;
    }

    @Override
    public int compare(Waypoint_Dades o1, Waypoint_Dades o2) {
        Collator tertiaryCollator = Collator.getInstance(new Locale("es"));
        tertiaryCollator.setStrength(Collator.TERTIARY);


        switch (opcio){
            case 1:
                int o1Distancia = (int)Math.pow(o1.getCoordenades()[0],2)+(int)Math.pow(o1.getCoordenades()[1],2)+(int)Math.pow(o1.getCoordenades()[2],2);
                int o2Distancia = (int)Math.pow(o2.getCoordenades()[0],2)+(int)Math.pow(o2.getCoordenades()[1],2)+(int)Math.pow(o2.getCoordenades()[2],2);
                if(o1Distancia >= o2Distancia)
                    return -1;
                else if(o1Distancia < o2Distancia)
                    return 1;
                else
                    return 0;

            case 3:
                int resultat;
                if(o1.getDataModificacio() == null && o2.getDataModificacio() == null){
                    resultat = 0;
                }
                if(o1.getDataModificacio() == null &&  o2.getDataModificacio() != null){
                    resultat = 1;
                }else if(o1.getDataModificacio() == null &&  o2.getDataModificacio() != null){
                    resultat = -1;
                }else {
                    resultat =o2.getDataModificacio().compareTo(o1.getDataModificacio());
                }

                if (resultat == 0){
                    int o1Distancia2 = (int)Math.pow(o1.getCoordenades()[0],2)+(int)Math.pow(o1.getCoordenades()[1],2)+(int)Math.pow(o1.getCoordenades()[2],2);
                    int o2Distancia2 = (int)Math.pow(o2.getCoordenades()[0],2)+(int)Math.pow(o2.getCoordenades()[1],2)+(int)Math.pow(o2.getCoordenades()[2],2);
                    if(o1Distancia2 >= o2Distancia2) {
                        resultat = -1;
                    }
                    else if(o1Distancia2 < o2Distancia2) {
                        resultat = 1;
                    }
                    else {
                        String o1Nom = o1.getNom();
                        String o2Nom = o2.getNom();
                        resultat = tertiaryCollator.compare(o1Nom,o2Nom);
                    }

                }

                return resultat;

            default:
                String o1Nom = o1.getNom();
                String o2Nom = o2.getNom();
                return tertiaryCollator.compare(o1Nom,o2Nom);
        }
    }
}
