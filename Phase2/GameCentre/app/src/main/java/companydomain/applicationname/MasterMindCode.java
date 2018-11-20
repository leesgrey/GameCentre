package companydomain.applicationname;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

/**
 * A MasterMind code combination.
 */
class MasterMindCode implements Serializable {

    /**
     * The code combination. All zeroes if it's an empty guess, no zeroes otherwise.
     */
    private int[] code;

    /**
     * Two integers: the first is how many right "colours" in the right spot,
     * the second is how many right "colours" are in the wrong spot.
     */
    private int[] correctness;

    /**
     * Initialize an answer MasterMindCode.
     *
     * @param code the code combination
     */
    MasterMindCode(int[] code) {
        this.code = code;
        this.correctness = new int[2];
        this.correctness[0] = code.length;
    }

    /**
     * Initialize a MasterMindCode guess, and compare it to the answer code.
     *
     * @param code the code combination
     * @param answer the answer MasterMindCode
     */
    MasterMindCode(int[] code, MasterMindCode answer) {
        this.code = code;
        this.initializeCorrectness(answer.getCode());
    }

    /**
     * Initialize the correctness attribute by comparing the code with the answer's code.
     *
     * @param answerCode the answer code
     */
    private void initializeCorrectness(int[] answerCode) {
        this.correctness = new int[2];
        for(int i = 0; i < this.code.length; i++) {
            if(this.code[i] == answerCode[i]) {
                this.correctness[0]++;
                this.correctness[1]--;
            }
        }
        int[] codeSorted = this.code.clone();
        Arrays.sort(codeSorted);
        int[] answerCodeSorted = answerCode.clone();
        Arrays.sort(answerCodeSorted);
        int i = 0;
        int j = 0;
        while(i < codeSorted.length && j < answerCodeSorted.length) {
            if(codeSorted[i] < answerCodeSorted[j]) {
                i++;
            } else if(codeSorted[i] > answerCodeSorted[j]) {
                j++;
            } else {
                this.correctness[1]++;
                i++;
                j++;
            }
        }
    }

    /**
     * Return code.
     *
     * @return code
     */
    int[] getCode() {
        return this.code;
    }

    /**
     * Return correctness.
     *
     * @return correctness
     */
    int[] getCorrectness() {
        return this.correctness;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof MasterMindCode) {
            return (((MasterMindCode) o).getCode() == this.code);
        }
        return false;
    }
}
