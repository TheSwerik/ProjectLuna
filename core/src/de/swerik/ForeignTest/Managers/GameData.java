package de.swerik.ForeignTest.Managers;

import java.io.Serializable;
import java.util.Arrays;

public class GameData implements Serializable {

    private static final long serialVersionUID = 1;

    private final int MAX_SCORES = 10;
    private long[] highscores;
    private String[] names;

    private long tentScore;

    public GameData() {
        highscores = new long[MAX_SCORES];
        names = new String[MAX_SCORES];
    }

    //Set up empty GameData
    public void init() {
        for (int i = 0; i < MAX_SCORES; i++) {
            highscores[i] = 0;
            names[i] = "---";
        }
    }

    public long[] getHighscores() {
        return highscores;
    }

    public String[] getNames() {
        return names;
    }

    public long getTentScore() {
        return tentScore;
    }

    public void setTentScore(long tentScore) {
        this.tentScore = tentScore;
    }

    public boolean isHighscore(long score) {
        return score > highscores[MAX_SCORES - 1];
    }

    public void addHighscore(long newScore, String name) {
        if (isHighscore(newScore)) {
            highscores[MAX_SCORES - 1] = newScore;
            names[MAX_SCORES - 1] = name;
            //unreliable:
//            Arrays.sort(highscores);
//            for (int i = MAX_SCORES - 1; i > 0; i--) {
//                if (highscores[i] == newScore) {
//                    names[i] = name;
//                }
//            }
            sort();
        }
    }

    private void sort() { // Insertion-sort
        int j;
        for (int i = 1; i < MAX_SCORES; i++) {
            j = i;
            while (j > 0 && highscores[j - 1] > highscores[j]) {
                //swap Highscore
                long temp = highscores[j];
                highscores[j] = highscores[j - 1];
                highscores[j - 1] = temp;

                //swap Name
                String tempS = names[j];
                names[j] = names[j - 1];
                names[j - 1] = tempS;

                j = j - 1;
            }
        }

        //swap from lowest to highest
        for (int i = 0; i < MAX_SCORES; i++) {
            if (i >= MAX_SCORES - i) {
                return;
            }
            long temp = highscores[MAX_SCORES - 1 - i];
            highscores[MAX_SCORES - 1 - i] = highscores[i];
            highscores[i] = temp;
            String tempS = names[MAX_SCORES - 1 - i];
            names[MAX_SCORES - 1 - i] = names[i];
            names[i] = tempS;
        }
    }
}