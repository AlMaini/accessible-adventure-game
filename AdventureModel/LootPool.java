package AdventureModel;
import CombatSystem.cards.Card;
import CombatSystem.cards.poison;
import CombatSystem.cards.potlid;
import CombatSystem.cards.shield;
import CombatSystem.cards.swingStick;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class that stores the lootpool for the current character, and returns rewards for the player.
 */
public class LootPool {
    // Lists of cards from rank 0-3
    private ArrayList<Card> lvl0Cards;
    private ArrayList<Card> lvl1Cards;
    private ArrayList<Card> lvl2Cards;
    private ArrayList<Card> lvl3Cards;
    private PlayerCreator currentPlayer;

    public LootPool(PlayerCreator currPlayer, ArrayList<Card> rank0, ArrayList<Card> rank1, ArrayList<Card> rank2, ArrayList<Card> rank3){
        this.lvl0Cards = rank0;
        this.lvl1Cards = rank1;
        this.lvl2Cards = rank2;
        this.lvl3Cards = rank3;
        this.currentPlayer = currPlayer;
    }

    /** Returns the rewards for the current player based on their XP and level.
     */
     public ArrayList<Card> getRewards(){
         // need to check the XP ranges
         // Maybe just add an attribute to keep track of it. lvl 0 is 0-10, 1 is 10-....

         // First make an empty array list.
         ArrayList<Card> rewardCards = new ArrayList<>();

         // now check the level of the player.
         if (currentPlayer.getLevel() == 0){
             // then pick a random number 3 times and give them that reward
             int randomIndex1 = ThreadLocalRandom.current().nextInt(0, lvl0Cards.size() + 1);        // generates a random number without needing to define an instance of the random class
             int randomIndex2 = ThreadLocalRandom.current().nextInt(0, lvl0Cards.size() + 1);
             int randomIndex3 = ThreadLocalRandom.current().nextInt(0, lvl0Cards.size() + 1);

             // add all the cards to the rewards list.
             rewardCards.add(lvl0Cards.get(randomIndex1));
             rewardCards.add(lvl0Cards.get(randomIndex2));
             rewardCards.add(lvl0Cards.get(randomIndex3));
         }

         else if (currentPlayer.getLevel() == 1){
             // Since each level includes all previous cards as well, merge the appropriate lists below:
             ArrayList<Card> allLvlCards = new ArrayList<>(lvl0Cards);
             allLvlCards.addAll(lvl1Cards);

             // All the same index fetching from before
             int randomIndex1 = ThreadLocalRandom.current().nextInt(0, allLvlCards.size() + 1);        // generates a random number without needing to define an instance of the random class
             int randomIndex2 = ThreadLocalRandom.current().nextInt(0, allLvlCards.size() + 1);
             int randomIndex3 = ThreadLocalRandom.current().nextInt(0, allLvlCards.size() + 1);

             rewardCards.add(allLvlCards.get(randomIndex1));
             rewardCards.add(allLvlCards.get(randomIndex2));
             rewardCards.add(allLvlCards.get(randomIndex3));
         }
         else if (currentPlayer.getLevel() == 2){
             ArrayList<Card> allLvlCards = new ArrayList<>(lvl0Cards);
             allLvlCards.addAll(lvl1Cards);
             allLvlCards.addAll(lvl2Cards);

             // All the same index fetching from before
             int randomIndex1 = ThreadLocalRandom.current().nextInt(0, allLvlCards.size() + 1);        // generates a random number without needing to define an instance of the random class
             int randomIndex2 = ThreadLocalRandom.current().nextInt(0, allLvlCards.size() + 1);
             int randomIndex3 = ThreadLocalRandom.current().nextInt(0, allLvlCards.size() + 1);

             rewardCards.add(allLvlCards.get(randomIndex1));
             rewardCards.add(allLvlCards.get(randomIndex2));
             rewardCards.add(allLvlCards.get(randomIndex3));
         }
         else if (currentPlayer.getLevel() >= 3){
             ArrayList<Card> allLvlCards = new ArrayList<>(lvl0Cards);
             allLvlCards.addAll(lvl1Cards);
             allLvlCards.addAll(lvl2Cards);
             allLvlCards.addAll(lvl3Cards);

             // All the same index fetching from before
             int randomIndex1 = ThreadLocalRandom.current().nextInt(0, allLvlCards.size() + 1);        // generates a random number without needing to define an instance of the random class
             int randomIndex2 = ThreadLocalRandom.current().nextInt(0, allLvlCards.size() + 1);
             int randomIndex3 = ThreadLocalRandom.current().nextInt(0, allLvlCards.size() + 1);

             rewardCards.add(allLvlCards.get(randomIndex1));
             rewardCards.add(allLvlCards.get(randomIndex2));
             rewardCards.add(allLvlCards.get(randomIndex3));
         }
         return rewardCards;
    }
}
