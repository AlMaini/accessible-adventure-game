package AdventureModel;

import TTS.TextToSpeech;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import views.AdventureGameView;

import java.util.Random;

public class Minigame2 implements Command{

    /**
     * The answer as a number in string form
     */
    String answerNum;

    /**
     * TextToSpeech object that facilitates text to speech.
     */
    TextToSpeech tts = new TextToSpeech();


    /**
     * The constructor for the riddle minigame.
     */
    public Minigame2() {
        this.answerNum = "3";
    }

    /**
     * Print Minigame instructions for the user
     */
    public String giveInstructions()
    {
        return """
                Riddle me this: Everyone has me but nobody can
                lose me. What am I?
                
                1 = Sight
                
                2 = Hunger
                
                3 = Shadow
                
                4 = Money
                """;
    }

    /**
     * Starts up a minigame.
     *
     *
     * @param model the adventure game model
     * @param view the adventure game view model
     */
    @Override
    public void excecute(AdventureGame model, AdventureGameView view) {
        view.updateScene("Standing in front of you is a hooded figure.");
        tts.read("Standing in front of you is a hooded figure.");
        view.updateItems();
        PauseTransition p = new PauseTransition(Duration.seconds(6));
        p.setOnFinished(e -> {
            view.formatText(giveInstructions());
            tts.read("Riddle me this: Everyone has me. but nobody can\n" +
                    "lose me. What am I?" +
                    "One. For Sight. Two. for Hunger. Three. For Shadow. Four. for Money");
        });
        p.play();

    }


    /**
     *
     * @param choice users input for minigame.
     * @return Closing text for the minigame based on choice.
     */
    public String check(String choice){
        if (choice.equals(answerNum)){
            return "You have proven your intelligence." + "\n" +
                    "Safe travels stranger.";
        }
        else {
            return "Seems you are not as smart as I thought." + "\n" +
                    "Safe travels stranger.";
        }
    }

}
