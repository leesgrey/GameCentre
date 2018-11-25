package companydomain.applicationname;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import static android.content.Context.MODE_PRIVATE;

class ScoreBoard implements Serializable {

    /**
     * A file containing the instance of ScoreBoard we will be using.
     */
    private String scoreBoardFile;

    /**
     * A HashMap containing ArrayLists for each size game.
     */
    private HashMap<Integer, ArrayList<Score>> scoreBoards;

    /**
     * Initialize a ScoreBoard.
     */
    @SuppressLint("UseSparseArrays")
    private ScoreBoard(String fileLocation) {
        scoreBoardFile = fileLocation;
        scoreBoards = new HashMap<>();
    }

    /**
     * Add a score to our set of scores.
     *
     * @param score the score to be added
     */
    void addScore(Score score) {
        if (scoreBoards.get(score.getSize()) == null) {
            scoreBoards.put(score.getSize(), new ArrayList<Score>());
        }
        scoreBoards.get(score.getSize()).add(score);
        Collections.sort(scoreBoards.get(score.getSize()));
    }

    // TODO: probably get rid of this
    /**
     * Return the file in which this Scoreboard is being stored.
     *
     * @return the file in which this Scoreboard is being stored
     */
    private String getScoreBoardFile() {
        return scoreBoardFile;
    }

    /**
     * Return the top "n" scores from games of size "size".
     *
     * @param n    the number of scores to be returned
     * @param size the size of the board for which we're looking at scores
     */
    ArrayList<Score> topNScores(int n, int size) {
        ArrayList<Score> topScores = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (scoreBoards.get(size) == null || i >= scoreBoards.get(size).size()) {
                topScores.add(new Score("Default", 9999, size));
            } else {
                topScores.add(scoreBoards.get(size).get(i));
            }
        }
        return topScores;
    }

    /**
     * Return the top "n" scores from games of size "size" for user "username".
     *
     * @param n        the number of scores to be returned
     * @param size     the size of the board for which we're looking at scores
     * @param username the username for which we're looking for scores
     */
    ArrayList<Score> topNUserScores(int n, int size, String username) {
        ArrayList<Score> topScores = new ArrayList<>();
        int numberOfScoresFound = 0;
        int scoresCheckedSoFar = 0;
        while (numberOfScoresFound < n) {
            // if there are no more scores for this user
            if (scoreBoards.get(size) == null || scoresCheckedSoFar >= scoreBoards.get(size).size()) {
                topScores.add(new Score("Default", 9999, size));
                numberOfScoresFound++;
                // if the next score is one of the user's
            } else if (username.equals(scoreBoards.get(size).get(scoresCheckedSoFar).getUsername())) {
                topScores.add(scoreBoards.get(size).get(scoresCheckedSoFar));
                numberOfScoresFound++;
                scoresCheckedSoFar++;
            } else {
                scoresCheckedSoFar++;
            }
        }
        return topScores;
    }

    /**
     * Create, save, and return a ScoreBoard.
     *
     * @param game the String "slidingTiles", "matching", or "masterMind"
     * @param activity an AppCompatActivity that we will use to do file writing
     */
    static private ScoreBoard createScoreBoard(String game, AppCompatActivity activity) {
        ScoreBoard scoreBoard;
        scoreBoard = new ScoreBoard(getFileLocation(game));
        scoreBoard.writeScoreBoard(activity);
        return scoreBoard;
    }

    /**
     * Write the ScoreBoard to the file.
     *
     * @param activity an AppCompatActivity that we will use to do file writing
     */
    void writeScoreBoard(AppCompatActivity activity) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    activity.openFileOutput(this.scoreBoardFile, MODE_PRIVATE));
            outputStream.writeObject(this);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Return the ScoreBoard contained in the file.
     *
     * @param game the String "slidingTiles", "matching", or "masterMind"
     * @return the ScoreBoard contained in the file
     */
    static ScoreBoard loadScoreBoard(String game, AppCompatActivity activity) {
        ScoreBoard scoreBoard = null;
        String scoreBoardFile = getFileLocation(game);
        try {
            InputStream inputStream = activity.openFileInput(scoreBoardFile);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                scoreBoard = (ScoreBoard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        if(scoreBoard == null) {
            return ScoreBoard.createScoreBoard(game, activity);
        }
        return scoreBoard;
    }

    /**
     * Return the file location of the ScoreBoard corresponding to the game.
     *
     * @param game the String "slidingTiles", "matching", or "masterMind"
     * @return the file location of the ScoreBoard corresponding to the game
     */
    private static String getFileLocation(String game) {
        switch (game) {
            case "slidingTiles":
                return "sliding_tiles_score_board.ser";
            case "matching":
                return "matching_score_board.ser";
            case "masterMind":
                return "masterMind_score_board.ser";
            default:
                return "default_score_board.ser";
        }
    }
}
