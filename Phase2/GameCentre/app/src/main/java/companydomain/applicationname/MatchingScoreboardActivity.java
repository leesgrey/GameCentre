package companydomain.applicationname;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MatchingScoreboardActivity extends AppCompatActivity {
    /**
     * The current user.
     */
    String currentUser;

    /**
     * The visual output of the current user.
     */
    TextView currentUserText;

    /**
     * The text fields for the various scoreboards.
     */
    TextView threeScoreOutput;
    TextView fourScoreOutput;
    TextView fiveScoreOutput;
    TextView userThreeScoreOutput;
    TextView userFourScoreOutput;
    TextView userFiveScoreOutput;

    /**
     * The scoreboard for Matching.
     */
    private SlidingTilesScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scoreboard);
        super.onCreate(savedInstanceState);

        scoreBoard = SlidingTilesScoreBoard.loadSlidingTilesScoreBoard(this);

        Intent i = getIntent();

        currentUser = i.getStringExtra("currentUser");

        currentUserText = findViewById(R.id.currentuserText);
        currentUserText.setText(String.format("Player: %s", currentUser));

        populateScoreboard();

        addContinueButtonListener();
        addChangeGameButtonListener();
    }

    /**
     * Fills in the scoreboard fields with scoreboard entries.
     */
    private void populateScoreboard() {
        threeScoreOutput = findViewById(R.id.threescoreOutput);
        fourScoreOutput = findViewById(R.id.fourscoreOutput);
        fiveScoreOutput = findViewById(R.id.fivescoreOutput);
        userThreeScoreOutput = findViewById(R.id.uthreescoreOutput);
        userFourScoreOutput = findViewById(R.id.ufourscoreOutput);
        userFiveScoreOutput = findViewById(R.id.ufivescoreOutput);

        threeScoreOutput.setText(getScoreboardValues(3, true));
        fourScoreOutput.setText(getScoreboardValues(4, true));
        fiveScoreOutput.setText(getScoreboardValues(5, true));
        userThreeScoreOutput.setText(getScoreboardValues(3, false));
        userFourScoreOutput.setText(getScoreboardValues(4, false));
        userFiveScoreOutput.setText(getScoreboardValues(5, false));
    }

    /**
     * Returns the top 3 entries of the SlidingTiles scoreboard of int size and whether it is_global.
     *
     * @param size      the size of the puzzle whose scores are of interest
     * @param is_global whether we are looking for global scores or not
     * @return the top 3 entries of the specified SlidingTiles scoreboard
     */
    private StringBuilder getScoreboardValues(int size, boolean is_global) {
        StringBuilder output = new StringBuilder();
        ArrayList<SlidingTilesScore> scores;

        if (is_global) {
            scores = scoreBoard.topNScores(3, size);
        } else {
            scores = scoreBoard.topNUserScores(3, size, currentUser);
        }
        int i = 1;
        for (SlidingTilesScore score : scores) {
            output.append(String.format("%o. %s: %s\n", i, score.getUsername(), Integer.toString(score.getScore())));
            i++;
        }
        return output;
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * Listener for Change game button
     */
    private void addChangeGameButtonListener() {
        Button signInButton = findViewById(R.id.changegame);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToChooseGame();
            }
        });
    }

    /**
     * Listener for continue button
     */
    private void addContinueButtonListener() {
        Button signInButton = findViewById(R.id.continueButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSelectGame();
            }
        });
    }

    /**
     * Switch to choose game activity
     */
    private void switchToChooseGame() {
        Intent tmp = new Intent(this, ChooseGameActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }

    /**
     * Switch to Starting activity
     */

    private void switchToSelectGame() {
        Intent tmp = new Intent(this, SlidingTilesMenuActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }
}