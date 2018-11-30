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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        addSignUpButtonListener();
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

                AccountInfo accountInfo = AccountInfo.loadAccountInfo(activity);

                if (email.equals("") || password.equals("") || repassword.equals("")) {
                    createToast("Please enter an email and password.");
                } else if (!password.equals(repassword)) {
                    createToast("Passwords do not match, please try again.");
                    passwordInput.getText().clear();
                    repasswordInput.getText().clear();
                } else if (accountInfo.accountExists(email)) {
                    createToast("Account exists already");
                    emailInput.getText().clear();
                } else {
                    GameSaveStates gameSaveStates = GameSaveStates.loadGameSaveStates(activity);
                    createToast("Successfully created account, please log in.");
                    accountInfo.addAccount(email, password);
                    AccountInfo.writeAccountInfo(accountInfo, activity);
                    gameSaveStates.addAccount(email);
                    GameSaveStates.writeGameSaveStates(gameSaveStates, activity);
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