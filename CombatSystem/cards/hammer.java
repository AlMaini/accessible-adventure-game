package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class hammer extends Card {
    public String actionName;
    public String desc;

    public hammer(){
        super("hammer", "A heavy hammer \n \nSmashing this hammer takes 10 HP but deals 40 HP of damage");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        player.updateHP(10);
        enemy.updateHP(40);
        
    }
}