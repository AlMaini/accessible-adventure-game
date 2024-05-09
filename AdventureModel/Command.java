package AdventureModel;


import views.AdventureGameView;

public interface Command {

    /**
     * Starts up a minigame.
     *
     *
     * @param model the adventure game model
     * @param view the adventure game view model
     */
    public void excecute(AdventureGame model, AdventureGameView view);
}
