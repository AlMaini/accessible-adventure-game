package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class swingSword extends Card {
    public String actionName;
    public String desc;

    public swingSword(){
        super("sword", "A sharp shiny sword \n \nSwinging it has chance of dealing between 20 HP of damage between 0 - 3 times to the enemy");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        // throw new UnsupportedOperationException("swingSword is not implemented");
        int random = (int) Math.floor(1 + Math.random() * (3 - 1));
        for(int i = 0; i < random; i++){
            enemy.updateHP(20);
        }
        
    }
}