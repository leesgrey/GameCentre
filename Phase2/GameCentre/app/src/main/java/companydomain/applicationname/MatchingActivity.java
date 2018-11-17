package companydomain.applicationname;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new MatchingBoardManager(3,4);
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "aaaaaaaa", Toast.LENGTH_SHORT).show();
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
