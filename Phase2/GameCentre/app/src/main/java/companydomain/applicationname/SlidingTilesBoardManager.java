package companydomain.applicationname;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SlidingTilesBoardManager extends BoardManager implements Serializable {

    /**
     * The number of undoes allowed for this game.
     */
    private int allowedUndoes;

    /**
     * The last allowedUndoes moves, represented by the position of the blank tile.
     */
    private LinkedList<Integer> previousMoves;

    /**
     * Manage a new shuffled board, with a given size and number of allowed undoes.
     *
     * @param allowedUndoes the number of allowed undoes
     * @param size the number of rows/columns
     */
    SlidingTilesBoardManager(int allowedUndoes, int size) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = size * size;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        do {
            Collections.shuffle(tiles);
        } while (!SlidingTilesBoardManager.isSolvable(tiles, size));
        this.board = new Board(tiles, size, size);
        this.allowedUndoes = allowedUndoes;
        this.previousMoves = new LinkedList<>();
    }

    /**
     * Check to see if a list of tiles represents a solvable game.
     *
     * @param tiles   the list of tiles we are checking for solvability
     * @param numCols the number of columns in the board
     */
    private static boolean isSolvable(List<Tile> tiles, int numCols) {
        // https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
        int inversions = 0;
        for (int i = 0; i < tiles.size() - 1; i++) {
            if (tiles.get(i).getId() == 0) {
                continue;
            }
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(j).getId() == 0) {
                    continue;
                }
                if (tiles.get(i).getId() > tiles.get(j).getId()) {
                    inversions++;
                }
            }
        }
        if (tiles.size() % 2 == 1) {
            return inversions % 2 == 0;
        } else {
            int blankPosition = 0;
            while (tiles.get(blankPosition).getId() != 0) {
                blankPosition++;
            }
            return (blankPosition / numCols) % 2 != inversions % 2;
        }
    }

    /**
     * Find the position of the blank tile.
     */
    private int findBlankPosition() {
        int position = 0;
        for (Tile t : this.board) {
            if (t.getId() == 0) {
                return position;
            }
            position++;
        }
        return position;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return true iff the tiles are in row-major order.
     */
    boolean puzzleSolved() {
        Iterator<Tile> tile_checker = this.board.iterator();
        for (int i = 1; i < this.board.numTiles(); i++) {
            if (i != tile_checker.next().getId()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int blankPosition = this.findBlankPosition();
        return (position == blankPosition - 1
                && position % this.board.getNumCols() != this.board.getNumCols() - 1)
                || (position == blankPosition + 1 && position % this.board.getNumCols() != 0)
                || position == blankPosition - this.board.getNumCols()
                || position == blankPosition + this.board.getNumCols();
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        this.insertPreviousMove();
        this.scoreCounter += 1;
        this.board.swapTiles(position, this.findBlankPosition());
    }

    /**
     * Update our record of moves previously made.
     */
    private void insertPreviousMove() {
        if (this.allowedUndoes == 0) {
            return;
        }
        if (this.previousMoves.size() >= this.allowedUndoes) {
            this.previousMoves.remove();
        }
        this.previousMoves.add(this.findBlankPosition());
    }

    /**
     * Undo a move. Return true upon success, false if there are no moves to be undone.
     *
     * @return if a move was successfully undone.
     */
    boolean undoMove() {
        if (this.previousMoves.size() == 0) {
            return false;
        }
        this.touchMove(this.previousMoves.removeLast());
        this.previousMoves.removeLast();
        return true;
    }
}
