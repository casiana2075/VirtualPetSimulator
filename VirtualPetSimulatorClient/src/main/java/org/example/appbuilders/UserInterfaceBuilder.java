package org.example.appbuilders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Duration;
import org.example.ClientApplication;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class UserInterfaceBuilder {
    public static void createStatsImageViews(ClientApplication app) {
        Map<String, ImageView> imageViews = new HashMap<>();
        imageViews.put("heart", initStatsImageView(app, "/Items/heart.png"));
        imageViews.put("fish", initStatsImageView(app, "/Items/fish.png"));
        imageViews.put("soap", initStatsImageView(app, "/Items/soap.png"));
        app.setImageViews(imageViews);
    }

    private static ImageView initStatsImageView(ClientApplication app, String path) {
        Image image = new Image(app
                .getClass()
                .getResourceAsStream(path));
        ImageView imageView = new ImageView(image);
        imageView.setTranslateX(50);
        if (path.equals("/Items/heart.png")) {
            imageView.setFitWidth(40);
            imageView.setFitHeight(30);
            imageView.setTranslateY(-5);
        } else if (path.equals("/Items/fish.png")) {
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            imageView.setTranslateY(-10);
        } else if (path.equals("/Items/soap.png")) {
            imageView.setFitWidth(50);
            imageView.setFitHeight(45);
            imageView.setTranslateY(-15);
        }
        return imageView;
    }

    public static void createProgressBars(ClientApplication app) {
        Map<String, ProgressBar> progressBars = new HashMap<>();
        progressBars.put("hunger", initProgressBar());
        progressBars.put("happiness", initProgressBar());
        progressBars.put("cleanness", initProgressBar());
        app.setProgressBars(progressBars);
    }

    private static ProgressBar initProgressBar() {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(1.0);
        progressBar.setStyle("-fx-accent: green;");
        progressBar.setPrefWidth(200);
        progressBar.progressProperty().addListener(new ProgressBarChangeListener(progressBar));
        return progressBar;
    }

    public static void createProgressBarsTimeline(ClientApplication app) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            double happiness = app.getProgressBars().get("happiness").getProgress();
            double hunger = app.getProgressBars().get("hunger").getProgress();
            double cleanness = app.getProgressBars().get("cleanness").getProgress();
            if (happiness > 0) {
                app.getProgressBars().get("happiness").setProgress(happiness - 0.05);
            }
            if (hunger > 0) {
                app.getProgressBars().get("hunger").setProgress(hunger - 0.1);
            }
            if (cleanness > 0) {
                app.getProgressBars().get("cleanness").setProgress(cleanness - 0.01);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        app.setProgressBarsTimeline(timeline);
    }

    public static void loadFont(ClientApplication app) {
        InputStream is = app
                .getClass()
                .getResourceAsStream("/Fonts/PixelatedFont.ttf");
        app.setFont(Font.loadFont(is, 20));
    }

    public static void createGameAreaLabels(ClientApplication app) {
        Map<String, Label> gameAreaLabels = new HashMap<>();
        Label nameLabel = new Label("Name: " + app.getPet().getName());
        nameLabel.setFont(app.getFont());
        gameAreaLabels.put("name", nameLabel);
        Label hungerLabel = new Label("Score: " + app.getUser().getScore());
        hungerLabel.setFont(app.getFont());
        gameAreaLabels.put("score", hungerLabel);
        app.setGameAreaLabels(gameAreaLabels);
    }

    public static void createPetInteractionButtons(ClientApplication app) {
        Map<String, Button> petInteractionButtons = new HashMap<>();
        petInteractionButtons.put("feed", initGameAreaButton(app, "/Buttons/feedBttn.png"));
        petInteractionButtons.put("play", initGameAreaButton(app, "/Buttons/playBttn.png"));
        petInteractionButtons.put("wash", initGameAreaButton(app, "/Buttons/washBttn.png"));
        app.setPetInteractionButtons(petInteractionButtons);
    }

    public static void createGameQuitButtons(ClientApplication app) {
        Map<String, Button> gameQuitButtons = new HashMap<>();
        gameQuitButtons.put("logout", initGameAreaButton(app, "/Buttons/logoutBttn.png"));
        gameQuitButtons.put("exit", initGameAreaButton(app, "/Buttons/exitBttn.png"));
        app.setGameQuitButtons(gameQuitButtons);
    }

    private static Button initGameAreaButton(ClientApplication app, String iconPath) {
        Image image = new Image(app
                .getClass()
                .getResourceAsStream(iconPath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        Button button = new Button("", imageView);
        button.setStyle("-fx-background-color: transparent;");
        button.setOnMouseEntered(event -> {
            imageView.setFitWidth(170);
            imageView.setFitHeight(170);
        });
        button.setOnMouseExited(event -> {
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
        });
        return button;
    }

    public static void createCatImageView(ClientApplication app) {
        Image catTailUpImage = new Image(app
                .getClass()
                .getResourceAsStream("/CatAnimations/catTailUp.png"));
        ImageView catImageView = new ImageView(catTailUpImage);
        catImageView.setFitWidth(550);
        catImageView.setFitHeight(550);
        app.setCatImageView(catImageView);
    }

    public static void createCatAnimations(ClientApplication app) {
        Map<String, Timeline> catAnimations = new HashMap<>();
        catAnimations.put("idle", AnimationBuilder.createIdleAnimation(app, app.getCatImageView()));
        catAnimations.put("eating", AnimationBuilder.createEatingAnimation(app, app.getCatImageView()));
        catAnimations.put("washing", AnimationBuilder.createWashingAnimation(app, app.getCatImageView()));
        catAnimations.put("playing", AnimationBuilder.createPlayingAnimation(app, app.getCatImageView()));
        app.setCatAnimations(catAnimations);
    }

    public static void createStaticLabels(ClientApplication app) {
        Map<String, Label> staticLabels = new HashMap<>();
        staticLabels.put("logIn", new Label("Please login to see your beloved pet!"));
        staticLabels.put("noAccount?", new Label("...or you don't have an account?"));
        staticLabels.put("signUp", new Label("Create an account to have a lovely pet!"));
        staticLabels.put("alreadyAccount?", new Label("...or you already have an account?"));
        for (Label label : staticLabels.values()) {
            label.setFont(app.getFont());
        }
        app.setStaticLabels(staticLabels);
    }

    public static void createHyperlinks(ClientApplication app) {
        Map<String, Hyperlink> hyperlinks = new HashMap<>();
        hyperlinks.put("toSignUp", new Hyperlink("Register here"));
        hyperlinks.put("toLogIn", new Hyperlink("Login here"));
        for (Hyperlink hyperlink : hyperlinks.values()) {
            hyperlink.setFont(app.getFont());
        }
        app.setHyperlinks(hyperlinks);
    }

    public static void createInputFields(ClientApplication app) {
        Map<String, TextField> inputFields = new HashMap<>();

        TextField identifierField = new TextField();
        identifierField.setPromptText("Username or Email");
        inputFields.put("identifier", identifierField);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        inputFields.put("username", usernameField);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        inputFields.put("email", emailField);

        PasswordField logInPasswordField = new PasswordField();
        logInPasswordField.setPromptText("Password");
        inputFields.put("logInPassword", logInPasswordField);

        PasswordField signUpPasswordField = new PasswordField();
        signUpPasswordField.setPromptText("Password");
        inputFields.put("signUpPassword", signUpPasswordField);

        TextField petNameField = new TextField();
        petNameField.setPromptText("Your Pet's Name");
        inputFields.put("petName", petNameField);

        for (TextField inputField : inputFields.values()) {
            inputField.setFont(app.getFont());
            inputField.setMaxWidth(300);
        }
        app.setInputFields(inputFields);
    }

    public static void createGameEnterButtons(ClientApplication app) {
        Map<String, Button> gameEnterButtons = new HashMap<>();
        gameEnterButtons.put("logIn", new Button("Login"));
        gameEnterButtons.put("signUp", new Button("Register"));

        Image toLoginImage = new Image(app
                .getClass()
                .getResourceAsStream("/Buttons/loginBttn.png"));
        ImageView toLoginImageView = new ImageView(toLoginImage);
        toLoginImageView.setFitWidth(150);
        toLoginImageView.setFitHeight(150);
        Button toLoginButton = new Button("", toLoginImageView);
        toLoginButton.setStyle("-fx-background-color: transparent;");
        gameEnterButtons.put("toLogIn", toLoginButton);

        Image toRegisterImage = new Image(app
                .getClass()
                .getResourceAsStream("/Buttons/createAccBttn.png"));
        ImageView toRegisterImageView = new ImageView(toRegisterImage);
        toRegisterImageView.setFitWidth(250);
        toRegisterImageView.setFitHeight(150);
        Button toRegisterButton = new Button("", toRegisterImageView);
        toRegisterButton.setStyle("-fx-background-color: transparent;");
        gameEnterButtons.put("toSignUp", toRegisterButton);

        Image exitImage = new Image(app
                .getClass()
                .getResourceAsStream("/Buttons/exitBttn.png"));
        ImageView exitImageView = new ImageView(exitImage);
        exitImageView.setFitWidth(150);
        exitImageView.setFitHeight(150);
        Button exitButton = new Button("", exitImageView);
        exitButton.setStyle("-fx-background-color: transparent;");
        gameEnterButtons.put("homeExit", exitButton);

        gameEnterButtons.put("toHomeFromLogIn", new Button("Home"));
        gameEnterButtons.put("toHomeFromSignUp", new Button("Home"));
        gameEnterButtons.put("exitFromHome", new Button("Home"));
        for (Button button : gameEnterButtons.values()) {
            button.setFont(app.getFont());
        }
        app.setGameEnterButtons(gameEnterButtons);
    }

    public static void createBoxes(ClientApplication app) {
        Map<String, Pane> boxes = new HashMap<>();

        Label loginErrorMessage = new Label("Incorrect username or password");
        loginErrorMessage.setFont(app.getFont());
        loginErrorMessage.setStyle("-fx-text-fill: red;");
        loginErrorMessage.setVisible(false);
        app.setLoginErrorMessage(loginErrorMessage);

        Label signUpErrorMessage = new Label("");
        signUpErrorMessage.setFont(app.getFont());
        signUpErrorMessage.setStyle("-fx-text-fill: red;");
        signUpErrorMessage.setVisible(false);
        app.setSignUpErrorMessage(signUpErrorMessage);

        HBox logInPromptBox = new HBox();
        logInPromptBox.getChildren().addAll(app.getStaticLabels().get("noAccount?"),
                app.getHyperlinks().get("toSignUp"));
        logInPromptBox.setAlignment(Pos.CENTER);
        boxes.put("logInPrompt", logInPromptBox);

        HBox signUpPromptBox = new HBox();
        signUpPromptBox.getChildren().addAll(app.getStaticLabels().get("alreadyAccount?"),
                app.getHyperlinks().get("toLogIn"));
        signUpPromptBox.setAlignment(Pos.CENTER);
        boxes.put("signUpPrompt", signUpPromptBox);

        VBox signUpBox = new VBox();
        signUpBox.getChildren().addAll(app.getStaticLabels().get("signUp"),
                app.getInputFields().get("username"),
                app.getInputFields().get("signUpPassword"),
                app.getInputFields().get("petName"),
                app.getInputFields().get("email"),
                boxes.get("signUpPrompt"),
                app.getSignUpErrorMessage(),
                app.getGameEnterButtons().get("signUp"),
                app.getGameEnterButtons().get("toHomeFromSignUp"));
        signUpBox.setAlignment(Pos.CENTER);
        signUpBox.setSpacing(20);
        signUpBox.setStyle("-fx-background-color: #A9A9A9;");
        boxes.put("signUp", signUpBox);

        VBox logInBox = new VBox();
        logInBox.getChildren().addAll(app.getStaticLabels().get("logIn"),
                app.getInputFields().get("identifier"),
                app.getInputFields().get("logInPassword"),
                boxes.get("logInPrompt"),
                app.getLoginErrorMessage(),
                app.getGameEnterButtons().get("logIn"),
                app.getGameEnterButtons().get("toHomeFromLogIn"));
        logInBox.setAlignment(Pos.CENTER);
        logInBox.setSpacing(20);
        logInBox.setStyle("-fx-background-color: #A9A9A9;");
        boxes.put("logIn", logInBox);

        HBox homeButtonsBox = new HBox();
        homeButtonsBox.getChildren().addAll(app.getGameEnterButtons().get("toLogIn"),
                app.getGameEnterButtons().get("toSignUp"),
                app.getGameEnterButtons().get("homeExit"));
        homeButtonsBox.setTranslateY(700);
        boxes.put("homeButtons", homeButtonsBox);

        HBox homeBox = new HBox();
        homeBox.setAlignment(Pos.CENTER);
        homeBox.setSpacing(20);
        homeBox.getChildren().add(boxes.get("homeButtons"));
        homeBox.setStyle("-fx-background-image: url('/Items/gameCover.png'); -fx-background-size: cover; -fx-background-position: center center;");
        boxes.put("home", homeBox);

        HBox statsBox = new HBox();
        statsBox.getChildren().addAll(app.getImageViews().get("heart"),
                app.getProgressBars().get("happiness"),
                app.getImageViews().get("fish"),
                app.getProgressBars().get("hunger"),
                app.getImageViews().get("soap"),
                app.getProgressBars().get("cleanness"));
        statsBox.setAlignment(Pos.TOP_CENTER);
        statsBox.setSpacing(50);
        statsBox.setPadding(new Insets(30, 0, 0, 0));
        statsBox.setStyle("-fx-background-color: #A9A9A9;");
        boxes.put("stats", statsBox);

        HBox userInfoBox = new HBox();
        userInfoBox.getChildren().addAll(app.getGameAreaLabels().get("name"),
                app.getGameAreaLabels().get("score"));
        userInfoBox.setAlignment(Pos.CENTER);
        userInfoBox.setSpacing(300);
        userInfoBox.setPadding(new Insets(10, 0, 0, 0));
        userInfoBox.setStyle("-fx-background-color: #A9A9A9;");
        boxes.put("nameAndScore", userInfoBox);

        VBox centerCatBox = new VBox();
        centerCatBox.setAlignment(Pos.CENTER);
        centerCatBox.setStyle("-fx-background-color: #A9A9A9;");
        centerCatBox.getChildren().add(app.getCatImageView());
        boxes.put("cat", centerCatBox);


        VBox gameQuitButtonsBox = new VBox();
        gameQuitButtonsBox.getChildren().addAll(app.getGameQuitButtons().get("logout"),
                app.getGameQuitButtons().get("exit"));
        gameQuitButtonsBox.setAlignment(Pos.BOTTOM_RIGHT);
        gameQuitButtonsBox.setStyle("-fx-background-color: #A9A9A9;");
        boxes.put("gameQuit", gameQuitButtonsBox);

        VBox petInteractionButtonsBox = new VBox();
        petInteractionButtonsBox.getChildren().addAll(app.getPetInteractionButtons().get("play"),
                app.getPetInteractionButtons().get("feed"),
                app.getPetInteractionButtons().get("wash"));
        petInteractionButtonsBox.setAlignment(Pos.BOTTOM_LEFT);
        petInteractionButtonsBox.setStyle("-fx-background-color: #A9A9A9;");
        boxes.put("petInteraction", petInteractionButtonsBox);

        VBox infoBox = new VBox(boxes.get("stats"),
                boxes.get("nameAndScore"));
        boxes.put("info", infoBox);

        BorderPane borderPaneGame = new BorderPane();
        borderPaneGame.setTop(boxes.get("info"));
        borderPaneGame.setLeft(boxes.get("petInteraction"));
        borderPaneGame.setRight(boxes.get("gameQuit"));
        borderPaneGame.setCenter(boxes.get("cat"));
        boxes.put("game", borderPaneGame);

        app.setBoxes(boxes);
    }

    public static void buildScenes(ClientApplication app) {
        Map<String, Scene> scenes = new HashMap<>();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getBounds();
        double width = bounds.getWidth();
        double height = bounds.getHeight();
        scenes.put("signUp", new Scene(app.getBoxes().get("signUp"), width, height));
        scenes.put("logIn", new Scene(app.getBoxes().get("logIn"), width, height));
        scenes.put("home", new Scene(app.getBoxes().get("home"), width, height));
        scenes.put("game", new Scene(app.getBoxes().get("game"), width, height));
        app.setScenes(scenes);
    }
}
