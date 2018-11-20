package companydomain.applicationname;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MasterMindCodeTest {
    private MasterMindCode answer;
    private MasterMindCode guess1;
    private MasterMindCode guess2;

    @Before
    public void setUp(){
        answer = new MasterMindCode(new int[] {4, 1, 1, 6, 5, 5});
        guess1 = new MasterMindCode(new int[] {1, 1, 1, 1, 1, 1}, answer);
        guess2 = new MasterMindCode(new int[] {3, 1, 4, 5, 3, 2}, answer);
    }

    public MasterMindCodeTest() {
        setUp();
        assertArrayEquals(new int[] {4, 1, 1, 6, 5, 5}, answer.getCode());
        assertArrayEquals(new int[] {1, 1, 1, 1, 1, 1}, guess1.getCode());
        assertArrayEquals(new int[] {3, 1, 4, 5, 3, 2}, guess2.getCode());
        assertArrayEquals(new int[] {6, 0}, answer.getCorrectness());
        assertArrayEquals(new int[] {2, 0}, guess1.getCorrectness());
        assertArrayEquals(new int[] {1, 2}, guess2.getCorrectness());
    }

    // TODO: figure out why this isn't working, cause the equals method seems to work
    @Test
    public void equalsTest() {
        setUp();
        assertTrue(answer.equals(new MasterMindCode(new int[] {1, 1, 4, 5, 5, 6})));
        assertTrue(guess1.equals(new MasterMindCode(new int[] {1, 1, 1, 1, 1, 1})));
        assertTrue(guess2.equals(new MasterMindCode(new int[] {4, 1, 3, 2, 3, 5}, answer)));
        assertFalse(answer.equals(guess1));
        assertFalse(answer.equals(guess2));
        assertFalse(guess1.equals(guess2));
    }
}
