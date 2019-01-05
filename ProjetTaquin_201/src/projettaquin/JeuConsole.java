/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projettaquin;

import java.util.List;
import java.util.Scanner;
import projettaquin.Plateau;
import projettaquin.Joueur;

/**
 *
 * @author Manon
 */

public class JeuConsole {

    public JeuConsole(){
        
    }
    
    public void debutJeu() {
        Plateau plateau = new Plateau();
        IAConsole ia;
        ia = new IAConsole();
        boolean bolaide = false;
        boolean repart = false;
        boolean finJeu = false;
        String t = "";
        while (t.equals("joueur") == false && t.equals("ia") == false) { // gère le mode joueur ou ia
            System.out.println("Voulez vous jouer tout seul ou faire jouer l'ia ? (rentrer joueur ou ia)");
            Scanner sc = new Scanner(System.in);
            t = sc.nextLine();
            if (t.equals("joueur")==true) {
                Joueur j = new Joueur();
                j.initialisationJoueur();
            } else if (t.equals("ia")==true) {
                Joueur j = new Joueur();
                j.initialisationIA();
            }
        }
        
        plateau.remplissagePlateau(); // Remplissage du plateau de case
        plateau.melangerPlateau(plateau); // Mélange du jeu
        plateau.affichagePlateau(); // Affichage du plateau
        
        while (finJeu == false || repart == false){
            if (t.equals("joueur") == true) {
                plateau.actionTour(plateau,repart);
            } else {
                while (bolaide == false){ // Fera appel à l'IA si besoin
                    plateau.actionTourAide(plateau,repart, bolaide);
                }
                if (bolaide == true){
                    // Fera appel à l'IA
                    plateau.actionTourAide(plateau,repart, bolaide);
                }
            }
        }
        plateau.finPartie(plateau);
    }
}
