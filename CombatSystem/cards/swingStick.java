package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class swingStick extends Card {
    public String actionName;
    public String desc;

    public swingStick(){
        super("stick", "A long stick \n \nSwinging it causes 20 HP of damage to the enemy");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        enemy.updateHP(20);
    }
}