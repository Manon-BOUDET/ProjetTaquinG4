/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projettaquin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Manon
 */

public class Plateau {
    private final int ligne = 4, colonne = 4; // ligne et colonne du plateau
    private Case[][] plateau;

/* Constructeurs */
    public Plateau(){
        
    }
    public Plateau(Plateau p) {
        this.plateau = new Case[ligne][colonne];
        for (int l = 0; l < ligne; l++) {
            for (int c = 0; c < colonne; c++) {
                this.plateau[l][c] = new Case(p.plateau[l][c]);
            }
        }
    }

 /* Getter & Setter */
    public Case[][] getPlateau() {
        return this.plateau;
    }
    public void setPlateau(Case[][] p){
        this.plateau = p;
    }
    public void setPlateau(Case c, int i, int j) { // i pour ligne, j pour colonne
        this.plateau[i][j] = c;
    }
    
    public Case getCase(int i, int j) {
        return this.plateau[i][j];
    }
    public Case getCase(Plateau p, int row, int column){
        return p.plateau[row][column];
    }
    public void setCase(int i, int j, Case c) {
        this.plateau[i][j].setPlace(c, true);
    }

/* Méthodes */
    //Remplit le tableau avec des cases allant de 1 à 15 et une case vide à la fin
    public void remplissagePlateau() {
        int i = 0;
        plateau = new Case[ligne][colonne];
        for (int l = 0; l < ligne; l++) {
            for (int c = 0; c < colonne; c++) {
                if (c == 3 && l == 3) {
                    //condition qui associe chaque cooordonnée à une case
                    int line = l + 1;
                    int col = c + 1;
                    Case ca = new Case(0,line,col);
                    this.plateau[l][c] = ca;
                } else {
                    //condition qui associe chaque cooordonnée à une case
                    int line = l + 1;
                    int col = c + 1;
                    i = (col) + (4 * line);
                    Case ca = new Case(i,line,col);
                    this.plateau[l][c] = ca;
                }
            }
        }
    }

    // Mélange le tableau aléatoirement
    public void melangerPlateau(Plateau p){
        for (int i=0;i<100;i++){
            List<Integer> vide = rechercheVide(p);
            int rowVide = vide.get(0); // Ligne de la case vide
            int colVide = vide.get(1); // Colonne de la case vide
            int random = (int)(Math.random() * 4);
            if ((random == 0)&&(rowVide>0)){
                deplacementSimple(p,rowVide -1,colVide);
            }
            else if ((random == 1)&&(rowVide<3)){
                deplacementSimple(p,rowVide +1,colVide);
            }
            else if ((random == 2)&&(colVide>0)){
                deplacementSimple(p,rowVide,colVide -1);
            }
            else if ((random == 3)&&(colVide<3)){
                deplacementSimple(p,rowVide,colVide +1);
            }
        }
    }
    
    public void deplacementSimple(Plateau p, int row, int col){
        List<Integer> vide = new ArrayList<Integer>();
        vide = rechercheVide(p);
        int rowVide = vide.get(0); // Ligne de la case vide
        int colVide = vide.get(1); // Colonne de la case vide
        if (((rowVide == row)&& (col == colVide -1))||((rowVide == row)&&(col == colVide +1))||((rowVide +1 == row)&&(col == colVide))||((rowVide -1 == row)&&(col == colVide))){
            Case ca = new Case();
            ca = getCaseByRowColumnIndex(p,row,col);
            deplacement(p,ca,rowVide,colVide);
        }
        else{
            System.out.println("Déplacement simple impossible.");
        }
    }
  
    public Case getCaseByRowColumnIndex(Plateau p,final int row,final int column) {
        // Retourne la case positionnée à la ligne "row" et la colonne "column"
        // Parcourt du tableau pour trouver la case
        Case ca = new Case();
        boolean trouve = false;
        while (trouve == false){
            for (int l = 0; l < ligne; l++) {
                for (int c = 0; c < colonne; c++) {
                    if (l==row && c==column){
                        ca = p.plateau[l][c];
                        trouve = true;
                    }
                }
            }
        }
        return ca;
    }
    
