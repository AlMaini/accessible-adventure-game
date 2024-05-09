package AdventureModel;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import CombatSystem.cards.*;

/**
 * This class keeps track of the progress
 * of the player in the game.
 */
public class Player implements Serializable {
    /**
     * The current room that the player is located in.
     */
    private Room currentRoom;

    /**
     * The list of items that the player is carrying at the moment.
     */
    public ArrayList<AdventureObject> inventory;

    /**
     * A variation of player that holds Additional attributes.
     */
    public PlayerCreator interactivePlayer;

    public LootPool lootPool;

    /**
     * The General Adventure Game Player Constructor
     */
    public Player(Room currentRoom) {
        this.inventory = new ArrayList<AdventureObject>();
        this.currentRoom = currentRoom;
        this.interactivePlayer = null;
    }

    /**
     * The Adventure Game Player Constructor that creates an interactive player.
     */
    public Player(Room currentRoom, int difficulty){
        this.inventory = new ArrayList<AdventureObject>();
        this.currentRoom = currentRoom;
        this.interactivePlayer = new PlayerCreator(difficulty);
        //rank 0 - brick, potlid, stick, poison
        // rank 1 - sword, club, mace, heal
        // rank 2 - hammer,  dagger, syche, smoke
        // rank 3 - reaperSyche, excalibur, katana,chugjug
        ArrayList<Card> rank0 = new ArrayList<>(List.of(new shield(), new potlid(), new swingStick(), new poison()));
        ArrayList<Card> rank1 = new ArrayList<>(List.of(new swingSword(), new swingClub(), new mace(), new heal()));
        ArrayList<Card> rank2 = new ArrayList<>(List.of(new hammer(), new dagger(), new scythe(), new smoke()));
        ArrayList<Card> rank3 = new ArrayList<>(List.of(new reaperScythe(), new excalibur(), new katana(), new chugjug()));
        this.lootPool = new LootPool(interactivePlayer, rank0, rank1, rank2, rank3);
    }

    /**
     * This method adds an object into players inventory if the object is present in
     * the room and returns true. If the object is not present in the room, the method
     * returns false.
     *
     * @param object name of the object to pick up
     * @return true if picked up, false otherwise
     */
    public boolean takeObject(String object){
        if(this.currentRoom.checkIfObjectInRoom(object)){
            AdventureObject object1 = this.currentRoom.getObject(object);
            this.currentRoom.removeGameObject(object1);
            this.addToInventory(object1);
            return true;
        } else {
            return false;
        }
    }


    /**
     * checkIfObjectInInventory
     * __________________________
     * This method checks to see if an object is in a player's inventory.
     *
     * @param s the name of the object
     * @return true if object is in inventory, false otherwise
     */
    public boolean checkIfObjectInInventory(String s) {
        for(int i = 0; i<this.inventory.size();i++){
            if(this.inventory.get(i).getName().equals(s)) return true;
        }
        return false;
    }


    /**
     * This method drops an object in the players inventory and adds it to the room.
     * If the object is not in the inventory, the method does nothing.
     *
     * @param s name of the object to drop
     */
    public void dropObject(String s) {
        for(int i = 0; i<this.inventory.size();i++){
            if(this.inventory.get(i).getName().equals(s)) {
                this.currentRoom.addGameObject(this.inventory.get(i));
                this.inventory.remove(i);
            }
        }
    }

    /**
     * Setter method for the current room attribute.
     *
     * @param currentRoom The location of the player in the game.
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * This method adds an object to the inventory of the player.
     *
     * @param object Prop or object to be added to the inventory.
     */
    public void addToInventory(AdventureObject object) {
        this.inventory.add(object);
    }


    /**
     * Getter method for the current room attribute.
     *
     * @return current room of the player.
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Getter method for string representation of inventory.
     *
     * @return ArrayList of names of items that the player has.
     */
    public ArrayList<String> getInventory() {
        ArrayList<String> objects = new ArrayList<>();
        for(int i=0;i<this.inventory.size();i++){
            objects.add(this.inventory.get(i).getName());
        }
        return objects;
    }


}
