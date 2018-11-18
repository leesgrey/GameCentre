package companydomain.applicationname;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class MatchingActivity extends AppCompatActivity implements Observer {
    /**
     * The board manager.
     */
    private MatchingBoardManager boardManager;

    /**
     * Text container that shows the current score of the game
     */
    private TextView scoreCounter;

    /**
     * The username of the current user.
     */
    private String currentUser;

    /**
     * The buttons to refresh.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The buttons that have already been seen.
     */
    private ArrayList<Button> revealedButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadBoardManagerFromFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        boardManager = new MatchingBoardManager(3,4);
        createTileButtons(this);
        setContentView(R.layout.activity_matching);
        Toast.makeText(this, "aaaaaaaa", Toast.LENGTH_SHORT).show();

        //this.gameSaveStates = GameSaveStates.loadGameSaveStates(this);
        //this.scoreBoard = SlidingTilesScoreBoard.loadSlidingTilesScoreBoard(this);
        Intent i = getIntent();

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
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     * Also save the current game state and check for a win.
     */
    public void refresh() {
        String scoreCounterString = Integer.toString(boardManager.getScoreCounter());
        scoreCounter.setText(scoreCounterString);
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        if (boardManager.puzzleSolved()) {
            endgame();
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        List<Tile> unmatched = this.boardManager.getUnmatched();
        List<Tile> selected = this.boardManager.getSelected();

        int nextPos = 0;
        for (Button b : tileButtons) {
            // if not in unmatched or selecte
            if(!unmatched.contains(board.getTile(nextPos)) || selected.contains(board.getTile(nextPos))){
                b.setBackgroundResource(board.getTile(nextPos).getBackground());
            }
            else{
                b.setBackgroundResource(R.drawable.flipped);
            }
            nextPos++;
        }
    }

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
     * Execute processes for a game that has ended.
     */
    private void endgame() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                switchToChooseGame();
            }
        }, 2000);
    }

    /**
     * Switch to the scoreboard page.
     */
    private void switchToChooseGame() {
        Intent tmp = new Intent(this, ChooseGameActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }

    public void update(Observable o, Object arg){
        refresh();
    }
}
