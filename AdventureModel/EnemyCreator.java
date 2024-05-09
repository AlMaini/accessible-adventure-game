package AdventureModel;
import java.util.List;
public class EnemyCreator implements EnemyInterface{
    /**
     * The name of the Enemy
     */
    private String name;

    /**
     * The description of the Enemy
     */
    private String desc;

    /**
     * The enemy's current Hit points.
     */
    private int HP;

    /**
     * The enemy's maximum possible Hit points.
     */
    private int maxHP;

    /**
     * The amount of damage the enemy is capable of doing.
     */
    private int attack;

    /**
     * The XP the enemy drops after being defeated.
     */
    private int XP;


    public EnemyCreator(String name, String desc, int HP, int maxHP, int atk, int XP){
        this.name = name;
        this.desc = desc;
        this.HP = HP;
        this.maxHP = maxHP;
        this.attack = atk;
        this.XP = XP;
    }

    /**
     * Returns the name of the enemy
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the description of the enemy
     */
    @Override
    public String getDesc() {
        return this.desc;
    }

    /**
     * Returns the current number of Hit points that the enemy has.
     */
    @Override
    public int getHP() {
        return this.HP;
    }

    @Override
    public int getMaxHP(){
        return this.maxHP;
    }

    @Override
    public int getXP(){
        return this.XP;
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    /**
     * updates the HP given the value.
     * @param HP The amount of hp to add (-HP) or to subtract (+HP) from the players currHP.
     */
    @Override
    public void updateHP(int HP) {
        this.HP -= HP;
    }
}
