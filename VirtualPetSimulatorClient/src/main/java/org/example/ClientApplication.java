package org.example;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
        UserInterfaceBuilder.setSizes(this);
        Map<String, ImageView> imageViews = UserInterfaceBuilder.createStatsImageViews(this);
        Map<String, ProgressBar> progressBars = UserInterfaceBuilder.createProgressBars();
        Timeline progressBarsTimeline = UserInterfaceBuilder.createProgressBarsTimeline(progressBars);
        Font font = UserInterfaceBuilder.loadFont(this);
        Map<String, Label> gameAreaLabels = UserInterfaceBuilder.createGameAreaLabels(this, font);
        Map<String, Button> petInteractionButtons = UserInterfaceBuilder.createPetInteractionButtons(this);
        Map<String, Button> gameQuitButtons = UserInterfaceBuilder.createGameQuitButtons(this);
        ImageView catImageView = UserInterfaceBuilder.createCatImageView(this);
        Map<String, Timeline> catAnimations = UserInterfaceBuilder.createCatAnimations(this, catImageView);
        Map<String, Label> staticLabels = UserInterfaceBuilder.createStaticLabels(font);
        Map<String, Hyperlink> hyperlinks = UserInterfaceBuilder.createHyperlinks(font);
        Map<String, TextField> inputFields = UserInterfaceBuilder.createInputFields(font);
        Map<String, Button> gameEnterButtons = UserInterfaceBuilder.createGameEnterButtons(this, font);
        Map<String, Pane> boxes = UserInterfaceBuilder.createBoxes(staticLabels,
                imageViews,
                progressBars,
                hyperlinks,
                inputFields,
                gameEnterButtons,
                gameAreaLabels,
                catImageView,
                gameQuitButtons,
                petInteractionButtons);
        Map<String, Scene> scenes = UserInterfaceBuilder.buildScenes(boxes, screenWidth, screenHeight);

        FunctionalityBuilder.bindHyperlinks(primaryStage, scenes, hyperlinks);

        gameQuitButtons.get("exit").setOnAction(event -> {
            ServiceCaller.updatePet(pet.getId(),
                    (int) (progressBars.get("hunger").getProgress() * 100),
                    (int) (progressBars.get("happiness").getProgress() * 100),
                    (int) (progressBars.get("cleanness").getProgress() * 100));
            Platform.exit();
        });


        // Create a login box/scene and add the login fields to it
        gameQuitButtons.get("logout").setOnAction(event -> {//action of logout button
            ServiceCaller.updatePet(pet.getId(),
                    (int) (progressBars.get("hunger").getProgress() * 100),
                    (int) (progressBars.get("happiness").getProgress() * 100),
                    (int) (progressBars.get("cleanness").getProgress() * 100));
            primaryStage.setScene(scenes.get("logIn"));
            primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Login...");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
            user = null;
            pet = null;
        });

        // Create a new scene for the home page
        gameEnterButtons.get("toLogIn").setOnAction(event -> {
            primaryStage.setScene(scenes.get("logIn"));
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        gameEnterButtons.get("toSignUp").setOnAction(event -> {
            primaryStage.setScene(scenes.get("signUp"));
            primaryStage.setTitle("Create Account");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });
        gameEnterButtons.get("toHomeFromLogIn").setOnAction(event -> { // action of home button
            primaryStage.setScene(scenes.get("home"));
            primaryStage.setTitle("Home");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });
        gameEnterButtons.get("toHomeFromSignUp").setOnAction(event -> { // action of home button
            primaryStage.setScene(scenes.get("home"));
            primaryStage.setTitle("Home");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        //a timeline for default cat animation

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

        // Create a scene with the borderPane as root

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
                    primaryStage.setScene(scenes.get("game"));
                    primaryStage.setTitle("Virtual Pet Game");
                    progressBars.get("happiness").setProgress((double) pet.getHappiness() / 100);
                    progressBars.get("hunger").setProgress((double) pet.getHunger() / 100);
                    progressBars.get("cleanness").setProgress((double) pet.getCleanness() / 100);
                    gameAreaLabels.get("name").setText("Name: " + pet.getName());
                    gameAreaLabels.get("score").setText("Score: " + user.getScore());
                    progressBarsTimeline.play();
                    catAnimations.get("idle").play();
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
                    primaryStage.setScene(scenes.get("game"));
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

        primaryStage.setScene(scenes.get("home"));
        primaryStage.setTitle("Virtual Pet Game");
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}