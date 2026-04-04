package com.example;

import java.awt.*;

public interface Drawable { // Interface pour les formes dessinables, elle définit les méthodes que doivent implémenter les formes pour pouvoir être utilisées dans le modèle et la vue, notamment la méthode getBounds pour obtenir les limites de la forme et la méthode accept pour accepter un visiteur d'intersection

    Rectangle getBounds();
    void accept(IntersectionVisiteur  Visiteur);



}