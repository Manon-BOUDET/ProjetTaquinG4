/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projettaquin;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Manon
 */

public class Joueur {
    private int id; // Identifiant du joueur
    private int score; // Score du joueur
    private int meilleurScore; // Meilleur score du joueur
    private int scoreDernierePartie; // Score de la dernière partie du joueur
    
/* Constructeurs */
    // Constructeur permettant d'ajouter ou de prendre des informations sur le joueur qui est dans la base de donnée
    public Joueur(int identifiant) {
        this.id = identifiant;
        this.score = 0;
        ArrayList<String> l = AvantConnexionBDD.avCo("SELECT", this.id, "SELECT * FROM 'joueurs' WHERE id=" + '"' + this.id + '"');
        if (l.size() == 0) {
            AvantConnexionBDD.avCo("INSERT", this.id, "INSERT INTO joueurs VALUES(" + '"' + this.id + '"'+ "," + "0,0)");
        } else if (l.size() == 1) {
            System.out.println(l.get(0));
            this.meilleurScore = Integer.parseInt(l.get(1));
            System.out.println(this.meilleurScore);
            this.scoreDernierePartie = Integer.parseInt(l.get(2));
            System.out.println(this.scoreDernierePartie);
        }
    }
    // Constructeur pour un joueur
    public Joueur(){
        this.id = 0;
        this.score = 0;
        this.meilleurScore = 0;
        this.scoreDernierePartie = 0;
    }

    // Constructeur pour l'ia
    public Joueur(int ident, int s, int meiScore, int scoreDerPart){
        this.id = ident;
        this.score = s;
        this.meilleurScore = meiScore;
        this.scoreDernierePartie = scoreDerPart;
    }
    
/* Getters & Setters */
    public Joueur getJoueur(Joueur j){
        Joueur joueur = new Joueur(j.id,j.score,j.meilleurScore,j.scoreDernierePartie);
        return joueur;
    }
    
    public void setScore(int i) {
        this.score = this.score + i;
    }
    public int getScore() {
        return this.score;
    }
    public void removeScore() {
        this.score = 0;
    }
    
    public void setDerScore(int s){
        this.scoreDernierePartie = s;
    }
    public int getDerScore(){
        return this.scoreDernierePartie;
    }

    public void setMeilScore(int m){
        this.meilleurScore = m;
    }
    public int getMeilScore(){
        return this.meilleurScore;
    }
    
/* Initialisations */
    // Initilise le Joueur 
    public Joueur initialisationJoueur() {
        boolean b = false;
        Scanner sc;
        Integer id = null;
        while (b == false) {
            System.out.println("Entrer un identifiant : (entier naturel à partir de 1)");
            System.out.println("test");
            sc = new Scanner(System.in);
            System.out.println("test1");
            String ident = sc.nextLine();
            System.out.println("test2");
            id = Integer.parseInt(ident);
            if (id > 0) {
                System.out.println("test3");
                b = true;
                
            }
        }
        Joueur joueur = new Joueur(id);
        return joueur;
    }
    // Initialise l'IA
    public Joueur initialisationIA(){
        Joueur joueur = new Joueur();
        return joueur;
    }
    
    // Mets à jour la base de donnée
    public void majDonee() {
        if (this.score > this.meilleurScore) {
            this.meilleurScore = this.score;
        }
        AvantConnexionBDD.avCo("UPDATE", this.id, "UPDATE joueurs SET meilleur_score=" + this.meilleurScore + ", score_dernière_partie=" + this.score + " WHERE identifiant=" + '"' + this.id + '"');
    }
}



