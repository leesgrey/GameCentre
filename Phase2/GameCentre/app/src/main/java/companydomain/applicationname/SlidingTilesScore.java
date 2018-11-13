package companydomain.applicationname;

import java.io.Serializable;

public class SlidingTilesScore implements Comparable<SlidingTilesScore>, Serializable {

    /**
     * The username associated with this score.
     */
    private String username;

    /**
     * The score they achieved.
     */
    private int score;

    /**
     * The size of the game this score was achieved on.
     */
    private int size;

    /**
     * A new SlidingTilesScore.
     *
     * @param username the username associated with this score
     * @param score    the score they achieved
     * @param size     the size of the game this score was achieved on
     */
    SlidingTilesScore(String username, int score, int size) {
        this.username = username;
        this.score = score;
        this.size = size;
    }

    int getScore() {
        return score;
    }

    String getUsername() {
        return username;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int compareTo(SlidingTilesScore slidingTilesScore) {
        if (slidingTilesScore.getScore() < this.score) {
            return 1;
        } else if (slidingTilesScore.getScore() > this.score) {
            return -1;
        }
        return 0;
    }
}
