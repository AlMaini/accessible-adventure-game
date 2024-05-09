package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class reaperScythe extends Card {
    public String actionName;
    public String desc;

    public reaperScythe(){
        super("reaperScythe", "The grim reaper's syche \n \nThis kills the enemy");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        enemy.updateHP(enemy.getHP());
        
    }
}