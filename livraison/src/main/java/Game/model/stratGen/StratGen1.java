package Game.model.stratGen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Game.model.ConfigFileSelector;
import Game.model.Plateau;
import Game.model.Point;
import Game.model.Form.CircleShape;
import Game.model.Form.RectangleShape;

/**
     * StratGen1 est une stratégie de génération d'obstacles pour le plateau de jeu.
     * 
     * Cette stratégie lit les obstacles à partir d'un fichier de configuration texte. Le format du fichier doit être le suivant :
     * Chaque ligne représente un obstacle.
     * Les obstacles sont définis par leurs types (RECTANGLE ou CERCLE) et leurs paramètres.
     * Le fichier de configuration est choisi selon la rotation définie dans ConfigFileSelector.
     */

public class StratGen1 implements StrategiePlateau {

    // Chemin du fichier de configuration utilisé
    private String cheminFichier;
    
    // Indique si la stratégie doit utiliser ConfigFileSelector (mode automatique)
    // ou un fichier spécifique (mode manuel)
    private boolean useConfigSelector;

    /**
     * Constructeur utilisant ConfigFileSelector pour les fichiers de config rotatifs.
     * 
     * En mode automatique, chaque nouvelle partie utilisera un fichier de configuration
     * différent selon la rotation définie dans ConfigFileSelector.
     */
    public StratGen1() {
        this.useConfigSelector = true;
        this.cheminFichier = ConfigFileSelector.getInstance().getCurrentConfigPath();
    }

    /**
     * Constructeur avec un chemin de fichier spécifique (rétrocompatibilité).
     * 
     * Ce constructeur permet de spécifier directement quel fichier de configuration utiliser,
     * sans passer par le système de rotation automatique.
     * 
     * @param cheminFichier Le chemin du fichier de configuration à utiliser
     */
    public StratGen1(String cheminFichier) {
        this.cheminFichier = cheminFichier;
        this.useConfigSelector = false;
    }

    /**
     * Obtient le chemin du fichier de configuration courant.
     * 
     * Si le mode automatique est activé (useConfigSelector = true), cette méthode
     * récupère le chemin du fichier suivant dans la rotation du ConfigFileSelector.
     * Sinon, elle retourne le chemin fixe fourni au constructeur.
     * 
     * @return Le chemin du fichier de configuration à utiliser
     */
    public String getCheminFichier() {
        // En mode automatique, on récupère toujours le chemin courant du sélecteur
        if (useConfigSelector) {
            this.cheminFichier = ConfigFileSelector.getInstance().getCurrentConfigPath();
        }
        return this.cheminFichier;
    }

    @Override
    public void genererObstacles(Plateau plateau) {
        try (BufferedReader reader = new BufferedReader(new FileReader(getCheminFichier()))) {
            String ligne;

            // Boucle de lecture ligne par ligne du fichier de configuration
            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim(); // Supprime les espaces au début et fin

                // Ignore les lignes vides
                if (ligne.isEmpty()) {
                    continue;
                }

                // Ignore les commentaires (lignes commençant par #)
                if (ligne.startsWith("#")) {
                    continue;
                }

                // Divise la ligne selon le séparateur ';' pour extraire les paramètres
                String[] parties = ligne.split(";");

                // Traite le cas des RECTANGLES (format : RECTANGLE;x1;y1;x2;y2)
                if (parties[0].equalsIgnoreCase("RECTANGLE")) {
                    if (parties.length != 5) {
                        System.out.println("Ligne invalide pour RECTANGLE : " + ligne);
                        continue;
                    }

                    // Extraction et conversion des coordonnées
                    double x1 = Double.parseDouble(parties[1]);
                    double y1 = Double.parseDouble(parties[2]);
                    double x2 = Double.parseDouble(parties[3]);
                    double y2 = Double.parseDouble(parties[4]);

                    // Création du rectangle avec les deux points (coin oppose)
                    RectangleShape rectangle = new RectangleShape(
                        new Point(x1, y1),
                        new Point(x2, y2)
                    );

                    // Ajout du rectangle au plateau
                    plateau.ajouterObstacle(rectangle);

                } 
                // Traite le cas des CERCLES (format : CERCLE;x;y;rayon)
                else if (parties[0].equalsIgnoreCase("CERCLE")) {
                    if (parties.length != 4) {
                        System.out.println("Ligne invalide pour CERCLE : " + ligne);
                        continue;
                    }

                    // Extraction et conversion du centre et du rayon
                    double x = Double.parseDouble(parties[1]);
                    double y = Double.parseDouble(parties[2]);
                    int rayon = Integer.parseInt(parties[3]);

                    // Création du cercle avec le centre et le rayon
                    CircleShape cercle = new CircleShape(
                        new Point(x, y),
                        rayon
                    );

                    // Ajout du cercle au plateau
                    plateau.ajouterObstacle(cercle);

                } 
                // Gère les types inconnus
                else {
                    System.out.println("Type inconnu dans le fichier : " + ligne);
                }
            }

        } 
        // Gère les erreurs de lecture du fichier
        catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + getCheminFichier());
            e.printStackTrace();
        } 
        // Gère les erreurs de conversion de nombres
        catch (NumberFormatException e) {
            System.out.println("Erreur de format dans le fichier texte.");
            e.printStackTrace();
        }
    }
}