package companydomain.applicationname;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTilesMenuActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_slidingtilesmenu);
        super.onCreate(savedInstanceState);
        addLoadButtonListener("slidingTiles");
    }

    /**
     * Changes to a game of provided size given a valid undo input.
     *
     * @param rows the rows of the puzzle
     * @param cols the columns of the puzzle
     */
    void initializeGame(int rows, int cols) {
        int undoes = getNumberOfUndoes();
        if (undoes == -1) {
            makeEmptyUndoToast();
        } else {
            boardManager = new SlidingTilesBoardManager(getNumberOfUndoes(), rows);
            switchToGame();
        }
    }

    /**
     * Activate the button that starts a 3x3 game.
     */
    void addStartFirstListener() {
        Button startButton = findViewById(R.id.startThreeButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame(3, 3);
            }
        });

    }

    /**
     * Activate the button that starts a 4x4 game.
     */
    void addStartSecondListener() {
        Button startButton = findViewById(R.id.startFourButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame(4, 4);
            }
        });
    }

    /**
     * Activate the button that starts a 5x5 game.
     */
    void addStartThirdListener() {
        Button startButton = findViewById(R.id.startFiveButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame(5, 5);
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
     * Notify the user to provide a number of undos.
     */
    private void makeEmptyUndoToast() {
        Toast.makeText(this, "Provide a maximum number of undos.", Toast.LENGTH_LONG).show();
    }

    /**
     * Switch to the SlidingTilesActivity view to play the game.
     */
    void switchToGame() {
        Intent tmp = new Intent(this, SlidingTilesActivity.class);
        saveBoardManagerToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }

    /**
     * Switch to the ScoreBoard page.
     */
    void switchToScoreboard() {
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
    void loadBoardManagerFromFile(String fileName) {

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

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, ChooseGameActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }
}
