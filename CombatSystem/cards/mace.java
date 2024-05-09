package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class mace extends Card {
    public String actionName;
    public String desc;

    public mace(){
        super("mace", "A spiky mace \n \nSwinging it deals 20 HP of damage, and has a chance to do 10 more");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        enemy.updateHP(20);
        int random = (int) Math.floor(0 + Math.random() * (100 - 0));
        if(random < 15){
            enemy.updateHP(10);
        }
        
    }
}