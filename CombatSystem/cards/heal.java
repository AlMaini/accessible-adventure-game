package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class heal extends Card {
    public String actionName;
    public String desc;

    public heal(){
        super("heal", "A health potion \n \nDrinking this heals the player for 20 HP");
    }

    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        player.updateHP(-20);
    }
}