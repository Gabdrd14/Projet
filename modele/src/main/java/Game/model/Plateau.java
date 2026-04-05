package Game.model;

public class Plateau {
    private int largeur;
    private int hauteur;
    private ListeForme obstacles; // forme placée à l'initialisation de la partie 
    private ListeForme formePlacee;  // forme placée par les joueurs 
    private StrategiePlateau strategieDeGen; // stratégie de génération d'obstacles

    public Plateau(int largeur, int hauteur, StrategiePlateau strategieDeGen) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.obstacles = new ListeForme();
        this.formePlacee = new ListeForme();
        this.strategieDeGen = strategieDeGen;
    }


    // fonction qui ajoute dans obstacle sur le plateau.
    public void ajouterObstacle(Shape obstacle){
        this.obstacles.ajoutForme(obstacle);
    }

    // fonction qui ajoute dans les formes normales sur le plateau.
    public void ajouterFormePlacee(Shape forme){
        this.formePlacee.ajoutForme(forme);
    }
    
    public void supprimerFormePlacee(Shape forme){
        this.formePlacee.supprimerForme(forme);
    }

    // largeur du plateau 
    public int getLargeur() {
        return largeur;
    }

    // hauteur du plateau 
    public int getHauteur() {
        return hauteur;
    }


    public ListeForme getObstacles() {
        return this.obstacles;
    }
    
    public ListeForme getFormePlacees() {
        return this.formePlacee;
    }

    public void setStrategieDeGen(StrategiePlateau strategieDeGen) {
        this.strategieDeGen = strategieDeGen;
    }

    public void viderObstacles() {
        obstacles.viderForme();
    }

    public void genererObs() {
        if (strategieDeGen == null) {
            throw new IllegalStateException("Aucune stratégie de génération définie.");
        }

        obstacles.viderForme();
        strategieDeGen.genererObstacles(this);
    }

    public void afficherObstacles() {
        for (Shape f : obstacles.listeForme) {
            System.out.println(f);
        }
    }
}