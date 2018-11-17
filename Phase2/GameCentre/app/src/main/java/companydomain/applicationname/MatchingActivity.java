package companydomain.applicationname;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MatchingActivity extends AppCompatActivity implements Observer {
    /**
     * The board manager.
     */
    private BoardManager boardManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void refresh() {

    }

    private void endgame() {

    }

    private void switchToScoreboard() {

    }


    private void createTileButtons(Context context) {

    }

    private void updateTileButtons() {

    }

    public void update(Observable o, Object arg){

    }
}
