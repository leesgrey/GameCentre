package companydomain.applicationname;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MasterMindMenuActivity extends AppCompatActivity {

    /**
     * The current user.
     */
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mastermindmenu);

        Intent i = getIntent();

        currentUser = i.getStringExtra("currentUser");

        TextView currentUserText = findViewById(R.id.currentuserText);
        currentUserText.setText(String.format("Player: %s", currentUser));

        addStartThreeListener();
        addStartFourListener();
        addStartFiveListener();
        addScoreboardButtonListener();
    }

    /**
     * Activate the button that starts a size 3 code game.
     */
    private void addStartThreeListener() {
        Button startButton = findViewById(R.id.startThreeButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(3);
            }
        });
    }

    /**
     * Activate the button that starts a size 4 code game.
     */
    private void addStartFourListener() {
        Button startButton = findViewById(R.id.startFourButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(4);
            }
        });
    }

    /**
     * Activate the button that starts a size 5 code game.
     */
    private void addStartFiveListener() {
        Button startButton = findViewById(R.id.startFiveButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(5);
            }
        });
    }

    /**
     * Activate the scoreboard button.
     */
    private void addScoreboardButtonListener() {
        Button scoreboardButton = findViewById(R.id.scoreboardButton);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreboard();
            }
        });
    }

    /**
     * Switch to the MasterMindGameActivity view to play the game.
     */
    private void switchToGame(int size) {
        Intent tmp = new Intent(this, MasterMindGameActivity.class);
        tmp.putExtra("currentUser", currentUser);
        tmp.putExtra("size", size);
        startActivity(tmp);
    }

    /**
     * Switch to the ScoreBoard page.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, ScoreboardActivity.class);
        tmp.putExtra("currentUser", currentUser);
        tmp.putExtra("previousGame", "masterMind");
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, ChooseGameActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }
}
