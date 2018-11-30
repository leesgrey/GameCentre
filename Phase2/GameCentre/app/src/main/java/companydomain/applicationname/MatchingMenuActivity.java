package companydomain.applicationname;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class MatchingMenuActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_matchingmenu);
        super.onCreate(savedInstanceState);
        addLoadButtonListener("matching");
    }

    /**
     * Changes to a game of provided size given a valid undo input.
     *
     * @param rows the number of rows in the puzzle
     * @param columns the number of columns in the puzzle
     */
    void initializeGame(int rows, int columns) {
        boardManager = new MatchingBoardManager(rows, columns);
        switchToGame();
    }

    /**
     * Activate the button that starts a 3x4 game.
     */
    void addStartFirstListener() {
        Button startButton = findViewById(R.id.startThreeButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame(3, 4);
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
                initializeGame(4, 5);
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
     * Switch to the SlidingTilesActivity view to play the game.
     */
    void switchToGame() {
        Intent tmp = new Intent(this, MatchingActivity.class);
        saveBoardManagerToFile(MatchingMenuActivity.TEMP_SAVE_FILENAME);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }

    /**
     * Switch to the ScoreBoard page.
     */
    void switchToScoreboard() {
        Intent tmp = new Intent(this, ScoreboardActivity.class);
        tmp.putExtra("currentUser", currentUser);
        tmp.putExtra("previousGame", "matching");
        ArrayList<String> difficulties = new ArrayList<>();
        difficulties.add("3x4");
        difficulties.add("4x4");
        difficulties.add("4x5");
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
                boardManager = (MatchingBoardManager) input.readObject();
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
