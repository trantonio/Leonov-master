/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Leonov.Menus;

/**
 *
 * @author gmartinez
 */
public class MenuConstructorPantalla {
    public static StringBuilder constructorPantalla (StringBuilder menu){
        int longMaxLiniaMenu = 0;
        int posTrobat = 0;
        int posBusqueda = 0;
        while (posTrobat > -1) {
            posTrobat = menu.indexOf("\n", posBusqueda);

            if (posTrobat > -1) {
                if ((posTrobat - posBusqueda) > longMaxLiniaMenu) {
                    longMaxLiniaMenu = posTrobat - posBusqueda;
                }

                menu.insert(posBusqueda, "|  ");

                posBusqueda = posTrobat + 4;
            }
        }
        longMaxLiniaMenu = longMaxLiniaMenu + 6;    //+6 perquè afegirem "|  " al principi i "  |" al final de cada linia.


        int longLiniaMenu = 0;
        String str = "                                                                                                                  ";
        int dif = 0;
        int posTrobat1 = menu.indexOf("\n", 0);
        //System.out.println("posTrobat1 = " + posTrobat1);
        int posTrobat2 = menu.indexOf("\n", (posTrobat1 + 1));
        //System.out.println("posTrobat2 = " + posTrobat2);
        if (posTrobat2 > -1) {
            //longLiniaMenu = menu.substring(0, posTrobat).length();
            longLiniaMenu = menu.substring((posTrobat1 + 1), posTrobat2).length();
            dif = longMaxLiniaMenu - longLiniaMenu;     

            //System.out.println("longLiniaMenu = " + longLiniaMenu + "; dif = " + dif + "; '" + menu.substring((posTrobat1 + 1), posTrobat2) + "'");

            //Abans hem afegit "|  " al principi de la linia i això ja són 2 espais en blanc. Ojo que després afegirem "  |" al final.
            if ((dif % 2) == 0){
                //menu.insert(1, str, 0, ((dif / 2) - 2));
                //menu.insert(((dif / 2) + longLiniaMenu - 2), str, 0, ((dif / 2) - 1));
                menu.insert((posTrobat1 + 2), str, 0, ((dif / 2) - 2));
                menu.insert((posTrobat1 + longLiniaMenu + (dif / 2) - 2), str, 0, ((dif / 2) - 1));
            } else {
                //menu.insert(1, str, 0, ((dif / 2) - 1));
                //menu.insert(((dif / 2) + longLiniaMenu - 1), str, 0, ((dif / 2) - 1));
                menu.insert((posTrobat1 + 2), str, 0, ((dif / 2) - 1));
                menu.insert((posTrobat1 + longLiniaMenu + (dif / 2) - 0), str, 0, ((dif / 2) - 1));
            }
        }


        posTrobat = 0;
        posBusqueda = 0;
        longLiniaMenu = 0;
        int tmp = 0;
        dif = 0;
        while (posTrobat > -1) {
            posTrobat = menu.indexOf("\n", posBusqueda);

            if (posTrobat > -1) {
                menu.insert(posTrobat, "  |");

                tmp = posTrobat + 3;
                longLiniaMenu = menu.substring(posBusqueda, tmp).length();

                dif = longMaxLiniaMenu - longLiniaMenu;
                if (dif > 0) {
                    menu.insert(posTrobat, str, 0, dif);
                }

                posBusqueda = posBusqueda + longMaxLiniaMenu + 1; //posTrobat + 4;
            }
        }


        int longMaxLiniaMenuTmp = longMaxLiniaMenu;
        StringBuilder liniaHoritzontal = new StringBuilder("");
        while (longMaxLiniaMenuTmp > 0) {
            liniaHoritzontal.append("=");

            longMaxLiniaMenuTmp--;
        }
        liniaHoritzontal.append(System.getProperty("line.separator"));


        menu.insert(0, liniaHoritzontal);
        menu.replace(((longMaxLiniaMenu / 2) - 5), ((longMaxLiniaMenu / 2) + 6), " Orbita 10 ");

        int newPos = longMaxLiniaMenu*4 + 4; 
        menu.insert(newPos, liniaHoritzontal);

        menu.append(liniaHoritzontal);
        menu.append(System.getProperty("line.separator"));
        menu.append(System.getProperty("line.separator"));            
        menu.append("opció?: ");
        
        return menu;
    }
}
