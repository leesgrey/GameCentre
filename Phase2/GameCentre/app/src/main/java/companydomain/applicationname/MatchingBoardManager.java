package companydomain.applicationname;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MatchingBoardManager extends BoardManager {
    /**
     * The list of unmatched tiles.
     */
    private List<Tile> unmatched;

    /**
     * The list of selected tiles for the current guess.
     */
    private List<Tile> selected;

    /**
     * Manage a board that has been pre-populated with a given size.
     *
     * @param numRows the number of rows
     * @param numCols the number of columns
     */
    MatchingBoardManager(int numRows, int numCols) {
        List<Tile> tiles = new ArrayList<>();
        selected = new ArrayList<>();
        unmatched = new ArrayList<>();
        int numTiles = numRows * numCols;
        for (int tileNum = 0; tileNum != numTiles / 2; tileNum++) {
            Tile firstOfPair = new Tile(tileNum + 1);
            Tile secondOfPair = new Tile(tileNum + 1);
            tiles.add(firstOfPair);
            tiles.add(secondOfPair);
            unmatched.add(firstOfPair);
            unmatched.add(secondOfPair);
        }
        Collections.shuffle(tiles);
        this.board = new Board(tiles, numRows, numCols);
    }

    /**
     * Return the unmatched tiles in the current game.
     *
     * @return the unmatched tiles in the current game.
     */
    List<Tile> getUnmatched() {
        return unmatched;
    }

    /**
     * Return the selected tiles for the current guess.
     *
     * @return the selected tiles for the current guess.
     */
    List<Tile> getSelected() {
        return selected;
    }

    /**
     * Return whether the tile is un-flipped and selectable.
     *
     * @param position the tile to check
     * @return whether the tile is un-flipped and selectable.
     */
    public boolean isValidTap(int position) {
        Tile id = this.board.getTile(position);
        return unmatched.contains(id) && !(selected.contains(id));
    }

    /**
     * Process a touch at position in the board, flipping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        if (selected.size() < 1) {
            selected.add(this.board.getTile(position));
        } else {
            this.scoreCounter += 1;
            selected.add(this.board.getTile(position));
            if (selected.get(0).getId() == selected.get(1).getId()) {
                this.unmatched.remove(selected.get(0));
                this.unmatched.remove(selected.get(1));
                this.selected.clear();
            } else {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        selected.clear();
                    }
                }, 5);
            }
        }
        board.flipCard();
    }

    /**
     * Return whether every tile is flipped over.
     *
     * @return whether every tile is flipped over.
     */
    public boolean puzzleSolved() {
        return unmatched.isEmpty();
    }
}