package AdventureModel;

import java.io.Serializable;
import java.util.*;

/**
 * The PassageTable class keeps track of all the exits or locations
 * a player can go from a room.
 */
public class PassageTable implements Serializable {

    /**
     * A list of all the Passages that exists from a room.
     */
    public List<Passage> passageTable;

    /**
     * PassageTable constructor
     */
    public PassageTable() {
        this.passageTable = new ArrayList<>();
    }

    /**
     * This method adds an exit or passage
     * to the table.
     *
     * @param entry A Passage which keeps track of a
     *             particular exit from a room.
     */
    void addDirection(Passage entry) {
        passageTable.add(entry);
    }


    /**
     * Getter method for passage table.
     *
     * @return this.passageTable
     */
    public List<Passage> getDirection(){ return this.passageTable; }

    /**
     * Pretty print the table.
     */
    public void printTable(){
        for (Passage m: this.passageTable) {
            m.printPassage();
        }
    }

    /**
     * Determine if a given command is an option in the current table
     *
     * @param direction the option to assess
     * @return true if option exists in MotionTable, else false
     */
    public boolean optionExists(String direction) {
        for (Passage m : this.passageTable) {
            if (m.getDirection().equals(direction)) return true;
        }
        return false;
    }

}