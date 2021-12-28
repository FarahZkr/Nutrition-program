package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {


        // FICHIERS

        String nomFichier = "nutrition.csv";
        String[][] matrice = fichier2matrice(nomFichier);


        afficherMenu(matrice);
    }


    public static void afficherMenu(String[][] nutrition) {
        Scanner sc = new Scanner(System.in);
        int choix;
        int choixId;
        String quitter;
        String decision;
        String matrice[][] = nutrition;
        do {
            System.out.println("*********************************************************************************\n" +
                    "1-\tAfficher l’ensemble des aliments depuis le fichier Nutrition.CSV                 *******\n" +
                    "2-\tAfficher les aliments en fonction d’une valeur nutritive à la fois               *******\n" +
                    "3-\tAfficher les valeurs nutritives d’un aliment (Recherche par ID)                  *******\n" +
                    "4-\tModifier une valeur nutritive d’un aliment par ID                                *******\n" +
                    "5-\tAjouter un aliment                                                               *******\n" +
                    "6-\tQuitter                                                                          *******\n" +
                    "*********************************************************************************");
            try {
                choix = Integer.parseInt(sc.nextLine());
                if (choixMenuValide(choix)) {
                    switch (choix) {
                        case 1:
                            afficherFichierNutriments(matrice);
                            break;
                        case 2:
                            matrice = fichier2matrice("nutrition.csv");
                            System.out.println("2-\tVeuillez choisir la teneur en un nutriment pour les aliments:");
                            int rang = menu2(matrice);
                            matrice = afficherMenu2(matrice, rang);

                            afficherFichierNutriments(matrice);
                            break;
                        case 3:
                            do {
                                System.out.println("Veuillez insérer le ID d’un aliment -->");
                                try {
                                    choixId = Integer.parseInt(sc.nextLine());
                                    matrice = fichier2matrice("nutrition.csv");
                                    String[] ligne = menu3(matrice, choixId);
                                    if (numeroDeIDValide(choixId, matrice)) { // 139 --> matrice.length + 1
                                        System.out.println("Id;Catégorie;Description;Energ_Kcal;Protéine;gras;Cholestérol;Sodium");
                                        System.out.println(Arrays.toString(ligne));
                                        break;
                                    } else if (!numeroDeIDValide(choixId, matrice)) {
                                        System.err.println("ID éronné!");
                                    }
                                } catch (Exception e) {
                                    System.err.println("ID éronné!");
                                }

                            } while (true);
                            break;
                        case 4:
                            int choixNutriment;
                            do {
                                System.out.println("Veuillez insérer le ID d’un aliment -->");
                                try {
                                    choixId = Integer.parseInt(sc.nextLine());
                                    matrice = fichier2matrice("nutrition.csv");
                                    String[] ligne = menu3(matrice, choixId);
                                    if (numeroDeIDValide(choixId, matrice)) {
                                        do {
                                            System.out.println("Veuillez saisir le numéro correspondant au nutriment  -->");
                                            System.out.println("1- Energ_Kcal; 2-Protéine; 3-gras; 4- Cholestérol; 5- Sodium  -->");
                                            System.out.println("choix :");
                                            try {
                                                choixNutriment = Integer.parseInt(sc.nextLine());
                                                if (choixNutriment >= 1 && choixNutriment <= 5) {
                                                    choixNutriment = choixNutriment + 2;
                                                    System.out.println("La valeur de base est : " + ligne[choixNutriment]);
                                                    break;
                                                } else {
                                                    System.err.println("Choix de nutriment invalide!");
                                                }

                                            } catch (Exception e) {
                                                System.err.println("Choix de nutriment invalide!");
                                            }
                                        } while (true);
                                        String nouvelleValeur;
                                        do {
                                            System.out.println("Voulez-vous conserver cette valeur (Oui) ou modifier la valeur (Non) -->");
                                            try {
                                                decision = new Scanner(System.in).nextLine();
                                                if (decision.equals("Non") || decision.equals("non")) {
                                                    do {
                                                        System.out.println("Saisir la nouvelle valeur ---------->>>>>");
                                                        try {
                                                            nouvelleValeur = (new Scanner(System.in)).nextLine();
                                                            if (valeurNutritiveValides(nouvelleValeur)) {
                                                                String vieilleValeur = matrice[choixId - 1][choixNutriment];
                                                                matrice[choixId - 1][choixNutriment] = nouvelleValeur; // choixId-1
                                                                String ranger = "Id;Catégorie;Description;Energ_Kcal;Protéine;gras;Cholestérol;Sodium";
                                                                if (nouveauFichier("nutrition.csv", matrice, ranger)) { // nutrition --> matrice
                                                                    System.out.println("Fichié modifié"); // SUCESS
                                                                    System.out.println(Arrays.toString(ligne));
                                                                    break;

                                                                } else {
                                                                    matrice[choixId - 1][choixNutriment] = vieilleValeur; // RESET LA VIEILLE VALEUR
                                                                    System.out.println("Fichier non modifié"); // ERROR
                                                                }
                                                                break;
                                                            }
                                                            if (!valeurNutritiveValides(nouvelleValeur)) {
                                                                System.err.println("Mauvaise valeur!");
                                                            }
                                                        } catch (Exception e) {
                                                            System.err.println("Mauvaise valeur!");
                                                        }
                                                    } while (true);
                                                    break;

                                                } else if (decision.equals("Oui") || decision.equals("oui")) {
                                                    break;
                                                } else {
                                                    System.err.println("Choix invalide!");
                                                }
                                            } catch (Exception e) {
                                                System.err.println("Choix invalide!");
                                            }
                                        } while (true);
                                        break;
                                    } else if (!numeroDeIDValide(choixId, matrice)) {
                                        System.err.println("ID éronné!");
                                    }
                                } catch (Exception e) {
                                    //System.out.println(e.getMessage());
                                    System.err.println("ID éronné!");
                                }
                            } while (true);
                            break;
                        case 5:
                            matrice = menu5(matrice);
                            break;
                        case 6:
                            do {
                                System.out.println("Voulez-vous quitter le programme?(Oui ou Non)");
                                try {
                                    quitter = sc.nextLine();
                                    if (quitter.equals("Oui") || quitter.equals("oui")) {
                                        System.out.println("Merci d'avoir utilisé notre programme!");
                                        System.exit(0);
                                        break;
                                    }
                                    if (quitter.equals("Non") || quitter.equals("non")) {
                                        System.out.println("D'accord!");

                                        break;
                                    } else {
                                        System.err.println("Choix invalide!");
                                    }
                                } catch (Exception e) {
                                    System.err.println("Choix invalide!");
                                }
                            } while (true);
                            break;
                    }
                } else if (!choixMenuValide(choix)) {
                    System.err.println("Choix éronné!");
                }
            } catch (Exception e) {
                System.err.println("Choix éronné!");
            }
        } while (true);

    }

    public static boolean numeroDeIDValide(int choixId, String[][] mat) {
        try {
            if (choixId > 0 && choixId < mat.length + 1) {

                return true;
            }
            return false;

        } catch (Exception e) {
            return false;
        }

    }


    public static boolean nouveauFichier(String nomFichier, String[][] mat, String ligne) throws FileNotFoundException {
        try {
            FileOutputStream fo = new FileOutputStream(nomFichier, false);
            PrintWriter sortie = new PrintWriter(fo);
            sortie.println(ligne); // le header

            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[i].length; j++) {
                    if (j == 0) {
                        sortie.print(mat[i][j]);
                        continue;
                    }
                    sortie.print(";" + mat[i][j]);
                }
                sortie.print("\n");
            }
            sortie.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static void afficherFichierNutriments(String[][] nutrition) {
        for (int i = 0; i < nutrition.length; i++) {
            for (int j = 0; j < nutrition[0].length; j++) {
                System.out.print(nutrition[i][j] + " ");

            }
            System.out.println();
        }


    }

    public static String[][] trier(String[][] matrice, boolean croissance, int valeur) {
        if (croissance) {
            for (int i = 0; i < matrice.length; i++) {
                for (int j = 0; j < matrice.length - 1; j++) {

                    if (Integer.parseInt(matrice[j][valeur]) > Integer.parseInt(matrice[j + 1][valeur])) {
                        echanger(matrice, j, j + 1);
                    }

                }

            }
        } else {
            for (int i = 0; i < matrice.length; i++) {
                for (int j = 0; j < matrice.length - 1; j++) {

                    if (Integer.parseInt(matrice[j][valeur]) < Integer.parseInt(matrice[j + 1][valeur])) {
                        echanger(matrice, j, j + 1);
                    }
                }

            }
        }
        return matrice;
    }

    public static void echanger(String[][] matrice, int i, int j) {
        String[] val1 = matrice[i];
        String[] val2 = matrice[j];
        matrice[i] = val2;
        matrice[j] = val1;
    }

    public static String[][] afficherMenu2(String[][] mat, int ranger) {

        String[][] tableau = new String[mat.length][4];
        for (int i = 0; i < tableau.length; i++) {
            tableau[i][0] = mat[i][0];
            tableau[i][1] = mat[i][1];
            tableau[i][2] = mat[i][2];
            tableau[i][3] = mat[i][ranger];
        }

        return trier(tableau, false, 3);

    }

    public static int menu2(String[][] matrice) throws FileNotFoundException {
        Scanner clavier = new Scanner(System.in);
        int teneur;
        String header, fichier;
        do {
            System.out.println("*********************************************************************************\n" +
                    "1-\tAfficher les aliments en fonction de son Énergie Kcal                 *******\n" +
                    "2-\tAfficher les aliments en fonction de son protéine                     *******\n" +
                    "3-\tAfficher les aliments en fonction de son gras                         *******\n" +
                    "4-\tAfficher les aliments en fonction de son cholestérol                  *******\n" +
                    "5-\tAfficher les aliments en fonction de sodium                           *******\n" +
                    "*********************************************************************************\n");
            try {
                teneur = Integer.parseInt(clavier.nextLine());
                switch (teneur) {
                    case 1:
                        teneur = 3;
                        header = "Id;Catégorie;Description;Energ_Kcal";
                        fichier = "nutrition_energie.csv";
                        break;
                    case 2:
                        teneur = 4;
                        header = "Id;Catégorie;Description;Protéine";
                        fichier = "nutrition_proteine.csv";
                        break;
                    case 3:
                        teneur = 5;
                        header = "Id;Catégorie;Description;gras";
                        fichier = "nutrition_gras.csv";
                        break;
                    case 4:
                        teneur = 6;
                        header = "Id;Catégorie;Description;Cholestérol";
                        fichier = "nutrition_cholesterol.csv";
                        break;
                    case 5:
                        teneur = 7;
                        header = "Id;Catégorie;Description;Sodium";
                        fichier = "nutrition_sodium.csv";
                        break;
                    default: {
                        System.err.println("Choix éronné!");
                    }
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Choix éronné!");
                continue;
            }
            if (nouveauFichier(fichier, afficherMenu2(matrice, teneur), header)) {
                System.out.println("Fichier créé!");
            } else {
                System.out.println("Fichier non créé!");
            }
            break;
        } while (true);
        return teneur;

    }

    public static String[] menu3(String[][] mat, int choixId) {

        String[] ligne = mat[choixId - 1];
        return ligne;
    }

    public static String[][] menu5(String[][] matrice) throws IOException {
        Scanner clavier = new Scanner(System.in);
        String ligne, categorie, description, energie, proteine, gras, cholesterol, sodium;
        int choixCategorie;


        System.out.println("**************************************************************************************");
        System.out.println("Veuillez insérer un nouvel aliment sous cette forme                :    \n" +
                "Catégorie;Description;Energ_Kcal;Protéine;gras;Cholestérol;Sodium\n" +
                "***************************************************************************************\n");
        System.out.println("Exemple : ");
        System.out.println("Salades;Crispy Chicken California Cobb Salad;380;27;23;125;1170      \n" +
                "**************************************************************************************\n");

        String[] tableau = new String[8];
        do {
            System.out.println("Veuillez choisir une catégorie:");
            System.out.println("1-Breuvages 2-Déjeuners 3-Salades 4-Croquettes 5-Sandwich 6-Desserts 7-Frites 8-Vinaigrettes à salades");
            try {
                choixCategorie = Integer.parseInt(clavier.nextLine());
                switch (choixCategorie) {
                    case 1:
                        categorie = "Breuvages";
                        break;
                    case 2:
                        categorie = "Déjeuners";
                        break;
                    case 3:
                        categorie = "Salades";
                        break;
                    case 4:
                        categorie = "Croquettes";
                        break;
                    case 5:
                        categorie = "Sandwichs";
                        break;
                    case 6:
                        categorie = "Desserts";
                        break;
                    case 7:
                        categorie = "Frites";
                        break;
                    case 8:
                        categorie = "Vinaigrettes à salades";
                        break;
                    default:
                        System.err.println("Choix éronné!");
                        continue;
                }
            } catch (Exception e) {
                System.err.println("Choix éronné!");
                continue;
            }
            break;
        } while (true);

        do {
            System.out.println("Veuillez écrire la description:");
            try {
                description = (new Scanner(System.in)).nextLine();
                if (!description.contains(";") && !description.replaceAll(" ", "").equals("")) {
                    tableau[1] = description;
                    break;
                } else {
                    System.err.println("Description invalide!");
                }
            } catch (Exception e) {
                System.err.println("Description invalide!");
            }
        } while (true);

        do {
            System.out.println("Veuillez écrire l'énergie:");
            energie = (new Scanner(System.in)).nextLine();
            if (!valeurNutritiveValides(energie)) {
                System.err.println("Énergie invalide");
            }
        } while (!valeurNutritiveValides(energie));

        do {
            System.out.println("Veuillez écrire la protéine:");
            proteine = (new Scanner(System.in)).nextLine();
            if (!valeurNutritiveValides(proteine)) {
                System.err.println("Protéine invalide!");
            }
        } while (!valeurNutritiveValides(proteine));
        do {
            System.out.println("Veuillez écrire le gras:");
            gras = (new Scanner(System.in)).nextLine();
            if (!valeurNutritiveValides(gras)) {
                System.err.println("Gras invalide!");
            }

        } while (!valeurNutritiveValides(gras));
        do {
            System.out.println("Veuillez écrire le cholestérol:");
            cholesterol = (new Scanner(System.in)).nextLine();
            if (!valeurNutritiveValides(cholesterol)) {
                System.err.println("Cholestérol invalide!");
            }

        } while (!valeurNutritiveValides(cholesterol));
        do {
            System.out.println("Veuillez écrire le sodium:");
            sodium = (new Scanner(System.in)).nextLine();
            if (!valeurNutritiveValides(sodium)) {
                System.err.println("Sodium invalide!");
            }

        } while (!valeurNutritiveValides(sodium));


        int id = matrice.length + 1;
        ligne = id + ";" + categorie + ";" + description + ";" + energie + ";" + proteine + ";" + gras + ";" + cholesterol + ";" + sodium;
        tableau[0] = String.valueOf(id);
        tableau[1] = categorie;
        tableau[2] = description;
        tableau[3] = energie;
        tableau[4] = proteine;
        tableau[5] = gras;
        tableau[6] = cholesterol;
        tableau[7] = sodium;
        System.out.println(Arrays.toString(tableau));

        if (afficherLigne("nutrition.csv", (ligne))) {
            matrice = fichier2matrice("nutrition.csv");


        }


        return matrice;
    }

    public static boolean valeurNutritiveValides(String element) {
        try {
            int nutriment = Integer.parseInt(element);
            if (nutriment >= 0 && nutriment <= 10000) {

                return true;
            }
            return false;

        } catch (Exception e) {
            return false;
        }

    }

    public static boolean choixMenuValide(int choix) {
        try {
            if (choix >= 0 && choix <= 6) {

                return true;
            }
            return false;

        } catch (Exception e) {
            return false;
        }

    }


    public static boolean afficherLigne(String nomFichier, String ligne) throws FileNotFoundException {
        try {
            FileOutputStream fo = new FileOutputStream(nomFichier, true);
            PrintWriter sortie = new PrintWriter(fo);


            sortie.print("\n");
            sortie.print(ligne);

            sortie.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private static String[][] fichier2matrice(String nomFichier) throws IOException {
        // Lecture de fichier
        FileReader monFr = new FileReader(nomFichier);

        BufferedReader fL = new BufferedReader(monFr);
        // Récuperer une ligne
        String ligne = fL.readLine();


        int max = 0;

        while (ligne != null) { // nombre de lignes
            ligne = fL.readLine();
            max++;
        }
        String[][] matrice = new String[max - 1][10];
        // fin du fichier
        fL.close();


        FileReader monFr2 = new FileReader(nomFichier);
        BufferedReader fL2 = new BufferedReader(monFr2);

        int i = 0;
        ligne = fL2.readLine();
        while (ligne != null && i <= matrice.length) {

            ligne = fL2.readLine();
            if (ligne != null) {
                matrice[i] = ligne.split(";");
                i++;
            } else {
                break;
            }
        }
        // fin du fichier
        fL2.close();

        return trier(matrice, true, 0);
    }
}
