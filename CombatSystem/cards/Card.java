package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;
import CombatSystem.IAction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The Card class is an abstract class representing a generic card in the combat system.
 * It implements the IAction interface and provides common methods for card actions and UI representation.
 */
public abstract class Card implements IAction {

    // Instance variables
    private String actionName;
    private String desc;

    /**
     * Constructor for the Card class.
     *
     * @param actionName - A string representing the name of the card action.
     * @param desc       - A string representing the description of the card action.
     */
    public Card(String actionName, String desc) {
        this.actionName = actionName;
        this.desc = desc;
    }

    /**
     * Abstract method to perform the action of the card.
     *
     * @param player - An object of PlayerCreator representing the player in combat.
     * @param enemy  - An object of EnemyCreator representing the enemy in combat.
     */
    public abstract void doAction(PlayerCreator player, EnemyCreator enemy);

    /**
     * Retrieves the name of the card action.
     *
     * @return String - The name of the card action.
     */
    public String getName() {
        return actionName;
    }

    /**
     * Retrieves the description of the card action.
     *
     * @return String - The description of the card action.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Retrieves the image associated with the card.
     *
     * @return Image - The image associated with the card.
     */
    public Image getImg() {
        return new Image("Cards\\" + actionName + ".jpg");
    }

    /**
     * Generates a VBox containing the full card UI representation.
     *
     * @return VBox - The VBox containing the full card UI representation.
     */
    public VBox getFullCard() {
        // Outer VBox
        VBox cardBox = new VBox();

        // Inner VBox for content
        VBox contentVBox = new VBox();
        contentVBox.setSpacing(10);

        // Create a white border for the inner VBox
        Border innerBorder = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(5)));
        contentVBox.setBorder(innerBorder);

        // Padding for spacing between the border and content
        contentVBox.setPadding(new Insets(10, 10, 50, 10));

        ImageView copiedImageView = new ImageView();
        copiedImageView.setImage(getImg());
        copiedImageView.setPreserveRatio(true);
        copiedImageView.setFitWidth(200);

        Label longDescLabel = new Label();
        longDescLabel.setText(getDesc());
        longDescLabel.setStyle("-fx-text-fill: white;");
        longDescLabel.setFont(new Font("Arial", 16));
        longDescLabel.setAlignment(Pos.CENTER);
        longDescLabel.setTextOverrun(OverrunStyle.CLIP);
        longDescLabel.setWrapText(true);

        // Add nodes to the inner VBox
        contentVBox.getChildren().addAll(copiedImageView, longDescLabel);

        // Add the inner VBox to the outer VBox
        cardBox.getChildren().add(contentVBox);

        return cardBox;
    }
}
