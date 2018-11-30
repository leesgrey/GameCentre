package companydomain.applicationname;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    /**
     * A Board to test.
     */
    Board slidingTileBoard, matchingBoard;

    @Before
    public void setUp() {
        List<Tile> slidingTiles = new ArrayList<>();
        List<Tile> matchingTiles = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            slidingTiles.add(new Tile(i));
        }
        for(int i = 2; i < 14; i++) {
            matchingTiles.add(new Tile(i / 2));
        }
        Collections.shuffle(slidingTiles);
        Collections.shuffle(matchingTiles);
        slidingTileBoard = new Board(slidingTiles, 3, 3);
        matchingBoard = new Board(matchingTiles, 3, 4);
    }

    @Test
    public void numTiles() {
        assertEquals(slidingTileBoard.numTiles(), 9);
        assertEquals(matchingBoard.numTiles(), 12);
    }

    @Test
    public void getNumRows() {
        assertEquals(slidingTileBoard.getNumRows(), 3);
        assertEquals(matchingBoard.getNumRows(), 3);
    }

    @Test
    public void getNumCols() {
        assertEquals(slidingTileBoard.getNumCols(), 3);
        assertEquals(matchingBoard.getNumCols(), 4);
    }

    @Test
    public void sortTilesWithZeroLast() {
        slidingTileBoard.sortTilesWithZeroLast();
        matchingBoard.sortTilesWithZeroLast();
        for(int i = 0; i < 8; i++) {
            assertEquals(slidingTileBoard.getTile(i).getId(), i + 1);
        }
        assertEquals(slidingTileBoard.getTile(8).getId(), 0);
        int counter = 2;
        for(Tile tile: matchingBoard) {
            assertEquals(tile.getId(), counter / 2);
            counter++;
        }
    }

    @Test
    public void getTile() {
        slidingTileBoard.sortTilesWithZeroLast();
        matchingBoard.sortTilesWithZeroLast();
        int counter = 0;
        for(Tile tile: slidingTileBoard) {
            assertEquals(tile, slidingTileBoard.getTile(counter));
            counter++;
        }
        counter = 0;
        for(Tile tile: matchingBoard) {
            assertEquals(tile, matchingBoard.getTile(counter));
            counter++;
        }
    }

    @Test
    public void swapTiles() {
        Tile tile5 = matchingBoard.getTile(5);
        Tile tile7 = matchingBoard.getTile(7);
        matchingBoard.swapTiles(5, 7);
        assertEquals(matchingBoard.getTile(5), tile7);
        assertEquals(matchingBoard.getTile(7), tile5);
    }

    @Test
    public void getSize() {
        assertEquals(slidingTileBoard.getSize(), 3);
        assertEquals(matchingBoard.getSize(), 3);
    }
}