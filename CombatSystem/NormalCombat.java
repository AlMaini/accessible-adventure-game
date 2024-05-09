package CombatSystem;

import java.util.ArrayList;
import java.util.List;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;
import CombatSystem.cards.*;

/**
 * The NormalCombat class implements the ICombat interface and represents a
 * standard level of combat in an adventure-based system. It includes methods to
 * handle combat actions, player and enemy interactions, as well as action and
 * card history.
 */
public class NormalCombat implements ICombat {

    // Instance variables
    private ArrayList<Card> actions;
    private PlayerCreator player;
    private ArrayList<String> actionHistory;
    private ArrayList<IAction> cardHistory;

    /**
     * Constructor for NormalCombat class with a specified player.
     *
     * @param player - An object of PlayerCreator representing the player in combat.
     */
    public NormalCombat(PlayerCreator player) {
        this.actions = new ArrayList<>(List.of(new swingStick(), new heal()));
        this.player = player;
        this.actionHistory = new ArrayList<>();
        this.cardHistory = new ArrayList<>();
    }

    /**
     * Default constructor for NormalCombat class with no specified player.
     */
    public NormalCombat() {
        this.actions = new ArrayList<>(List.of(new swingStick(), new heal()));
        this.player = null;
        this.actionHistory = new ArrayList<>();
        this.cardHistory = new ArrayList<>();
    }

    /**
     * Retrieves the available actions for the combat.
     *
     * @return ArrayList<Card> - A list of available actions for the player in combat.
     */
    public ArrayList<Card> getActions() {
        return actions;
    }

    /**
     * Initiates a combat with the specified enemy using the provided action card.
     *
     * @param enemy - An object of EnemyCreator representing the enemy in combat.
     * @param card  - An object implementing the IAction interface representing
     *              the action card played during combat.
     * @return boolean - True if the combat is successful, false otherwise.
     */
    public boolean fight(EnemyCreator enemy, IAction card) {
        card.doAction(player, enemy);
        this.actions.remove(card);

        if (enemy.getHP() > 0) {
            // Enemy turn
            player.updateHP(enemy.getAttack());

            if (player.getHP() <= 0 || this.actions.isEmpty()) {
                // Player lost
                return true;
            } else {
                return false; // Continue fighting
            }
        } else {
            // Player won
            this.player.updateXP(5);
            return true;
        }
    }

    /**
     * Updates the action history with the provided action.
     *
     * @param action - A string representing the action taken during combat.
     */
    public void updateActionHistory(String action) {
        // Add it to the start of the list, so it's the most recent action
        this.actionHistory.add(0, action);
    }

    /**
     * Retrieves the history of actions taken during combat.
     *
     * @return ArrayList<String> - A list of strings representing the combat action history.
     */
    public ArrayList<String> getActionHistory() {
        return this.actionHistory;
    }

    /**
     * Retrieves the player participating in the combat.
     *
     * @return PlayerCreator - An object representing the player in combat.
     */
    public PlayerCreator getPlayer() {
        return this.player;
    }

    /**
     * Updates the card history with the provided action.
     *
     * @param action - An object implementing the IAction interface representing
     *               the action taken during combat.
     */
    public void updateCardHistory(IAction action) {
        // Add it to the start of the list, so it's the most recent action
        this.cardHistory.add(0, action);
    }

    /**
     * Retrieves the history of action cards used during combat.
     *
     * @return ArrayList<IAction> - A list of action cards used during combat.
     */
    public ArrayList<IAction> getCardHistory() {
        return this.cardHistory;
    }

    /**
     * Updates the combat with a new set of action cards.
     *
     * @param cards - An ArrayList of Card representing the new set of action cards.
     */
    public void updateCards(ArrayList<Card> cards) {
        this.actions.addAll(cards);
    }
}
