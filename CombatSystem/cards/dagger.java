package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class dagger extends Card {
    public String actionName;
    public String desc;

    public dagger(){
        super("dagger", "A pointy dagger \n \nStabbing with it causes 15 HP of damage to the enemy with a chance to deal 50 HP of damage");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        int random = (int) Math.floor(0 + Math.random() * (100 - 0));
        if(random < 15){
            enemy.updateHP(50);
        }
        else{
            enemy.updateHP(15);
        }
        
    }
}