package companydomain.applicationname;

import android.support.annotation.NonNull;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    // TODO: collapse these two variables
    /**
     * The number of rows.
     */
    private int numRows;

    /**
     * The number of columns.
     */
    private int numCols;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[] tiles;

    private Tile[] hiddenTiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles    the tiles for the board
     * @param numRows the number of rows in the board
     * @param numCols the number of columns in the board
     */
    Board(List<Tile> tiles, int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.tiles = new Tile[numRows * numCols];
        Iterator<Tile> iter = tiles.iterator();

        for (int position = 0; position != this.numTiles(); position++) {
            this.tiles[position] = iter.next();
        }
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return this.numRows * this.numCols;
    }

    /**
     * Return the number of rows on the board.
     *
     * @return the number of rows on the board
     */
    int getNumRows() {
        return this.numRows;
    }

    /**
     * Return the number of rows on the board.
     *
     * @return the number of rows on the board
     */
    int getNumCols() {
        return this.numCols;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param position the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int position) {
        return tiles[position];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param position1 the first tile position
     * @param position2 the second tile position
     */
    void swapTiles(int position1, int position2) {
        Tile swapping_tile = this.tiles[position1];
        this.tiles[position1] = this.tiles[position2];
        this.tiles[position2] = swapping_tile;

        setChanged();
        notifyObservers();
    }

    void flipCard(int position) {
        setChanged();
        notifyObservers();
    }

    @Override
    @NonNull
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Return an Iterator which iterates over the tiles in this Board in row-major order.
     */
    @Override
    public @NonNull
    Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    // TODO: why do we have this
    /**
     * Returns an int indicating the size of the Sliding Tile board.
     *
     * @return the size of the Sliding Tile board.
     */
    public int getSize() {
        return this.numCols;
    }

    /**
     * An Iterator class for this board that iterates over its tiles in row-major order.
     */
    private class BoardIterator implements Iterator<Tile> {
        /**
         * The position of the most recently returned tile (or -1 if none have been returned).
         */
        private int position;

        /**
         * A new BoardIterator for this Board.
         */
        BoardIterator() {
            this.position = -1;
        }

        /**
         * Increment position and return the next Tile in row-major order to be iterated over.
         */
        @Override
        public Tile next() {
            this.position++;
            return tiles[position];
        }

        /**
         * Return true iff there is another Tile to be iterated over.
         */
        @Override
        public boolean hasNext() {
            return this.position < numRows * numCols - 1;
        }
    }
}
