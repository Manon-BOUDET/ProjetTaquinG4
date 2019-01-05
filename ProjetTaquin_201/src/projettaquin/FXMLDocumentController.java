package projettaquin;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.RowConstraints;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;

/**
 *
 * @author Nicolas DAVID, Manon BOUDET, Eléonore GUISE
 * @version 1.0.1
 */
public class FXMLDocumentController implements Initializable {

    /*
     * Variables globales correspondant à des objets définis dans la vue (fichier .fxml)
     * Ces variables sont ajoutées à la main et portent le même nom que les fx:id dans Scene Builder
     */
    @FXML
    private GridPane grille; // plateau de jeu
    @FXML
    private Pane fond; // panneau recouvrant toute la fenêtre
    @FXML
    private Label labelWin; // Message de victoire
    
    public void initialisation2() {
        // Pour chaques cases du plateau, on va créer une tuile qui contiendra un label représentant un numéro.
        // On initialise ici la grille réussie (donc valide).        
        int col, line, i = 0;
        for (col = 0; col <= 3; col++) {
            for (line = 0; line <= 3; line++) {
                if (col == 3 && line == 3) {
                    Pane paneTuile = new Pane();
                    paneTuile.setPrefSize(100, 100);
                    Label num = new Label(null);
                    grille.add(paneTuile, col, line);
                    paneTuile.getChildren().add(num);
                    break;
                }
                i = (1 + col) + (4 * line);
                Pane paneTuile = new Pane();
                paneTuile.setPrefSize(100, 100);
                GridPane grilleInit = grille;
                paneTuile.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        Node source = (Node) mouseEvent.getSource();
                        Integer colIndex = GridPane.getColumnIndex(source);
                        Integer rowIndex = GridPane.getRowIndex(source);
                        //System.out.println(rowIndex);
                        //System.out.println(colIndex);
                    }
                });

                Label num = new Label(Integer.toString(i));

                paneTuile.getStyleClass().add("pane"); // On applique à l'objet le style css correspondant
                num.getStyleClass().add("tuile"); // On applique à l'objet le style css correspondant
                grille.add(paneTuile, col, line);
                GridPane.setHalignment(num, HPos.CENTER);
                paneTuile.getChildren().add(num);
                if (i >= 10) { // Lorsque le label contient deux chiffres, on décale le texte pour le recentrer sur la case
                    num.setTranslateX(-10);
                }
            }
        }
    }

    public void deplacement(GridPane grille,Pane pane,final int row,final int column) {
        // Echange la position de deux cases (nodes).
        Node node = (Node) pane;
        Node nodeFinal = (Node) getPaneByRowColumnIndex(grille,row,column);
        int ligneDepart = grille.getRowIndex(node);
        int colonneDepart = grille.getColumnIndex(node);
        grille.setColumnIndex(node,column);
        grille.setRowIndex(node,row);
        grille.setColumnIndex(nodeFinal,colonneDepart);
        grille.setRowIndex(nodeFinal,ligneDepart);
    }
    
    public void deplacementSimple(int row, int col){
        List<Integer> vide = rechercheVide(grille);
        int rowVide = vide.get(0);
        int colVide = vide.get(1);
        if (((rowVide == row)&& (col == colVide -1))||((rowVide == row)&&(col == colVide +1))||((rowVide +1 == row)&&(col == colVide))||((rowVide -1 == row)&&(col == colVide))){
            deplacement(grille,getPaneByRowColumnIndex(grille,row,col),rowVide,colVide);
        }
        else{
            System.out.println("Déplacement simple impossible.");
        }
    }

    public List<Integer> rechercheVide(GridPane grille) {
        // Retourne les coordonnées (ligne et colonne) de la case vide dans une ArrayList.
        List<Integer> coordonnees = new ArrayList<Integer>();
        int row = 0, column= 0;
        Node result = null;
        ObservableList<Node> children = grille.getChildren();
        if (children.size() > 16) {
            children.remove(0);
        }
        for (Node node: children) {
            if (getLabel((Pane) node).getText() == null) {
                result = node;
                break;

            }
        }
        row = grille.getRowIndex(result);
        column = grille.getColumnIndex(result);
        coordonnees.add(row);
        coordonnees.add(column);
        return coordonnees;
    }

    public Pane getPaneByRowColumnIndex(GridPane grille,final int row,final int column) {
        // Retourne le Pane (la case) positionnée à la ligne "row" et la colonne "column".
        // On va donc parcourir la liste des enfants de la grille, sélectionner celui qui correspond à la bonne position et le convertir en Pane.
        Node result = null;
        ObservableList<Node> children = grille.getChildren();
        if (children.size() > 16) {
            children.remove(0);
        }
        for (Node node: children) {
            if ((grille.getRowIndex(node) == row) && (grille.getColumnIndex(node) == column)) {
                result = node;
                break;
            }
        }
        return (Pane) result;
    }

    public Label getLabel(Pane pane) {
        // Retourne le Label (le numéro) d'une case.
        // On va parcourir la liste des enfants de la case, mais pour une case on a qu'un enfant (on a un seul numéro par case)
        // donc on va le récupérer et le convertir en Label.
        Node result = null;
        ObservableList<Node> children = pane.getChildren();
        for (Node node : children) {
            result = node;
            break;
        }
        return (Label) result;

    }
    
    public void finPartie(){
        List<Integer> vide = rechercheVide(grille);
        int rowVide = vide.get(0);
        int colVide = vide.get(1);
        if (rowVide == 3 && colVide ==3){
            int bonnePos = 0;
            for (int i=0;i<4;i++){
                for (int j=0;j<4;j++){
                    if (i != 3 || j !=3){
                        Pane tuile = getPaneByRowColumnIndex(grille, i, j);
                        Label label = getLabel(tuile);
                        int numero = Integer.parseInt(label.getText());
                        if ((1 + j) + (4 * i) == numero) {
                            //System.out.println("C'est égal");
                            bonnePos +=1;
                        }else {
                            //System.out.println("Ce n'est pas égal");
                        }
                    }  
                }
            }
            if (bonnePos == 15){
                //System.out.println("Bravo, vous avez gagné !");
                labelWin.setText("Bravo, vous avez gagné !");
                return;
            }
        } 
        //System.out.println("Pas encore fini");
        labelWin.setText("");
    }
    
    public void melanger(){
        for (int i=0;i<100;i++){
           List<Integer> vide = rechercheVide(grille);
            int rowVide = vide.get(0);
            int colVide = vide.get(1);
            int random = (int)(Math.random() * 4);
            if ((random == 0)&&(rowVide>0)){
                deplacementSimple(rowVide -1,colVide);
            }
            else if ((random == 1)&&(rowVide<3)){
                deplacementSimple(rowVide +1,colVide);
            }
            else if ((random == 2)&&(colVide>0)){
                deplacementSimple(rowVide,colVide -1);
            }
            else if ((random == 3)&&(colVide<3)){
                deplacementSimple(rowVide,colVide +1);
            }
        }
    }

    @Override
    public void initialize(URL url,ResourceBundle rb) {
        // initialisation de la grille
        grille.getStyleClass().add("gridpane"); // On applique à l'objet le style css correspondant
        initialisation2();
        melanger();
        labelWin.setText("");
    }

    @FXML
    private void handleButtonActionAide(MouseEvent event) {
        System.out.println("Activation de l'aide");
    }

    @FXML
    private void handleButtonActionMelange(MouseEvent event) {
        System.out.println("Activation de la réinitialisation de grille");
        melanger();
        labelWin.setText("");
    }
}
