package companydomain.applicationname;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MasterMindManagerCodeTest {
    /**
     * The MasterMindCombination answer.
     */
    private MasterMindCombination answer;
    /**
     * The first guessed MasterMindCombination.
     */
    private MasterMindCombination guess1;
    /**
     * The second guessed MasterMindCombination.
     */
    private MasterMindCombination guess2;


    /**
     * Initializes the MasterMindCombination instances, i.e. answer, guess1, and guess2.
     */
    @Before
    public void setUp(){
        answer = new MasterMindCombination(new int[] {4, 1, 1, 6, 5, 5});
        guess1 = new MasterMindCombination(new int[] {1, 1, 1, 1, 1, 1}, answer);
        guess2 = new MasterMindCombination(new int[] {3, 1, 4, 5, 3, 2}, answer);
    }


    /**
     * Test the getCode() method.
     */

    @Test
    public void getCodeTest() {
        setUp();
        assertArrayEquals(new int[] {4, 1, 1, 6, 5, 5}, answer.getCode());
        assertArrayEquals(new int[] {1, 1, 1, 1, 1, 1}, guess1.getCode());
        assertArrayEquals(new int[] {3, 1, 4, 5, 3, 2}, guess2.getCode());


    }

    /**
     * Test the getCorrectness() method.
     */
    @Test
    public void getCorrectnessTest() {
        setUp();
        assertArrayEquals(new int[] {6, 0}, answer.getCorrectness());
        assertArrayEquals(new int[] {2, 0}, guess1.getCorrectness());
        assertArrayEquals(new int[] {1, 2}, guess2.getCorrectness());

    }


    /**
     * Test comparison method of MasterMindCombination.
     */

    @Test
    public void equalsTest() {
        setUp();
        assertEquals(answer,new MasterMindCombination(new int[] {4, 1, 1, 6, 5, 5}));
        assertEquals(guess1, new MasterMindCombination(new int[] {1, 1, 1, 1, 1, 1}));
        assertEquals(guess2, new MasterMindCombination(new int[] {3, 1, 4, 5, 3, 2}, answer));
        assertNotEquals(answer, guess1);
        assertNotEquals(answer, guess2);
        assertNotEquals(guess1, guess2);
    }
}
