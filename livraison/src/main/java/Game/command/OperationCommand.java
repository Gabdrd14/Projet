package Game.command;

public interface OperationCommand {
	
	// On exécute l’action de la commande //
	public void operate();
	
	// On annule l’action de la commande //
	public void compensate();

}
