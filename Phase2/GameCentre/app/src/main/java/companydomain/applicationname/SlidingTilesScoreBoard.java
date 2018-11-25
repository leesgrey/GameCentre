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

class SlidingTilesScoreBoard implements Serializable {

    /**
     * A file containing the instance of SlidingTilesScoreBoard we will be using.
     */
    private static String slidingTilesScoreBoardFile = "sliding_tiles_score_board.ser";

    /**
     * A HashMap containing ArrayLists for each size game.
     */
    private HashMap<Integer, ArrayList<SlidingTilesScore>> scoreBoards;

    /**
     * Initialize a SlidingTilesScoreBoard.
     */
    @SuppressLint("UseSparseArrays")
    private SlidingTilesScoreBoard() {
        scoreBoards = new HashMap<>();
    }

    /**
     * Add a score to our set of scores.
     *
     * @param score the score to be added
     */
    void addScore(SlidingTilesScore score) {
        if (scoreBoards.get(score.getSize()) == null) {
            scoreBoards.put(score.getSize(), new ArrayList<SlidingTilesScore>());
        }
        scoreBoards.get(score.getSize()).add(score);
        Collections.sort(scoreBoards.get(score.getSize()));
    }

    /**
     * Return the top "n" scores from games of size "size".
     *
     * @param n    the number of scores to be returned
     * @param size the size of the board for which we're looking at scores
     */
    ArrayList<SlidingTilesScore> topNScores(int n, int size) {
        ArrayList<SlidingTilesScore> topScores = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (scoreBoards.get(size) == null || i >= scoreBoards.get(size).size()) {
                topScores.add(new SlidingTilesScore("Default", 9999, size));
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
    ArrayList<SlidingTilesScore> topNUserScores(int n, int size, String username) {
        ArrayList<SlidingTilesScore> topScores = new ArrayList<>();
        int numberOfScoresFound = 0;
        int scoresCheckedSoFar = 0;
        while (numberOfScoresFound < n) {
            // if there are no more scores for this user
            if (scoreBoards.get(size) == null || scoresCheckedSoFar >= scoreBoards.get(size).size()) {
                topScores.add(new SlidingTilesScore("Default", 9999, size));
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
     * Create, save, and return a SlidingTilesScoreBoard if one does not already exist.
     *
     * @param slidingTilesScoreBoard either a SlidingTilesScoreBoard or null
     * @param activity               an AppCompatActivity that we will use to do file writing
     */
    static private SlidingTilesScoreBoard createSlidingTilesScoreBoard(
            SlidingTilesScoreBoard slidingTilesScoreBoard, AppCompatActivity activity) {
        if (slidingTilesScoreBoard == null) {
            slidingTilesScoreBoard = new SlidingTilesScoreBoard();
            writeSlidingTilesScoreBoard(slidingTilesScoreBoard, activity);
        }
        return slidingTilesScoreBoard;
    }

    /**
     * Write the SlidingTilesScoreBoard to the file.
     *
     * @param slidingTilesScoreBoard the SlidingTilesScoreBoard we're writing to the file
     * @param activity               an AppCompatActivity that we will use to do file writing
     */
    static void writeSlidingTilesScoreBoard(SlidingTilesScoreBoard slidingTilesScoreBoard,
                                            AppCompatActivity activity) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    activity.openFileOutput(slidingTilesScoreBoardFile, MODE_PRIVATE));
            outputStream.writeObject(slidingTilesScoreBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Return the SlidingTilesScoreBoard contained in the file.
     *
     * @return the SlidingTilesScoreBoard contained in the file
     */
    static SlidingTilesScoreBoard loadSlidingTilesScoreBoard(AppCompatActivity activity) {
        SlidingTilesScoreBoard slidingTilesScoreBoard = null;
        try {
            InputStream inputStream = activity.openFileInput(slidingTilesScoreBoardFile);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                slidingTilesScoreBoard = (SlidingTilesScoreBoard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return SlidingTilesScoreBoard.createSlidingTilesScoreBoard(slidingTilesScoreBoard, activity);
    }


}
