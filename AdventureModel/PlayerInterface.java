package AdventureModel;

public interface PlayerInterface {

    public String getName();
    public int getHP();
    public int getDifficulty();
    public int getXP();
    public int getLevel();
    public void updateHP(int HP);
    public void updateXP(int EXP);
    public void updateNextLevelXP();

}
