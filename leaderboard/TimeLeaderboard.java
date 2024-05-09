package leaderboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class TimeLeaderboard extends Leaderboard {
    private HashMap<String, ArrayList<String>> entries;
    private int numEntries;

    /**
     * TextToSpeech Constructor
     *
     * Initializes a new TimeLeaderboard
     */
    public TimeLeaderboard() {
        this.entries = new HashMap<>();
        update();
    }

    /**
     * Return an ArrayList of String objects containing data for the leaderboard.
     * Sort the entries in ascending order based on player's time elapsed stat.
     *
     * @return ArrayList<String> an ArrayList of String objects containing the player
     * entries sorted by time elapsed in ascending order.
     */
    @Override
    public ArrayList<String> getLeaderboard() {
        ArrayList<String> leaderboard = new ArrayList<String>();
        for (String user: entries.keySet()) {
            String userData = "";
            userData += entries.get(user).get(3) + " ";
            userData += user;
            leaderboard.add(userData);
        }
        Collections.sort(leaderboard, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                int num1 = extractNumber(str1);
                int num2 = extractNumber(str2);

                // Ascending order
                return Integer.compare(num1, num2);
            }

            // Extract integer from the start of the string
            private int extractNumber(String str) {
                String[] parts = str.split(" ");
                return Integer.parseInt(parts[0]);
            }
        });
        return leaderboard;
    }

    /**
     * Update the entries in the leaderboard.
     */
    @Override
    public void update() {
        this.numEntries = 0;
        String statFile = "leaderboard" + File.separator + "leaderboard.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(statFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ArrayList<String> info = new ArrayList<String>();

                String difficulty = reader.readLine();
                String experience = reader.readLine();
                String date = reader.readLine();
                String time = reader.readLine();

                info.add(difficulty);
                info.add(experience);
                info.add(date);
                info.add(time);
                entries.put(line, info);
                this.numEntries++;

                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     *  - 2: omit this entry (unused)
     *  - 3: time elapsed
     *
     * @return HashMap a hashmap where the keys are the names of users, and the entries of the keys
     * are ArrayLists of String objects specifying the player's difficulty, XP, and time elapsed.
     */
    @Override
    public HashMap<String, ArrayList<String>> getFullStats() {
        return entries;
    }
}
