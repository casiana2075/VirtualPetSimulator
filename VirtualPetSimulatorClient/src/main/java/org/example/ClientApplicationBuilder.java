package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class ClientApplicationBuilder {
    public static void setSizes(ClientApplication clientApplication) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getBounds();
        double width = bounds.getWidth();
        double height = bounds.getHeight();
        clientApplication.setScreenWidth(width);
        clientApplication.setScreenHeight(height);
    }

    public static Map<String, ImageView> createStatsImageViews(ClientApplication clientApplication) {
        Map<String, ImageView> imageViews = new HashMap<>();
        imageViews.put("heart", initStatsImageView(clientApplication, "/Items/heart.png"));
        imageViews.put("fish", initStatsImageView(clientApplication, "/Items/fish.png"));
        imageViews.put("soap", initStatsImageView(clientApplication, "/Items/soap.png"));
        return imageViews;
    }

    private static ImageView initStatsImageView(ClientApplication clientApplication, String path) {
        Image image = new Image(clientApplication
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

    public static Map<String, ProgressBar> createProgressBars() {
        Map<String, ProgressBar> progressBars = new HashMap<>();
        progressBars.put("hunger", initProgressBar());
        progressBars.put("happiness", initProgressBar());
        progressBars.put("cleanness", initProgressBar());
        return progressBars;
    }

    private static ProgressBar initProgressBar() {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(1.0);
        progressBar.setStyle("-fx-accent: green;");
        progressBar.setPrefWidth(200);
        progressBar.progressProperty().addListener(new ProgressBarChangeListener(progressBar));
        return progressBar;
    }

    public static Timeline createProgressBarsTimeline(Map<String, ProgressBar> progressBars) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            double happiness = progressBars.get("happiness").getProgress();
            double hunger = progressBars.get("hunger").getProgress();
            double cleanness = progressBars.get("cleanness").getProgress();
            if (happiness > 0) {
                progressBars.get("happiness").setProgress(happiness - 0.05);
            }
            if (hunger > 0) {
                progressBars.get("hunger").setProgress(hunger - 0.1);
            }
            if (cleanness > 0) {
                progressBars.get("cleanness").setProgress(cleanness - 0.01);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    public static Font loadFont(ClientApplication clientApplication) {
        InputStream is = clientApplication
                .getClass()
                .getResourceAsStream("/Fonts/PixelatedFont.ttf");
        return Font.loadFont(is, 20);
    }

    public static Map<String, Label> createGameAreaLabels(ClientApplication clientApplication, Font font) {
        Map<String, Label> gameAreaLabels = new HashMap<>();
        Label nameLabel = new Label("Name: " + clientApplication.getPet().getName());
        nameLabel.setFont(font);
        gameAreaLabels.put("name", nameLabel);
        Label hungerLabel = new Label("Score: " + clientApplication.getUser().getScore());
        hungerLabel.setFont(font);
        gameAreaLabels.put("score", hungerLabel);
        return gameAreaLabels;
    }

    public static Map<String, Button> createPetInteractionButtons(ClientApplication clientApplication) {
        Map<String, Button> petInteractionButtons = new HashMap<>();
        petInteractionButtons.put("feed", initGameAreaButton(clientApplication, "/Buttons/feedBttn.png"));
        petInteractionButtons.put("play", initGameAreaButton(clientApplication, "/Buttons/playBttn.png"));
        petInteractionButtons.put("wash", initGameAreaButton(clientApplication, "/Buttons/washBttn.png"));
        return petInteractionButtons;
    }

    public static Map<String, Button> createGameQuitButtons(ClientApplication clientApplication) {
        Map<String, Button> gameQuitButtons = new HashMap<>();
        gameQuitButtons.put("logout", initGameAreaButton(clientApplication, "/Buttons/logoutBttn.png"));
        gameQuitButtons.put("exit", initGameAreaButton(clientApplication, "/Buttons/exitBttn.png"));
        return gameQuitButtons;
    }

    private static Button initGameAreaButton(ClientApplication clientApplication, String iconPath) {
        Image image = new Image(clientApplication
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

    public static ImageView createCatImageView(ClientApplication clientApplication) {
        Image catTailUpImage = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catTailUp.png"));
        ImageView catImageView = new ImageView(catTailUpImage);
        catImageView.setFitWidth(550);
        catImageView.setFitHeight(550);
        return catImageView;
    }

    public static Map<String, Timeline> createCatAnimations(ClientApplication clientApplication, ImageView catImageView) {
        Map<String, Timeline> catAnimations = new HashMap<>();
        catAnimations.put("idle", AnimationBuilder.createIdleAnimation(clientApplication, catImageView));
        catAnimations.put("eating", AnimationBuilder.createEatingAnimation(clientApplication, catImageView));
        catAnimations.put("washing", AnimationBuilder.createWashingAnimation(clientApplication, catImageView));
        catAnimations.put("playing", AnimationBuilder.createPlayingAnimation(clientApplication, catImageView));
        return catAnimations;
    }

    public static Map<String, Label> createStaticLabels(Font font) {
        Map<String, Label> staticLabels = new HashMap<>();
        staticLabels.put("logIn", new Label("Please login to see your beloved pet!"));
        staticLabels.put("noAccount?", new Label("...or you don't have an account?"));
        staticLabels.put("signUp", new Label("Create an account to have a lovely pet!"));
        staticLabels.put("alreadyAccount?", new Label("...or you already have an account?"));
        for (Label label : staticLabels.values()) {
            label.setFont(font);
        }
        return staticLabels;
    }

    public static Map<String, Hyperlink> createHyperlinks(Font font) {
        Map<String, Hyperlink> hyperlinks = new HashMap<>();
        hyperlinks.put("toSignUp", new Hyperlink("Register here"));
        hyperlinks.put("toLogIn", new Hyperlink("Login here"));
        for (Hyperlink hyperlink : hyperlinks.values()) {
            hyperlink.setFont(font);
        }
        return hyperlinks;
    }

    public static Map<String, TextField> createInputFields(Font font) {
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
            inputField.setFont(font);
            inputField.setMaxWidth(300);
        }
        return inputFields;
    }

    public static Map<String, Button> createGameEnterButtons(ClientApplication clientApplication, Font font) {
        Map<String, Button> gameEnterButtons = new HashMap<>();
        gameEnterButtons.put("logIn", new Button("Login"));
        gameEnterButtons.put("signUp", new Button("Register"));

        Image toLoginImage = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/Buttons/loginBttn.png"));
        ImageView toLoginImageView = new ImageView(toLoginImage);
        toLoginImageView.setFitWidth(150);
        toLoginImageView.setFitHeight(150);
        Button toLoginButton = new Button("", toLoginImageView);
        toLoginButton.setStyle("-fx-background-color: transparent;");
        gameEnterButtons.put("toLogIn", toLoginButton);


        Image toRegisterImage = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/Buttons/createAccBttn.png"));
        ImageView toRegisterImageView = new ImageView(toRegisterImage);
        toRegisterImageView.setFitWidth(250);
        toRegisterImageView.setFitHeight(150);
        Button toRegisterButton = new Button("", toRegisterImageView);
        toRegisterButton.setStyle("-fx-background-color: transparent;");
        gameEnterButtons.put("toSignUp", toRegisterButton);

        gameEnterButtons.put("toHomeFromLogIn", new Button("Home"));
        gameEnterButtons.put("toHomeFromSignUp", new Button("Home"));
        for (Button button : gameEnterButtons.values()) {
            button.setFont(font);
        }
        return gameEnterButtons;
    }
}
