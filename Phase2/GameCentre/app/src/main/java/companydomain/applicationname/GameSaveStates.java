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
 * A set of email addresses each with a set of game save states.
 */
class GameSaveStates implements Serializable {

    /**
     * A file containing the instance of GameSaveStates we will be using.
     */
    private static String gameSaveStatesFile = "game_save_states.ser";

    /**
     * The set of all accountInfo that have signed up for this Game Centre.
     */
    private HashMap<String, HashMap<String, BoardManager>> gameSaveStates;

    /**
     * Initialize the set of GameSaveStates to an empty set.
     */
    private GameSaveStates() {
        this.gameSaveStates = new HashMap<>();
    }

    /**
     * Add an email address to our set of email addresses.
     *
     * @param email the email address
     */
    void addAccount(String email) {
        this.gameSaveStates.put(email, new HashMap<String, BoardManager>());
    }

    /**
     * Add a game save state to an email address.
     *
     * @param email         the email address
     * @param game          the name of the game
     * @param gameSaveState the save state of the game
     */
    void addGameSaveState(String email, String game, BoardManager gameSaveState) {
        if (this.gameSaveStates.containsKey(email)) {
            this.gameSaveStates.get(email).put(game, gameSaveState);
        }
    }

    /**
     * Return if a user has a game save state.
     *
     * Precondition: this.gameSaveStates.containsKey(email)
     *
     * @param email the email address
     * @param game  the name of the game
     * @return if the user has a game save state
     */
    boolean gameSaveStateExists(String email, String game) {
        return this.gameSaveStates.get(email).containsKey(game);
    }

    /**
     * Read a game save state for an email address.
     *
     * Precondition: this.gameSaveStates.containsKey(email)
     *
     * @param email the email address
     * @param game  the name of the game
     * @return the save state of the game
     */
    BoardManager getGameSaveState(String email, String game) {
        return this.gameSaveStates.get(email).get(game);
    }

    /**
     * Delete an game save state.
     *
     * Precondition: this.gameSaveStates.containsKey(email)
     *
     * @param email the email address whose game is to be deleted
     * @param game  the game to be deleted
     */
    void removeGame(String email, String game) {
        this.gameSaveStates.get(email).remove(game);
    }

    /**
     * Create, save, and return a GameSaveStates if one does not already exist.
     *
     * @param gameSaveStates either a GameSaveStates or null
     * @param activity       an AppCompatActivity that we will use to do file writing
     */
    static private GameSaveStates createGameSaveStates(GameSaveStates gameSaveStates,
                                                       AppCompatActivity activity) {
        if (gameSaveStates == null) {
            gameSaveStates = new GameSaveStates();
            writeGameSaveStates(gameSaveStates, activity);
        }
        return gameSaveStates;
    }

    /**
     * Write the game save states to the file.
     *
     * @param gameSaveStates the game save states we're writing to the file
     * @param activity       an AppCompatActivity that we will use to do file writing
     */
    static void writeGameSaveStates(GameSaveStates gameSaveStates, AppCompatActivity activity) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    activity.openFileOutput(gameSaveStatesFile, MODE_PRIVATE));
            outputStream.writeObject(gameSaveStates);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Return the game save states contained in the file.
     *
     * @return the game save states contained in the file
     */
    static GameSaveStates loadGameSaveStates(AppCompatActivity activity) {
        GameSaveStates gameSaveStates = null;
        try {
            InputStream inputStream = activity.openFileInput(gameSaveStatesFile);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                gameSaveStates = (GameSaveStates) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return GameSaveStates.createGameSaveStates(gameSaveStates, activity);
    }
}