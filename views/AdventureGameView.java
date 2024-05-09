package views;

import AdventureModel.*;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import CombatSystem.EasyCombat;
import CombatSystem.HardCombat;
import CombatSystem.IAction;
import CombatSystem.ICombat;
import CombatSystem.NormalCombat;
import CombatSystem.cards.Card;
import CombatSystem.*;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;
import leaderboard.Leaderboard;
import leaderboard.LeaderboardManager;
import leaderboard.TimeLeaderboard;
import leaderboard.XPLeaderboard;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import TTS.TextToSpeech;


/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 */
public class AdventureGameView {

    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton, leaderboardButton; //buttons
    Boolean helpToggle = false; //is help on display?
    Boolean leaderboardToggle = false; //is leaderboard on display?
    String leaderboardPath = "leaderboard" + File.separator + "leaderboard.txt";
    LeaderboardManager manageBoards = new LeaderboardManager(); //Manager that updates all leaderboards.
    TimeLeaderboard boardTime = new TimeLeaderboard(); //Leaderboard sorted by time; observes changes pushed by manager.
    XPLeaderboard boardXP = new XPLeaderboard(); //Leaderboard sorted by XP; observes changes pushed by manager.
    Leaderboard thisBoard = boardXP; //Default selected leaderboard
    ICombat combat; // the combat system

    GridPane gridPane = new GridPane(); //to hold images and buttons
    GridPane combatGridPane = new GridPane(); //to hold combat UI
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input

    // NEW THING ADDED HERE FROM LORENZ - FRIDAY DEC 1 2:33
    public int num = 0;

    MinigameReciever currentMinigame =  new MinigameReciever();
    // NEW THING ADDED HERE FROM LORENZ - FRIDAY DEC 1 2:33
    TextToSpeech tts = new TextToSpeech();
    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    private int secondsPassed; //keep track of elapsed time for the timer - Carl

    private int difficulty; // The difficulty provided by the player.

    private String playerUsername; // The username (with no whitespaces) provided by the player

