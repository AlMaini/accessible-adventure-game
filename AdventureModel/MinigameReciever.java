package AdventureModel;

import TTS.TextToSpeech;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.AdventureGameView;

import java.io.IOException;
import java.util.Random;

public class MinigameReciever {
    AdventureGame model;
    AdventureGameView view;

    Minigame1 game1 = null;
    Minigame2 game2 = null;
    int currGame = 0;

    TextToSpeech tts = new TextToSpeech();

    public MinigameReciever(AdventureGame model, AdventureGameView view) {
        this.model = model;
        this.view = view;
    }

    public MinigameReciever() {

    }

    public void startMinigame(){
        Random r = new Random();
        int randomNum = r.nextInt(0, 2); // Change this to three when i want to do the third minigame
        if (randomNum == 0) {
            game1 = new Minigame1();
            game1.excecute(this.model, this.view);
            currGame = 1;
        }
        else if (randomNum == 1){
            game2 = new Minigame2();
            game2.excecute(this.model, this.view);
            currGame = 2;
        }
    }

    public void play(String choice){
        if (currGame == 1) {
            if (choice.equals("1")) {
                view.formatText(game1.check("1"));
                tts.read(game1.check("1"));
                PauseTransition p = new PauseTransition(Duration.seconds(5));
                p.setOnFinished(e -> {
                    view.formatText("The only path you see in front of you is North.");
                    tts.read("The only path you see in front of you is North.");
                    view.num = 0;
                });
                p.play();

            } else if (choice.equals("2")) {
                view.formatText(game1.check("2"));
                tts.read(game1.check("2"));
                PauseTransition p = new PauseTransition(Duration.seconds(5));
                p.setOnFinished(e -> {
                    view.formatText("The only path you see in front of you is North.");
                    tts.read("The only path you see in front of you is North.");
                    view.num = 0;
                });
                p.play();
            }
        }
        else if (currGame == 2){
            if (choice.equals("1")) {
                view.formatText(game2.check("1"));
                tts.read(game2.check("1"));
                PauseTransition p = new PauseTransition(Duration.seconds(6));
                p.setOnFinished(e -> {
                    view.formatText("The only path you see in front of you is North.");
                    tts.read("The only path you see in front of you is North.");
                    view.num = 0;
                });
                p.play();

            } else if (choice.equals("2")) {
                view.formatText(game2.check("2"));
                tts.read(game2.check("2"));
                PauseTransition p = new PauseTransition(Duration.seconds(6));
                p.setOnFinished(e -> {
                    view.formatText("The only path you see in front of you is North.");
                    tts.read("The only path you see in front of you is North.");
                    view.num = 0;
                });
                p.play();
            }
            if (choice.equals("3")) {
                view.formatText(game2.check("3"));
                tts.read(game2.check("3"));
                PauseTransition p = new PauseTransition(Duration.seconds(6));
                p.setOnFinished(e -> {
                    view.formatText("The only path you see in front of you is North.");
                    tts.read("The only path you see in front of you is North.");
                    view.num = 0;
                });
                p.play();

            } else if (choice.equals("4")) {
                view.formatText(game2.check("4"));
                tts.read(game2.check("4"));
                PauseTransition p = new PauseTransition(Duration.seconds(6));
                p.setOnFinished(e -> {
                    view.formatText("The only path you see in front of you is North.");
                    tts.read("The only path you see in front of you is North.");
                    view.num = 0;
                });
                p.play();
            }
        }


    }

}
