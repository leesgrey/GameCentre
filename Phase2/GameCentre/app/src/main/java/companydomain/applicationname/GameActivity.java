package companydomain.applicationname;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class GameActivity extends AppCompatActivity implements Observer {
    /**
     * The board manager.
     */
    BoardManager boardManager;

    /**
     * The buttons to refresh.
     */
    ArrayList<Button> tileButtons;

    /**
     * Text container that shows the current score of the game
     */
    TextView scoreCounter;

    /**
     * The username of the current user.
     */
    String currentUser;

    /**
     * The game save states.
     */
    GameSaveStates gameSaveStates;

    /**
     * The scoreboard.
     */
    ScoreBoard scoreBoard;

    GestureDetectGridView gridView;
    static int columnWidth, columnHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTileButtons(this);
        this.gameSaveStates = GameSaveStates.loadGameSaveStates(this);
        Intent i = getIntent();

        this.currentUser = i.getStringExtra("currentUser");

        TextView currUser = findViewById(R.id.currentuserText);
        currUser.setText(String.format("Player: %s", currentUser));

        addView();
        establishLayout();
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     * Also save the current game state and check for a win.
     */
    public void refresh() {
        updateTileButtons();
        String scoreCounterString = Integer.toString(boardManager.getScoreCounter());
        scoreCounter.setText(scoreCounterString);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        if (boardManager.puzzleSolved()) {
            endGame();
        }
    }

    abstract void updateTileButtons();

    abstract void endGame();
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
     * Adds views and button listeners to activity.
     */
    void addView() {
        gridView = findViewById(R.id.grid);
        scoreCounter = findViewById(R.id.scoreCounter);
        gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
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

    public void update(Observable o, Object arg) {
        refresh();
    }
}
