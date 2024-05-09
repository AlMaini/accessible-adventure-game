package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class poison extends Card {
    public String actionName;
    public String desc;

    public poison(){
        super("poison", "A stinky, bubbly poison\n \nAttacking an enemy with this damages the enemy for 10 HP");
    }

    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        enemy.updateHP(10);  
    }
}