/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projettaquin;

/**
 *
 * @author Manon
 */

public class Case extends Plateau{
    private boolean place; // Initialiser à false pour dire que la case n'est pas à la bonne palce
    protected int valeur;  // Valeur d'une case
    private int ligne;     // Ligne d'une case
    private int colonne;   // Colonne d'une case

/* Constructeurs */
    public Case(){
        
    }
    public Case(int v, int l, int c) {
        this.place = false;
        this.valeur = v;
        this.ligne = l;
        this.colonne = c;
    }
    public Case(Case c) {
        this.place = c.place;
        this.ligne = c.ligne;
        this.colonne = c.colonne;
        if (c.valeur != 0){
            this.valeur = c.valeur;
        } else {
            this.valeur = 0; // Pour la case vide (sans valeur)
        }
    }
    
/* Getters & Setters */
    // @Override
    public boolean getPlace() {
        return this.place;
    }
    public boolean getPlace(Case ca) {
        return ca.place;
    }
    
    public void setPlace(boolean b) {
        this.place = b;
    }
   public void setPlace(Case c, boolean b){
        c.place = b;
    }
   
    public void setPlace(int v, boolean b) {
        this.place = b;
        this.valeur = v;
    }
    public void setPlace(Case ca, int v, boolean b) {
        this.place = b;
        this.valeur = v;
    }
    public void setCase(Case ca, int v, int l, int c, boolean b) {
        ca.valeur = v;
        ca.ligne = l;
        ca.colonne = c;
        ca.place = b;
    }

    public int getValeur() {
        return this.valeur;
    }
    public int getValeur(Case c) {
        return c.valeur;
    }
    
    public void setValeur(int v){
        this.valeur = v;
    }
    public void setValeur(Case ca, int v){
        ca.valeur = v;
    }
    
    public int getLigne() {
        return this.ligne;
    }
    public int getLigne(Case ca) {
        return ca.ligne;
    }

    public void setLigne(int l){
        this.ligne = l;
    }
    public void setLigne(Case ca, int l){
        ca.ligne = l;
    }
    
    public int getColonne() {
        return this.colonne;
    }
    public int getColonne(Case ca) {
        return ca.colonne;
    }
    
    public void setColonne(int c) {
        this.colonne = c;
    }
    public void setColonne(Case ca, int c) {
        ca.colonne = c;
    }
    
    public void setCoordonnees(int l, int c){
        this.ligne = l;
        this.colonne = c;
    }
    public void setCoordonnees(Case ca, int l, int c){
        ca.ligne = l;
        ca.colonne = c;
    }
}
