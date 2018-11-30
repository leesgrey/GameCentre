package companydomain.applicationname;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTilesMenuActivity extends AppCompatActivity {

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    /**
     * The board manager.
     */
    private SlidingTilesBoardManager boardManager;

    /**
     * The game save states.
     */
    private GameSaveStates gameSaveStates;

    /**
     * The current user.
     */
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveBoardManagerToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_slidingtilesmenu);

        Intent i = getIntent();

        currentUser = i.getStringExtra("currentUser");

        TextView currentUserText = findViewById(R.id.currentuserText);
        currentUserText.setText(String.format("Player: %s", currentUser));

        addStartThreeListener();
        addStartFourListener();
        addStartFiveListener();
        addLoadButtonListener();
        addScoreboardButtonListener();
        this.gameSaveStates = GameSaveStates.loadGameSaveStates(this);
    }

    /**
     * Changes to a game of provided size given a valid undo input.
     *
     * @param size the size of the puzzle
     */
    private void initializeGame(int size) {
        int undoes = getNumberOfUndoes();
        if (undoes == -1) {
            makeEmptyUndoToast();
        } else {
            boardManager = new SlidingTilesBoardManager(getNumberOfUndoes(), size);
            switchToGame();
        }
    }

    /**
     * Activate the button that starts a 3x3 game.
     */
    private void addStartThreeListener() {
        Button startButton = findViewById(R.id.startThreeButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame(3);
            }
        });

    }

    /**
     * Activate the button that starts a 4x4 game.
     */
    private void addStartFourListener() {
        Button startButton = findViewById(R.id.startFourButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame(4);
            }
        });
    }

    /**
     * Activate the button that starts a 5x5 game.
     */
    private void addStartFiveListener() {
        Button startButton = findViewById(R.id.startFiveButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame(5);
            }
        });
    }

    /**
     * Return the number of undoes specified in the text input area.
     *
     * @return the number of undoes specified in the text input area
     */
    private int getNumberOfUndoes() {
        EditText numUndoesEditText = findViewById(R.id.numUndoInput);
        String undoesInput = numUndoesEditText.getText().toString();
        if (undoesInput.equals("")) {
            return -1;
        }
        int numUndoes = Integer.parseInt(undoesInput);
        if (numUndoes <= 0) {
            return 0;
        }
        return numUndoes;
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
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser.equals("Guest") || !gameSaveStates.gameSaveStateExists(currentUser,
                        "slidingTiles")) {
                    makeLoadFailToast();
                } else {
                    boardManager = (SlidingTilesBoardManager) gameSaveStates.getGameSaveState(currentUser,
                            "slidingTiles");
                    makeToastLoadedText();
                    switchToGame();
                }
            }
        });
    }

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
     * Notify the user to provide a number of undos.
     */
    private void makeEmptyUndoToast() {
        Toast.makeText(this, "Provide a maximum number of undos.", Toast.LENGTH_LONG).show();
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
     * Switch to the SlidingTilesActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, SlidingTilesActivity.class);
        saveBoardManagerToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }

    /**
     * Switch to the ScoreBoard page.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, ScoreboardActivity.class);
        tmp.putExtra("currentUser", currentUser);
        tmp.putExtra("previousGame", "slidingTiles");
        ArrayList<String> difficulties = new ArrayList<>();
        difficulties.add("3x3");
        difficulties.add("4x4");
        difficulties.add("5x5");
        tmp.putStringArrayListExtra("difficulties", difficulties);
        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadBoardManagerFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (SlidingTilesBoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

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

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, ChooseGameActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }
}
