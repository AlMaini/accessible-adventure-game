package CombatSystem;

import java.util.ArrayList;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;
import CombatSystem.cards.Card;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * The IAction interface defines the contract for actions that can be performed
 * during a combat encounter between a player and an enemy in an adventure game.
 */
public interface IAction {

    /**
     * Executes the specified action on the player and enemy during a combat encounter.
     *
     * @param player The PlayerCreator object representing the player in the combat.
     * @param enemy  The EnemyCreator object representing the enemy in the combat.
     */
    public void doAction(PlayerCreator player, EnemyCreator enemy);

    /**
     * Gets the name of the action.
     *
     * @return A String representing the name of the action.
     */
    public String getName();

    /**
     * Gets the description of the action.
     *
     * @return A String representing the description of the action.
     */
    public String getDesc();

    /**
     * Gets the image associated with the action.
     *
     * @return An Image object representing the visual representation of the action.
     */
    public Image getImg();
    public VBox getFullCard();
}
