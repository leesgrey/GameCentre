package companydomain.applicationname;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * The set of all accountInfo that have signed up for this Game Centre.
 */
class AccountInfo implements Serializable {

    /**
     * A file containing the instance of AccountInfo we will be using.
     */
    private static String accountInfoFile = "account_info.ser";

    /**
     * The set of all accountInfo (email and password pairs).
     */
    private HashMap<String, String> loginInfo = new HashMap<>();

    /**
     * Return whether or not this email address has an account associated to it.
     *
     * @param email the email address
     * @return whether or not this email address has an account associated to it
     */
    boolean accountExists(String email) {
        return this.loginInfo.containsKey(email);
    }

    /**
     * Return whether or not this email address and password pair are a valid pair.
     *
     * @param email    the email address
     * @param password the password
     * @return whether or not this email address and password pair are a valid pair
     */
    boolean loginValid(String email, String password) {
        return password.equals(this.loginInfo.get(email));
    }

    /**
     * Add a new account to our set.
     *
     * @param email    the email address
     * @param password the password
     */
    void addAccount(String email, String password) {
        this.loginInfo.put(email, password);
    }

    /**
     * Create, save, and return a AccountInfo if one does not already exist.
     *
     * @param accountInfo either an AccountInfo or null
     * @param activity    an AppCompatActivity that we will use to do file writing
     */
    static private AccountInfo createAccountInfo(AccountInfo accountInfo,
                                                 AppCompatActivity activity) {
        if (accountInfo == null) {
            accountInfo = new AccountInfo();
            writeAccountInfo(accountInfo, activity);
        }
        return accountInfo;
    }

    /**
     * Write the account info to the file.
     *
     * @param accountInfo the account info we're writing to the file
     * @param activity    an AppCompatActivity that we will use to do file writing
     */
    static void writeAccountInfo(AccountInfo accountInfo, AppCompatActivity activity) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    activity.openFileOutput(accountInfoFile, MODE_PRIVATE));
            outputStream.writeObject(accountInfo);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Return the account info contained in the file.
     *
     * @return the account info contained in the file
     */
    static AccountInfo loadAccountInfo(AppCompatActivity activity) {
        AccountInfo accountInfo = null;
        try {
            InputStream inputStream = activity.openFileInput(accountInfoFile);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                accountInfo = (AccountInfo) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return AccountInfo.createAccountInfo(accountInfo, activity);
    }
}
