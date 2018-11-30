package companydomain.applicationname;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {
    /**
     * A test board
     */
    Board testBoard;

    /**
     * Setup board.
     */
    private void setupBoard(List<Tile> tiles, int numRows, int numCols)
    {
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

    /*
   Test numTiles() method.
    */
    @Test
    public void numTilesTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        int expetedNumTiles = 3*3;
        int actualNumTiles = testBoard.numTiles();
        assertEquals(expetedNumTiles, actualNumTiles);
    }

    /*
  Test getNumRows() method.
   */
    @Test
    public void getNumRowsTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        int expectedRows = 3;
        int actualRows = testBoard.getNumRows();
        assertEquals(expectedRows, actualRows);
    }

    /*
  Test getNumCols() method.
   */
    @Test
    public void getNumColsTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        int expectedCols = 3;
        int actualCols = testBoard.getNumRows();
        assertEquals(expectedCols, actualCols);
    }

    /*
  Test getTile() method.
   */
    @Test
    public void getTileTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        Tile expectedTile = new Tile(5);
        Tile actualTile = testBoard.getTile(4);
        assertEquals(expectedTile.getId(), actualTile.getId());
    }

    /*
  Test swapTiles() method.
   */
    @Test
    public void swapTilesTest() {
        List<Tile> tiles = makeOrderedTiles(3,3);
        setupBoard(tiles, 3,3);
        assertEquals(testBoard.getTile(2).getId(),new Tile(3).getId());
        assertEquals(testBoard.getTile(3).getId(),new Tile(4).getId());
        testBoard.swapTiles(2,3);
        assertEquals(testBoard.getTile(2).getId(),new Tile(4).getId());
        assertEquals(testBoard.getTile(3).getId(), new Tile(3).getId());

    }




}