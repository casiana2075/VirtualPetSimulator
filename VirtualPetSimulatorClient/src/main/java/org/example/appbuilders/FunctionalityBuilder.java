package org.example.appbuilders;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.example.ClientApplication;
import org.example.Result;
import org.example.ServiceCaller;
import org.example.entities.Pet;
import org.example.entities.User;

public abstract class FunctionalityBuilder {
    public static void bindHyperlinks(ClientApplication app, Stage primaryStage) {
        app.getHyperlinks().get("toSignUp").setOnAction(event -> {
            primaryStage.setScene(app.getScenes().get("signUp"));
            primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Create An Account...");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        app.getHyperlinks().get("toLogIn").setOnAction(event1 -> {
            primaryStage.setScene(app.getScenes().get("logIn"));
            primaryStage.setTitle("Back to Previous Page");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
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
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });
        app.getGameEnterButtons().get("toSignUp").setOnAction(event -> {
            primaryStage.setScene(app.getScenes().get("signUp"));
            primaryStage.setTitle("Create Account");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });
        app.getGameEnterButtons().get("toHomeFromLogIn").setOnAction(event -> {
            primaryStage.setScene(app.getScenes().get("home"));
            primaryStage.setTitle("Home");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });
        app.getGameEnterButtons().get("toHomeFromSignUp").setOnAction(event -> {
            primaryStage.setScene(app.getScenes().get("home"));
            primaryStage.setTitle("Home");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        });

        app.getGameEnterButtons().get("logIn").setOnAction(event -> {
            String identifier = app.getInputFields().get("identifier").getText();
            String password = app.getInputFields().get("logInPassword").getText();
            Result<User> loginResult = ServiceCaller.logIn(identifier, password);
            if (loginResult.isSuccess()) {
                app.getInputFields().get("identifier").clear();
                app.getInputFields().get("logInPassword").clear();
                app.setUser(loginResult.getData());
                Result<Pet> petResult = ServiceCaller.getPet(app.getUser().getId());
                if (petResult.isSuccess()) {
                    app.setPet(petResult.getData());
                    primaryStage.setScene(app.getScenes().get("game"));
                    primaryStage.setTitle("Virtual Pet Game");
                    app.getProgressBars().get("happiness").setProgress((double) app.getPet().getHappiness() / 100);
                    app.getProgressBars().get("hunger").setProgress((double) app.getPet().getHunger() / 100);
                    app.getProgressBars().get("cleanness").setProgress((double) app.getPet().getCleanness() / 100);
                    app.getGameAreaLabels().get("name").setText("Name: " + app.getPet().getName());
                    app.getGameAreaLabels().get("score").setText("Score: " + app.getUser().getScore());
                    app.getProgressBarsTimeline().play();
                    app.getCatAnimations().get("idle").play();
                }
            }
        });
        app.getGameEnterButtons().get("signUp").setOnAction(event -> {
            String username = app.getInputFields().get("username").getText();
            String password = app.getInputFields().get("signUpPassword").getText();
            String email = app.getInputFields().get("email").getText();
            String petName = app.getInputFields().get("petName").getText();
            Result<User> registerResult = ServiceCaller.signUp(username, email, password, petName);
            if (registerResult.isSuccess()) {
                app.getInputFields().get("username").clear();
                app.getInputFields().get("signUpPassword").clear();
                app.getInputFields().get("email").clear();
                app.getInputFields().get("petName").clear();
                app.setUser(registerResult.getData());
                Result<Pet> petResult = ServiceCaller.getPet(app.getUser().getId());
                if (petResult.isSuccess()) {
                    app.setPet(petResult.getData());
                    primaryStage.setScene(app.getScenes().get("game"));
                    primaryStage.setTitle("Virtual Pet Game");
                    app.getProgressBars().get("happiness").setProgress((double) app.getPet().getHappiness() / 100);
                    app.getProgressBars().get("hunger").setProgress((double) app.getPet().getHunger() / 100);
                    app.getProgressBars().get("cleanness").setProgress((double) app.getPet().getCleanness() / 100);
                    app.getGameAreaLabels().get("name").setText("Name: " + app.getPet().getName());
                    app.getGameAreaLabels().get("score").setText("Score: " + app.getUser().getScore());
                    app.getProgressBarsTimeline().play();
                    app.getCatAnimations().get("idle").play();
                }
            }
        });
    }

    private static void addFunctionalityToGameQuitButtons(ClientApplication app, Stage primaryStage) {
        app.getGameQuitButtons().get("exit").setOnAction(event -> {
            ServiceCaller.updatePet(app.getPet().getId(),
                    (int) (app.getProgressBars().get("hunger").getProgress() * 100),
                    (int) (app.getProgressBars().get("happiness").getProgress() * 100),
                    (int) (app.getProgressBars().get("cleanness").getProgress() * 100));
            Platform.exit();
        });
        app.getGameQuitButtons().get("logout").setOnAction(event -> {
            ServiceCaller.updatePet(app.getPet().getId(),
                    (int) (app.getProgressBars().get("hunger").getProgress() * 100),
                    (int) (app.getProgressBars().get("happiness").getProgress() * 100),
                    (int) (app.getProgressBars().get("cleanness").getProgress() * 100));
            primaryStage.setScene(app.getScenes().get("logIn"));
            primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Login...");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
            app.setUser(null);
            app.setPet(null);
        });
    }

