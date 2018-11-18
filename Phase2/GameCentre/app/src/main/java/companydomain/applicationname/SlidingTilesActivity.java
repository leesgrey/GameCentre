package companydomain.applicationname;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The game activity.
 */
public class SlidingTilesActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SlidingTilesBoardManager boardManager;

    /**
     * The buttons to refresh.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Text container that shows the current score of the game
     */
    private TextView scoreCounter;

    /**
     * The username of the current user.
     */
    private String currentUser;

    /**
     * The game save states.
     */
    private GameSaveStates gameSaveStates;

    /**
     * The scoreboard.
     */
    private SlidingTilesScoreBoard scoreBoard;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     * Also save the current game state and check for a win.
     */
    public void refresh() {
        updateTileButtons();
        String scoreCounterString = Integer.toString(boardManager.getScoreCounter());
        scoreCounter.setText(scoreCounterString);
        gameSaveStates.addGameSaveState(currentUser, "slidingTiles", this.boardManager);
        GameSaveStates.writeGameSaveStates(gameSaveStates, this);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        if (boardManager.puzzleSolved()) {
            endgame();
        }
    }

    /**
     * Execute processes for a game that has ended.
     */
    private void endgame() {
        SlidingTilesScore score = new SlidingTilesScore(currentUser,
                boardManager.getScoreCounter(), boardManager.getSize());
        if (!currentUser.equals("Guest")) {
            gameSaveStates.removeGame(currentUser, "slidingTiles");
            GameSaveStates.writeGameSaveStates(gameSaveStates, this);
        }
        scoreBoard.addScore(score);
        SlidingTilesScoreBoard.writeSlidingTilesScoreBoard(scoreBoard, this);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                switchToScoreboard();
            }
        }, 2000);
    }

    /**
     * Switch to the scoreboard page.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, ScoreboardActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadBoardManagerFromFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        this.gameSaveStates = GameSaveStates.loadGameSaveStates(this);
        this.scoreBoard = SlidingTilesScoreBoard.loadSlidingTilesScoreBoard(this);
        Intent i = getIntent();

        this.currentUser = i.getStringExtra("currentUser");

        TextView currUser = findViewById(R.id.currentuserText);
        currUser.setText(String.format("Player: %s", currentUser));

        addView();
        establishLayout();
    }

    /**
     * Adds views and button listeners to activity.
     */
    private void addView() {
        gridView = findViewById(R.id.grid);
        scoreCounter = findViewById(R.id.scoreCounter);
        gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        addUndoButtonListener();
    }

    /**
     * Sets up desired dimensions and refreshes display.
     */
    private void establishLayout() {
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().getNumCols();
                        columnHeight = displayHeight / boardManager.getBoard().getNumRows();

                        refresh();
                    }
                });

        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.height = 1400;
        gridView.setLayoutParams(layoutParams);
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int position = 0; position != board.numTiles(); position++) {
            Button tmp = new Button(context);
            tmp.setBackgroundResource(board.getTile(position).getBackground());
            this.tileButtons.add(tmp);
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        //scoreCounter.setText(boardManager.getScoreCounter());
        int nextPos = 0;
        for (Button b : tileButtons) {
            b.setBackgroundResource(board.getTile(nextPos).getBackground());
            nextPos++;
        }
    }

    /**
     * Activate the undo button.
     */
    private void addUndoButtonListener() {
        Button testButton = findViewById(R.id.undoButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boardManager.undoMove()) {
                    makeNoUndoesToast();
                }
            }
        });
    }

    /**
     * Make a popup saying there are no more undoes.
     */
    private void makeNoUndoesToast() {
        Toast.makeText(this, "There are no more undoes",
                Toast.LENGTH_LONG).show();
    }


    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveBoardManagerToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
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
    public void update(Observable o, Object arg) {
        refresh();
    }

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, SlidingTilesMenuActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }
}