    // Recherche les coordonnées de la case vide
    public List<Integer> rechercheVide(Plateau p) {
        Case[][] plat = p.plateau;
        // Retourne les coordonnées (ligne et colonne) de la case vide dans une ArrayList
        List<Integer> coordonnees = new ArrayList<Integer>();
        for (int row = 0; row < ligne; row ++) {
            for (int column = 0; column < colonne; column++) {
                Case ca = p.plateau[row][column];
                int val = ca.getValeur();
                if (val == 0){
                    coordonnees.add(row);
                    coordonnees.add(column);
                    return coordonnees;
                }
            }
        }
        return coordonnees;
    }

    public void deplacement(Plateau p, Case ca, int l, int c){
        // Echange la position de deux cases
        Case caseFinale = new Case();
        caseFinale = getCaseByRowColumnIndex(p,l,c);
        int ligneDep = ca.getLigne(ca);
        int colonneDep = ca.getColonne(ca);
        ca.setColonne(ca,c);
        ca.setLigne(ca,l);
        caseFinale.setColonne(caseFinale,colonneDep);
        caseFinale.setLigne(caseFinale,ligneDep);
    }

    // Echange de place entre deux cases
    private void echangePlace(int l1, int c1, int l2, int c2, Plateau p) {
        Case ca = new Case();
        ca = p.getPlateau()[l1][c1];
        int v = ca.getValeur();
        //int v = valeur.getPlateau()[l1][c1];
        //int v = p.getCase(l1,c1).getValeur();
        //Case c = new Case(p.getPlateau()[l1][c1]);
        p.getPlateau()[l1][c1].setPlace(p.getPlateau()[l2][c2].getValeur(), true);
        p.getPlateau()[l2][c2].setPlace(v, true);
    }

