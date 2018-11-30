package companydomain.applicationname;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {

    /** A score instance
     *
     */
    Score scoreTest;
    /** Setup a score to test.
     *
     */
    private void setupScore(String username, int score, int size)
    { scoreTest = new Score(username, score, size);

    }

    /** Test getScore() method.
     *
     */
    @Test
    public void getScoreTest() {
        setupScore("testusername", 3,3);
        int expectedScore = 3;
        int actualScore = scoreTest.getScore();
        assertEquals(expectedScore, actualScore);
    }

    /**Test getUsername() method
     *
     */
    @Test
    public void getUsernameTest() {
        setupScore("testusername", 3,3);
        String expectedUsername = "testusername";
        String actualUsername = scoreTest.getUsername();
        assertEquals(expectedUsername, actualUsername);
    }

    /** Test getSize() method.
     *
     */
    @Test
    public void getSizeTest() {
        setupScore("testusername", 3,3);
        int expectedSize = 3;
        int actualSize = scoreTest.getSize();
        assertEquals(expectedSize, actualSize);
    }

    /** Test compareTo() method.
     *
     */
    @Test
    public void compareToTest() {
        setupScore("testusername",3,3);
        int expectedResult = 1;
        int actualResult = scoreTest.compareTo(new Score("testusername1", 2,3));
        assertEquals(expectedResult,actualResult);
    }
}