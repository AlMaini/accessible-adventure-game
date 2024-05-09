package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class swingClub extends Card {
    public String actionName;
    public String desc;

    public swingClub(){
        super("club", "A large club \n \nSwinging it has chance of dealing between 16 - 24 HP of damage to the enemy");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        //throw new UnsupportedOperationException("swingClub is not implemented");
        int random = (int) Math.floor(0.8 + Math.random() * (1.2 - 0.8));
        enemy.updateHP(20 * random);
    }
}
