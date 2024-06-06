package org.example;

import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

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
}
