package leaderboard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface ILeaderboard.
 *
 * Organizes and stores data related to player statistics.
 */
public interface ILeaderboard {
    /**
     * Return an ArrayList of String objects containing data for the leaderboard.
     */
    ArrayList<String> getLeaderboard();

    /**
     * Update the entries in the leaderboard.
     */
    void update();

    /**
     * Return the number of entries in the leaderboard.
     *
     * @return int
     */
    int getNumEntries();

    /**
     * Return the HashMap of the stats for each recorded player.
     * When accessing entries.get(<user>):
     *  - 0: difficulty
     *  - 1: xp
     *  - 2: date of play
     *  - 3: time elapsed
     *
     * @return HashMap<String, ArrayList<String>>
     */
    HashMap<String, ArrayList<String>> getFullStats();

}
