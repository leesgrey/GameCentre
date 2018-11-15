package companydomain.applicationname;

/**
 * A MasterMind code combination.
 */
class MasterMindCode {

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
        for(int i = 0; i < this.code.length; i++) {
            for(int j = 0; j < answerCode.length; j++) {
                if(this.code[i] == answerCode[j]) {
                    if(i == j) {
                        this.correctness[0]++;
                    } else {
                        this.correctness[1]++;
                    }
                }
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
