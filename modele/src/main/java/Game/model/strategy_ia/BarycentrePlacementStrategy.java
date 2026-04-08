// package Game.model.strategy_ia;

// import Game.model.Form.RectangleShape;
// import Game.model.Form.Shape;

// import java.util.List;

// import Game.model.Plateau;

// public class BarycentrePlacementStrategy implements PlacementStrategy { 
//     @Override
//     public Shape placeShape(Plateau plateau) {
        
//         List<Shape> formesPlacees = plateau.getFormesPlacees();
//         if (formesPlacees.isEmpty()) {
//             // Si aucune forme n'est placée, placer au centre du plateau
//             int centerX = plateau.getHauteur() / 2;
//             int centerY = plateau.getLargeur() / 2;
//             // Créer une forme centrée (ex: un carré de taille 1x1)
//             return new RectangleShape(centerX, centerY);  // a corrige pour correspondre au constructeur de RectangleShape
//         }   else {
//             // Calculer le barycentre des formes placées
//             double sumX = 0;
//             double sumY = 0;
//             for (Shape shape : formesPlacees) {
//                 sumX += shape.getBounds().getX() + shape.getBounds().getWidth() / 2.0; // centre de la forme
//                 sumY += shape.getBounds().getY() + shape.getBounds().getHeight() / 2.0; // centre de la forme
//             }
//             double barycentreX = sumX / formesPlacees.size();
//             double barycentreY = sumY / formesPlacees.size();
            
//             // Placer une nouvelle forme proche du barycentre
//             int newX = (int) Math.round(barycentreX);
//             int newY = (int) Math.round(barycentreY);
//             // Créer une forme centrée sur le barycentre (ex: un carré de taille 1x1)
//             return new RectangleShape(newX, newY);  // a corrige pour correspondre au constructeur de RectangleShape
//         }   
//         return null;
//     }
// }