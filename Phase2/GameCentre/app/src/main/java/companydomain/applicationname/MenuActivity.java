package companydomain.applicationname;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class MenuActivity extends AppCompatActivity {
    /**
     * A temporary save file.
     */
    static final String TEMP_SAVE_FILENAME = "save_file_temp.ser";

    /**
     * The board manager.
     */
    BoardManager boardManager;

    /**
     * The game save states.
     */
    GameSaveStates gameSaveStates;

    /**
     * The current user.
     */
    String currentUser;

    /**
     * Text field to show who the current user is
     */
    TextView currentUserText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveBoardManagerToFile(TEMP_SAVE_FILENAME);

        Intent i = getIntent();

        currentUser = i.getStringExtra("currentUser");

        currentUserText = findViewById(R.id.currentuserText);
        currentUserText.setText(String.format("Player: %s", currentUser));

        addStartFirstListener();
        addStartSecondListener();
        addStartThirdListener();
        addScoreboardButtonListener();
        this.gameSaveStates = GameSaveStates.loadGameSaveStates(this);
    }

    /**
     * Activate the button that starts a game of the first difficulty.
     */
    abstract void addStartFirstListener();

    /**
     * Activate the button that starts a game of the first difficulty.
     */
    abstract void addStartSecondListener();

    /**
     * Activate the button that starts a game of the first difficulty.
     */
    abstract void addStartThirdListener();

    /**
     * Changes to a game of provided size given a valid undo input.
     *
     * @param rows the rows of the puzzle
     * @param cols the columns of the puzzle
     */
    abstract void initializeGame(int rows, int cols);

    /**
     * Activate the load button.
     */
    void addLoadButtonListener(String game) {
        final String previous = game;
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser.equals("Guest") || !gameSaveStates.gameSaveStateExists(currentUser,
                        previous)) {
                    makeLoadFailToast();
                } else {
                    boardManager = (SlidingTilesBoardManager) gameSaveStates.getGameSaveState(currentUser,
                            previous);
                    makeToastLoadedText();
                    switchToGame();
                }
            }
        });
    }


    abstract void switchToGame();

    /**
     * Make a popup that says that there are no saved games.
     */
    private void makeLoadFailToast() {
        Toast.makeText(this, "You have no saves", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
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
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadBoardManagerFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the ScoreBoard page.
     */
    abstract void switchToScoreboard();

    abstract void loadBoardManagerFromFile(String fileName);

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveBoardManagerToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
