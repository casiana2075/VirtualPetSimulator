package org.example;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.entities.Pet;
import org.example.entities.User;

import java.util.Map;

public abstract class FunctionalityBuilder {
    public static void bindHyperlinks(Stage primaryStage, Map<String, Scene> scenes, Map<String, Hyperlink> hyperlinks) {
        hyperlinks.get("toSignUp").setOnAction(event -> {
            primaryStage.setScene(scenes.get("signUp"));
            primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Create An Account...");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        hyperlinks.get("toLogIn").setOnAction(event1 -> {
            primaryStage.setScene(scenes.get("logIn"));
            primaryStage.setTitle("Back to Previous Page");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });
    }

    public static void addFunctionalityToButtons(ClientApplication clientApplication,
                                                 Stage primaryStage,
                                                 Map<String, Scene> scenes,
                                                 Map<String, Button> gameEnterButtons,
                                                 Map<String, Button> gameExitButtons,
                                                 Map<String, Button> petInteractionButtons,
                                                 Map<String, TextField> inputFields,
                                                 Map<String, ProgressBar> progressBars,
                                                 Map<String, Label> gameAreaLabels,
                                                 Map<String, Timeline> catAnimations,
                                                 Timeline progressBarsTimeline) {
        addFunctionalityToGameEnterButtons(clientApplication,
                primaryStage,
                gameEnterButtons,
                scenes,
                inputFields,
                progressBars,
                gameAreaLabels,
                catAnimations,
                progressBarsTimeline);
        addFunctionalityToGameExitButtons(clientApplication,
                primaryStage,
                scenes,
                gameExitButtons,
                progressBars);
        addFunctionalityToPetInteractionButtons(clientApplication,
                petInteractionButtons,
                progressBars,
                gameAreaLabels,
                catAnimations);
    }

