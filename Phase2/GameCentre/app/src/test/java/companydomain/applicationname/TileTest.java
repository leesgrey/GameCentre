package companydomain.applicationname;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    /**
     * Two tiles to test. The zero tile and the five tile.
     */
    Tile zeroTile, fiveTile;

    @Before
    public void setUp() {
        zeroTile = new Tile(0);
        fiveTile = new Tile(5);
    }


    @Test
    public void getBackground() {
        assertEquals(zeroTile.getBackground(), R.drawable.tile_0);
        assertEquals(fiveTile.getBackground(), R.drawable.tile_5);
    }

    @Test
    public void getId() {
        assertEquals(zeroTile.getId(), 0);
        assertEquals(fiveTile.getId(), 5);
    }

    @Test
    public void compareTo() {
        assertEquals(zeroTile.compareTo(fiveTile), -5);
        assertEquals(fiveTile.compareTo(zeroTile), 5);
        assertEquals(zeroTile.compareTo(zeroTile), 0);
    }
}