    private static void addFunctionalityToPetInteractionButtons(ClientApplication app) {

        app.getPetInteractionButtons().get("feed").setOnAction(event -> {
            app.getPetInteractionButtons().get("feed").setDisable(true);
            app.getPetInteractionButtons().get("wash").setDisable(true);
            app.getPetInteractionButtons().get("play").setDisable(true);
            app.getCatAnimations().get("idle").pause();
            app.getCatAnimations().get("eating").play();
            if (app.getProgressBars().get("hunger").getProgress() < 1.0) {
                app.getProgressBars().get("hunger").setProgress(app.getProgressBars().get("hunger").getProgress() + 0.3);
                ServiceCaller.updatePet(app.getPet().getId(),
                        (int) (app.getProgressBars().get("hunger").getProgress() * 100),
                        (int) (app.getProgressBars().get("happiness").getProgress() * 100),
                        (int) (app.getProgressBars().get("cleanness").getProgress() * 100));
                Result<Integer> scoreResult = ServiceCaller.getScore(app.getUser().getId());
                if (scoreResult.isSuccess()) {
                    app.getUser().setScore(scoreResult.getData());
                    app.getGameAreaLabels().get("score").setText("Score: " + app.getUser().getScore());
                }
            }
        });

        app.getPetInteractionButtons().get("wash").setOnAction(event -> {
            app.getPetInteractionButtons().get("feed").setDisable(true);
            app.getPetInteractionButtons().get("wash").setDisable(true);
            app.getPetInteractionButtons().get("play").setDisable(true);
            app.getCatAnimations().get("idle").pause();
            app.getCatAnimations().get("washing").play();
            if (app.getProgressBars().get("cleanness").getProgress() < 1.0) {
                app.getProgressBars().get("cleanness").setProgress(app.getProgressBars().get("cleanness").getProgress() + 0.3);
                ServiceCaller.updatePet(app.getPet().getId(),
                        (int) (app.getProgressBars().get("hunger").getProgress() * 100),
                        (int) (app.getProgressBars().get("happiness").getProgress() * 100),
                        (int) (app.getProgressBars().get("cleanness").getProgress() * 100));
                Result<Integer> scoreResult = ServiceCaller.getScore(app.getUser().getId());
                if (scoreResult.isSuccess()) {
                    app.getUser().setScore(scoreResult.getData());
                    app.getGameAreaLabels().get("score").setText("Score: " + app.getUser().getScore());
                }
            }
        });

        app.getPetInteractionButtons().get("play").setOnAction(event -> {
            app.getPetInteractionButtons().get("feed").setDisable(true);
            app.getPetInteractionButtons().get("wash").setDisable(true);
            app.getPetInteractionButtons().get("play").setDisable(true);
            app.getCatAnimations().get("idle").pause();
            app.getCatAnimations().get("playing").play();
            if (app.getProgressBars().get("happiness").getProgress() < 1.0) {
                app.getProgressBars().get("happiness").setProgress(app.getProgressBars().get("happiness").getProgress() + 0.3);
                ServiceCaller.updatePet(app.getPet().getId(),
                        (int) (app.getProgressBars().get("hunger").getProgress() * 100),
                        (int) (app.getProgressBars().get("happiness").getProgress() * 100),
                        (int) (app.getProgressBars().get("cleanness").getProgress() * 100));
                Result<Integer> scoreResult = ServiceCaller.getScore(app.getUser().getId());
                if (scoreResult.isSuccess()) {
                    app.getUser().setScore(scoreResult.getData());
                    app.getGameAreaLabels().get("score").setText("Score: " + app.getUser().getScore());
                }
            }
        });
    }

    public static void addAnimationEndHandlers(ClientApplication app) {
        app.getCatAnimations().get("eating").setOnFinished(event -> {
            app.getCatAnimations().get("idle").play();
            app.getPetInteractionButtons().get("feed").setDisable(false);
            app.getPetInteractionButtons().get("wash").setDisable(false);
            app.getPetInteractionButtons().get("play").setDisable(false);
        });

        app.getCatAnimations().get("washing").setOnFinished(event -> {
            app.getCatAnimations().get("idle").play();
            app.getPetInteractionButtons().get("feed").setDisable(false);
            app.getPetInteractionButtons().get("wash").setDisable(false);
            app.getPetInteractionButtons().get("play").setDisable(false);
        });

        app.getCatAnimations().get("playing").setOnFinished(event -> {
            app.getCatAnimations().get("idle").play();
            app.getPetInteractionButtons().get("feed").setDisable(false);
            app.getPetInteractionButtons().get("wash").setDisable(false);
            app.getPetInteractionButtons().get("play").setDisable(false);
        });
    }
}
