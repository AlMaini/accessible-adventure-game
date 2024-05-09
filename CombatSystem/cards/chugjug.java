package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class chugjug extends Card {
    public String actionName;
    public String desc;

    public chugjug(){
        super("chugjug", "The elixer of life \n \nHeals you to full");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        player.updateHP(-120);
        
    }
}