    //Permet de savoir si une case particulière est bien placé
    public boolean caseOccupe(int l, int c) {
        boolean bool = false;
        if (plateau[l][c].getPlace() == true){
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }
    
    // Détermine la case a déplacé et appelle les autres methodes pour la déplacer sur la plateau
    public void choisirAction(Plateau p) {
        boolean b = false;
        boolean alea = false;
        String t = "";
        while (t.equals("z") == false || t.equals("s") == false || t.equals("q") == false || t.equals("d") == false) {
            System.out.println("Où voulez-vous déplacer la case vide : en haut (z), en bas (s), à gauche (q) ou à droite(d) ?"); 
            Scanner sc = new Scanner(System.in);
            t = sc.nextLine();
            if (t.equals("z")) {
                List<Integer> vide = new ArrayList<Integer>();
                vide = rechercheVide(p);
                int rowVide = vide.get(0); // Ligne de la case vide
                int colVide = vide.get(1); // Colonne de la case vide
                int l = rowVide - 1;
                int c = colVide;
                deplacementSimple(p,l,c);
            } else if (t.equals("s")) {
                List<Integer> vide = new ArrayList<Integer>();
                vide = rechercheVide(p);
                int rowVide = vide.get(0); // Ligne de la case vide
                int colVide = vide.get(1); // Colonne de la case vide
                int l = rowVide + 1;
                int c = colVide;
                deplacementSimple(p,l,c);
            } else if (t.equals("q")) {
                List<Integer> vide = new ArrayList<Integer>();
                vide = rechercheVide(p);
                int rowVide = vide.get(0); // Ligne de la case vide
                int colVide = vide.get(1); // Colonne de la case vide
                int l = rowVide;
                int c = colVide - 1;
                deplacementSimple(p,l,c);  
            } else if (t.equals("d")) {
                List<Integer> vide = new ArrayList<Integer>();
                vide = rechercheVide(p);
                int rowVide = vide.get(0); // Ligne de la case vide
                int colVide = vide.get(1); // Colonne de la case vide
                int l = rowVide;
                int c = colVide + 1;
                deplacementSimple(p,l,c);
            } else {
                System.out.println("Vous avez mal ortographié votre choix parmi z, s, d ou d.");
            }
        }
    }

    public void finPartie(Plateau p){
        List<Integer> vide = rechercheVide(p);
        int rowVide = vide.get(0); // Ligne de la case vide
        int colVide = vide.get(1); // Colonne de la case vide
        if (rowVide == 3 && colVide ==3){
            int bonnePos = 0;
            for (int i=0;i<4;i++){
                for (int j=0;j<4;j++){
                    if (i != 3 || j !=3){
                        Case ca = getCaseByRowColumnIndex(p,i,j);
                        int val = ca.getValeur();
                        if ((1 + j) + (4 * i) == val) {
                            //System.out.println("La case est à la bonne place.");
                            bonnePos +=1;
                        }else {
                            //System.out.println("La case n'est pas à la bonne place.");
                        }
                    }
                }
            }
            if (bonnePos == 15){
                System.out.println("Bravo, vous avez gagné !");
            }
        } 
        System.out.println("Bravo, vous avez gagné !");
    }
    
    // Permet d'afficher le plateau
    public void affichagePlateau() {
        for (int l = 0; l < ligne; l++) {
            List<String> chain = new ArrayList<String>();
            chain.add("["); // Permet de mettre un début à une ligne (graphique)
            for (int c = 0; c < colonne; c++) {
                Case ca = getCase(l,c);
                int valeur = ca.getValeur();
                String val = String.valueOf(valeur);
                if (c == 3) {
                    chain.add(val);
                } else {
                    chain.add(val);
                    chain.add("|"); // Permet de séparer deux cases dans une ligne (graphique)
                }
            }
            chain.add("]"); // Permet de mettre une fin à une ligne (graphique)
            System.out.println(chain);
        }
    }

    // Gère les différentes actions que l'on peut effectuer à chaque tour
    public void actionTour(Plateau plateau, boolean repart) {
        plateau.choisirAction(plateau);
        plateau.affichagePlateau();
        //j.majDonee(); // Mets à jour les données dans la base de donnée
        
        // Le joueur peut réinitialiser le jeu
        String reini = "";
        while (reini.equals("oui") == false && reini.equals("non") == false) {
            System.out.println("Voulez-vous activer la réinitialisation de grille ? (oui / non)");
            Scanner sc = new Scanner(System.in);
            reini = sc.nextLine();
            if (reini.equals("oui") == true){
                repart = true;
            } else {
                repart = false;
                actionTour(plateau,repart);
            }
        }
    }
    
    // Gère les différentes actions que l'on peut effectuer à chaque tour avec la possibilité d'avoir une aide
    public void actionTourAide(Plateau plateau, boolean repart, boolean bolaide) {
        // Le joueur peut demander de l'aide pour un déplacement
        String aide = "";
        while (aide.equals("oui") == false && aide.equals("non") == false) {
            System.out.println("Voulez-vous activer l'aide ? (oui / non)");
            Scanner sc = new Scanner(System.in);
            aide = sc.nextLine();
            if (aide.equals("oui") == true){
                bolaide = true;
            } else {
                bolaide = false;
            }
        }
        if (bolaide == false){
            plateau.choisirAction(plateau);
            plateau.affichagePlateau();
            //j.majDonee(); // Mets à jour les données dans la base de donnée
        
            // Le joueur peut réinitialiser le jeu
            String reini = "";
            while (reini.equals("oui") == false && reini.equals("non") == false) {
                System.out.println("Voulez-vous activer la réinitialisation de grille ? (oui / non)");
                Scanner sc = new Scanner(System.in);
                reini = sc.nextLine();
                if (reini.equals("oui") == true){
                    repart = true;
                } else {
                    repart = false;
                    actionTourAide(plateau,repart,bolaide);
                }
            }
        }
    }
}
