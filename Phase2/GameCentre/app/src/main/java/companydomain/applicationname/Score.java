package companydomain.applicationname;

import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {

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
     * A new Score.
     *
     * @param username the username associated with this score
     * @param score    the score they achieved
     * @param size     the size of the game this score was achieved on
     */
    Score(String username, int score, int size) {
        this.username = username;
        this.score = score;
        this.size = size;
    }

    /**
     * Returns the score value.
     *
     * @return the core
     */
    int getScore() {
        return score;
    }

    /**
     * Returns the username of the score.
     *
     * @return the username
     */
    String getUsername() {
        return username;
    }

    /**
     * Returns the size of the score's game.
     *
     * @return the game size
     */
    public int getSize() {
        return size;
    }

    @Override
    public int compareTo(Score o) {
        return this.score - o.score;
    }
}
