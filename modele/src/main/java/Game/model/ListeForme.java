package Game.model;
import java.util.ArrayList;

public class ListeForme {

    // cette classe doit etre ecoutable 
    // liste de forme écoutable 
    protected ArrayList<Shape> listeForme;

    public ListeForme(){
        this.listeForme = new ArrayList<>();
    }
    
    //poutr le mvc je doit ajouter un ecouteur à chaque forme 
    public void ajoutForme(Shape forme){
        this.listeForme.add(forme);
    }

    public void supprimerForme(Shape forme){
        this.listeForme.remove(forme);
    }

    public void viderForme(){
        this.listeForme.clear();
    }

    // methode pour metre à jour la vue 


    // methode pour calculer l'aire total des formes des groupes de formes 

}