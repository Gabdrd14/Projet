package Game.model;

import java.util.ArrayList;

public abstract class AbstractModeleEcoutable implements ModelEcoutable {
	
	private ArrayList<EcouteurModel> ecouteurs = new ArrayList<>();

	@Override
	public void ajoutEcouteur(EcouteurModel e) {
		ecouteurs.add(e);
	}
	
	protected void fireChange() {
		for (EcouteurModel l : ecouteurs) {
			l.stateChanged(this);	
		}
	}
	
}