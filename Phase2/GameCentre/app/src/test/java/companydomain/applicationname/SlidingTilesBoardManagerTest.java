package companydomain.applicationname;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SlidingTilesBoardManagerTest {

    /**
     * A SlidingTilesBoardManager to test.
     */
    private SlidingTilesBoardManager slidingTilesBoardManager;
    private Board board;

    @Before
    public void setUp() {
        slidingTilesBoardManager = new SlidingTilesBoardManager(2, 4);
        board = slidingTilesBoardManager.getBoard();
        board.sortTilesWithZeroLast();
    }

    @Test
    public void puzzleSolved() {
        board.swapTiles(0, 1);
        assertFalse(slidingTilesBoardManager.puzzleSolved());
        board.swapTiles(0, 1);
        assertTrue(slidingTilesBoardManager.puzzleSolved());
    }

    @Test
    public void isValidTap() {
        for(int i = 0; i < 16; i++) {
            if(i == 11 || i == 14) {
                assertTrue(slidingTilesBoardManager.isValidTap(i));
            }
            else {
                assertFalse(slidingTilesBoardManager.isValidTap(i));
            }
        }
        board.swapTiles(15, 0);
        for(int i = 0; i < 16; i++) {
            if(i == 1 || i == 4) {
                assertTrue(slidingTilesBoardManager.isValidTap(i));
            }
            else {
                assertFalse(slidingTilesBoardManager.isValidTap(i));
            }
        }
    }

    @Test
    public void touchMove() {
        slidingTilesBoardManager.touchMove(14);
        for(int i = 0; i < 16; i++) {
            if(i != 14 && i != 15) {
                assertEquals(board.getTile(i).getId(), i + 1);
            }
            assertEquals(board.getTile(14).getId(), 0);
            assertEquals(board.getTile(15).getId(), 15);
        }
    }

    @Test
    public void undoMove() {
        slidingTilesBoardManager.touchMove(11);
        slidingTilesBoardManager.touchMove(10);
        slidingTilesBoardManager.touchMove(6);
        assertTrue(slidingTilesBoardManager.undoMove());
        slidingTilesBoardManager.undoMove();
        slidingTilesBoardManager.undoMove();
        slidingTilesBoardManager = new SlidingTilesBoardManager(0, 3);
        slidingTilesBoardManager.touchMove(7);
        assertFalse(slidingTilesBoardManager.undoMove());
    }

    @Test
    public void getBoard() {
        assertEquals(board, slidingTilesBoardManager.board);
    }

    @Test
    public void getScoreCounter() {
        assertEquals(slidingTilesBoardManager.getScoreCounter(), 0);
        slidingTilesBoardManager.touchMove(14);
        assertEquals(slidingTilesBoardManager.getScoreCounter(), 1);
        slidingTilesBoardManager.touchMove(10);
        assertEquals(slidingTilesBoardManager.getScoreCounter(), 2);
        slidingTilesBoardManager.undoMove();
        assertEquals(slidingTilesBoardManager.getScoreCounter(), 1);
    }

    @Test
    public void getSize() {
        assertEquals(slidingTilesBoardManager.getSize(), 4);
        slidingTilesBoardManager = new SlidingTilesBoardManager(0, 5);
        assertEquals(slidingTilesBoardManager.getSize(), 5);
    }
}