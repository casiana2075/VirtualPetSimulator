package org.example;

import javafx.animation.Timeline;
import javafx.application.Application;
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
        FunctionalityBuilder.addFunctionalityToButtons(this,
                primaryStage,
                scenes,
                gameEnterButtons,
                gameQuitButtons,
                petInteractionButtons,
                inputFields,
                progressBars,
                gameAreaLabels,
                catAnimations,
                progressBarsTimeline);
        FunctionalityBuilder.addAnimationEndHandlers(catAnimations, petInteractionButtons);

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