package Game.model;

/**
 * Classe pour gérer la sélection cyclique des fichiers de configuration pour StratGen1.
 * Permet de passer d'un fichier de configuration à l'autre lors de chaque nouvelle partie.
 */
public class ConfigFileSelector {
    
    private static ConfigFileSelector instance;
    private int currentConfigIndex = 0; // Index du fichier de config actuel (0 = config1.txt)
    private static final int NUM_CONFIGS = 10; // Nombre de fichiers de config disponibles (config1.txt à config10.txt)
    private static final String CONFIG_BASE_PATH = "src/main/java/Game/model/stratConfig/config";
    private static final String CONFIG_EXTENSION = ".txt";
    
    private ConfigFileSelector() {
    }
    
    /**
     * Obtient l'instance unique (Singleton)
     */
    public static ConfigFileSelector getInstance() {
        if (instance == null) {
            instance = new ConfigFileSelector();
        }
        return instance;
    }
    
    /**
     * Obtient le chemin du fichier de configuration courant
     */
    public String getCurrentConfigPath() {
        int configNum = currentConfigIndex + 1; // config1.txt, config2.txt, etc.
        return CONFIG_BASE_PATH + configNum + CONFIG_EXTENSION;
    }
    
    /**
     * Passe au fichier de configuration suivant (cycliquement)
     */
    public void nextConfig() {
        currentConfigIndex = (currentConfigIndex + 1) % NUM_CONFIGS;
        System.out.println("Configuration suivante : " + getCurrentConfigPath());
    }
    
    /**
     * Obtient l'index actuel du fichier de configuration
     */
    public int getCurrentConfigIndex() {
        return currentConfigIndex;
    }
    
    /**
     * Définit l'index du fichier de configuration
     */
    public void setCurrentConfigIndex(int index) {
        if (index >= 0 && index < NUM_CONFIGS) {
            this.currentConfigIndex = index;
        }
    }
    
    /**
     * Réinitialise le sélecteur au premier fichier
     */
    public void reset() {
        currentConfigIndex = 0;
    }
}
