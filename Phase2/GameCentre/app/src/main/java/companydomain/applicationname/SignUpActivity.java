package companydomain.applicationname;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SignUpActivity extends AppCompatActivity {

    /**
     * The account info.
     */
    AccountInfo accountInfo;

    /**
     * The game save states.
     */
    GameSaveStates gameSaveStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        addSignUpButtonListener();
        this.accountInfo = AccountInfo.loadAccountInfo(this);
        this.gameSaveStates = GameSaveStates.loadGameSaveStates(this);
    }

    /**
     * Make a popup saying to enter email/password.
     */
    private void makeEmptyToast() {
        Toast.makeText(this, "Please enter an email and password.",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Make a popup saying the passwords don't match.
     */
    private void makeMatchingToast() {
        Toast.makeText(this, "Passwords do not match, please try again.",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Make a popup saying the account already exists.
     */
    private void makeExistToast() {
        Toast.makeText(this, "Account exists already",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Make a popup saying an account was create.
     */
    private void createdAccountToast() {
        Toast.makeText(this, "Successfully created account, please log in.",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Activate the sign up button.
     */
    private void addSignUpButtonListener() {
        final AppCompatActivity activity = this;
        Button signUpButton = findViewById(R.id.submitButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText emailInput = findViewById(R.id.emailInput);
                EditText passwordInput = findViewById(R.id.passwordInput);
                EditText repasswordInput = findViewById(R.id.repasswordInput);

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String repassword = repasswordInput.getText().toString();

                if (email.equals("") || password.equals("") || repassword.equals("")) {
                    makeEmptyToast();
                } else if (!password.equals(repassword)) {
                    makeMatchingToast();
                    passwordInput.getText().clear();
                    repasswordInput.getText().clear();
                } else if (accountInfo.accountExists(email)) {
                    makeExistToast();
                    emailInput.getText().clear();
                } else {
                    createdAccountToast();
                    accountInfo.addAccount(email, password);
                    AccountInfo.writeAccountInfo(accountInfo, activity);
                    gameSaveStates.addAccount(email);
                    GameSaveStates.writeGameSaveStates(gameSaveStates, activity);
                    createdAccountToast();
                    switchToSignIn();
                }
            }
        });
    }

    /**
     * Switch to the sign in page.
     */
    private void switchToSignIn() {
        Intent tmp = new Intent(this, SignInActivity.class);
        startActivity(tmp);
    }
}