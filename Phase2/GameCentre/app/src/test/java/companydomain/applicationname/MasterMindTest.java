package companydomain.applicationname;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class MasterMindTest {
    /** The mastermind manager for testing. */
    MasterMind mastermind;


    /** Returns a random code for a game with 4 slots and 8 colours.*/
    private int[] createCode() {
        Random randomIntGenerator = new Random();
        int[] randomCode = new int[4];
        for(int i = 0; i < 4; i++) {
            randomCode[i] = randomIntGenerator.nextInt(8) + 1;
        }
        return randomCode;
    }


    /** Setup mastermind manager with 4 slots, 8 colours, and 0 guesses. */
    private void setupMastermind()
    {this.mastermind = new MasterMind(4,8,0);

    }


    @Test
    public void testMakeGuess() {
        setupMastermind();
        int[] guess = createCode();
        mastermind.makeGuess(guess);
        int[] guessedCode =  mastermind.getLastNGuesses(1)[0].getCode();
        assertArrayEquals(guess, guessedCode);
    }

    @Test
    public void testgetLastNGuesses() {
        setupMastermind();
        int[] guesscode1 = createCode();
        int[] guesscode2 = createCode();
        mastermind.makeGuess(guesscode1);
        mastermind.makeGuess(guesscode2);
        int[] lastGuessedCode =  mastermind.getLastNGuesses(1)[1].getCode();
        assertArrayEquals(guesscode2, lastGuessedCode);
        int[] secondLastGuessedCode =  mastermind.getLastNGuesses(1)[0].getCode();
        assertArrayEquals(guesscode1, secondLastGuessedCode);

    }


    @Test
    public void testgameWon() {
        // TODO: might have to change the scope of mastermind's answer instance.
        setupMastermind();

    }
}