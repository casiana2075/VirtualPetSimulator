package org.example;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
        Map<String, Label> staticLabels = ClientApplicationBuilder.createStaticLabels(font);
        Map<String, Hyperlink> hyperlinks = ClientApplicationBuilder.createHyperlinks(font);
        Map<String, TextField> inputFields = ClientApplicationBuilder.createInputFields(font);
        Map<String, Button> gameEnterButtons = ClientApplicationBuilder.createGameEnterButtons(this, font);
        Map<String, Pane> boxes = ClientApplicationBuilder.createBoxes(staticLabels,
                imageViews,
                progressBars,
                hyperlinks,
                inputFields,
                gameEnterButtons,
                gameAreaLabels,
                catImageView,
                gameQuitButtons,
                petInteractionButtons);

        gameQuitButtons.get("exit").setOnAction(event -> {
            ServiceCaller.updatePet(pet.getId(),
                    (int) (progressBars.get("hunger").getProgress() * 100),
                    (int) (progressBars.get("happiness").getProgress() * 100),
                    (int) (progressBars.get("cleanness").getProgress() * 100));
            Platform.exit();
        });

        Scene registerScene = new Scene(boxes.get("signUp"), screenWidth, screenHeight);
        //action of register here link
        hyperlinks.get("toSignUp").setOnAction(event -> {
            primaryStage.setScene(registerScene);
            primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Create An Account...");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        // Create a login box/scene and add the login fields to it
        Scene loginScene = new Scene(boxes.get("logIn"), screenWidth, screenHeight);//scene for login
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
        hyperlinks.get("toLogIn").setOnAction(event1 -> {
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Back to Previous Page");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        // Create a new scene for the home page
        gameEnterButtons.get("toLogIn").setOnAction(event -> {
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        gameEnterButtons.get("toSignUp").setOnAction(event -> {
            primaryStage.setScene(registerScene);
            primaryStage.setTitle("Create Account");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });
        Scene homeScene = new Scene(boxes.get("home"), screenWidth, screenHeight); // scene for home
        gameEnterButtons.get("toHomeFromLogIn").setOnAction(event -> { // action of home button
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Home");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });
        gameEnterButtons.get("toHomeFromSignUp").setOnAction(event -> { // action of home button
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Home");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

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
        borderPaneGame.setTop(boxes.get("info"));
        borderPaneGame.setLeft(boxes.get("petInteraction"));
        borderPaneGame.setRight(boxes.get("gameQuit"));
        borderPaneGame.setCenter(boxes.get("cat"));

        // Create a scene with the borderPane as root
        Scene gameScene = new Scene(borderPaneGame, screenWidth, screenHeight);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Virtual Pet Game");
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);
        primaryStage.show();

        gameEnterButtons.get("logIn").setOnAction(event -> {
            String identifier = inputFields.get("identifier").getText();
            String password = inputFields.get("logInPassword").getText();
            Result<User> loginResult = ServiceCaller.logIn(identifier, password);
            if (loginResult.isSuccess()) {
                inputFields.get("identifier").clear();
                inputFields.get("logInPassword").clear();
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

        gameEnterButtons.get("signUp").setOnAction(event -> {
            String username = inputFields.get("username").getText();
            String password = inputFields.get("signUpPassword").getText();
            String email = inputFields.get("email").getText();
            String petName = inputFields.get("petName").getText();
            Result<User> registerResult = ServiceCaller.signUp(username, email, password, petName);
            if (registerResult.isSuccess()) {
                inputFields.get("username").clear();
                inputFields.get("signUpPassword").clear();
                inputFields.get("email").clear();
                inputFields.get("petName").clear();
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