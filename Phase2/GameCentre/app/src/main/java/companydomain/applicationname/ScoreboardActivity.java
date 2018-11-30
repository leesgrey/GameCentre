package companydomain.applicationname;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardActivity extends AppCompatActivity {
    /**
     * The current user.
     */
    private String currentUser;

    /**
     * The scoreboard for Sliding Tiles.
     */
    private ScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scoreboard);
        super.onCreate(savedInstanceState);

        Intent i = getIntent();

        currentUser = i.getStringExtra("currentUser");
        String previousGame = i.getStringExtra("previousGame");
        ArrayList<String> previousGameDifficulties = i.getStringArrayListExtra("difficulties");

        displayDifficulties(previousGameDifficulties);

        scoreBoard = ScoreBoard.loadScoreBoard(previousGame, this);

        TextView currentUserText = findViewById(R.id.currentuserText);
        currentUserText.setText(String.format("Player: %s", currentUser));

        populateScoreboard();

        addChangeGameButtonListener();
    }

    void displayDifficulties(ArrayList<String> difficulties){
        // The text fields for game difficulties.
        TextView firstGlobal = findViewById(R.id.firstGlobal);
        TextView firstUser = findViewById(R.id.firstUser);
        TextView secondGlobal = findViewById(R.id.secondGlobal);
        TextView secondUser = findViewById(R.id.secondUser);
        TextView thirdGlobal = findViewById(R.id.thirdGlobal);
        TextView thirdUser = findViewById(R.id.thirdUser);

        firstGlobal.setText(difficulties.get(0));
        firstUser.setText(difficulties.get(0));
        secondGlobal.setText(difficulties.get(1));
        secondUser.setText(difficulties.get(1));
        thirdGlobal.setText(difficulties.get(2));
        thirdUser.setText(difficulties.get(2));
    }

    /**
     * Fills in the scoreboard fields with scoreboard entries.
     */
    private void populateScoreboard() {
        // The text fields for the various scoreboards.
        TextView firstGlobalScore = findViewById(R.id.threescoreOutput);
        TextView secondGlobalScore = findViewById(R.id.fourscoreOutput);
        TextView thirdGlobalScore = findViewById(R.id.fivescoreOutput);
        TextView firstUserScore = findViewById(R.id.uthreescoreOutput);
        TextView secondUserScore = findViewById(R.id.ufourscoreOutput);
        TextView thirdUserScore = findViewById(R.id.ufivescoreOutput);

        firstGlobalScore.setText(getScoreboardValues(3, true));
        secondGlobalScore.setText(getScoreboardValues(4, true));
        thirdGlobalScore.setText(getScoreboardValues(5, true));
        firstUserScore.setText(getScoreboardValues(3, false));
        secondUserScore.setText(getScoreboardValues(4, false));
        thirdUserScore.setText(getScoreboardValues(5, false));
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
        List<Score> scores;

        if (is_global) {
            scores = scoreBoard.topNScores(3, size);
        } else {
            scores = scoreBoard.topNUserScores(3, size, currentUser);
        }
        int i = 1;
        for (Score score : scores) {
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
     * Switch to choose game activity
     */
    private void switchToChooseGame() {
        Intent tmp = new Intent(this, ChooseGameActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }
}