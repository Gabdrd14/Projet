package Game.model;

import java.util.ArrayList;

public abstract class AbstractModeleEcoutable implements ModelEcoutable {
	
	private ArrayList<EcouteurModel> ecouteurs = new ArrayList<>();

	@Override
	public void ajoutEcouteur(EcouteurModel e) {
		ecouteurs.add(e);
	}
	
	// On déclenche la notification de tous les écouteurs //
	protected void fireChange() {
		for (EcouteurModel l : ecouteurs) {
			l.stateChanged(this);	
		}
	}
	
	// On informe tous les observateurs qu’un changement a eu lieu //
	public void notifyObservers() {
		fireChange();
		
	}
	
}