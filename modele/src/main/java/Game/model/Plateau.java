package Game.model;
import java.util.ArrayList;
import java.util.List;
import Game.model.collision.CollisionUtil;


public class Plateau {
    private int largeur;
    private int hauteur;
    private List<Shape> shapes ;
    private List<Shape> shapes2 ; 
    private List<Shape> liste_obstacle ;
    
    protected int compteur_piece ; // Compteur de pièces restantes pour chaque joueur, il commence à 8 et diminue à chaque ajout de forme, lorsque le compteur atteint 0, la partie est terminée
    private int score_joueur1 ; // Score du joueur 1,
    private int score_joueur2 ; // Score du joueur 2, 


    private StrategiePlateau strategieDeGen; // stratégie de génération d'obstacles


    public Plateau(int largeur, int hauteur, StrategiePlateau strategieDeGen) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.liste_obstacle = new ArrayList<>();
        this.shapes = new ArrayList<>();
        this.shapes2 = new ArrayList<>();
        this.score_joueur1 = 0;
        this.score_joueur2 = 0;
        this.compteur_piece = 8 ;
        this.strategieDeGen = strategieDeGen;

    }



    public boolean collision(boolean isPlayer1) { // true si collision, false sinon

        List<Shape> current = isPlayer1 ? shapes : shapes2;
        List<Shape> other   = isPlayer1 ? shapes2 : shapes;


        for (int i = 0; i < current.size(); i++) {
            Shape a = current.get(i);

            for (int j = i + 1; j < current.size(); j++) {
                if (CollisionUtil.intersects(a, current.get(j))) {
                    return true;
                }
            }

            for (Shape b : other) {
                if (CollisionUtil.intersects(a, b)) {
                    return true;
                }
            
            
            }
        }

        return false;
    }



    protected void score_game() { // retourne le score du jeu, il est calculé en fonction du nombre de formes de chaque type pour chaque joueur, le score est égal au nombre de rectangles du joueur 1 moins le nombre de rectangles du joueur 2 plus le nombre de cercles du joueur 1 moins le nombre de cercles du joueur 2, un score positif signifie que le joueur 1 est en avance, un score négatif signifie que le joueur 2 est en avance, un score nul signifie que les deux joueurs sont à égalité


        for (Shape s : shapes) {
            if (s instanceof RectangleShape) {

                int forme_valeur = s.getBounds().getSize().width * s.getBounds().getSize().height; // Calcul de la valeur de la forme en fonction de sa taille (aire du rectangle)
                score_joueur1 += forme_valeur; 
            } else if (s instanceof CircleShape) {
                int forme_valeur = (int) (Math.PI * Math.pow(s.getBounds().getSize().width / 2, 2)); // Calcul de la valeur de la forme en fonction de sa taille (aire du cercle)²
                score_joueur1 += forme_valeur; 
            }
        

        for (Shape y : shapes2) {
            if (y instanceof RectangleShape) {
                int forme_valeur = y.getBounds().getSize().width * y.getBounds().getSize().height; // Calcul de la valeur de la forme en fonction de sa taille (aire du rectangle)
                score_joueur2 += forme_valeur; 
            } else if (y instanceof CircleShape) {
                int forme_valeur = (int) (Math.PI * Math.pow(y.getBounds().getSize().width / 2, 2)); // Calcul de la valeur de la forme en fonction de sa taille (aire du cercle)
                score_joueur2 += forme_valeur; 
            } 
        }}

    }



    public String getCurrentPlayer() {   // retourne le nom du joueur actuel
        return (compteur_piece > 4) ? "joueur_1" : "joueur_2";

    }
    
    public int getscorejoueur1() { // retourne le score du joueur 1
        return score_joueur1;
    }

    public int getscorejoueur2() { // retourne le score du joueur 2
        return score_joueur2;
    }


    public void ajouterObstacle(Shape obstacle) { // Méthode pour ajouter un obstacle au modèle, elle est utilisée par la stratégie de génération d'obstacles pour ajouter des obstacles fixes sur le plateau, elle ajoute l'obstacle à la liste des obstacles
        liste_obstacle.add(obstacle);
    }

    
    public List<Shape> getObstacles() { // Méthode pour obtenir la liste des obstacles du modèle, elle est utilisée par la vue pour dessiner les obstacles sur le plateau, elle retourne la liste des obstacles
         return liste_obstacle;
     }

    
    public void viderObstacles() { // Méthode pour vider la liste des obstacles du modèle, elle est utilisée par la stratégie de génération d'obstacles pour réinitialiser les obstacles avant d'en générer de nouveaux, elle vide la liste des obstacles
        liste_obstacle.clear();



    }


    public void genererObs() {
        if (strategieDeGen == null) {
            throw new IllegalStateException("Aucune stratégie de génération définie.");
        }

        liste_obstacle.clear();
        strategieDeGen.genererObstacles(this);
    }


    public void ajouterFormePlacee(Shape forme){  // test 

        boolean isObstacle = compteur_piece > 8 ; // si compteur > 8 alors on ajoute un obstacle, sinon on ajoute une forme de joueur

        boolean isPlayer1 = compteur_piece > 4; // si compteur > 4 alors c'est le joueur 1 qui joue, sinon c'est le joueur 2

        List<Shape> current = isObstacle ? liste_obstacle : (isPlayer1 ? shapes : shapes2);
        current.add(forme);
        compteur_piece--;

        if (collision(isPlayer1)) {
            current.remove(current.size() - 1);
            System.out.println("Collision -> suppression");
        }

        if (compteur_piece == 0) {
            System.out.println("FINISH");
        }
    }




    public void supprimerFormePlacee(Shape forme){ // test 

        for (Shape s : shapes) {
            if (s.equals(forme)) {
                shapes.remove(s);
                
                System.out.println("Shapes 1 :" + forme);
                return;
            }
        }

         for (Shape s : shapes2) {
            if (s.equals(forme)) {
                shapes2.remove(s);
                System.out.println("Shapes 2 :" + forme);
                return;
            }
        }

    }




    public List<Shape> getFormePlacees() {
        List<Shape> allShape = new ArrayList<>();
        allShape.addAll(shapes);
        allShape.addAll(shapes2);
        return allShape;
    }



    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }



    public void setStrategieDeGen(StrategiePlateau strategieDeGen) {
        this.strategieDeGen = strategieDeGen;
    }

}