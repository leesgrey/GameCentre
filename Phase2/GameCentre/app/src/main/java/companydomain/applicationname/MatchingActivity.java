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
import java.util.Observable;
import java.util.Observer;

public class MatchingActivity extends AppCompatActivity implements Observer {
    /**
     * The board manager.
     */
    private MatchingBoardManager boardManager;

    /**
     * The buttons to refresh.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new MatchingBoardManager(3,4);
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "aaaaaaaa", Toast.LENGTH_SHORT).show();
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.setBoardManager(boardManager);
        establishLayout();
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
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
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


    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int position = 0; position != board.numTiles(); position++) {
            Button tmp = new Button(context);
            tmp.setBackgroundResource(board.getTile(position).getBackground());
            this.tileButtons.add(tmp);
        }
    }

    public void update(Observable o, Object arg){

    }
}
