package Game.model.history_game;

import Game.model.entity.Entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Gère l'enregistrement des scores par partie et le calcul de la moyenne
 * glissante sur les 10 dernières parties.
 *
 * Format d'une ligne dans history.txt :
 *   GAME_N|NomJoueur1:score1|NomJoueur2:score2|...
 */
public class save_game {

    private static final String FILE_NAME = "history.txt";
    private static final int WINDOW = 10;

    /**
     * Enregistre les scores de la partie courante.
     * Si la fenêtre de 10 parties est atteinte, calcule et retourne
     * le score moyen de chaque joueur sur ces 10 parties.
     * Retourne null si moins de 10 parties ont été jouées.
     */
    public static Map<String, Double> checkAndSave(List<Entity> players) {
        List<String> lines = readLines();

        // Construction de la nouvelle ligne
        int gameNum = lines.size() + 1;
        StringBuilder sb = new StringBuilder("GAME_" + gameNum);
        for (Entity p : players) {
            sb.append("|").append(p.getName()).append(":").append(String.format(Locale.US, "%.2f", p.getScore()));
        }
        lines.add(sb.toString());

        // Fenêtre glissante : on ne conserve que les WINDOW dernières parties
        if (lines.size() > WINDOW) {
            lines = lines.subList(lines.size() - WINDOW, lines.size());
        }

        writeLines(lines);

        // Si la fenêtre est pleine, on retourne les moyennes
        if (lines.size() == WINDOW) {
            return computeAverages(lines);
        }
        return null;
    }

    /**
     * Calcule le score moyen de chaque joueur sur les lignes fournies.
     */
    private static Map<String, Double> computeAverages(List<String> lines) {
        Map<String, List<Double>> scores = new LinkedHashMap<>();

        for (String line : lines) {
            String[] parts = line.split("\\|");
            // parts[0] = "GAME_N", parts[1..] = "Nom:score"
            for (int i = 1; i < parts.length; i++) {
                String[] entry = parts[i].split(":");
                if (entry.length == 2) {
                    String name = entry[0].trim();
                    try {
                        double score = Double.parseDouble(entry[1].trim().replace(',', '.'));
                        scores.computeIfAbsent(name, k -> new ArrayList<>()).add(score);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }

        Map<String, Double> averages = new LinkedHashMap<>();
        for (Map.Entry<String, List<Double>> e : scores.entrySet()) {
            List<Double> vals = e.getValue();
            double avg = vals.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            averages.put(e.getKey(), avg);
        }
        return averages;
    }

    // ---- utilitaires I/O ----

    private static List<String> readLines() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) lines.add(line);
            }
        } catch (IOException ignored) {
            // le fichier peut ne pas encore exister
        }
        return lines;
    }

    private static void writeLines(List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Retourne le nombre de parties enregistrées dans l'historique. */
    public static int getGameCount() {
        return readLines().size();
    }
}