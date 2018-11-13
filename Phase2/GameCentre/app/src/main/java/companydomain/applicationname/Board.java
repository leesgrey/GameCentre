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

    /**
     * The number of rows.
     */
    private int num_rows;

    /**
     * The number of columns.
     */
    private int num_cols;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == num_rows * num_cols
     *
     * @param tiles    the tiles for the board
     * @param num_rows the number of rows in the board
     * @param num_cols the number of columns in the board
     */
    Board(List<Tile> tiles, int num_rows, int num_cols) {
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        this.tiles = new Tile[num_rows * num_cols];
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
        return this.num_rows * this.num_cols;
    }

    /**
     * Return the number of rows on the board.
     *
     * @return the number of rows on the board
     */
    int getNum_rows() {
        return this.num_rows;
    }

    /**
     * Return the number of rows on the board.
     *
     * @return the number of rows on the board
     */
    int getNum_cols() {
        return this.num_cols;
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

    /**
     * Returns an int indicating the size of the Sliding Tile board.
     *
     * @return the size of the Sliding Tile board.
     */
    public int getSize() {
        return this.num_cols;
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
            return this.position < num_rows * num_cols - 1;
        }
    }
}