    private static void addFunctionalityToGameEnterButtons(ClientApplication clientApplication,
                                                           Stage primaryStage,
                                                           Map<String, Button> gameEnterButtons,
                                                           Map<String, Scene> scenes,
                                                           Map<String, TextField> inputFields,
                                                           Map<String, ProgressBar> progressBars,
                                                           Map<String, Label> gameAreaLabels,
                                                           Map<String, Timeline> catAnimations,
                                                           Timeline progressBarsTimeline) {
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
        gameEnterButtons.get("toHomeFromLogIn").setOnAction(event -> {
            primaryStage.setScene(scenes.get("home"));
            primaryStage.setTitle("Home");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });
        gameEnterButtons.get("toHomeFromSignUp").setOnAction(event -> {
            primaryStage.setScene(scenes.get("home"));
            primaryStage.setTitle("Home");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        gameEnterButtons.get("logIn").setOnAction(event -> {
            String identifier = inputFields.get("identifier").getText();
            String password = inputFields.get("logInPassword").getText();
            Result<User> loginResult = ServiceCaller.logIn(identifier, password);
            if (loginResult.isSuccess()) {
                inputFields.get("identifier").clear();
                inputFields.get("logInPassword").clear();
                clientApplication.setUser(loginResult.getData());
                Result<Pet> petResult = ServiceCaller.getPet(clientApplication.getUser().getId());
                if (petResult.isSuccess()) {
                    clientApplication.setPet(petResult.getData());
                    primaryStage.setScene(scenes.get("game"));
                    primaryStage.setTitle("Virtual Pet Game");
                    progressBars.get("happiness").setProgress((double) clientApplication.getPet().getHappiness() / 100);
                    progressBars.get("hunger").setProgress((double) clientApplication.getPet().getHunger() / 100);
                    progressBars.get("cleanness").setProgress((double) clientApplication.getPet().getCleanness() / 100);
                    gameAreaLabels.get("name").setText("Name: " + clientApplication.getPet().getName());
                    gameAreaLabels.get("score").setText("Score: " + clientApplication.getUser().getScore());
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
                clientApplication.setUser(registerResult.getData());
                Result<Pet> petResult = ServiceCaller.getPet(clientApplication.getUser().getId());
                if (petResult.isSuccess()) {
                    clientApplication.setPet(petResult.getData());
                    primaryStage.setScene(scenes.get("game"));
                    primaryStage.setTitle("Virtual Pet Game");
                    progressBars.get("happiness").setProgress((double) clientApplication.getPet().getHappiness() / 100);
                    progressBars.get("hunger").setProgress((double) clientApplication.getPet().getHunger() / 100);
                    progressBars.get("cleanness").setProgress((double) clientApplication.getPet().getCleanness() / 100);
                    gameAreaLabels.get("name").setText("Name: " + clientApplication.getPet().getName());
                    gameAreaLabels.get("score").setText("Score: " + clientApplication.getUser().getScore());
                    progressBarsTimeline.play();
                }
            }
        });
    }

    private static void addFunctionalityToGameExitButtons(ClientApplication clientApplication,
                                                          Stage primaryStage,
                                                          Map<String, Scene> scenes,
                                                          Map<String, Button> gameExitButtons,
                                                          Map<String, ProgressBar> progressBars) {
        gameExitButtons.get("exit").setOnAction(event -> {
            ServiceCaller.updatePet(clientApplication.getPet().getId(),
                    (int) (progressBars.get("hunger").getProgress() * 100),
                    (int) (progressBars.get("happiness").getProgress() * 100),
                    (int) (progressBars.get("cleanness").getProgress() * 100));
            Platform.exit();
        });
        gameExitButtons.get("logout").setOnAction(event -> {
            ServiceCaller.updatePet(clientApplication.getPet().getId(),
                    (int) (progressBars.get("hunger").getProgress() * 100),
                    (int) (progressBars.get("happiness").getProgress() * 100),
                    (int) (progressBars.get("cleanness").getProgress() * 100));
            primaryStage.setScene(scenes.get("logIn"));
            primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Login...");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
            clientApplication.setUser(null);
            clientApplication.setPet(null);
        });
    }

    private static void addFunctionalityToPetInteractionButtons(ClientApplication clientApplication,
                                                               Map<String, Button> petInteractionButtons,
                                                               Map<String, ProgressBar> progressBars,
                                                               Map<String, Label> gameAreaLabels,
                                                               Map<String, Timeline> catAnimations) {

        petInteractionButtons.get("feed").setOnAction(event -> {
            petInteractionButtons.get("feed").setDisable(true);
            petInteractionButtons.get("wash").setDisable(true);
            petInteractionButtons.get("play").setDisable(true);
            catAnimations.get("idle").pause();
            catAnimations.get("eating").play();
            if (progressBars.get("hunger").getProgress() < 1.0) {
                progressBars.get("hunger").setProgress(progressBars.get("hunger").getProgress() + 0.3);
                ServiceCaller.updatePet(clientApplication.getPet().getId(),
                        (int) (progressBars.get("hunger").getProgress() * 100),
                        (int) (progressBars.get("happiness").getProgress() * 100),
                        (int) (progressBars.get("cleanness").getProgress() * 100));
                Result<Integer> scoreResult = ServiceCaller.getScore(clientApplication.getUser().getId());
                if (scoreResult.isSuccess()) {
                    clientApplication.getUser().setScore(scoreResult.getData());
                    gameAreaLabels.get("score").setText("Score: " + clientApplication.getUser().getScore());
                }
            }
        });

        petInteractionButtons.get("wash").setOnAction(event -> {
            petInteractionButtons.get("feed").setDisable(true);
            petInteractionButtons.get("wash").setDisable(true);
            petInteractionButtons.get("play").setDisable(true);
            catAnimations.get("idle").pause();
            catAnimations.get("washing").play();
            if (progressBars.get("cleanness").getProgress() < 1.0) {
                progressBars.get("cleanness").setProgress(progressBars.get("cleanness").getProgress() + 0.3);
                ServiceCaller.updatePet(clientApplication.getPet().getId(),
                        (int) (progressBars.get("hunger").getProgress() * 100),
                        (int) (progressBars.get("happiness").getProgress() * 100),
                        (int) (progressBars.get("cleanness").getProgress() * 100));
                Result<Integer> scoreResult = ServiceCaller.getScore(clientApplication.getUser().getId());
                if (scoreResult.isSuccess()) {
                    clientApplication.getUser().setScore(scoreResult.getData());
                    gameAreaLabels.get("score").setText("Score: " + clientApplication.getUser().getScore());
                }
            }
        });

        petInteractionButtons.get("play").setOnAction(event -> {
            petInteractionButtons.get("feed").setDisable(true);
            petInteractionButtons.get("wash").setDisable(true);
            petInteractionButtons.get("play").setDisable(true);
            catAnimations.get("idle").pause();
            catAnimations.get("playing").play();
            if (progressBars.get("happiness").getProgress() < 1.0) {
                progressBars.get("happiness").setProgress(progressBars.get("happiness").getProgress() + 0.3);
                ServiceCaller.updatePet(clientApplication.getPet().getId(),
                        (int) (progressBars.get("hunger").getProgress() * 100),
                        (int) (progressBars.get("happiness").getProgress() * 100),
                        (int) (progressBars.get("cleanness").getProgress() * 100));
                Result<Integer> scoreResult = ServiceCaller.getScore(clientApplication.getUser().getId());
                if (scoreResult.isSuccess()) {
                    clientApplication.getUser().setScore(scoreResult.getData());
                    gameAreaLabels.get("score").setText("Score: " + clientApplication.getUser().getScore());
                }
            }
        });
    }

    public static void addAnimationEndHandlers(Map<String, Timeline> catAnimations,
                                               Map<String, Button> petInteractionButtons) {
        catAnimations.get("eating").setOnFinished(event -> {
            catAnimations.get("idle").play();
            petInteractionButtons.get("feed").setDisable(false);
            petInteractionButtons.get("wash").setDisable(false);
            petInteractionButtons.get("play").setDisable(false);
        });

        catAnimations.get("washing").setOnFinished(event -> {
            catAnimations.get("idle").play();
            petInteractionButtons.get("feed").setDisable(false);
            petInteractionButtons.get("wash").setDisable(false);
            petInteractionButtons.get("play").setDisable(false);
        });

        catAnimations.get("playing").setOnFinished(event -> {
            catAnimations.get("idle").play();
            petInteractionButtons.get("feed").setDisable(false);
            petInteractionButtons.get("wash").setDisable(false);
            petInteractionButtons.get("play").setDisable(false);
        });
    }
}
