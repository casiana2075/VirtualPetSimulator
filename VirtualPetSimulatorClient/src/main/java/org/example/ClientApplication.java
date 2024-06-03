package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class ClientApplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        ProgressBar lifeBar = new ProgressBar();
        ProgressBar foodBar = new ProgressBar();
        ProgressBar cleanBar = new ProgressBar();

        lifeBar.setProgress(1.0);
        foodBar.setProgress(1.0);
        cleanBar.setProgress(1.0);
        lifeBar.setStyle("-fx-accent: green;");
        foodBar.setStyle("-fx-accent: green;");
        cleanBar.setStyle("-fx-accent: green;");

        lifeBar.setPrefWidth(200);
        foodBar.setPrefWidth(200);
        cleanBar.setPrefWidth(200);

        lifeBar.progressProperty().addListener(new ProgressBarChangeListener(lifeBar));
        foodBar.progressProperty().addListener(new ProgressBarChangeListener(foodBar));
        cleanBar.progressProperty().addListener(new ProgressBarChangeListener(cleanBar));

        // Top button box
        Image heartImage = new Image(getClass().getResourceAsStream("/Items/heart.png"));
        ImageView heartImageView = new ImageView(heartImage);
        Image fishImage = new Image(getClass().getResourceAsStream("/Items/fish.png"));
        ImageView fishImageView = new ImageView(fishImage);
        Image soapImage = new Image(getClass().getResourceAsStream("/Items/soap.png"));
        ImageView soapImageView = new ImageView(soapImage);

        heartImageView.setFitWidth(40);
        heartImageView.setFitHeight(30);
        fishImageView.setFitWidth(40);
        fishImageView.setFitHeight(40);
        soapImageView.setFitWidth(50);
        soapImageView.setFitHeight(45);

        heartImageView.setTranslateX(50);
        heartImageView.setTranslateY(-5);
        fishImageView.setTranslateX(50);
        fishImageView.setTranslateY(-10);
        soapImageView.setTranslateX(50);
        soapImageView.setTranslateY(-15);

        // top labels box
        Label coinsLabel = new Label("Coins: 100$");
        Label nameLabel = new Label("Name: Pisu");
        Label scoreLabel = new Label("Score: 1425");
        coinsLabel.setFont(new Font(25));
        nameLabel.setFont(new Font(25));
        scoreLabel.setFont(new Font(25));

        //Left button box
        Image feedImage = new Image(getClass().getResourceAsStream("/Buttons/feedBttn.png"));
        ImageView feedImageView = new ImageView(feedImage);
        Image washImage = new Image(getClass().getResourceAsStream("/Buttons/washBttn.png"));
        ImageView washImageView = new ImageView(washImage);
        Image playImage = new Image(getClass().getResourceAsStream("/Buttons/playBttn.png"));
        ImageView playImageView = new ImageView(playImage);

        feedImageView.setFitWidth(150);
        feedImageView.setFitHeight(150);
        washImageView.setFitWidth(150);
        washImageView.setFitHeight(150);
        playImageView.setFitWidth(150);
        playImageView.setFitHeight(150);

        Button feedButton = new Button("", feedImageView);
        Button washButton = new Button("", washImageView);
        Button playButton = new Button("", playImageView);

        feedButton.setStyle("-fx-background-color: transparent;");
        washButton.setStyle("-fx-background-color: transparent;");
        playButton.setStyle("-fx-background-color: transparent;");

        feedButton.setOnMouseEntered(event -> {
            feedImageView.setFitWidth(170);
            feedImageView.setFitHeight(170);
        });
        feedButton.setOnMouseExited(event -> {
            feedImageView.setFitWidth(150);
            feedImageView.setFitHeight(150);
        });

        washButton.setOnMouseEntered(event -> {
            washImageView.setFitWidth(170);
            washImageView.setFitHeight(170);
        });
        washButton.setOnMouseExited(event -> {
            washImageView.setFitWidth(150);
            washImageView.setFitHeight(150);
        });

        playButton.setOnMouseEntered(event -> {
            playImageView.setFitWidth(170);
            playImageView.setFitHeight(170);
        });
        playButton.setOnMouseExited(event -> {
            playImageView.setFitWidth(150);
            playImageView.setFitHeight(150);
        });

        // Right button box
        Image exitImage = new Image(getClass().getResourceAsStream("/Buttons/exitBttn.png"));
        ImageView exitImageView = new ImageView(exitImage);
        Image logoutImage = new Image(getClass().getResourceAsStream("/Buttons/logoutBttn.png"));
        ImageView logoutImageView = new ImageView(logoutImage);

        exitImageView.setFitWidth(150);
        exitImageView.setFitHeight(150);
        logoutImageView.setFitWidth(150);
        logoutImageView.setFitHeight(150);

        Button exitButton = new Button("", exitImageView);
        Button logoutButton = new Button("", logoutImageView);

        exitButton.setStyle("-fx-background-color: transparent;");
        logoutButton.setStyle("-fx-background-color: transparent;");

        exitButton.setOnMouseEntered(event -> {
            exitImageView.setFitWidth(170);
            exitImageView.setFitHeight(170);
        });
        exitButton.setOnMouseExited(event -> {
            exitImageView.setFitWidth(150);
            exitImageView.setFitHeight(150);
        });
        exitButton.setOnAction(event -> {
            Platform.exit();
        });

        logoutButton.setOnMouseEntered(event -> {
            logoutImageView.setFitWidth(170);
            logoutImageView.setFitHeight(170);
        });
        logoutButton.setOnMouseExited(event -> {
            logoutImageView.setFitWidth(150);
            logoutImageView.setFitHeight(150);
        });

        // Bottom button box
        Image shopImage = new Image(getClass().getResourceAsStream("/Buttons/shopBttn.png"));
        ImageView shopImageView = new ImageView(shopImage);

        shopImageView.setFitWidth(150);
        shopImageView.setFitHeight(150);

        Button shopButton = new Button("", shopImageView);

        shopButton.setStyle("-fx-background-color: transparent;");

        shopButton.setOnMouseEntered(event -> {
            shopImageView.setFitWidth(170);
            shopImageView.setFitHeight(170);
        });
        shopButton.setOnMouseExited(event -> {
            shopImageView.setFitWidth(150);
            shopImageView.setFitHeight(150);
        });

        // Center Cat Box
        Image catTailUpImage = new Image(getClass().getResourceAsStream("/CatAnimations/catTailUp.png"));
        Image catTailDownImage = new Image(getClass().getResourceAsStream("/CatAnimations/catTailDown.png"));
        ImageView catImageView = new ImageView(catTailUpImage);
        catImageView.setFitWidth(550);
        catImageView.setFitHeight(550);

        //login fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            // login verification code here
        });

        // Create a login box and add the login fields to it
        VBox loginBox = new VBox();
        usernameField.setMaxWidth(300);
        passwordField.setMaxWidth(300);
        loginBox.getChildren().addAll(usernameField, passwordField, loginButton);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setSpacing(20);
        loginBox.setStyle("-fx-background-color: #A9A9A9;");
        //scene for login
        Scene loginScene = new Scene(loginBox, 1200, 800);
        //action of button
        logoutButton.setOnAction(event -> {
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Login...");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();

        });

        // Create a top box and add the progress bars to it
        HBox topStateBarBox = new HBox();
        topStateBarBox.getChildren().addAll(heartImageView, lifeBar, fishImageView, foodBar,soapImageView, cleanBar);
        topStateBarBox.setAlignment(Pos.TOP_CENTER);
        topStateBarBox.setSpacing(50);
        topStateBarBox.setPadding(new Insets(30, 0, 0, 0));
        topStateBarBox.setStyle("-fx-background-color: #A9A9A9;");

        // Create a top box and add the labels to it
        HBox userInfoBox = new HBox();
        userInfoBox.getChildren().addAll(coinsLabel, nameLabel, scoreLabel);
        userInfoBox.setAlignment(Pos.CENTER);
        userInfoBox.setSpacing(300);
        userInfoBox.setPadding(new Insets(10, 0, 0, 0));
        userInfoBox.setStyle("-fx-background-color: #A9A9A9;");

        // Create center box and add the cat image to it
        VBox centerCatBox = new VBox();
        centerCatBox.getChildren().addAll(shopButton);
        centerCatBox.setAlignment(Pos.CENTER);
        centerCatBox.setStyle("-fx-background-color: #A9A9A9;");
        centerCatBox.getChildren().add(catImageView);

        // Create bottom box and add the button to it
        VBox bottomButtonBox = new VBox();
        bottomButtonBox.getChildren().addAll(shopButton);
        bottomButtonBox.setAlignment(Pos.CENTER);
        bottomButtonBox.setStyle("-fx-background-color: #A9A9A9;");

        // Create right box and add the buttons to it
        VBox rightButtonBox = new VBox();
        rightButtonBox.getChildren().addAll(logoutButton, exitButton);
        rightButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightButtonBox.setStyle("-fx-background-color: #A9A9A9;");

        //Create a vertical box and add the buttons to it
        VBox leftButtonBox = new VBox();
        leftButtonBox.getChildren().addAll(feedButton, washButton, playButton);
        leftButtonBox.setAlignment(Pos.BOTTOM_LEFT);
        leftButtonBox.setStyle("-fx-background-color: #A9A9A9;");

        //a timeline for default cat animation
        Timeline catAnimation = new Timeline(
                new KeyFrame(Duration.seconds(1.0), event -> catImageView.setImage(catTailUpImage)),
                new KeyFrame(Duration.seconds(1.5), event -> catImageView.setImage(catTailDownImage))
        );
        catAnimation.setCycleCount(Timeline.INDEFINITE);
        catAnimation.play();

        //feed animation
        Image catEating1Image = new Image(getClass().getResourceAsStream("/CatAnimations/catEating1.png"));
        Image catEating2Image = new Image(getClass().getResourceAsStream("/CatAnimations/catEating2.png"));
        Timeline catEatingAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.7), event -> catImageView.setImage(catEating1Image)),
                new KeyFrame(Duration.seconds(1.0), event -> catImageView.setImage(catEating2Image))
        );
        catEatingAnimation.setCycleCount(5);
        feedButton.setOnAction(event -> {
            catAnimation.pause();
            catEatingAnimation.play();
            if (foodBar.getProgress() < 1.0) {
                foodBar.setProgress(foodBar.getProgress() + 0.3);
            }
        });
        catEatingAnimation.setOnFinished(event -> {
            catAnimation.play();
        });

        //wash animation
        Image catBath1Image = new Image(getClass().getResourceAsStream("/CatAnimations/catBath1.png"));
        Image catBath2Image = new Image(getClass().getResourceAsStream("/CatAnimations/catBath2.png"));
        Image catBath3Image = new Image(getClass().getResourceAsStream("/CatAnimations/catBath3.png"));
        Image catBath4Image = new Image(getClass().getResourceAsStream("/CatAnimations/catBath3.1.png"));
        Timeline catWashAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.0), event -> catImageView.setImage(catBath1Image)),
                new KeyFrame(Duration.seconds(0.5), event -> catImageView.setImage(catBath2Image)),
                new KeyFrame(Duration.seconds(1.5), event -> catImageView.setImage(catBath3Image)),
                new KeyFrame(Duration.seconds(2.5), event -> catImageView.setImage(catBath4Image))
        );
        catWashAnimation.setCycleCount(3);
        washButton.setOnAction(event -> {
            catAnimation.pause();
            catWashAnimation.play();
            if (cleanBar.getProgress() < 1.0) {
                cleanBar.setProgress(cleanBar.getProgress() + 0.3);
            }
        });
        catWashAnimation.setOnFinished(event -> {
            catAnimation.play();
        });

        //play animation
        Image catPlay1Image = new Image(getClass().getResourceAsStream("/CatAnimations/catPlaying1.png"));
        Image catPlay2Image = new Image(getClass().getResourceAsStream("/CatAnimations/catPlaying2.png"));
        Image catPlay3Image = new Image(getClass().getResourceAsStream("/CatAnimations/catPlaying3.png"));
        Image catPlay4Image = new Image(getClass().getResourceAsStream("/CatAnimations/catPlaying4.png"));
        Timeline catPlayAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.0), event -> catImageView.setImage(catPlay1Image)),
                new KeyFrame(Duration.seconds(1.0), event -> catImageView.setImage(catPlay2Image)),
                new KeyFrame(Duration.seconds(2.0), event -> catImageView.setImage(catPlay3Image)),
                new KeyFrame(Duration.seconds(2.5), event -> catImageView.setImage(catPlay4Image)),
                new KeyFrame(Duration.seconds(3.0), event -> catImageView.setImage(catPlay3Image)),
                new KeyFrame(Duration.seconds(3.5), event -> catImageView.setImage(catPlay4Image)),
                new KeyFrame(Duration.seconds(4.0), event -> catImageView.setImage(catPlay3Image)),
                new KeyFrame(Duration.seconds(4.5), event -> catImageView.setImage(catPlay4Image)),
                new KeyFrame(Duration.seconds(5.0), event -> catImageView.setImage(catPlay3Image)),
                new KeyFrame(Duration.seconds(5.5), event -> catImageView.setImage(catPlay4Image))
        );
        catPlayAnimation.setCycleCount(1);
        playButton.setOnAction(event -> {
            catAnimation.pause();
            catPlayAnimation.play();
            if (lifeBar.getProgress() < 1.0) {
                lifeBar.setProgress(lifeBar.getProgress() + 0.3);
            }
        });
        catPlayAnimation.setOnFinished(event -> {
            catAnimation.play();
        });

        //a timeline that decreases the progress value over time
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            if (lifeBar.getProgress() > 0) {
                lifeBar.setProgress(lifeBar.getProgress() - 0.1);
            }
            if (foodBar.getProgress() > 0) {
                foodBar.setProgress(foodBar.getProgress() - 0.1);
            }
            if (cleanBar.getProgress() > 0) {
                cleanBar.setProgress(cleanBar.getProgress() - 0.1);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Create a BorderPane and add the buttonBoxes and stateBarBox to it
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topStateBarBox);
        borderPane.setTop(new VBox(topStateBarBox, userInfoBox));
        borderPane.setLeft(leftButtonBox);
        borderPane.setRight(rightButtonBox);
        borderPane.setBottom(bottomButtonBox);
        borderPane.setCenter(centerCatBox);

        // Create a scene with the borderPane as root
        Scene gameScene = new Scene(borderPane, 1200, 800);
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Virtual Pet Game");
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class ProgressBarChangeListener implements ChangeListener<Number> {
        private final ProgressBar progressBar;

        public ProgressBarChangeListener(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            double progress = newValue.doubleValue();
            if (progress >= 0.7) {
                progressBar.setStyle("-fx-accent: green;");
            } else if (progress >= 0.3) {
                progressBar.setStyle("-fx-accent: yellow;");
            } else {
                progressBar.setStyle("-fx-accent: red;");
            }
        }
    }
}