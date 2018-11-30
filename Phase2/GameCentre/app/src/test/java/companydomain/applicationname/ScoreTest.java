package companydomain.applicationname;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {

    /**
     * A Score to test.
     */
    private Score score;

    /**
     * Initialize a Score.
     */
    @Before
    public void setUp() {
        score = new Score("Bob", 7, 3);
    }

    @Test
    public void getScore() {
        assertEquals(score.getScore(), 7);
    }

    @Test
    public void getUsername() {
        assertEquals(score.getUsername(), "Bob");
    }

    @Test
    public void getSize() {
        assertEquals(score.getSize(), 3);
    }

    @Test
    public void compareTo() {
        assertEquals(score.compareTo(new Score("Alice", 3, 3)), 4);
        assertEquals(score.compareTo(new Score("Candice", 7, 3)), 0);
        assertEquals(score.compareTo(new Score("Eve", 17, 3)), -10);
    }
}