package companydomain.applicationname;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MasterMindCodeTest {
    /**
     * The MasterMindCode answer.
     */
    private MasterMindCode answer;
    /**
     * The first guessed MasterMindCode.
     */
    private MasterMindCode guess1;
    /**
     * The second guessed MasterMindCode.
     */
    private MasterMindCode guess2;


    /**
     * Initializes the MasterMinCode instances, i.e. answer, guess1, and guess2.
     */
    @Before
    public void setUp(){
        answer = new MasterMindCode(new int[] {4, 1, 1, 6, 5, 5});
        guess1 = new MasterMindCode(new int[] {1, 1, 1, 1, 1, 1}, answer);
        guess2 = new MasterMindCode(new int[] {3, 1, 4, 5, 3, 2}, answer);
    }


    /**
     * Test the getCode() method.
     */

    @Test
    public void getCode() {
        setUp();
        assertArrayEquals(new int[] {4, 1, 1, 6, 5, 5}, answer.getCode());
        assertArrayEquals(new int[] {1, 1, 1, 1, 1, 1}, guess1.getCode());
        assertArrayEquals(new int[] {3, 1, 4, 5, 3, 2}, guess2.getCode());


    }

    /**
     * Test the getCorectness() method.
     */
    @Test
    public void getCorrectness() {
        setUp();
        assertArrayEquals(new int[] {6, 0}, answer.getCorrectness());
        assertArrayEquals(new int[] {2, 0}, guess1.getCorrectness());
        assertArrayEquals(new int[] {1, 2}, guess2.getCorrectness());

    }


    /**
     * Test comparison method of MasterMindCode.
     */

    @Test
    public void equals() {
        setUp();
        assertEquals(answer,new MasterMindCode(new int[] {4, 1, 1, 6, 5, 5}));
        assertEquals(guess1, new MasterMindCode(new int[] {1, 1, 1, 1, 1, 1}));
        assertEquals(guess2, new MasterMindCode(new int[] {3, 1, 4, 5, 3, 2}, answer));
        assertNotEquals(answer, guess1);
        assertNotEquals(answer, guess2);
        assertNotEquals(guess1, guess2);
    }
}
