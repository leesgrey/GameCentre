package companydomain.applicationname;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class ChooseGameActivity extends AppCompatActivity {

    /**
     * The GUI component showing who the current user of the application is
     */
    public TextView currUser;

    /**
     * The String object that keeps track of the email/username of the current user
     */
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosegame);

        Intent i = getIntent();

        currentUser = i.getStringExtra("currentUser");

        currUser = findViewById(R.id.welcomeText);
        String welcomeText = "Welcome, " + currentUser;
        currUser.setText(welcomeText);

        this.addListeners();
    }

    /**
     * Add the listeners to this activity.
     */
    private void addListeners() {
        addSlidingTilesButtonListener();
        addMatchingButtonListener();
        addMastermindButtonListener();
        addLogoutButtonListener();
    }

    /**
     * Make a popup for when you logout.
     */
    private void makeLogoutToast() {
        Toast.makeText(this, "Logged out.",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Activate the Logout button.
     */
    private void addLogoutButtonListener() {
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeLogoutToast();
                switchToActivity(SignInActivity.class);
            }
        });
    }

    /**
     * Activate the SlidingTiles button.
     */
    private void addSlidingTilesButtonListener() {
        Button SlidingTilesButton = findViewById(R.id.slidingtilesbutton);
        SlidingTilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(SlidingTilesMenuActivity.class);
            }
        });
    }

    /**
     * Activate the Game2 button.
     */
    private void addMatchingButtonListener() {
        Button MatchingButton = findViewById(R.id.MatchingButton);
        MatchingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(MatchingMenuActivity.class);
            }
        });
    }

    /**
     * Activate the Game3 button.
     */
    private void addMastermindButtonListener() {
        Button MastermindButton = findViewById(R.id.MastermindButton);
        MastermindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * Change to the provided activity.
     */
    private void switchToActivity(Class<? extends Activity> next) {
        Intent tmp = new Intent(this, next);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }

    /**
     * This is a flow control method we implemented to prevent the user from returning to login
     * when they are not supposed to
     */
    @Override
    public void onBackPressed() {
    }
}