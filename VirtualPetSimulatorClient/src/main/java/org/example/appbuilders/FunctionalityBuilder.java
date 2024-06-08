package org.example.appbuilders;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import org.example.ClientApplication;
import org.example.Result;
import org.example.ServiceCaller;
import org.example.entities.Pet;
import org.example.entities.User;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public abstract class FunctionalityBuilder {
    private static final Map<String, String> buttonToAnimation = new HashMap<>() {{
        put("feed", "eating");
        put("wash", "washing");
        put("play", "playing");
    }};
    private static final Map<String, String> buttonToStat = new HashMap<>() {{
        put("feed", "hunger");
        put("wash", "cleanness");
        put("play", "happiness");
    }};

    public static void bindHyperlinks(ClientApplication app, Stage primaryStage) {
        app.getHyperlinks().get("toSignUp").setOnAction(event -> {
            primaryStage.setScene(app.getScenes().get("signUp"));
            primaryStage.setTitle("Create Account");
        });

        app.getHyperlinks().get("toLogIn").setOnAction(event1 -> {
            primaryStage.setScene(app.getScenes().get("logIn"));
            primaryStage.setTitle("Login");
        });
    }

    public static void addFunctionalityToButtons(ClientApplication clientApplication, Stage primaryStage) {
        addFunctionalityToGameEnterButtons(clientApplication, primaryStage);
        addFunctionalityToGameQuitButtons(clientApplication, primaryStage);
        addFunctionalityToPetInteractionButtons(clientApplication);
    }

    private static void addFunctionalityToGameEnterButtons(ClientApplication app, Stage primaryStage) {
        app.getGameEnterButtons().get("toLogIn").setOnAction(event -> {
            primaryStage.setScene(app.getScenes().get("logIn"));
            primaryStage.setTitle("Login");
        });
        app.getGameEnterButtons().get("toSignUp").setOnAction(event -> {
            primaryStage.setScene(app.getScenes().get("signUp"));
            primaryStage.setTitle("Create Account");
        });
        app.getGameEnterButtons().get("toHomeFromLogIn").setOnAction(event -> {
            primaryStage.setScene(app.getScenes().get("home"));
            primaryStage.setTitle("Welcome to Virtual Pet Simulator!");
        });
        app.getGameEnterButtons().get("toHomeFromSignUp").setOnAction(event -> {
            primaryStage.setScene(app.getScenes().get("home"));
            primaryStage.setTitle("Welcome to Virtual Pet Simulator!");
        });

        app.getGameEnterButtons().get("logIn").setOnAction(event -> {
            app.getLoginErrorMessage().setVisible(false);
            String identifier = app.getInputFields().get("identifier").getText().toUpperCase();
            String password = app.getInputFields().get("logInPassword").getText().toUpperCase();
            if(identifier.isEmpty() || password.isEmpty()){
                app.getLoginErrorMessage().setText("Write your username and password first");
                app.getLoginErrorMessage().setVisible(true);
            } else {
                Result<User> loginResult = ServiceCaller.logIn(identifier, password);
                if (loginResult.isSuccess()) {
                    handleSuccessfulUserFetching(app, loginResult.getData());
                    Result<Pet> petResult = ServiceCaller.getPet(app.getUser().getId());
                    if (petResult.isSuccess()) {
                        handleSuccessfulPetFetching(app, primaryStage, petResult.getData());
                        Result<Integer> updatedScore = ServiceCaller.getScore(app.getUser().getId());
                        if (updatedScore.isSuccess()) {
                            app.getLoginErrorMessage().setVisible(false);
                            app.getUser().setScore(updatedScore.getData());
                            app.getGameAreaLabels().get("score").setText("Score: " + app.getUser().getScore());
                        } else {
                            app.getLoginErrorMessage().setText(updatedScore.getError());
                            app.getLoginErrorMessage().setVisible(true);
                        }
                    } else {
                        app.getLoginErrorMessage().setText(petResult.getError());
                        app.getLoginErrorMessage().setVisible(true);
                    }
                } else {
                    app.getLoginErrorMessage().setText(loginResult.getError());
                    app.getLoginErrorMessage().setVisible(true);
                }
            }
        });

        app.getGameEnterButtons().get("signUp").setOnAction(event -> {
            app.getSignUpErrorMessage().setVisible(false);
            String username = app.getInputFields().get("username").getText().toUpperCase();
            String password = app.getInputFields().get("signUpPassword").getText().toUpperCase();
            String email = app.getInputFields().get("email").getText().toUpperCase();
            String petName = app.getInputFields().get("petName").getText().toUpperCase();
            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || petName.isEmpty()) {
                app.getSignUpErrorMessage().setText("All fields must be filled.");
                app.getSignUpErrorMessage().setVisible(true);
            } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                app.getSignUpErrorMessage().setText("Invalid email format.");
                app.getSignUpErrorMessage().setVisible(true);
            } else {
                Result<User> registerResult = ServiceCaller.signUp(username, email, password, petName);
                if (registerResult.isSuccess()) {
                    handleSuccessfulUserFetching(app, registerResult.getData());
                    Result<Pet> petResult = ServiceCaller.getPet(app.getUser().getId());
                    if (petResult.isSuccess()) {
                        handleSuccessfulPetFetching(app, primaryStage, petResult.getData());
                    } else {
                        app.getSignUpErrorMessage().setText(petResult.getError());
                        app.getSignUpErrorMessage().setVisible(true);
                    }
                } else {
                    app.getSignUpErrorMessage().setText(registerResult.getError());
                    app.getSignUpErrorMessage().setVisible(true);
                }
            }
        });

        app.getGameEnterButtons().get("homeExit").setOnAction(event -> {
            Platform.exit();
        });
    }

    private static void handleSuccessfulUserFetching(ClientApplication app, User user) {
        app.setUser(user);
        app.getInputFields().forEach((key, value) -> value.clear());
    }

    private static void handleSuccessfulPetFetching(ClientApplication app, Stage primaryStage, Pet pet) {
        app.setPet(pet);
        primaryStage.setScene(app.getScenes().get("game"));
        primaryStage.setTitle("Virtual Pet Game");
        app.getProgressBars().get("happiness").setProgress((double) pet.getHappiness() / 100);
        app.getProgressBars().get("hunger").setProgress((double) pet.getHunger() / 100);
        app.getProgressBars().get("cleanness").setProgress((double) pet.getCleanness() / 100);
        app.getGameAreaLabels().get("name").setText("Name: " + pet.getName());
        app.getGameAreaLabels().get("score").setText("Score: " + app.getUser().getScore());
        app.getProgressBarsTimeline().play();
        app.getCatAnimations().get("idle").play();
    }

    private static void addFunctionalityToGameQuitButtons(ClientApplication app, Stage primaryStage) {
        Media byeCatSound = new Media(ClientApplication.class.getResource("/Sounds/byeCatSound.mp3").toString());
        MediaPlayer byeCatMediaPlayer = new MediaPlayer(byeCatSound);
        app.getGameQuitButtons().get("exit").setOnAction(event -> {
            Result<Void> saveResult = ServiceCaller.savePet(app.getPet().getId(),
                    (int) (app.getProgressBars().get("hunger").getProgress() * 100),
                    (int) (app.getProgressBars().get("happiness").getProgress() * 100),
                    (int) (app.getProgressBars().get("cleanness").getProgress() * 100));
            if (saveResult.isSuccess()) {
                Platform.exit();
            } else {
                System.out.println("An error occurred while saving your pet: " + saveResult.getError());
            }
        });
        app.getGameQuitButtons().get("logout").setOnAction(event -> {
            byeCatMediaPlayer.play();
            Result<Void> saveResult = ServiceCaller.savePet(app.getPet().getId(),
                    (int) (app.getProgressBars().get("hunger").getProgress() * 100),
                    (int) (app.getProgressBars().get("happiness").getProgress() * 100),
                    (int) (app.getProgressBars().get("cleanness").getProgress() * 100));
            if (saveResult.isSuccess()) {
                primaryStage.setScene(app.getScenes().get("logIn"));
                primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Login...");
                primaryStage.setResizable(false);
                primaryStage.setMaximized(false);
                primaryStage.show();
                app.setUser(null);
                app.setPet(null);
            } else {
                System.out.println("An error occurred while saving your pet: " + saveResult.getError());
            }
        });
    }

    private static void addFunctionalityToPetInteractionButtons(ClientApplication app) {
        for (String buttonName : app.getPetInteractionButtons().keySet()) {
            app.getPetInteractionButtons().get(buttonName).setOnAction(event -> {
                setAllGameSceneButtonsState(app, true);
                app.getCatAnimations().get("idle").pause();
                app.getCatAnimations().get(buttonToAnimation.get(buttonName)).play();
                app.getSounds().get(buttonName).play();
                ProgressBar statBar = app.getProgressBars().get(buttonToStat.get(buttonName));
                if (statBar.getProgress() < 1.0) {
                    int scoreDifference = updatePetStat(app, buttonName);
                    if (scoreDifference != 0) {
                        app.getUser().setScore(app.getUser().getScore() + scoreDifference);
                        app.getGameAreaLabels().get("score").setText("Score: " + app.getUser().getScore());
                    }
                }
            });
        }
    }

    private static int updatePetStat(ClientApplication app, String buttonClicked) {
        switch (buttonClicked) {
            case "feed" -> {
                int oldHunger = (int) (app.getProgressBars().get("hunger").getProgress() * 100);
                app.getProgressBars().get("hunger").setProgress(
                        Math.min(1.0, app.getProgressBars().get("hunger").getProgress() + 0.3));
                int newHunger = (int) (app.getProgressBars().get("hunger").getProgress() * 100);
                app.getProgressBars().get("cleanness").setProgress(
                        app.getProgressBars().get("cleanness").getProgress() - 0.1);
                return newHunger - oldHunger;
            }
            case "wash" -> {
                int oldCleanness = (int) (app.getProgressBars().get("cleanness").getProgress() * 100);
                app.getProgressBars().get("cleanness").setProgress(
                        Math.min(1.0, app.getProgressBars().get("cleanness").getProgress() + 0.75));
                int newCleanness = (int) (app.getProgressBars().get("cleanness").getProgress() * 100);
                app.getProgressBars().get("happiness").setProgress(
                        app.getProgressBars().get("happiness").getProgress() - 0.1);
                return newCleanness - oldCleanness;
            }
            case "play" -> {
                int oldHappiness = (int) (app.getProgressBars().get("happiness").getProgress() * 100);
                app.getProgressBars().get("happiness").setProgress(
                        Math.min(1.0, app.getProgressBars().get("happiness").getProgress() + 0.3));
                int newHappiness = (int) (app.getProgressBars().get("happiness").getProgress() * 100);
                app.getProgressBars().get("cleanness").setProgress(
                        app.getProgressBars().get("cleanness").getProgress() - 0.1);
                return newHappiness - oldHappiness;
            }
            default -> {
                return 0;
            }
        }
    }

    public static void addAnimationEndHandlers(ClientApplication app) {
        for (String animationName : app.getCatAnimations().keySet()) {
            if (animationName.equals("idle")) continue;
            app.getCatAnimations().get(animationName).setOnFinished(event -> {
                app.getCatAnimations().get("idle").play();
                setAllGameSceneButtonsState(app, false);
            });
        }
        for (MediaPlayer sound : app.getSounds().values()) {
            sound.setOnEndOfMedia(sound::stop);
        }
    }

    private static void setAllGameSceneButtonsState(ClientApplication app, boolean disabled) {
        app.getGameQuitButtons().values().forEach(button -> button.setDisable(disabled));
        app.getPetInteractionButtons().values().forEach(button -> button.setDisable(disabled));
    }
}
