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
class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The number of undoes allowed for this game.
     */
    private int allowedUndoes;

    /**
     * This keeping track of the score for the game
     */
    private int scoreCounter = 0;

    /**
     * The last allowedUndoes moves, represented by the position of the blank tile.
     */
    private LinkedList<Integer> previousMoves;

    /**
     * Manage a board that has been pre-populated, with a default number of allowed undoes.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this(board, 3);
    }

    /**
     * Manage a board that has been pre-populated, with a given number of allowed undoes.
     *
     * @param board         the board
     * @param allowedUndoes the number of allowed undoes
     */
    private BoardManager(Board board, int allowedUndoes) {
        this.board = board;
        this.allowedUndoes = allowedUndoes;
        this.previousMoves = new LinkedList<>();
    }

    // TODO: we should get rid of this
    /**
     * Manage a new shuffled board, with a default size and number of allowed undoes.
     */
    BoardManager() {
        this(4, 4);
    }

    /**
     * Manage a new shuffled board, with a given size and a default number of allowed undoes.
     *
     * @param num_rows the number of rows
     * @param num_cols the number of columns
     */
    private BoardManager(int num_rows, int num_cols) {
        this(3, num_rows, num_cols);
    }

    /**
     * Manage a new shuffled board, with a given size and number of allowed undoes.
     *
     * @param allowedUndoes the number of allowed undoes
     * @param num_rows      the number of rows
     * @param num_cols      the number of columns
     */
    BoardManager(int allowedUndoes, int num_rows, int num_cols) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = num_rows * num_cols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        Collections.shuffle(tiles);
        this.board = new Board(tiles, num_rows, num_cols);
        this.allowedUndoes = allowedUndoes;
        this.previousMoves = new LinkedList<>();
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
     * Return the current board.
     *
     * @return the current board.
     */
    Board getBoard() {
        return board;
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
                && position % this.board.getNum_cols() != this.board.getNum_cols() - 1)
                || (position == blankPosition + 1 && position % this.board.getNum_cols() != 0)
                || position == blankPosition - this.board.getNum_cols()
                || position == blankPosition + this.board.getNum_cols();
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

    // TODO: we should get rid of this
    /**
     * Swap the bottom-right tile with the tile immediately to its left.
     */
    void cheatCode() {
        this.scoreCounter++;
        this.getBoard().swapTiles(this.getBoard().numTiles() - 1, this.getBoard().numTiles() - 2);
    }

    /**
     * Return the current score.
     *
     * @return the current score
     */
    int getScoreCounter() {
        return scoreCounter;
    }

    /**
     * Return the size of the current SlidingTiles game.
     *
     * @return the size of the current SlidingTiles game.
     */
    int getSize() {
        return board.getSize();
    }
}
