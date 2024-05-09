package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class potlid extends Card {
    public String actionName;
    public String desc;

    public potlid(){
        super("potlid", "It's just a potlid, man \n \nThrowing it deals 5 damage");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        enemy.updateHP(5);
        
    }
}