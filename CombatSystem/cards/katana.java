package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class katana extends Card {
    public String actionName;
    public String desc;

    public katana(){
        super("katana", "A razor sharp katana \n \nSwinging it deals 25 HP of damage, but if the enemy is 50 HP or under it kills it");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        if(enemy.getHP() <= 50){
            enemy.updateHP(enemy.getHP());
        }
        else{
            enemy.updateHP(25);
        }
        
        
    }
}