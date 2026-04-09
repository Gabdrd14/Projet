package Game.model.entity;
import Game.model.Form.Shape;
import java.util.List;
import java.util.ArrayList;

public abstract class Entity {

    private List<Shape> shapes; // Liste des formes placées par un joueur
    private double score; // Score du joueur
    private String name; // Nom du joueur
    private int piecesPlacedThisTurn; // Nombre de pièces placées dans le tour actuel
    private int totalPiecesPlaced; // Nombre total de pièces placées

    public Entity(){
        this.shapes = new ArrayList<>();
        this.score = 0;
        this.name = "Player";
        this.piecesPlacedThisTurn = 0;
        this.totalPiecesPlaced = 0;

    }

    public List<Shape> getShapes() { // Récupère la liste des formes placées par le joueur
        return shapes;
    }
    
    public void addShape(Shape shape) { // Ajoute une forme à la liste des formes placées par le joueur
        this.shapes.add(shape);
    }
    
    public void setName(String name) { // Définit le nom du joueur
        this.name = name;
    }

    public String getName() { // Récupère le nom du joueur
        return name;
    }

    public double getScore() { // Récupère le score du joueur
        return score;
    }
    public void setScore(double score) { // Définit le score du joueur
        this.score = score;
    }

    public int getPiecesPlacedThisTurn() { // Récupère le nombre de pièces placées dans le tour actuel
        return piecesPlacedThisTurn;
    }

    public void incrementPiecesThisTurn() { // Incrémente le nombre de pièces placées dans le tour actuel
        this.piecesPlacedThisTurn++;
        this.totalPiecesPlaced++;
    }

    public void resetPiecesThisTurn() { // Réinitialise le compteur de pièces pour le tour actuel
        this.piecesPlacedThisTurn = 0;
    }

    public int getTotalPiecesPlaced() { // Récupère le nombre total de pièces placées
        return totalPiecesPlaced;
    }

}
