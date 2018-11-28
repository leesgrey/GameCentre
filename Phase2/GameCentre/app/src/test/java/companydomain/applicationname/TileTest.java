package companydomain.applicationname;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    /** A tile to test.
     *
     */
    Tile testTile;


    /**
     * A method to setupTile.
     */
    private void setupTile(int id)
        {testTile = new Tile(id);
        }

    @Test
    public void getBackgroundTest() {
        setupTile(5);

        assertEquals(R.drawable.tile_5, testTile.getBackground());


    }
    @Test
    public void getIdTest() {
        setupTile(5);
        int expectedId = 5;
        int actualId  = testTile.getId();
        assertEquals(expectedId, actualId);

    }

    @Test
    public void equalsTest() {
        setupTile(5);
        Tile testTile1 = new Tile(6);
        boolean expectedFalse = testTile == testTile1;
        assertEquals(expectedFalse,false);
        Tile testTile2 = new Tile(5);
        boolean expectedTrue = testTile.equals(testTile2);
        assertEquals(expectedTrue, true);

    }

    @Test
    public void compareTo() {
        setupTile(5);
        Tile testTile1 = new Tile(4);
        int expectedValue = 1;
        int actualValue = testTile.compareTo(testTile1);
        assertEquals(expectedValue, actualValue);
    }
}