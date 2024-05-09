package leaderboard;

import java.util.*;

/**
 * Abstract Class Leaderboard.
 *
 * This is an abstract class and should not be initialized.
 * Many methods are not implemented.
 *
 * Organizes and stores data related to player statistics.
 */
public abstract class Leaderboard implements ILeaderboard{
    private HashMap<String, ArrayList<String>> entries;
    private int numEntries;

    /**
     * Return an ArrayList of String objects containing data for the leaderboard.
     */
    @Override
    public ArrayList<String> getLeaderboard() {
        throw new UnsupportedOperationException();
    }

    /**
     * Update the entries in the leaderboard.
     */
    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the number of entries in the leaderboard.
     *
     * @return int
     */
    @Override
    public int getNumEntries() {
        return this.numEntries;
    }

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
    @Override
    public HashMap<String, ArrayList<String>> getFullStats() {
        return entries;
    }
}
