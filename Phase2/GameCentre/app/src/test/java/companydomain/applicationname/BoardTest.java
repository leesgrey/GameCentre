package companydomain.applicationname;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {
    /* A test board

     */
    Board testBoard;
    int numRows;
    int numCols;

    /*
    Setup board.
     */
    private void setupBoard(List<Tile> tiles, int numRows, int numCols)
    {   this.numRows = numRows;
        this.numCols = numCols;
        testBoard = new Board(tiles, numRows, numCols);
    }

    /**
    * Make a set of tiles that are in order.
    * @return a set of tiles that are in order
    */
    private List<Tile> makeOrderedTiles(int numRows, int numCols) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = numRows * numCols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    @Test
    public void numTilesTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        int expetedNumTiles = 3*3;
        int actualNumTiles = testBoard.numTiles();
        assertEquals(expetedNumTiles, actualNumTiles);
    }

    @Test
    public void getNumRowsTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        int expectedRows = 3;
        int actualRows = testBoard.getNumRows();
        assertEquals(expectedRows, actualRows);
    }

    @Test
    public void getNumColsTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        int expectedCols = 3;
        int actualCols = testBoard.getNumRows();
        assertEquals(expectedCols, actualCols);
    }

    @Test
    public void getTileTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        Tile expectedTile = new Tile(5);
        Tile actualTile = testBoard.getTile(4);
        assertEquals(expectedTile, actualTile);
    }

    @Test
    public void swapTilesTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        assertEquals(testBoard.getTile(2),new Tile(3));
        assertEquals(testBoard.getTile(3),new Tile(4));
        testBoard.swapTiles(2,3);
        assertEquals(testBoard.getTile(2),new Tile(4));
        assertEquals(testBoard.getTile(3), new Tile(3));

    }


    @Test
    public void flipCardTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        assertFalse(testBoard.hasChanged());
        testBoard.flipCard();
        assertTrue(testBoard.hasChanged());

    }


}