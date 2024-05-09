package AdventureModel;
import TTS.TextToSpeech;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import views.AdventureGameView;

import java.util.Random;


public class Minigame1 implements Command{
    /**
     * The answer in string form
     */
    String answer;

    /**
     * The answer as a number in string form
     */
    String answerNum;

    /**
     * TextToSpeech object that facilitates text to speech.
     */
    TextToSpeech tts = new TextToSpeech();

    /**
     * The constructor for the heads or tails minigame.
     */
    public Minigame1() {
        Random r = new Random();
        int randomNum = r.nextInt(0, 2);
        if (randomNum == 0) {
            this.answer = "HEADS";
            this.answerNum = "1";
        } else {
            this.answer = "TAILS";
            this.answerNum = "2";
        }
    }


    /**
     * Return Minigame instructions for the user
     */
    public String giveInstructions()
    {
        return """
                HELLO TRAVELLER, LET US PLAY A SIMPLE GAME OF HEADS OR TAILS
                I SHALL FLIP A COIN AND YOU MUST CHOOSE EITHER:
                
                1 = HEADS
                
                2 = TAILS
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
        view.updateScene("You stumble into a funny looking creature. The creature approaches you.");
        tts.read("You stumble into a funny looking creature. The creature approaches you.");
        view.updateItems();
        PauseTransition p = new PauseTransition(Duration.seconds(6));
        p.setOnFinished(e -> {
            view.formatText(giveInstructions());
            tts.read("HELLO TRAVELLER, LET US PLAY A SIMPLE GAME OF HEADS " +
                    "OR TAILS. I SHALL FLIP A COIN AND YOU MUST CHOOSE EITHER." +
                    "One. For Heads. Or two. for tails.");
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
            return "HO HO HO. IT LANDED ON " + this.answer + ".\n" +
                        "SEEMS LIKE FORTUNE IS ON YOUR SIDE." + "\n" +
                        "UNTIL WE MEET AGAIN.";
        }
        else {
            return "HE HE HE IT LANDED ON " + this.answer + ".\n" +
                    "BETTER LUCK NEXT TIME.";
        }
    }


}
