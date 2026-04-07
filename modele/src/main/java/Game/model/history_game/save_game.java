package Game.model.history_game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class save_game {

    private static final String FILE_NAME = "history.txt";

    public static void save_score(double score_joueur1, double score_joueur2) {

        String score = "Joueur 1: " + score_joueur1 + " - Joueur 2: " + score_joueur2;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getLineCount(String filename) {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    public static void check_history(double score_joueur1, double score_joueur2) {

        if (getLineCount(FILE_NAME) >= 10) {
            try {
                List<String> lines = new ArrayList<>();

                // Lire toutes les lignes
                BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                reader.close();

                // Garder seulement les 9 dernières (on ajoutera la nouvelle = 10)
                if (lines.size() >= 9) {
                    lines = lines.subList(lines.size() - 9, lines.size());
                }

                // Réécrire le fichier
                BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
                for (String l : lines) {
                    writer.write(l);
                    writer.newLine();
                }

                // Ajouter le nouveau score
                writer.write("Joueur 1: " + score_joueur1 + " - Joueur 2: " + score_joueur2);
                writer.newLine();

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save_score(score_joueur1, score_joueur2);
        }
    }

    public void extract_history() {

        double score_joueur1 = 0;
        double score_joueur2 = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                // Exemple ligne :
                // Joueur 1: 10.0 - Joueur 2: 20.0

                String[] parts = line.split(" - ");

                String joueur1Part = parts[0].split(": ")[1];
                String joueur2Part = parts[1].split(": ")[1];

                score_joueur1 += Double.parseDouble(joueur1Part);
                score_joueur2 += Double.parseDouble(joueur2Part);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}