package Game.model.entity;
import Game.model.Form.Shape;
import java.util.List;
import java.util.ArrayList;

public abstract class Entity {

    private List<Shape> shapes; // Liste des formes placées par un joueur
    private double score; // Score du joueur
    private String name; // Nom du joueur

    public Entity(){
        this.shapes = new ArrayList<>();
        this.score = 0;
        this.name = "Player";

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


}
