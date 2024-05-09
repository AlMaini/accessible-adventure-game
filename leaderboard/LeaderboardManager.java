package leaderboard;

import leaderboard.*;

import java.util.ArrayList;

/**
 * Class LeaderboardManager.
 *
 * Manages Leaderboard objects and notifies all observing Leaderboard objects when to update.
 */
public class LeaderboardManager {
    ArrayList<Leaderboard> leaderboards;

    /**
     * LeaderboardManager Constructor.
     *
     * Initializes a new LeaderboardManager object with an empty ArrayList for Leaderboard observers to
     * be registered.
     */
    public LeaderboardManager() {
        this.leaderboards = new ArrayList<Leaderboard>();
    }

    /**
     * LeaderboardManager Constructor (with specified Leaderboard objects).
     *
     * Initializes a new LeaderboardManager object with a given ArrayList containing Leaderboard observers to
     * be registered.
     *
     * @param observers an ArrayList containing Leaderboard observers to be registered.
     */
    public LeaderboardManager(ArrayList<Leaderboard> observers) {
        this.leaderboards = observers;
    }

    /**
     * Registers a Leaderboard object as a new observer to this LeaderboardManager.
     */
    public void addLeaderboard(Leaderboard leaderboard) {
        this.leaderboards.add(leaderboard);
    }

    /**
     * Notifies all Leaderboard objects that are observing this manager to update their entries.
     */
    public void update() {
        for (Leaderboard leaderboard: leaderboards) {
            leaderboard.update();
        }
    }
}
