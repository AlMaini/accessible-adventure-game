package AdventureModel;

import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerCreator implements PlayerInterface{
    /**
     * The name of the player
     */
    public String name;

    /**
     * The players current Hit points.
     */
    private int HP;

    /**
     * The difficulty of the player from 0-2 (accessible to hard)
     */
    private int difficulty;

    /**
     * The Experience Points that the player has.
     *
     */
    private int XP;

    /**
     * The Experience Points that the player has.
     * Each level has 10 experience points.
     * XP scaling for easy is x2, x1 for normal, and x2 for hard.
     * E.g: 10 times 1.2
     */
    private int nextLevelXP;

    /**
     * The current level the player is based on the Experience points (XP) that the player has.
     * E.g: lvl 0 is xp points 0-10, lvl 1 is xp points 10-20....
     */
    private int level;

    // TODO: SETUP HEALTH AND XP GAIN FOR THE DIFFICULTY LEVEL.
    public PlayerCreator(int difficulty){
        this.HP = 100;
        this.difficulty = difficulty;
        this.XP = 0;
        this.nextLevelXP = 10;
        this.level = 0;
    }

    /**
     * Returns the current name of the player.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the current number of Hit points that the player has.
     */
    @Override
    public int getHP() {
        return this.HP;
    }

    /**
     * Returns the current difficulty that the player is on.
     */
    @Override
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Returns the current XP that the player has gathered.
     */
    @Override
    public int getXP(){
        return this.XP;
    }

    /**
     * Returns the current level that the player is.
     * The player level updates each time the player reaches the current value at nextLevelXP
     */
    @Override
    public int getLevel(){
        return this.level;
    }

    /**
     * updates the HP given the value.
     * @param HP The amount of hp to add (-HP) or to subtract (+HP) from the players currHP.
     */
    @Override
    public void updateHP(int HP) {
        this.HP -= HP;
    }

    /**
     * updates the XP given the value.
     * @param EXP The amount of XP to add to the players Xp.
     */
    @Override
    public void updateXP(int EXP){
        this.XP += EXP;
        // check if the new xp of the player is >= the rank of nextLevelXP
        if (this.XP >= this.nextLevelXP){
            updateNextLevelXP();
        }
    }


    /**
     * updates the player's nextLevelXP depending on the selected difficulty.
     * DO NOT CALL THIS METHOD DIRECTLY UNLESS YOU KNOW WHAT YOU ARE DOING.
     */
    public void updateNextLevelXP(){
        double multiplier = 0.0;

        if (this.difficulty == 0){
            multiplier = 0.5;
        }
        else if (this.difficulty == 1){
            multiplier = 1.0;
        }
        else if (this.difficulty == 2){
            multiplier = 2.0;
        }

        int newXP = (int) Math.floor(this.nextLevelXP * multiplier);   // cast value to int instead of decimal values.
        this.nextLevelXP += newXP;  // update the next level XP
        this.level += 1;
    }

}