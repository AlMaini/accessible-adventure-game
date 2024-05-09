package CombatSystem;

import java.util.ArrayList;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;
import CombatSystem.cards.Card;

/**
 * The ICombat interface outlines the contract for implementing combat functionality
 * within an adventure-based system. It defines methods that must be implemented by
 * classes utilizing this interface to manage combat actions, histories, and
 * interactions between players and enemies.
 */
public interface ICombat {

    /**
     * Returns an ArrayList of cards representing available actions for the combat.
     *
     * @return ArrayList<Card> - A list of available actions for the player in combat.
     */
    public ArrayList<Card> getActions();

    /**
     * Returns an ArrayList of strings representing the history of actions taken
     * during combat.
     *
     * @return ArrayList<String> - A list of strings representing the combat action history.
     */
    public ArrayList<String> getActionHistory();

    /**
     * Updates the action history with the provided action.
     *
     * @param action - A string representing the action taken during combat.
     */
    public void updateActionHistory(String action);

    /**
     * Updates the card history with the provided action.
     *
     * @param action - An object implementing the IAction interface representing
     *               the action taken during combat.
     */
    public void updateCardHistory(IAction action);

    /**
     * Initiates a combat with the specified enemy using the provided action card.
     *
     * @param enemy - An object of EnemyCreator representing the enemy in combat.
     * @param card  - An object implementing the IAction interface representing the
     *              action card played during combat.
     * @return boolean - True if the combat is successful, false otherwise.
     */
    public boolean fight(EnemyCreator enemy, IAction card);

    /**
     * Returns the player participating in the combat.
     *
     * @return PlayerCreator - An object representing the player in combat.
     */
    public PlayerCreator getPlayer();

    /**
     * Returns an ArrayList of IAction representing the history of action cards used
     * during combat.
     *
     * @return ArrayList<IAction> - A list of action cards used during combat.
     */
    public ArrayList<IAction> getCardHistory();

    /**
     * Updates the combat with a new set of action cards.
     *
     * @param cards - An ArrayList of Card representing the new set of action cards.
     */
    public void updateCards(ArrayList<Card> cards);
}

