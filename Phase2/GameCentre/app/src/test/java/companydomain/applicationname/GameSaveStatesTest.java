package companydomain.applicationname;

import android.support.v7.app.AppCompatActivity;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameSaveStatesTest {


    /**
     * A method to test GameSaveStates.
     */

    @Test
    public void gamSaveStatesTest() {
        AppCompatActivity randomActivity = new MatchingActivity();
        GameSaveStates testGameSaveStates = GameSaveStates.loadGameSaveStates(randomActivity);
        testGameSaveStates.addAccount("testemail");
        testGameSaveStates.addGameSaveState("testemail", "testgame", testGameSaveStates);
        assertTrue(testGameSaveStates.gameSaveStateExists("testemail", "testgame"));

        testGameSaveStates.removeGame("testemail", "testgame");
        assertFalse(testGameSaveStates.gameSaveStateExists("testemail", "testgame"));
    }
}