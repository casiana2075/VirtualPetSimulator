package org.example;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.example.appbuilders.FunctionalityBuilder;
import org.example.appbuilders.UserInterfaceBuilder;
import org.example.entities.Pet;
import org.example.entities.User;

import java.util.Map;

@Getter @Setter
public class ClientApplication extends Application {
    private Map<String, ImageView> imageViews;
    private Map<String, ProgressBar> progressBars;
    private Timeline progressBarsTimeline;
    private Font font;
    private Label loginErrorMessage;
    private Label signUpErrorMessage;
    private Map<String, Label> gameAreaLabels;
    private Map<String, Button> petInteractionButtons;
    private Map<String, Button> gameQuitButtons;
    private ImageView catImageView;
    private Map<String, Timeline> catAnimations;
    private Map<String, Label> staticLabels;
    private Map<String, Hyperlink> hyperlinks;
    private Map<String, TextField> inputFields;
    private Map<String, Button> gameEnterButtons;
    private Map<String, Pane> boxes;
    private Map<String, Scene> scenes;
    private Map<String, MediaPlayer> sounds;

    private User user = new User();
    private Pet pet = new Pet();

    public synchronized User getCurrentUser() {
        return user;
    }

    public synchronized Pet getCurrentPet() {
        return pet;
    }

    private void buildUserInterface() {
        UserInterfaceBuilder.createStatsImageViews(this);
        UserInterfaceBuilder.createProgressBars(this);
        UserInterfaceBuilder.createProgressBarsTimeline(this);
        UserInterfaceBuilder.loadFont(this);
        UserInterfaceBuilder.createGameAreaLabels(this);
        UserInterfaceBuilder.createPetInteractionButtons(this);
        UserInterfaceBuilder.createGameQuitButtons(this);
        UserInterfaceBuilder.createCatImageView(this);
        UserInterfaceBuilder.createCatAnimations(this);
        UserInterfaceBuilder.createStaticLabels(this);
        UserInterfaceBuilder.createHyperlinks(this);
        UserInterfaceBuilder.createInputFields(this);
        UserInterfaceBuilder.createGameEnterButtons(this);
        UserInterfaceBuilder.createBoxes(this);
        UserInterfaceBuilder.buildScenes(this);
        UserInterfaceBuilder.loadSoundEffects(this);
    }

    private void addFunctionality(Stage primaryStage) {
        FunctionalityBuilder.bindHyperlinks(this, primaryStage);
        FunctionalityBuilder.addFunctionalityToButtons(this, primaryStage);
        FunctionalityBuilder.addAnimationEndHandlers(this);
    }

    private void launchHomeScene(Stage primaryStage) {
        primaryStage.setScene(scenes.get("home"));
        primaryStage.setTitle("Virtual Pet Game");
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);
        primaryStage.show();
    }

    private void startAutosaver() {
        Thread autosaverThread = new Thread(new Autosaver(this, 3));
        autosaverThread.setDaemon(true);
        autosaverThread.start();
    }

    @Override
    public void start(Stage primaryStage) {
        buildUserInterface();
        addFunctionality(primaryStage);
        launchHomeScene(primaryStage);
        startAutosaver();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
