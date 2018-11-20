package companydomain.applicationname;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manage game of MasterMind, including managing the previous guesses, making guesses,
 * keeping track of score, and checking if the game has been won.
 */
class MasterMind implements Serializable {

    /**
     * Answer key.
     */
    private MasterMindCode answer;

    /**
     * Keep track of previous guesses. The first element is the newest guess on record.
     */
    private ArrayList<MasterMindCode> previousGuesses;

    /**
     * The current score for the game.
     */
    private int scoreCounter;

    /**
     * Initialize the MasterMind game with a random answer, an empty previousGuesses list,
     * and a score of 0.
     *
     * Precondition: numColours >= numSlots
     * Precondition: numPreviousGuesses > 0
     *
     * @param numSlots the number of slots in the answer
     * @param numColours the number of "colours" that could be in each slot
     * @param numPreviousGuesses the number of previous guesses to keep track of
     */
    MasterMind(int numSlots, int numColours, int numPreviousGuesses) {
        this.createAnswer(numSlots, numColours);
        this.createEmptyPreviousGuesses(numSlots, numPreviousGuesses);
        this.scoreCounter = 0;
    }

    /**
     * Create a random answer key.
     * @param numSlots the number of slots in the answer
     * @param numColours the number of "colours" that could be in each slot
     */
    private void createAnswer(int numSlots, int numColours) {
        Random randomIntGenerator = new Random();
        int[] answerCode = new int[numSlots];
        for(int i = 0; i < numSlots; i++) {
            answerCode[i] = randomIntGenerator.nextInt(numColours) + 1;
        }
        this.answer = new MasterMindCode(answerCode);
    }

    /**
     * Create an ArrayList of empty guesses.
     * @param numSlots the number of slots in the answer
     * @param numPreviousGuesses the number of previous guesses to keep track of
     */
    private void createEmptyPreviousGuesses(int numSlots, int numPreviousGuesses) {
        this.previousGuesses = new ArrayList<>();
        int[] emptyGuessCode = new int[numSlots];
        MasterMindCode emptyGuess = new MasterMindCode(emptyGuessCode, this.answer);
        for(int i = 0; i < numPreviousGuesses; i++) {
            this.previousGuesses.add(emptyGuess);
        }
    }

    /**
     * Make a guess.
     *
     * Precondition: guess is of the right length and has the right "colours".
     *
     * @param guessCode the integers guessed
     */
    void makeGuess(int[] guessCode) {
        MasterMindCode guess = new MasterMindCode(guessCode, this.answer);
        this.previousGuesses.add(0, guess);
        this.previousGuesses.remove(this.previousGuesses.size() - 1);
        this.scoreCounter++;
    }

    /**
     * Return an ArrayList of arrays of ints representing the last n guesses, from newest to oldest.
     *
     * Precondition: n <= this.numPreviousGuesses
     *
     * @param n the number of previous guesses wanted
     * @return an array of MasterMindCodes representing the last n guesses, from newest to oldest
     * (guesses filled with zeroes are default values when not enough guesses have been made)
     */
    MasterMindCode[] getLastNGuesses(int n) {
        MasterMindCode[] lastNGuesses = new MasterMindCode[n];
        for(int i = 0; i < n; i++) {
            lastNGuesses[i] = this.previousGuesses.get(i);
        }
        return lastNGuesses;
    }

    /**
     * Return the current score.
     *
     * @return the current score
     */
    int getScore() {
        return this.scoreCounter;
    }

    // TODO: potentially collapse this into makeGuess
    /**
     * Return if the game has been won.
     *
     * @return true iff the game has been won
     */
    boolean gameWon() {
        return this.answer.equals(this.previousGuesses.get(this.previousGuesses.size() - 1));
    }

    /**
     * Return the answer code.
     *
     * @return the answer code
     */
    int[] getAnswerCode() {
        return this.answer.getCode();
    }


    /**
     * Set the answer code.
     *
     */
    void setAnswerCode(MasterMindCode answerCode)
    {
        this.answer = answerCode;
    }
}
