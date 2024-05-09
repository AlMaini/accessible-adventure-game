package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class scythe extends Card {
    public String actionName;
    public String desc;

    public scythe(){
        super("scythe", "A soul-stealing scythe\n \nAttacking an enemy with this damages the enemy for 25 HP, and heals the user for 25 HP");
    }

    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        enemy.updateHP(25);
        player.updateHP(-25);
        
    }
}