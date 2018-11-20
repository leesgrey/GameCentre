package companydomain.applicationname;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class MasterMindTest {

    /**
     * The masterMind manager for testing.
     */
    private MasterMind masterMind;

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
     * Initialize the MasterMind instance.
     *
     * @param numSlots the number of slots in the code
     * @param numColours the number of possible colours in each slot
     * @param numPreviousGuesses the number of guesses that should be stored
     */
    private void setupMasterMind(int numSlots, int numColours, int numPreviousGuesses) {
        this.masterMind = new MasterMind(numSlots, numColours, numPreviousGuesses);
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
        masterMind.makeGuess(guessCode0);
        masterMind.makeGuess(guessCode1);
        MasterMindCode[] lastGuessedCodes = masterMind.getLastNGuesses(3);
        assertArrayEquals(guessCode0, lastGuessedCodes[2].getCode());
        assertArrayEquals(guessCode1, lastGuessedCodes[1].getCode());
        assertArrayEquals(emptyGuess, lastGuessedCodes[0].getCode());
    }

    /**
     * Test gameWon.
     */
    @Test
    public void gameWonTest() {
        setupMasterMind(4, 8, 5);
        assertFalse(masterMind.gameWon());
        masterMind.makeGuess(masterMind.getAnswerCode());
        assertTrue(masterMind.gameWon());
    }
}