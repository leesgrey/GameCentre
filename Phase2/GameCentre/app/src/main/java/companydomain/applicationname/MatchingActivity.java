package companydomain.applicationname;

import android.content.Context;
import android.content.Intent;
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
    private MatchingBoardManager boardManager;

    public void update(Observable o, Object arg){

    }
}
