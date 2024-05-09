package CombatSystem.cards;

import AdventureModel.EnemyCreator;
import AdventureModel.PlayerCreator;

public class shield extends Card {
    public String actionName;
    public String desc;

    public shield(){
        super("shield", "A sturdy shield \n \nBlocks 10 damage");
    }
    @Override
    public void doAction(PlayerCreator player, EnemyCreator enemy){
        player.updateHP(-10);
        
    }
}