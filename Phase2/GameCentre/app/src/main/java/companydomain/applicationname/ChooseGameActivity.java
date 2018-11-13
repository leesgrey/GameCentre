package companydomain.applicationname;

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
        addGame2ButtonListener();
        addGame3ButtonListener();
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
                switchToSignIn();
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
                switchToSlidingTilesStarting();
            }
        });
    }

    /**
     * Activate the Game2 button.
     */
    private void addGame2ButtonListener() {
        Button Game2Button = findViewById(R.id.Game2Button);
        Game2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * Activate the Game3 button.
     */
    private void addGame3ButtonListener() {
        Button Game3Button = findViewById(R.id.Game3Button);
        Game3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * Go to the sliding tiles starting screen.
     */
    private void switchToSlidingTilesStarting() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }

    /**
     * Go to the sign in page.
     */
    private void switchToSignIn() {
        Intent tmp = new Intent(this, SignInActivity.class);
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