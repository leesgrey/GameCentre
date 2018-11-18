package companydomain.applicationname;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MatchingBoardManager extends BoardManager {

    /**
     * This keeping track of the score for the game
     */
    private int scoreCounter = 0;
    Board board;

    List<Tile> unmatched;
    List<Tile> selected;

    MatchingBoardManager(int numRows, int numCols) {
        List<Tile> tiles = new ArrayList<>();
        selected = new ArrayList<>();
        unmatched = new ArrayList<>();
        int numTiles = numRows * numCols;
        for (int tileNum = 0; tileNum != numTiles/2; tileNum++) {
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

    public List<Tile> getUnmatched(){
        return unmatched;
    }

    public List<Tile> getSelected(){
        return selected;
    }

    public boolean isValidTap(int position){
        Tile id = this.board.getTile(position);
        return unmatched.contains(id) && !(selected.contains(id));
    }

    public void touchMove(int position) {
        if (selected.size() <1){
            selected.add(this.board.getTile(position));
        }
        else{
            this.scoreCounter += 1;
            selected.add(this.board.getTile(position));
            if(selected.get(0).getId() == selected.get(1).getId()){
                this.unmatched.remove(selected.get(0));
                this.unmatched.remove(selected.get(1));
                this.selected.clear();
            }
            else{
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
     * Return the current board.
     *
     * @return the current board.
     */
    Board getBoard() {
        return board;
    }
    /**
     * Return the current score.
     *
     * @return the current score
     */
    int getScoreCounter() {
        return scoreCounter;
    }

    public boolean puzzleSolved() {
        return unmatched.isEmpty();
    }
}
