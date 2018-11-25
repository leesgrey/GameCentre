package companydomain.applicationname;

import java.io.Serializable;

abstract class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    Board board;

    /**
     * This keeps track of the score for the game
     */
    int scoreCounter = 0;

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
    abstract boolean puzzleSolved();

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    abstract boolean isValidTap(int position);

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    abstract void touchMove(int position);

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
