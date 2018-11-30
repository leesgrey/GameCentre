package companydomain.applicationname;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class MatchingBoardManagerTest {

    /**
     * A MatchingBoardManager to test.
     */
    MatchingBoardManager matchingBoardManager;
    @Before
    public void setUp() {
        matchingBoardManager = new MatchingBoardManager(4, 5);
        matchingBoardManager.getBoard().sortTilesWithZeroLast();
    }

    @Test
    public void getUnmatched() {
        List<Tile> unmatched = matchingBoardManager.getUnmatched();
        Collections.sort(unmatched);
        for(int i = 0; i < 20; i++) {
            assertEquals(unmatched.get(i).getId(), (i / 2) + 1);
        }
        matchingBoardManager.touchMove(0);
        matchingBoardManager.touchMove(1);
        for(int i = 0; i < 18; i++) {
            assertEquals(unmatched.get(i).getId(), (i / 2) + 2);
        }
    }

    @Test
    public void getSelected() {
        assertEquals(matchingBoardManager.getSelected().size(), 0);
        matchingBoardManager.touchMove(2);
        assertEquals(matchingBoardManager.getSelected().size(), 1);
        assertEquals(matchingBoardManager.getSelected().get(0), matchingBoardManager.getBoard().getTile(2));
    }

    @Test
    public void isValidTap() {
        assertTrue(matchingBoardManager.isValidTap(0));
        matchingBoardManager.touchMove(0);
        assertFalse(matchingBoardManager.isValidTap(0));
        matchingBoardManager.touchMove(1);
        assertFalse(matchingBoardManager.isValidTap(0));
        matchingBoardManager.touchMove(5);
        assertFalse(matchingBoardManager.isValidTap(0));
    }

    @Test
    public void puzzleSolved() {
        for(int i = 0; i < 20; i++) {
            assertFalse(matchingBoardManager.puzzleSolved());
            matchingBoardManager.touchMove(i);
        }
        assertTrue(matchingBoardManager.puzzleSolved());
    }

    @Test
    public void getScoreCounter() {
        for(int i = 0; i < 20; i++) {
            matchingBoardManager.touchMove(i);
            assertEquals(matchingBoardManager.getScoreCounter(), (i + 1) / 2);
        }
    }

    @Test
    public void getSize() {
        assertEquals(matchingBoardManager.getSize(), 5);
    }
}