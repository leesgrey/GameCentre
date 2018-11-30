package companydomain.applicationname;

import org.junit.Test;

import java.util.ArrayList;

import java.util.List;


import static org.junit.Assert.*;

public class MatchingBoardManagerTest {

    private MatchingBoardManager matchingBoardManager;
    private int numRows;
    private int numCols;
    List<Tile> unMatchedTiles;
    List<Tile> selectedTiles;

    /**
     * Initialize the MatchingBoardManager instance.
     *
     * @param numRows the number of rows
     * @param numCols the number of columns
     */
    private void setupMatchingBoard(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.matchingBoardManager = new MatchingBoardManager(numRows, numCols);
    }


    private void createUnmatchedandSelectedTiles()
    {   selectedTiles = new ArrayList<>();
        unMatchedTiles = new ArrayList<>();
        int numTiles = numRows * numCols;
        for (int tileNum = 0; tileNum != numTiles/2; tileNum++) {
            Tile firstOfPair = new Tile(tileNum + 1);
            Tile secondOfPair = new Tile(tileNum + 1);
            unMatchedTiles.add(firstOfPair);
            unMatchedTiles.add(secondOfPair);
        }
    }

/**
 * Test isValidTap() method.
 */
    @Test
    public void isValidTapTest() {
        setupMatchingBoard(3,4);
        boolean testtap1 = matchingBoardManager.isValidTap(5);

        assertEquals(true, testtap1);

        List<Tile> selected = matchingBoardManager.getSelected();
        List<Tile> unmatched = matchingBoardManager.getUnmatched();
        Tile id = matchingBoardManager.board.getTile(5);

        selected.add(id);
        boolean testtap2 = matchingBoardManager.isValidTap(5);
        assertEquals(false, testtap2);

        unmatched.remove(id);
        boolean testtap3 = matchingBoardManager.isValidTap(5);
        assertEquals(false, testtap3);

        }

    /**
     * Test touchMove() method.
     */
    @Test
    public void touchMoveTest() {

        setupMatchingBoard(3,4);
        createUnmatchedandSelectedTiles();

        List<Tile> unMatchedTest = this.unMatchedTiles;

        List<Tile> selectedActual = matchingBoardManager.getSelected();
        List<Tile> unMatchedActual = matchingBoardManager.getUnmatched();


        matchingBoardManager.board.sortTilesWithZeroLast();

        Tile id = matchingBoardManager.board.getTile(3);

        matchingBoardManager.touchMove(0);
        matchingBoardManager.touchMove(1);

        assertEquals(unMatchedTest.size()-2,  unMatchedActual.size());


        matchingBoardManager.touchMove(5);
        assertEquals(1, selectedActual.size());

        matchingBoardManager.touchMove(5);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, selectedActual.size());


    }

    /**
     * Test puzzleSolved() method.
     */
    @Test
    public void puzzleSolvedTest() {
        setupMatchingBoard(3, 4);
        assertFalse(matchingBoardManager.puzzleSolved());
        matchingBoardManager.board.sortTilesWithZeroLast();

        System.out.println();
        for (int i = 0; i < matchingBoardManager.board.numTiles() ; i++) {
            matchingBoardManager.touchMove(i);
            matchingBoardManager.touchMove(i);
            }
        assertTrue(matchingBoardManager.puzzleSolved());
    }

    }
