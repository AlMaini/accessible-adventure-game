package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class excalibur extends Card {
    public String actionName;
    public String desc;

    public excalibur(){
        super("excalibur", "The mythical sword of King Arthur \n \nSwinging it deals 100 damage");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        enemy.updateHP(100);
        
    }
}