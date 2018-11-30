package companydomain.applicationname;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

/**
 * The initial sign-up activity for Game Centre.
 */
public class SignInActivity extends AppCompatActivity {

    /**
     * The current user.
     */
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = null;

        setContentView(R.layout.activity_signin);
        addSignInButtonListener();
        addSignUpButtonListener();
        addGuestButtonListener();
    }

    /**
     * Activate the sign up button.
     */
    private void addSignUpButtonListener() {
        Button signUpButton = findViewById(R.id.SignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(SignUpActivity.class);
            }
        });
    }

    /**
     * Activate the guest button.
     */
    private void addGuestButtonListener() {
        Button guestButton = findViewById(R.id.GuestButton);
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = "Guest";
                switchToActivity(ChooseGameActivity.class);
            }
        });
    }

    /**
     * Make a long toast with a custom message.
     *
     * @param message the message to be displayed
     */
    private void createToast(String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }

    /**
     * Change to the provided activity.
     *
     * @param next the next activity to display
     */
    private void switchToActivity(Class<? extends Activity> next) {
        Intent tmp = new Intent(this, next);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }

    /**
     * Activate the sign in button.
     */
    private void addSignInButtonListener() {
        final AppCompatActivity activity = this;
        Button signInButton = findViewById(R.id.SignInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailInput = findViewById(R.id.emailInput);
                EditText passwordInput = findViewById(R.id.passwordInput);

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                AccountInfo accountInfo = AccountInfo.loadAccountInfo(activity);

                if (email.equals("") || password.equals("")) {
                    createToast("Please enter an email and password.");
                } else if (!accountInfo.accountExists(email)) {
                    createToast("Email not found, please sign up.");
                    emailInput.getText().clear();
                    passwordInput.getText().clear();
                } else if (accountInfo.loginValid(email, password)) {
                    currentUser = email;
                    switchToActivity(ChooseGameActivity.class);
                } else {
                    createToast("Incorrect password, please try again.");
                    emailInput.getText().clear();
                    passwordInput.getText().clear();
                }
            }
        });
    }
}