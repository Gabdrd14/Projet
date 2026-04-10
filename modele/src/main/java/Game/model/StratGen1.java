// package Game.model;
// import Game.model.Form.CircleShape;
// import Game.model.Form.RectangleShape;

// public class StratGen1 implements StrategiePlateau {

//     @Override
//     public void genererObstacles(Plateau plateau) {

//         plateau.ajouterObstacle(new CircleShape(new Point(93, 460), 30));

//         plateau.ajouterObstacle(new CircleShape(new Point(359, 339), 48));

//         plateau.ajouterObstacle(new CircleShape(new Point(1110, 318), 20));
//         plateau.ajouterObstacle(new CircleShape(new Point(478, 529), 30));
//         plateau.ajouterObstacle(new RectangleShape(
//                 new Point(619, 258),
//                 new Point(802, 327)
//         ));

//         plateau.ajouterObstacle(new CircleShape(new Point(400, 150), 30));

//         plateau.ajouterObstacle(new RectangleShape(
//                 new Point(1327, 562),
//                 new Point(1360, 611)
//         ));

//         plateau.ajouterObstacle(new RectangleShape(
//                 new Point(1280, 13),
//                 new Point(1361, 57)
//         ));

//         plateau.ajouterObstacle(new RectangleShape(
//                 new Point(1, 571),
//                 new Point(83, 618)
//         ));

//         plateau.ajouterObstacle(new RectangleShape(
//                 new Point(1, 7),
//                 new Point(53,74)
//         ));

//     }
// }

package Game.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Game.model.Form.CircleShape;
import Game.model.Form.RectangleShape;

public class StratGen1 implements StrategiePlateau {

    private String cheminFichier;
    private boolean useConfigSelector; // Indique si on utilise ConfigFileSelector

    /**
     * Constructeur utilisant ConfigFileSelector pour les fichiers de config rotatifs
     */
    public StratGen1() {
        this.useConfigSelector = true;
        this.cheminFichier = ConfigFileSelector.getInstance().getCurrentConfigPath();
    }

    /**
     * Constructeur avec un chemin de fichier spécifique (rétrocompatibilité)
     */
    public StratGen1(String cheminFichier) {
        this.cheminFichier = cheminFichier;
        this.useConfigSelector = false;
    }

    /**
     * Obtient le chemin du fichier de configuration courant
     */
    public String getCheminFichier() {
        if (useConfigSelector) {
            this.cheminFichier = ConfigFileSelector.getInstance().getCurrentConfigPath();
        }
        return this.cheminFichier;
    }

    @Override
    public void genererObstacles(Plateau plateau) {
        try (BufferedReader reader = new BufferedReader(new FileReader(getCheminFichier()))) {
            String ligne;

            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim();

                // ignorer lignes vides
                if (ligne.isEmpty()) {
                    continue;
                }

                // ignorer commentaires
                if (ligne.startsWith("#")) {
                    continue;
                }

                String[] parties = ligne.split(";");

                if (parties[0].equalsIgnoreCase("RECTANGLE")) {
                    if (parties.length != 5) {
                        System.out.println("Ligne invalide pour RECTANGLE : " + ligne);
                        continue;
                    }

                    double x1 = Double.parseDouble(parties[1]);
                    double y1 = Double.parseDouble(parties[2]);
                    double x2 = Double.parseDouble(parties[3]);
                    double y2 = Double.parseDouble(parties[4]);

                    RectangleShape rectangle = new RectangleShape(
                        new Point(x1, y1),
                        new Point(x2, y2)
                    );

                    plateau.ajouterObstacle(rectangle);

                } else if (parties[0].equalsIgnoreCase("CERCLE")) {
                    if (parties.length != 4) {
                        System.out.println("Ligne invalide pour CERCLE : " + ligne);
                        continue;
                    }

                    double x = Double.parseDouble(parties[1]);
                    double y = Double.parseDouble(parties[2]);
                    int rayon = Integer.parseInt(parties[3]);

                    CircleShape cercle = new CircleShape(
                        new Point(x, y),
                        rayon
                    );

                    plateau.ajouterObstacle(cercle);

                } else {
                    System.out.println("Type inconnu dans le fichier : " + ligne);
                }
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + getCheminFichier());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format dans le fichier texte.");
            e.printStackTrace();
        }
    }
}