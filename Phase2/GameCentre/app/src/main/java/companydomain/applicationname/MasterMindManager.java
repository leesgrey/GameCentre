package companydomain.applicationname;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Manage game of MasterMindManager, including managing the previous guesses, making guesses,
 * keeping track of score, and checking if the game has been won.
 */
class MasterMindManager implements Serializable {

    /**
     * Answer key.
     */
    private MasterMindCombination answer;

    /**
     * Keep track of a set number of previous guesses.
     * The first element is the newest guess on record.
     */
    private List<MasterMindCombination> previousGuesses;

    /**
     * The current score for the game.
     */
    private int scoreCounter;

    /**
     * Initialize the MasterMindManager game with a random answer, an empty previousGuesses list,
     * and a score of 0.
     *
     * Precondition: numColours >= numSlots
     * Precondition: numPreviousGuesses > 0
     *
     * @param numSlots the number of slots in the answer
     * @param numPreviousGuesses the number of previous guesses to keep track of
     */
    MasterMindManager(int numSlots, int numPreviousGuesses) {
        this.createAnswer(numSlots);
        this.createEmptyPreviousGuesses(numSlots, numPreviousGuesses);
        this.scoreCounter = 0;
    }

    /**
     * Create a random answer key.
     * @param numSlots the number of slots in the answer
     */
    private void createAnswer(int numSlots) {
        Random randomIntGenerator = new Random();
        int[] answerCode = new int[numSlots];
        for(int i = 0; i < numSlots; i++) {
            answerCode[i] = randomIntGenerator.nextInt(10);
        }
        this.answer = new MasterMindCombination(answerCode);
    }

    /**
     * Create an ArrayList of empty guesses.
     * @param numSlots the number of slots in the answer
     * @param numPreviousGuesses the number of previous guesses to keep track of
     */
    private void createEmptyPreviousGuesses(int numSlots, int numPreviousGuesses) {
        this.previousGuesses = new ArrayList<>();
        int[] emptyGuessCode = new int[numSlots];
        for(int i = 0; i < numSlots; i++) {
            emptyGuessCode[i] = -1;
        }
        MasterMindCombination emptyGuess = new MasterMindCombination(emptyGuessCode, this.answer);
        for(int i = 0; i < numPreviousGuesses; i++) {
            this.previousGuesses.add(emptyGuess);
        }
    }

    /**
     * Make a guess. Return if it was correct.
     *
     * Precondition: guess is of the right length and has the right "colours".
     *
     * @param guessCode the integers guessed
     * @return if the guess was correct
     */
    boolean makeGuess(int[] guessCode) {
        MasterMindCombination guess = new MasterMindCombination(guessCode, this.answer);
        this.previousGuesses.add(0, guess);
        this.previousGuesses.remove(this.previousGuesses.size() - 1);
        this.scoreCounter++;
        return Arrays.equals(this.answer.getCode(), guessCode);
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
    MasterMindCombination[] getLastNGuesses(int n) {
        MasterMindCombination[] lastNGuesses = new MasterMindCombination[n];
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
     * @param answerCode the answer code to be set
     */
    void setAnswerCode(MasterMindCombination answerCode)
    {
        this.answer = answerCode;
    }

    /**
     * Undo the most recent guess.
     */
    void undoMove() {
        this.previousGuesses.remove(0);
        this.previousGuesses.add(new MasterMindCombination(new int[this.getAnswerCode().length], this.answer));
        this.scoreCounter -= 1;
    }
}
