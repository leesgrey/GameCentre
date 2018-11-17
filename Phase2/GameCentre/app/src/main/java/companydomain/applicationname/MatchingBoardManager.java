package companydomain.applicationname;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class MatchingBoardManager extends BoardManager {

    Board board;

    MatchingBoardManager(int numRows, int numCols) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = numRows * numCols;
        for (int tileNum = 0; tileNum != numTiles/2; tileNum++) {
            tiles.add(new Tile(tileNum + 1));
            tiles.add(new Tile(tileNum + 1));
        }
        Collections.shuffle(tiles);
        this.board = new Board(tiles, numRows, numCols);
    }

    public boolean isValidTap(int position){
        return false;
    }

    public void touchMove(int position) {

    }

    /**
     * Return the current board.
     *
     * @return the current board.
     */
    Board getBoard() {
        return board;
    }

    public boolean puzzleSolved() {
        return false;
    }
}
