package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class smoke extends Card {
    public String actionName;
    public String desc;

    public smoke(){
        super("smoke", "A smoke bomb \n \nThrowing it dodges the next attack");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        player.updateHP(-20);
        
    }
}