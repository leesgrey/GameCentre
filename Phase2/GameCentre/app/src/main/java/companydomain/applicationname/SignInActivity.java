package companydomain.applicationname;

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
    String currentUser;

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
     * Create a Toast that tells the user to fill in all fields.
     */
    private void makeEmptyToast() {
        Toast.makeText(this, "Please enter an email and password.",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Create a Toast that tells the user that the password is incorrect.
     */
    private void makeIncorrectToast() {
        Toast.makeText(this, "Incorrect password, please try again.",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Create a Toast that tells the user that the email is not associated with an account.
     */
    private void makeAccountMissingToast() {
        Toast.makeText(this, "Email not found, please sign up.",
                Toast.LENGTH_LONG).show();
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
                    makeEmptyToast();
                } else if (!accountInfo.accountExists(email)) {
                    makeAccountMissingToast();
                    emailInput.getText().clear();
                    passwordInput.getText().clear();
                } else if (accountInfo.loginValid(email, password)) {
                    currentUser = email;
                    switchToChooseGame();
                } else {
                    makeIncorrectToast();
                    emailInput.getText().clear();
                    passwordInput.getText().clear();
                }
            }
        });
    }

    /**
     * Activate the sign up button.
     */
    private void addSignUpButtonListener() {
        Button signUpButton = findViewById(R.id.SignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUp();
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
                switchToChooseGame();
            }
        });
    }

    /**
     * Change screen to the sign up activity.
     */
    private void switchToSignUp() {
        Intent tmp = new Intent(this, SignUpActivity.class);
        startActivity(tmp);
    }

    /**
     * Change screen to the choose game activity.
     */
    private void switchToChooseGame() {
        Intent tmp = new Intent(this, ChooseGameActivity.class);
        tmp.putExtra("currentUser", currentUser);
        startActivity(tmp);
    }
}