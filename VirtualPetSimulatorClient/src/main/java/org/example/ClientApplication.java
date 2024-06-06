package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.example.entities.Pet;
import org.example.entities.User;

import java.util.Map;

@Getter @Setter
public class ClientApplication extends Application {
    private double screenHeight;
    private double screenWidth;
    private User user = new User();
    private Pet pet = new Pet();

    public void start(Stage primaryStage) throws Exception {
        ClientApplicationBuilder.setSizes(this);
        Map<String, ImageView> imageViews = ClientApplicationBuilder.createStatsImageViews(this);
        Map<String, ProgressBar> progressBars = ClientApplicationBuilder.createProgressBars();
        Timeline progressBarsTimeline = ClientApplicationBuilder.createProgressBarsTimeline(progressBars);
        Font font = ClientApplicationBuilder.loadFont(this);
        Map<String, Label> gameAreaLabels = ClientApplicationBuilder.createGameAreaLabels(this, font);
        Map<String, Button> petInteractionButtons = ClientApplicationBuilder.createPetInteractionButtons(this);
        Map<String, Button> gameQuitButtons = ClientApplicationBuilder.createGameQuitButtons(this);
        ImageView catImageView = ClientApplicationBuilder.createCatImageView(this);
        Map<String, Timeline> catAnimations = ClientApplicationBuilder.createCatAnimations(this, catImageView);

        gameQuitButtons.get("exit").setOnAction(event -> {
            ServiceCaller.updatePet(pet.getId(),
                    (int) (progressBars.get("hunger").getProgress() * 100),
                    (int) (progressBars.get("happiness").getProgress() * 100),
                    (int) (progressBars.get("cleanness").getProgress() * 100));
            Platform.exit();
        });

        //login fields
        Label loginTitleLabel = new Label("Please login to see your beloved pet!");
        loginTitleLabel.setFont(font);
        TextField loginUsernameField = new TextField();
        loginUsernameField.setPromptText("Username");
        loginUsernameField.setFont(font);
        PasswordField loginPasswordField = new PasswordField();
        loginPasswordField.setPromptText("Password");
        loginPasswordField.setFont(font);
        Button loginButton = new Button("Login");
        loginButton.setFont(font);
        Button homeButton = new Button("Home");
        homeButton.setFont(font);
        Label promptLabel = new Label("...or you don't have an account? ");
        promptLabel.setFont(font);
        Hyperlink registerLink = new Hyperlink("Register here");
        registerLink.setFont(font);
        HBox loginPromptBox = new HBox();
        loginPromptBox.getChildren().addAll(promptLabel, registerLink);
        loginPromptBox.setAlignment(Pos.CENTER);


        //register fields
        Label registerTitleLabel = new Label("Create an account to have a lovely pet!");
        registerTitleLabel.setFont(font);
        TextField registerUsernameField = new TextField();
        registerUsernameField.setPromptText("Username");
        registerUsernameField.setFont(font);
        PasswordField registerPasswordField = new PasswordField();
        registerPasswordField.setPromptText("Create a password");
        registerPasswordField.setFont(font);
        TextField registerPetNameField = new TextField();
        registerPetNameField.setPromptText("Your Pet Name");
        registerPetNameField.setFont(font);
        TextField registerEmailField = new TextField();
        registerEmailField.setPromptText("Email Address");
        registerEmailField.setFont(font);
        Button registerButton = new Button("Register");
        registerButton.setFont(font);
        Label registerPromptLabel = new Label("...or you already have an account? ");
        registerPromptLabel.setFont(font);
        Hyperlink loginLink = new Hyperlink("Login here");
        loginLink.setFont(font);
        HBox registerPromptBox = new HBox();
        registerPromptBox.getChildren().addAll(registerPromptLabel, loginLink);
        registerPromptBox.setAlignment(Pos.CENTER);
        VBox registerBox = new VBox();
        registerUsernameField.setMaxWidth(300);
        registerPasswordField.setMaxWidth(300);
        registerPetNameField.setMaxWidth(300);
        registerEmailField.setMaxWidth(300);
        registerBox.getChildren().addAll(registerTitleLabel, registerUsernameField, registerPasswordField, registerPetNameField, registerEmailField, registerPromptBox, registerButton, homeButton);
        registerBox.setAlignment(Pos.CENTER);
        registerBox.setSpacing(20);
        registerBox.setStyle("-fx-background-color: #A9A9A9;");
        //scene for register
        Scene registerScene = new Scene(registerBox, screenWidth, screenHeight);
        //action of register here link
        registerLink.setOnAction(event -> {
            primaryStage.setScene(registerScene);
            primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Create An Account...");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        // Create a login box/scene and add the login fields to it
        VBox loginBox = new VBox();
        loginUsernameField.setMaxWidth(300);
        loginPasswordField.setMaxWidth(300);
        loginBox.getChildren().addAll(loginTitleLabel,loginUsernameField, loginPasswordField, loginPromptBox, loginButton, homeButton);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setSpacing(20);
        loginBox.setStyle("-fx-background-color: #A9A9A9;");
        Scene loginScene = new Scene(loginBox, screenWidth, screenHeight);//scene for login
        gameQuitButtons.get("logout").setOnAction(event -> {//action of logout button
            ServiceCaller.updatePet(pet.getId(),
                    (int) (progressBars.get("hunger").getProgress() * 100),
                    (int) (progressBars.get("happiness").getProgress() * 100),
                    (int) (progressBars.get("cleanness").getProgress() * 100));
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Login...");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
            user = null;
            pet = null;
        });
        //action of login here link
        loginLink.setOnAction(event1 -> {
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Back to Previous Page");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        // Create a new scene for the home page
        Image toLoginImage = new Image(getClass().getResourceAsStream("/Buttons/loginBttn.png"));
        ImageView toLoginImageView = new ImageView(toLoginImage);
        toLoginImageView.setFitWidth(150);
        toLoginImageView.setFitHeight(150);
        Button toLoginButton = new Button("", toLoginImageView);
        toLoginButton.setStyle("-fx-background-color: transparent;");
        toLoginButton.setOnAction(event -> {
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        Image toRegisterImage = new Image(getClass().getResourceAsStream("/Buttons/createAccBttn.png"));
        ImageView toRegisterImageView = new ImageView(toRegisterImage);
        toRegisterImageView.setFitWidth(250);
        toRegisterImageView.setFitHeight(150);
        Button toRegisterButton = new Button("", toRegisterImageView);
        toRegisterButton.setStyle("-fx-background-color: transparent;");
        toRegisterButton.setOnAction(event -> {
            primaryStage.setScene(registerScene);
            primaryStage.setTitle("Create Account");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });
        HBox homeBox = new HBox();
        homeBox.setAlignment(Pos.CENTER);
        homeBox.setSpacing(20);
        homeBox.setStyle("-fx-background-image: url('/Items/gameCover.png'); -fx-background-size: cover; -fx-background-position: center center;");
        HBox homeButtonBox = new HBox();
        homeButtonBox.getChildren().addAll(toLoginButton, toRegisterButton);
        homeButtonBox.setTranslateY(500);
        homeBox.getChildren().add(homeButtonBox);
        Scene homeScene = new Scene(homeBox, screenWidth, screenHeight); // scene for home
        homeButton.setOnAction(event -> { // action of home button
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Home");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        // Create a top box and add the progress bars to it
        HBox topStateBarBox = new HBox();
        topStateBarBox.getChildren().addAll(imageViews.get("heart"),
                progressBars.get("happiness"),
                imageViews.get("fish"),
                progressBars.get("hunger"),
                imageViews.get("soap"),
                progressBars.get("cleanness"));
        topStateBarBox.setAlignment(Pos.TOP_CENTER);
        topStateBarBox.setSpacing(50);
        topStateBarBox.setPadding(new Insets(30, 0, 0, 0));
        topStateBarBox.setStyle("-fx-background-color: #A9A9A9;");

        // Create a top box and add the labels to it
        HBox userInfoBox = new HBox();
        userInfoBox.getChildren().addAll(gameAreaLabels.get("name"), gameAreaLabels.get("score"));
        userInfoBox.setAlignment(Pos.CENTER);
        userInfoBox.setSpacing(300);
        userInfoBox.setPadding(new Insets(10, 0, 0, 0));
        userInfoBox.setStyle("-fx-background-color: #A9A9A9;");

        // Create center box and add the cat image to it
        VBox centerCatBox = new VBox();
        centerCatBox.setAlignment(Pos.CENTER);
        centerCatBox.setStyle("-fx-background-color: #A9A9A9;");
        centerCatBox.getChildren().add(catImageView);

        // Create right box and add the buttons to it
        VBox rightButtonBox = new VBox();
        rightButtonBox.getChildren().addAll(gameQuitButtons.get("logout"), gameQuitButtons.get("exit"));
        rightButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightButtonBox.setStyle("-fx-background-color: #A9A9A9;");

        //Create a left box and add the buttons to it
        VBox leftButtonBox = new VBox();
        leftButtonBox.getChildren().addAll(petInteractionButtons.get("play"),
                petInteractionButtons.get("wash"),
                petInteractionButtons.get("feed"));
        leftButtonBox.setAlignment(Pos.BOTTOM_LEFT);
        leftButtonBox.setStyle("-fx-background-color: #A9A9A9;");

        //a timeline for default cat animation
        catAnimations.get("idle").play();

        petInteractionButtons.get("feed").setOnAction(event -> {
            petInteractionButtons.get("feed").setDisable(true);
            petInteractionButtons.get("wash").setDisable(true);
            petInteractionButtons.get("play").setDisable(true);
            catAnimations.get("idle").pause();
            catAnimations.get("eating").play();
            if (progressBars.get("hunger").getProgress() < 1.0) {
                progressBars.get("hunger").setProgress(progressBars.get("hunger").getProgress() + 0.3);
                ServiceCaller.updatePet(pet.getId(),
                        (int) (progressBars.get("hunger").getProgress() * 100),
                        (int) (progressBars.get("happiness").getProgress() * 100),
                        (int) (progressBars.get("cleanness").getProgress() * 100));
                Result<Integer> scoreResult = ServiceCaller.getScore(user.getId());
                if (scoreResult.isSuccess()) {
                    user.setScore(scoreResult.getData());
                    gameAreaLabels.get("score").setText("Score: " + user.getScore());
                }
            }
        });
        catAnimations.get("eating").setOnFinished(event -> {
            catAnimations.get("idle").play();
            petInteractionButtons.get("feed").setDisable(false);
            petInteractionButtons.get("wash").setDisable(false);
            petInteractionButtons.get("play").setDisable(false);
        });

        petInteractionButtons.get("wash").setOnAction(event -> {
            petInteractionButtons.get("feed").setDisable(true);
            petInteractionButtons.get("wash").setDisable(true);
            petInteractionButtons.get("play").setDisable(true);
            catAnimations.get("idle").pause();
            catAnimations.get("washing").play();
            if (progressBars.get("cleanness").getProgress() < 1.0) {
                progressBars.get("cleanness").setProgress(progressBars.get("cleanness").getProgress() + 0.3);
                ServiceCaller.updatePet(pet.getId(),
                        (int) (progressBars.get("hunger").getProgress() * 100),
                        (int) (progressBars.get("happiness").getProgress() * 100),
                        (int) (progressBars.get("cleanness").getProgress() * 100));
                Result<Integer> scoreResult = ServiceCaller.getScore(user.getId());
                if (scoreResult.isSuccess()) {
                    user.setScore(scoreResult.getData());
                    gameAreaLabels.get("score").setText("Score: " + user.getScore());
                }
            }
        });
        catAnimations.get("washing").setOnFinished(event -> {
            catAnimations.get("idle").play();
            petInteractionButtons.get("feed").setDisable(false);
            petInteractionButtons.get("wash").setDisable(false);
            petInteractionButtons.get("play").setDisable(false);
        });

        petInteractionButtons.get("play").setOnAction(event -> {
            petInteractionButtons.get("feed").setDisable(true);
            petInteractionButtons.get("wash").setDisable(true);
            petInteractionButtons.get("play").setDisable(true);
            catAnimations.get("idle").pause();
            catAnimations.get("playing").play();
            if (progressBars.get("happiness").getProgress() < 1.0) {
                progressBars.get("happiness").setProgress(progressBars.get("happiness").getProgress() + 0.3);
                ServiceCaller.updatePet(pet.getId(),
                        (int) (progressBars.get("hunger").getProgress() * 100),
                        (int) (progressBars.get("happiness").getProgress() * 100),
                        (int) (progressBars.get("cleanness").getProgress() * 100));
                Result<Integer> scoreResult = ServiceCaller.getScore(user.getId());
                if (scoreResult.isSuccess()) {
                    user.setScore(scoreResult.getData());
                    gameAreaLabels.get("score").setText("Score: " + user.getScore());
                }
            }
        });
        catAnimations.get("playing").setOnFinished(event -> {
            catAnimations.get("idle").play();
            petInteractionButtons.get("feed").setDisable(false);
            petInteractionButtons.get("wash").setDisable(false);
            petInteractionButtons.get("play").setDisable(false);
        });

        // Create a BorderPaneGame and add the buttonBoxes and stateBarBox to it
        BorderPane borderPaneGame = new BorderPane();
        borderPaneGame.setTop(topStateBarBox);
        borderPaneGame.setTop(new VBox(topStateBarBox, userInfoBox));
        borderPaneGame.setLeft(leftButtonBox);
        borderPaneGame.setRight(rightButtonBox);
        borderPaneGame.setCenter(centerCatBox);

        // Create a scene with the borderPane as root
        Scene gameScene = new Scene(borderPaneGame, screenWidth, screenHeight);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Virtual Pet Game");
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);
        primaryStage.show();

        loginButton.setOnAction(event -> {
            String username = loginUsernameField.getText();
            String password = loginPasswordField.getText();
            Result<User> loginResult = ServiceCaller.logIn(username, password);
            if (loginResult.isSuccess()) {
                loginUsernameField.clear();
                loginPasswordField.clear();
                user = loginResult.getData();
                Result<Pet> petResult = ServiceCaller.getPet(user.getId());
                if (petResult.isSuccess()) {
                    pet = petResult.getData();
                    primaryStage.setScene(gameScene);
                    primaryStage.setTitle("Virtual Pet Game");
                    progressBars.get("happiness").setProgress((double) pet.getHappiness() / 100);
                    progressBars.get("hunger").setProgress((double) pet.getHunger() / 100);
                    progressBars.get("cleanness").setProgress((double) pet.getCleanness() / 100);
                    gameAreaLabels.get("name").setText("Name: " + pet.getName());
                    gameAreaLabels.get("score").setText("Score: " + user.getScore());
                    progressBarsTimeline.play();
                }
            }
        });

        registerButton.setOnAction(event -> {
            String username = registerUsernameField.getText();
            String password = registerPasswordField.getText();
            String email = registerEmailField.getText();
            String petName = registerPetNameField.getText();
            Result<User> registerResult = ServiceCaller.signUp(username, email, password, petName);
            if (registerResult.isSuccess()) {
                registerUsernameField.clear();
                registerPasswordField.clear();
                registerEmailField.clear();
                registerPetNameField.clear();
                user = registerResult.getData();
                Result<Pet> petResult = ServiceCaller.getPet(user.getId());
                if (petResult.isSuccess()) {
                    pet = petResult.getData();
                    primaryStage.setScene(gameScene);
                    primaryStage.setTitle("Virtual Pet Game");
                    progressBars.get("happiness").setProgress((double) pet.getHappiness() / 100);
                    progressBars.get("hunger").setProgress((double) pet.getHunger() / 100);
                    progressBars.get("cleanness").setProgress((double) pet.getCleanness() / 100);
                    gameAreaLabels.get("name").setText("Name: " + pet.getName());
                    gameAreaLabels.get("score").setText("Score: " + user.getScore());
                    progressBarsTimeline.play();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}