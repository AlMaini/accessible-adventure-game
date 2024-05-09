package leaderboard;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardTest {
    /*
     * NOTE: THESE TESTS ONLY WORK WITH A SPECIFIC <leaderboard.txt> FILE -- THE FILE HAS ENTRIES FOR
     * THREE PLAYERS, <user1> <user2> AND <user3>.
     *
     * THESE TESTS WILL FAIL OTHERWISE!
     */
    @Test
    void processFileTest() throws IOException {
        Leaderboard board = new TimeLeaderboard();
        assertEquals(board.getNumEntries(), 3);
    }

    @Test
    void xpLeaderboardReturnTest() {
        Leaderboard board = new XPLeaderboard();
        assertEquals(board.getLeaderboard().size(), 3);
        ArrayList<String> result = new ArrayList<String>();
        result.add("44 user3");
        result.add("3 user1");
        result.add("1 user2");

        assertEquals(board.getLeaderboard(), result);
    }

    @Test
    void timeLeaderboardReturnTest() {
        Leaderboard board = new TimeLeaderboard();
        assertEquals(board.getLeaderboard().size(), 3);
        ArrayList<String> result = new ArrayList<String>();
        result.add("1 user2");
        result.add("3 user3");
        result.add("44 user1");
        assertEquals(board.getLeaderboard(), result);
        assertEquals(board.getFullStats().size(), 3);
    }
}
