package AdventureModel;

public interface EnemyInterface {

    public String getName();
    public String getDesc();
    public int getHP();
    public int getMaxHP();
    public int getXP();
    public int getAttack();

    /**
     * updates the HP given the value.
     * @param HP The amount of hp to add (-HP) or to subtract (+HP) from the players currHP.
     */
    public void updateHP(int HP);
}
