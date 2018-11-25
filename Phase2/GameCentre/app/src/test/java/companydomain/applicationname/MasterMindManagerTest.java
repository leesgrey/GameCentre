package companydomain.applicationname;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class MasterMindManagerTest {

    /**
     * The masterMindManager manager for testing.
     */
    private MasterMindManager masterMindManager;

    /**
     * Returns a random code for a game with numSlots slots and numColours colours.
     *
     * @param numSlots the number of slots in the code
     * @param numColours the number of possible colours in each slot
     * @return a random code for a game with numSlots slots and numColours colours
     */
    private int[] createRandomCode(int numSlots, int numColours) {
        Random randomIntGenerator = new Random();
        int[] code = new int[numSlots];
        for(int i = 0; i < numSlots; i++) {
            code[i] = randomIntGenerator.nextInt(numColours) + 1;
        }
        return code;
    }


    /**
     * Initialize the MasterMindManager instance.
     *
     * @param numSlots the number of slots in the code
     * @param numColours the number of possible colours in each slot
     * @param numPreviousGuesses the number of guesses that should be stored
     */
    private void setupMasterMind(int numSlots, int numColours, int numPreviousGuesses) {
        this.masterMindManager = new MasterMindManager(numSlots, numColours, numPreviousGuesses);
    }

    /**
     * Test that makeGuess correctly stores guesses.
     */
    @Test
    public void makeGuessTest() {
        setupMasterMind(4, 8, 5);
        int[] guessCode0 = createRandomCode(4, 8);
        int[] guessCode1 = createRandomCode(4, 8);
        int[] emptyGuess = new int[4];
        masterMindManager.makeGuess(guessCode0);
        masterMindManager.makeGuess(guessCode1);
        MasterMindCombination[] lastGuessedCodes = masterMindManager.getLastNGuesses(3);
        assertArrayEquals(guessCode1, lastGuessedCodes[0].getCode());
        assertArrayEquals(guessCode0, lastGuessedCodes[1].getCode());
        assertArrayEquals(emptyGuess, lastGuessedCodes[2].getCode());
    }

    /**
     * Test gameWon.
     */
    @Test
    public void gameWonTest() {
        setupMasterMind(4, 8, 5);
        assertFalse(masterMindManager.gameWon());
        masterMindManager.makeGuess(masterMindManager.getAnswerCode());
        assertTrue(masterMindManager.gameWon());
    }


    /**
     * Test getLastNGuesses() method.
     */
    @Test
    public void getLastNGuessesTest() {
        setupMasterMind(4, 8, 5);
        int[] guessCode0 = createRandomCode(4, 8);
        int[] guessCode1 = createRandomCode(4, 8);
        masterMindManager.makeGuess(guessCode0);
        masterMindManager.makeGuess(guessCode1);
        int[][] testGuesses = {guessCode1, guessCode0};
        int[][] actualGuesses = {masterMindManager.getLastNGuesses(2)[0].getCode(),
                masterMindManager.getLastNGuesses(2)[1].getCode()};

        assertArrayEquals(testGuesses, actualGuesses);


    }


    /**
     * Test getScore() method.
     */

    @Test
    public void getScoreTest() {
        setupMasterMind(4, 8, 5);
        masterMindManager.makeGuess(masterMindManager.getAnswerCode());
        assertEquals(1, masterMindManager.getScore());
    }

    /**
     * Test getAnswerCode() method.
     */

    @Test
    public void getAnswerCodeTest() {
        setupMasterMind(4, 8, 5);
        masterMindManager.setAnswerCode(new MasterMindCombination(new int[]{1, 2, 3}));
        assertArrayEquals(new int[]{1, 2, 3}, masterMindManager.getAnswerCode());

    }
}