    private Scene curScene;


    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) throws UnknownHostException {
        this.model = model;
        this.stage = stage;
        this.combat = new HardCombat(new PlayerCreator(0));
        this.difficulty = 0;
        initCombatUI();
        initCharSelect();
    }

    private void initCombatUI() {
        combatGridPane.setPadding(new Insets(20));
        combatGridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        row3.setVgrow(Priority.SOMETIMES);

        combatGridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
        combatGridPane.getRowConstraints().addAll( row1 , row2 , row1 );
    }

    /**
     * Initialize the UI for Character select
     * NOTE: In order for the images to work correctly, "THE_GAME" must be the root file of this project.
     */
    public void initCharSelect(){
        File EimgFile = new File("views\\DifficultyImages\\Easy.jpg");
        Image Eimg = new Image(EimgFile.toURI().toString());
        ImageView EimgView = new ImageView(Eimg);
        EimgView.setFitWidth(200);    // change the width of the image that it must fit into
        EimgView.setPreserveRatio(true); // preserves aspect ratio when being resized

        File NimgFile = new File("views\\DifficultyImages\\Medium.jpg");
        Image Nimg = new Image(NimgFile.toURI().toString());
        ImageView NimgView = new ImageView(Nimg);
        NimgView.setFitWidth(200);    // change the width of the image that it must fit into
        NimgView.setPreserveRatio(true); // preserves aspect ratio when being resized

        File HimgFile = new File("views\\DifficultyImages\\Hard.jpg");
        Image Himg = new Image(HimgFile.toURI().toString());
        ImageView HimgView = new ImageView(Himg);
        HimgView.setFitWidth(200);    // change the width of the image that it must fit into
        HimgView.setPreserveRatio(true); // preserves aspect ratio when being resized

        // Setting up the easy Button
        Button easyButton = new Button("Easy");
        // setting an action for a button press/enter
        easyButton.setOnAction(e -> {
            // Change player difficulty
            model.player = new Player(model.getRooms().get(1), 0);
            this.combat = new EasyCombat(model.player.interactivePlayer);
            handleUserName();
        });
        easyButton.setOnMouseEntered(e -> {
            tts.read("Easy.");
            // Increases button size on mouse hover
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), easyButton);
            scaleTransition.setToX(1.2);
            scaleTransition.setToY(1.2);
            scaleTransition.play();
        });

        easyButton.setOnMouseExited(e -> {
            // Otherwise, decrease button size on mouse hover
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), easyButton);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });
        easyButton.setGraphic(EimgView);
        easyButton.setContentDisplay(ContentDisplay.TOP);
        easyButton.setAccessibleText("Easy Mode");
        easyButton.setFont(new Font("Times New Roman", 18));
        easyButton.setMaxWidth(100);
        makeButtonAccessible(easyButton, "Easy Mode", "This button takes selects Easy Mode.", "This button selects Easy Mode. Click it to set the difficulty of the game to easy.");

        // Setting up the normal-mode button
        Button normalButton = new Button("Normal");
        // setting an action for a button press/enter
        normalButton.setOnAction(e -> {
            model.player = new Player(model.getRooms().get(1), 1);
            this.combat = new NormalCombat(model.player.interactivePlayer);
            handleUserName();
        });

        normalButton.setOnMouseEntered(e -> {
            tts.read("Normal.");
            // Increases button size on mouse hover
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), normalButton);
            scaleTransition.setToX(1.2);
            scaleTransition.setToY(1.2);
            scaleTransition.play();
        });

        normalButton.setOnMouseExited(e -> {
            // Otherwise, decrease button size on mouse hover
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), normalButton);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });
        normalButton.setGraphic(NimgView);
        normalButton.setContentDisplay(ContentDisplay.TOP);
        normalButton.setAccessibleText("Normal Mode");
        normalButton.setFont(new Font("Times New Roman", 18));
        normalButton.setMaxWidth(100);
        makeButtonAccessible(normalButton, "Normal Mode", "This button takes selects Normal Mode.", "This button selects Normal Mode. Click it to set the difficulty of the game to normal.");

        // Setting up the hard-mode button
        Button hardButton = new Button("Hard");
        // setting an action for a button press/enter
        hardButton.setOnAction(e -> {
            // Change player difficulty
            model.player = new Player(model.getRooms().get(1), 2);
            this.combat = new HardCombat(model.player.interactivePlayer);
            handleUserName();
        });

        hardButton.setOnMouseEntered(e -> {
            tts.read("Hard.");
            // Increases button size on mouse hover
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), hardButton);
            scaleTransition.setToX(1.2);
            scaleTransition.setToY(1.2);
            scaleTransition.play();
        });

        hardButton.setOnMouseExited(e -> {
            // Otherwise, decrease button size on mouse hover
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), hardButton);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });
        hardButton.setGraphic(HimgView);
        hardButton.setContentDisplay(ContentDisplay.TOP);
        hardButton.setAccessibleText("Hard Mode");
        hardButton.setFont(new Font("Times New Roman", 18));
        //hardButton.setMaxWidth(100);
        makeButtonAccessible(hardButton, "Hard Mode", "This button takes selects Hard Mode.", "This button selects Hard Mode. Click it to set the difficulty of the game to Hard.");

        HBox difficultySelect = new HBox();         // Setting up HBox
        difficultySelect.getChildren().addAll(easyButton, normalButton, hardButton);
        difficultySelect.setSpacing(20);
        difficultySelect.setAlignment(Pos.CENTER);
        //HBox.setHgrow(easyButton, Priority.ALWAYS);

        Label charSelectLabel =  new Label("Select your difficulty.");
        charSelectLabel.setAlignment(Pos.TOP_CENTER);
        charSelectLabel.setStyle("-fx-text-fill: white;");
        charSelectLabel.setFont(new Font("Times New Roman", 35));

        // Read text on hover
        charSelectLabel.setOnMouseEntered(mouseEvent -> {
            tts.read(charSelectLabel.getText());
        });

        // Setting up the VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(25));
        textEntry.getChildren().addAll(charSelectLabel);
        textEntry.setSpacing(50);                   // Spacing between the children in the Vbox
        textEntry.setAlignment(Pos.CENTER);
        textEntry.getChildren().add(difficultySelect);

        // Render everything
        var scene = new Scene(textEntry, 1000, 800);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
        tts.read("Select your difficulty.");
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {
        // setting up the stage
        this.stage.setTitle("Group 36's Adventure Game");

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        row3.setVgrow(Priority.SOMETIMES);

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
        gridPane.getRowConstraints().addAll( row1 , row2 , row1 );

        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();
        saveButton.setOnMouseEntered(e -> {
            tts.read(saveButton.getText());
        });

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();
        loadButton.setOnMouseEntered(e -> {
            tts.read(loadButton.getText());
        });

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 150, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();
        helpButton.setOnMouseEntered(e -> {
            tts.read(helpButton.getText());
        });

        leaderboardButton = new Button("Leaderboard");
        leaderboardButton.setId("Leaderboard");
        customizeButton(leaderboardButton, 150, 50);
        makeButtonAccessible(leaderboardButton, "Leaderboard Button", "This button displays the leaderboard.",
                "This button displays the leaderboard standings. Left click it to view how different players did in their playthroughs." +
                        " Right click it to change how the leaderboard is sorted.");
        addLeaderboardEvent();
        leaderboardButton.setOnMouseEntered(e -> {
            tts.read(leaderboardButton.getText());
        });

        // Add leaderboards to manager.
        manageBoards.addLeaderboard(boardTime);
        manageBoards.addLeaderboard(boardXP);
        manageBoards.update();

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(saveButton, helpButton, leaderboardButton, loadButton);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        inputTextField = new TextField();
        inputTextField.setFont(new Font("Arial", 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        //labels for inventory and room items
        Label objLabel =  new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: white;");
        objLabel.setFont(new Font("Arial", 16));

        Label invLabel =  new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: white;");
        invLabel.setFont(new Font("Arial", 16));

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        Label commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle("-fx-text-fill: white;");
        commandLabel.setFont(new Font("Arial", 16));

        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add(textEntry, 0, 2, 3, 1 );

        // Live timer for the player to see how long they take
        Label timerLabel = new Label("Elapsed Time: 0 seconds");
        timerLabel.setStyle("-fx-text-fill: white;");
        timerLabel.setFont(new Font("Arial", 16));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                secondsPassed++;
                timerLabel.setText("Elapsed Time: " + secondsPassed + " seconds");
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        StackPane timer = new StackPane();
        timer.getChildren().add(timerLabel);
        gridPane.add(timer, 1, 3);

        // Render everything
        var scene = new Scene(gridPane , 1000, 800);
        scene.setFill(Color.BLACK);
        this.curScene = scene;
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }


    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", 16));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute
     *
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        inputTextField.setOnKeyPressed(press -> {
            // NEW THING ADDED HERE FROM LORENZ - FRIDAY DEC 1 2:33
            if (press.getCode() == KeyCode.ENTER && num == 1) { // ENTER key-press
                submitEvent2(inputTextField.getText().strip(), currentMinigame);
                press.consume();
                inputTextField.setText("");
            }
            // NEW THING ADDED HERE FROM LORENZ - FRIDAY DEC 1 2:33
            else if (press.getCode() == KeyCode.ENTER) { // ENTER key-press
                submitEvent(inputTextField.getText().strip());
                press.consume();
                inputTextField.setText("");
            } else if (press.getCode() == KeyCode.TAB) { // TAB key-press
                helpButton.requestFocus();
            }
        });
    }

    private void handleUserName(){
        // start a new scene with a text field and a regular VBox.
        TextField usernameTextField = new TextField();
        usernameTextField.setFont(new Font("Arial", 16));
        usernameTextField.setFocusTraversable(true);       // TODO: Do this for the player select text
        usernameTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        usernameTextField.setAccessibleRoleDescription("Username Entry Box");
        usernameTextField.setAccessibleText("Enter your desired Username in this box.");
        usernameTextField.setAccessibleHelp("This is the area in which you can enter the desired username to play the game with.  Pick a name and hit return to continue.");

        usernameTextField.setOnKeyPressed(press -> {
            if (press.getCode() == KeyCode.ENTER) { // ENTER key-press
                // send the textfield to player_username
                model.player.interactivePlayer.name = usernameTextField.getText().strip().replaceAll("\\s+","");
                press.consume();
                intiUI();
            } else if (press.getCode() == KeyCode.TAB) { // TAB key-press
                usernameTextField.requestFocus();
            }
        });

        Label usernameLabel =  new Label("Enter a Username");
        usernameLabel.setAlignment(Pos.TOP_CENTER);
        usernameLabel.setStyle("-fx-text-fill: white;");
        usernameLabel.setFont(new Font("Times New Roman", 35));

        // Setting up the VBox
        VBox textInput = new VBox();
        textInput.setStyle("-fx-background-color: #000000;");
        textInput.setPadding(new Insets(25));
        textInput.getChildren().add(usernameLabel);
        textInput.setSpacing(50);                   // Spacing between the children in the Vbox
        textInput.setAlignment(Pos.CENTER);
        textInput.getChildren().add(usernameTextField);

        var scene = new Scene(textInput, 1000, 800);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
        tts.read(usernameLabel.getText());

    }
    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("FIGHT")) {
            combatGridPane = new GridPane();
            initCombatUI();
            var combatScene = new Scene(combatGridPane, 1000, 800);
            //this.curScene = this.stage.getScene();
            this.stage.setScene(combatScene);
            this.stage.setResizable(false);
            this.stage.show();
            EnemyCreator enemy = new EnemyCreator("slime", "smn", 100, 100, 10, 10);  // TODO: ADD
            updateFightScene("Enemies\\enemyImages\\" + enemy.getName() + ".jpg", enemy);
            return;
        }

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP") && !output.equals("MINIGAME"))) {
            updateScene(output);
            updateItems();
            objectsInInventory.setDisable(false);
            objectsInRoom.setDisable(false);
            inputTextField.setDisable(false);
        } else if (output.equals("GAME OVER")) {
            inputTextField.setDisable(true);
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            //write code here to handle "FORCED" events!
            //Your code will need to display the image in the
            //current room and pause, then transition to
            //the forced room.
            objectsInInventory.setDisable(true);
            objectsInRoom.setDisable(true);
            inputTextField.setDisable(true);
            updateScene("");
            updateItems();
            PauseTransition p = new PauseTransition(Duration.seconds(6));
            p.setOnFinished(e -> {
                submitEvent("FORCED");
            });
            p.play();
        }
        // NEW THING ADDED HERE FROM LORENZ - FRIDAY DEC 1 2:33
        else if (output.equals("MINIGAME")){
            currentMinigame = new MinigameReciever(this.model, this);
            currentMinigame.startMinigame();
            num = 1;
        }
        // NEW THING ADDED HERE FROM LORENZ - FRIDAY DEC 1 2:33
        else if (output.equals("FIGHT")) {
            combatGridPane = new GridPane();
            initCombatUI();
            var combatScene = new Scene(combatGridPane, 1000, 800);
            //this.curScene = this.stage.getScene();
            this.stage.setScene(combatScene);
            this.stage.setResizable(false);
            this.stage.show();
            EnemyCreator enemy = new EnemyCreator("slime", "smn", 100, 100, 10, 10);  // TODO: ADD
            updateFightScene("Enemies\\enemyImages\\" + enemy.getName() + ".jpg", enemy);
            return;
        }
    }

    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent2(String text, MinigameReciever game) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("1")) {
            game.play("1");
        }
        else if (text.equalsIgnoreCase("2")) {
            game.play("2");
        }
        if (text.equalsIgnoreCase("3")) {
            game.play("3");
        }
        if (text.equalsIgnoreCase("4")) {
            game.play("4");
        }
    }



    /**
     * showCommands
     * __________________________
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the
     * current room.
     */
    private void showCommands() {
        String commandText = "You can move in these directions: \n \n" + model.getPlayer().getCurrentRoom().getCommands();
        roomDescLabel.setText(commandText);
    }


    /**
     * updateScene
     * __________________________
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     *
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {

        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomImageView,roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: #000000;");

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    // MADE THIS FUNCTION PUBLIC (WAS PRIVATE BEFORE)
    public void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", 16));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     * __________________________
     *
     * Get the image for the current room and place
     * it in the roomImageView
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     *
     * This method is partially completed, but you are asked to finish it off.
     *
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     *
     * Images of each object are in the assets
     * folders of the given adventure game.
     */
    public void updateItems() {

        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        objectsInRoom.getChildren().clear();
        for (AdventureObject obj: model.getPlayer().getCurrentRoom().objectsInRoom) {
            File imgFile = new File(this.model.getDirectoryName() + File.separator + "objectImages" + File.separator + obj.getName() + ".jpg");
            Image img = new Image(imgFile.toURI().toString());
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(90);
            imgView.setPreserveRatio(true);

            Button advObj = new Button(obj.getName());
            advObj.setOnAction(e -> {
                submitEvent("TAKE " + obj.getName());
            });
            advObj.setGraphic(imgView);
            advObj.setContentDisplay(ContentDisplay.TOP);
            advObj.setAccessibleText(obj.getName());
            advObj.setMaxWidth(100);
            objectsInRoom.getChildren().add(advObj);
            makeButtonAccessible(advObj, obj.getName(), "This button takes " + obj.getName() + ".", "This button takes the " + obj.getName() + " object. Click it to take it from the current room into your inventory.");
        }

        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        objectsInInventory.getChildren().clear();
        for (String obj: model.getPlayer().getInventory()) {
            File imgFile = new File(this.model.getDirectoryName() + File.separator + "objectImages" + File.separator + obj + ".jpg");
            Image img = new Image(imgFile.toURI().toString());
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(90);
            imgView.setPreserveRatio(true);

            Button advObj = new Button(obj);
            advObj.setOnAction(e -> {
                submitEvent("DROP " + obj);
            });
            advObj.setGraphic(imgView);
            advObj.setContentDisplay(ContentDisplay.TOP);
            advObj.setAccessibleText(obj);
            advObj.setMaxWidth(100);
            objectsInInventory.getChildren().add(advObj);
            makeButtonAccessible(advObj, obj, "This button drops " + obj + ".", "This button drops the " + obj + " object. Click it to drop it from your inventory into the current room.");
        }
        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";

        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        gridPane.add(scI,2,1);


    }

    /**
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        // Toggle (invert) the button status
        helpToggle = !helpToggle;
        if (leaderboardToggle) {
            leaderboardToggle = false;
        }

        gridPane.getChildren().remove(roomDescLabel.getParent());
        gridPane.getChildren().remove(roomImageView.getParent());

        if (helpToggle) { // Help will be opened
            // Create text (containing instructions) + scroll pane objects
            ScrollPane scrollInstructions = new ScrollPane();
            Text instructions = new Text(model.getInstructions());

            // Style text
            instructions.setStyle("-fx-fill: white;");
            instructions.setFont(new Font("Arial", 16));
            instructions.setWrappingWidth(600);

            // Style scroll pane
            scrollInstructions.setContent(instructions);
            scrollInstructions.setPadding(new Insets(10));
            scrollInstructions.setStyle("-fx-background: #000000; -fx-background-color:transparent;-fx-border-color: white");
            scrollInstructions.setFitToWidth(true);

            gridPane.add(scrollInstructions, 1 , 1, 1, 1); // Add to grid pane
        } else { // Help will be closed
            gridPane.getChildren().remove(gridPane.getChildren().size() - 1); // Remove the latest Node (scrollInstructions)
            updateScene(""); // Reset the scene
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * Show the game leaderboard.
     */
    public void showLeaderboard() {
        // Toggle (invert) the button status
        leaderboardToggle = !leaderboardToggle;
        if (helpToggle) {
            helpToggle = false;
        }

        gridPane.getChildren().remove(roomDescLabel.getParent());
        gridPane.getChildren().remove(roomImageView.getParent());

        if (leaderboardToggle) { // Leaderboard will be opened
            // Create text (containing leaderboard) + scroll pane objects
            ScrollPane scrollLeaderboard = new ScrollPane();
            String leaderboardDisplay = "";
            if (thisBoard == boardXP) {
                leaderboardDisplay = leaderboardDisplay.concat("Currently sorted by: XP\n(Right-click the Leaderboard button to change sorting.)\n\n");
            } else if (thisBoard == boardTime) {
                leaderboardDisplay = leaderboardDisplay.concat("Currently sorted by: Time Elapsed\n(Right-click the Leaderboard button to change sorting.)\n\n");
            }
            int boardStanding = 1;
            HashMap<String, ArrayList<String>> allData = thisBoard.getFullStats();
            for (String user: thisBoard.getLeaderboard()) {
                String userID = user.split(" ")[1];
                ArrayList<String> userData = allData.get(userID);
                leaderboardDisplay = leaderboardDisplay.concat(boardStanding + ")    Player: " + userID + "    ");
                leaderboardDisplay = leaderboardDisplay.concat("Difficulty: " + userData.get(0) + "    ");
                leaderboardDisplay = leaderboardDisplay.concat("XP: " + userData.get(1) + "    ");
                leaderboardDisplay = leaderboardDisplay.concat("Elapsed time: " + userData.get(3) + "    ");
                leaderboardDisplay = leaderboardDisplay.concat("\n\n");
                boardStanding++;
            }
            Text leaderboardText = new Text(leaderboardDisplay);

            // Style text
            leaderboardText.setStyle("-fx-fill: white;");
            leaderboardText.setFont(new Font("Arial", 16));
            leaderboardText.setWrappingWidth(600);

            // Style scroll pane
            scrollLeaderboard.setContent(leaderboardText);
            scrollLeaderboard.setPadding(new Insets(10));
            scrollLeaderboard.setStyle("-fx-background: #000000; -fx-background-color:transparent;-fx-border-color: white");
            scrollLeaderboard.setFitToWidth(true);

            gridPane.add(scrollLeaderboard, 1 , 1, 1, 1); // Add to grid pane
        } else { // Help will be closed
            gridPane.getChildren().remove(gridPane.getChildren().size() - 1); // Remove the latest Node
            updateScene(""); // Reset the scene
        }
    }

    /**
     * This method handles the event related to the
     * leaderboard button.
     */
    public void addLeaderboardEvent() {
        leaderboardButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showLeaderboard();
        });
        leaderboardButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if (thisBoard == boardXP) {
                    thisBoard = boardTime;
                    if (leaderboardToggle) {
                        showLeaderboard();
                        stopArticulation();
                        showLeaderboard();
                    }
                } else {
                    thisBoard = boardXP;
                    if (leaderboardToggle) {
                        showLeaderboard();
                        stopArticulation();
                        showLeaderboard();
                    }
                }
            }
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }
    // TODO: Add documentation here for both methods
    public int getPlayerDifficulty(){
        return this.difficulty;
    }

    public String getPlayerUsername(){
        return this.playerUsername;
    }
    /**
     * Updates the combat scene with the latest information, including player and enemy health,
     * available actions, action history, and visual representations of the player and enemy.
     *
     * @param enemyImgPath - The file path to the image representing the enemy.
     * @param enemy        - An object of EnemyCreator representing the enemy in combat.
     */

    public void updateFightScene(String enemyImgPath, EnemyCreator enemy){
        Label weaponToolTipLabel = makeLabel("Hover over an action to see it's description");

        Label enemyName = makeLabel(enemy.getName());
        Label enemyHPLabel = makeLabel(enemy.getName() + " HP: ");
        Label enemyHPNumberLabel = makeLabel(Integer.toString(enemy.getHP()));
        enemyHPNumberLabel.setPrefHeight(20);

        ProgressBar enemyHealthBar = new ProgressBar();
        enemyHealthBar.setProgress((double) enemy.getHP() / 100.00);
        enemyHealthBar.setStyle("-fx-accent: red;");
        enemyHealthBar.setPrefWidth(300);

        HBox enemyHealthBarBox = new HBox();
        enemyHealthBarBox.setAlignment(Pos.CENTER);
        enemyHealthBarBox.getChildren().addAll(enemyHPLabel, enemyHealthBar);
        VBox enemyHealth = new VBox(enemyHPNumberLabel, enemyHealthBarBox);
        enemyHealth.setAlignment(Pos.CENTER);

        Label playerHPLabel = makeLabel(this.combat.getPlayer().getName() + " HP: ");

        ProgressBar playerHealthBar = new ProgressBar();
        playerHealthBar.setProgress((double) this.combat.getPlayer().getHP() / 100.00);
        playerHealthBar.setStyle("-fx-accent: green;");
        playerHealthBar.setPrefWidth(300);
        Label playerHPNumberLabel = makeLabel(Integer.toString(this.combat.getPlayer().getHP()));
        playerHPNumberLabel.setPrefHeight(20);
        

        HBox playerHealthBox = new HBox();
        playerHealthBox.setAlignment(Pos.CENTER);
        playerHealthBox.getChildren().addAll(playerHPLabel, playerHealthBar);
        VBox playerHealth = new VBox(playerHPNumberLabel, playerHealthBox);
        playerHealth.setAlignment(Pos.CENTER);

        Image enemyImageFile = new Image(enemyImgPath);
        ImageView enemyImageView = new ImageView(enemyImageFile);
        enemyImageView.setPreserveRatio(true);
        enemyImageView.setFitWidth(400);
        enemyImageView.setFitHeight(400);

        ArrayList<Button> buttonList = new ArrayList<>();
        for(IAction action : this.combat.getActions()){
            Button button = makeActionButton(action, enemy);
            makeButtonAccessible(button, action.getName(), action.getName(), action.getDesc());
            buttonList.add(button);
        }


        Label enemyHistoryLabel = makeLabel(enemy.getName() + " Action History:");
        VBox enemyHistoryVBox = new VBox();
        enemyHistoryVBox.setSpacing(5);
        ScrollPane enemyScrollPane = new ScrollPane(enemyHistoryVBox);
        enemyScrollPane.setFitToWidth(true);
        enemyScrollPane.setFitToHeight(true);
        enemyScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Label actionHistoryLabel = makeLabel(this.combat.getPlayer().getName() + " Action History:");
        VBox actionHistoryVBox = new VBox();
        actionHistoryVBox.setSpacing(5);
            for(String move : this.combat.getActionHistory()){
                ImageView copiedImageView = new ImageView();
                copiedImageView.setImage(new Image("Cards\\" + move.split("Used ")[1] + ".jpg"));
                copiedImageView.setPreserveRatio(true);
                copiedImageView.setFitWidth(50);
                Label moveLabel = makeLabel(move);
                moveLabel.setStyle("-fx-text-fill: black;");    
                HBox moveBox = new HBox(moveLabel, copiedImageView);
                HBox.setHgrow(copiedImageView, Priority.ALWAYS); // Allow ImageView to grow
                moveBox.setAlignment(Pos.CENTER); // Center contents in HBox
                actionHistoryVBox.getChildren().add(moveBox);

                Label enemyMoveLabel = makeLabel("Dealt " + Integer.toString(enemy.getAttack()) + " Damage");
                enemyMoveLabel.setStyle("-fx-text-fill: black;");
                enemyHistoryVBox.getChildren().add(enemyMoveLabel);
            }
        ScrollPane scrollPane = new ScrollPane(actionHistoryVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox enemyHistorVBox = new VBox(enemyHistoryLabel, enemyScrollPane);
        VBox playerHistoryVBox = new VBox(actionHistoryLabel, scrollPane); 
        VBox historyVBox = new VBox(playerHistoryVBox, enemyHistorVBox);
        
        double offset = 500.00 - (100.00 * (buttonList.size() +1 ));
        offset = offset / buttonList.size();
        HBox actionsPane = new HBox(offset);
        actionsPane.getChildren().addAll(buttonList);
        actionsPane.setAlignment(Pos.CENTER);
        VBox fightPane = new VBox(30, enemyName, enemyImageView, enemyHealth, actionsPane, playerHealth);

        fightPane.setPadding(new Insets(10));
        fightPane.setAlignment(Pos.TOP_CENTER);
        fightPane.setStyle("-fx-background-color: #000000;");

        combatGridPane.add(fightPane, 1, 1);
        combatGridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 2);
        combatGridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 0);
        combatGridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 1);
        combatGridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 0);
        combatGridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 2 && GridPane.getRowIndex(node) == 0);
        combatGridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 2 && GridPane.getRowIndex(node) == 1);
        combatGridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 2 && GridPane.getRowIndex(node) == 2);
        combatGridPane.add(weaponToolTipLabel, 0,1);
        combatGridPane.add(historyVBox, 2,1);
        stage.sizeToScene();
    }

    public Button makeActionButton(IAction action, EnemyCreator enemy){
        Button curButton = new Button(action.getName());
        Image curImage = action.getImg();
        ImageView curImageView = new ImageView(curImage);
        curImageView.setFitWidth(100);
        curImageView.setPreserveRatio(true);
        curButton.setGraphic(curImageView);
        curButton.setContentDisplay(ContentDisplay.TOP);
        curButton.setMinHeight(200);
        curButton.setStyle("-fx-stroke: white; -fx-stroke-width: 2;");
        curButton.setOnAction(e -> {

            //System.out.println("Did the thing");
            this.combat.updateActionHistory("Used " + action.getName());
            this.combat.updateCardHistory(action);
            if(!this.combat.fight(enemy, action)){
                PauseTransition p = new PauseTransition(Duration.seconds(0.75));
                p.setOnFinished(f -> {
                    updateFightScene("Enemies\\enemyImages\\" + enemy.getName() +"Injured.jpg", enemy);
                    
                });
                p.play();
                
                updateFightScene("Enemies\\enemyImages\\" + enemy.getName() +"Hit.jpg", enemy);
            }
            else{
                if(this.combat.getPlayer().getHP() <= 0 || this.combat.getActions().isEmpty()){gameOver();}
                else{
                    winCombat(new Image("Enemies\\enemyImages\\" + enemy.getName() +"Dead.jpg"));
                }
            }
        });

        curButton.setOnMouseEntered(e -> {
            // the enlarging effect
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), curButton);
            scaleTransition.setToX(1.2);
            scaleTransition.setToY(1.2);
            curButton.setTranslateY(-30);
            curButton.setViewOrder(-1.0);
            scaleTransition.play();

            combatGridPane.getChildren().removeIf( node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 1);
            curButton.setStyle("-fx-background-color: derive(black, 80%);");
            VBox card = action.getFullCard();
            combatGridPane.add(card, 0,1);

        });

        curButton.setOnMouseExited(e -> {
            combatGridPane.getChildren().removeIf( node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 1);
            curButton.setStyle(null); // Reset to default style
            Label weaponToolTipLabel = makeLabel("Hover over a card to see it's description");
            combatGridPane.add(weaponToolTipLabel, 0, 1);

            // the shrinking effect
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), curButton);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            curButton.setTranslateY(0);
            curButton.setViewOrder(0.0);
            scaleTransition.play();

        });

        return curButton;
    }

    private void gameOver() {
        Label gameOverLabel = makeLabel("You lost");

        if(this.combat.getPlayer().getHP() <= 0){
            gameOverLabel = makeLabel("You died \n \nYou lose!");
        }
        else if(this.combat.getActions().isEmpty()){
            gameOverLabel = makeLabel("You ran out of cards\n \nYou lose!");
        }
        gameOverLabel.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        //Write player's stats to leaderboard.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(leaderboardPath, true))) {
            writer.newLine();
            writer.write(model.player.interactivePlayer.getName()); // Write name
            writer.newLine();
            if (difficulty == 0) { // Write player's difficulty
                writer.write("Easy");
            } else if (difficulty == 1) {
                writer.write("Normal");
            } else if (difficulty == 2) {
                writer.write("Hard");
            }
            writer.newLine();
            writer.write(Integer.toString(model.player.interactivePlayer.getXP())); // Write XP
            writer.newLine();
            writer.write("date"); // Placeholder for date (no longer used in leaderboard)
            writer.newLine();
            writer.write(Integer.toString(secondsPassed)); // Write elapsed time
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        manageBoards.update(); // Update all observing leaderboards with new entry

        // Render everything
        var scene = new Scene( gameOverLabel,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            Platform.exit();
        });
        pause.play();
    }

    private void winCombat(Image enemyImage) {
        Label winCombatLabel = makeLabel("You Win!");
        winCombatLabel.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        winCombatLabel.setPrefHeight(100);

        ImageView enemyImageView = new ImageView(enemyImage);
        enemyImageView.setPreserveRatio(true);
        enemyImageView.setFitWidth(400);
        enemyImageView.setFitHeight(400);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(winCombatLabel, enemyImageView);
        vbox.setStyle("-fx-background-color: black");
        vbox.setAlignment(Pos.CENTER);

        // Render everything
        var winScene = new Scene(vbox,  1000, 800);
        winScene.setFill(Color.BLACK);
        this.stage.setScene(winScene);
        this.stage.setResizable(false);
        this.stage.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            rewardsScreen();
        });
        pause.play();
    }

    public void rewardsScreen(){
        ArrayList<Card> rewards = this.model.player.lootPool.getRewards();
        this.combat.updateCards(rewards);

        Label rewardLabel = makeLabel("Your Rewards: ");
        rewardLabel.setPrefHeight(100);
        rewardLabel.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        rewardLabel.setPrefHeight(100);

        HBox cardRewards = new HBox(20);
        for(Card card : rewards){//TODO: get cards from reward pool
            VBox reward = card.getFullCard();
            reward.setPrefWidth(150);
            reward.setPrefHeight(300);
            cardRewards.getChildren().add(reward);
        }
        cardRewards.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(20, rewardLabel, cardRewards);
        vbox.setStyle("-fx-background-color: black");
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // Render everything
        var rewardsScene = new Scene(vbox,  1000, 800);
        rewardsScene.setFill(Color.BLACK);
        this.stage.setScene(rewardsScene);
        this.stage.setResizable(false);
        this.stage.sizeToScene();
        this.stage.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            this.stage.setScene(curScene);
            updateScene("");
            this.stage.show();
        });
        pause.play();
    }

    public Label makeLabel(String text){
        Label curLabel = new Label();
        curLabel.setText(text);
        curLabel.setStyle("-fx-text-fill: white;");
        curLabel.setFont(new Font("Arial", 16));
        curLabel.setAlignment(Pos.CENTER);
        curLabel.setPrefHeight(500);
        curLabel.setTextOverrun(OverrunStyle.CLIP);
        curLabel.setWrapText(true);
        return curLabel;
    }


}
