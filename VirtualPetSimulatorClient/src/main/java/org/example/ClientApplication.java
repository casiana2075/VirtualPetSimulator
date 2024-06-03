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

import java.io.InputStream;

public class ClientApplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        ProgressBar lifeBar = new ProgressBar();
        ProgressBar foodBar = new ProgressBar();
        ProgressBar cleanBar = new ProgressBar();

        int coins = 100; // PLACEHOLDER for api coins
        int score = 1425; // PLACEHOLDER for api score
        String petName = "Pisu"; // PLACEHOLDER for api petName

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

        // Top labels box
        Label coinsLabel = new Label("Coins: " + coins + "$");
        Label nameLabel = new Label("Name: " + petName);
        Label scoreLabel = new Label("Score: " + score);
        InputStream is = getClass().getResourceAsStream("/Fonts/PixelatedFont.ttf");
        Font pixelatedFont = Font.loadFont(is, 20);
        coinsLabel.setFont(pixelatedFont);
        nameLabel.setFont(pixelatedFont);
        scoreLabel.setFont(pixelatedFont);

        // Left button box
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

        // Center Cat Box
        Image catTailUpImage = new Image(getClass().getResourceAsStream("/CatAnimations/catTailUp.png"));
        Image catTailDownImage = new Image(getClass().getResourceAsStream("/CatAnimations/catTailDown.png"));
        ImageView catImageView = new ImageView(catTailUpImage);
        catImageView.setFitWidth(550);
        catImageView.setFitHeight(550);

        //login fields
        Label loginTitleLabel = new Label("Please login to see your beloved pet!");
        loginTitleLabel.setFont(pixelatedFont);
        TextField loginUsernameField = new TextField();
        loginUsernameField.setPromptText("Username");
        loginUsernameField.setFont(pixelatedFont);
        PasswordField loginPasswordField = new PasswordField();
        loginPasswordField.setPromptText("Password");
        loginPasswordField.setFont(pixelatedFont);
        Button loginButton = new Button("Login");
        loginButton.setFont(pixelatedFont);
        Button homeButton = new Button("Home");
        homeButton.setFont(pixelatedFont);
        Label promptLabel = new Label("...or you don't have an account? ");
        promptLabel.setFont(pixelatedFont);
        Hyperlink registerLink = new Hyperlink("Register here");
        registerLink.setFont(pixelatedFont);
        HBox loginPromptBox = new HBox();
        loginPromptBox.getChildren().addAll(promptLabel, registerLink);
        loginPromptBox.setAlignment(Pos.CENTER);

        loginButton.setOnAction(event -> {
            String username = loginUsernameField.getText();
            String password = loginPasswordField.getText();
            // login verification code here
        });

        //register fields
        Label registerTitleLabel = new Label("Create an account to have a lovely pet!");
        registerTitleLabel.setFont(pixelatedFont);
        TextField registerUsernameField = new TextField();
        registerUsernameField.setPromptText("Username");
        registerUsernameField.setFont(pixelatedFont);
        PasswordField registerPasswordField = new PasswordField();
        registerPasswordField.setPromptText("Create a password");
        registerPasswordField.setFont(pixelatedFont);
        TextField registerPetNameField = new TextField();
        registerPetNameField.setPromptText("Your Pet Name");
        registerPetNameField.setFont(pixelatedFont);
        TextField registerEmailField = new TextField();
        registerEmailField.setPromptText("Email Address");
        registerEmailField.setFont(pixelatedFont);
        Button registerButton = new Button("Register");
        registerButton.setFont(pixelatedFont);
        Label registerPromptLabel = new Label("...or you already have an account? ");
        registerPromptLabel.setFont(pixelatedFont);
        Hyperlink loginLink = new Hyperlink("Login here");
        loginLink.setFont(pixelatedFont);
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
        Scene registerScene = new Scene(registerBox, 1200, 800);
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
        Scene loginScene = new Scene(loginBox, 1200, 800);//scene for login
        logoutButton.setOnAction(event -> {//action of logout button
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Welcome to Virtual Pet Simulator! Please Login...");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
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
        Scene homeScene = new Scene(homeBox, 1200, 800); // scene for home
        homeButton.setOnAction(event -> { // action of home button
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Home");
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
        centerCatBox.setAlignment(Pos.CENTER);
        centerCatBox.setStyle("-fx-background-color: #A9A9A9;");
        centerCatBox.getChildren().add(catImageView);

        // Create right box and add the buttons to it
        VBox rightButtonBox = new VBox();
        rightButtonBox.getChildren().addAll(logoutButton, exitButton);
        rightButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightButtonBox.setStyle("-fx-background-color: #A9A9A9;");

        //Create a left box and add the buttons to it
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
            washButton.setDisable(true);
            playButton.setDisable(true);
            catAnimation.pause();
            catEatingAnimation.play();
            if (foodBar.getProgress() < 1.0) {
                foodBar.setProgress(foodBar.getProgress() + 0.3);
            }
        });
        catEatingAnimation.setOnFinished(event -> {
            catAnimation.play();
            washButton.setDisable(false);
            playButton.setDisable(false);
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
            feedButton.setDisable(true);
            playButton.setDisable(true);
            catAnimation.pause();
            catWashAnimation.play();
            if (cleanBar.getProgress() < 1.0) {
                cleanBar.setProgress(cleanBar.getProgress() + 0.3);
            }
        });
        catWashAnimation.setOnFinished(event -> {
            catAnimation.play();
            feedButton.setDisable(false);
            playButton.setDisable(false);
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
            feedButton.setDisable(true);
            washButton.setDisable(true);
            catAnimation.pause();
            catPlayAnimation.play();
            if (lifeBar.getProgress() < 1.0) {
                lifeBar.setProgress(lifeBar.getProgress() + 0.3);
            }
        });
        catPlayAnimation.setOnFinished(event -> {
            catAnimation.play();
            feedButton.setDisable(false);
            washButton.setDisable(false);
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

        // Create a BorderPaneGame and add the buttonBoxes and stateBarBox to it
        BorderPane borderPaneGame = new BorderPane();
        borderPaneGame.setTop(topStateBarBox);
        borderPaneGame.setTop(new VBox(topStateBarBox, userInfoBox));
        borderPaneGame.setLeft(leftButtonBox);
        borderPaneGame.setRight(rightButtonBox);
        borderPaneGame.setCenter(centerCatBox);

        // Create a scene with the borderPane as root
        Scene gameScene = new Scene(borderPaneGame, 1200, 800);
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

//when running the app I want the app to start from a stage where I
// have two buttons called login(loginBttn) and create account(createAccBttn) and a title
// "Virtual Pet Simulator" which is an image called gameCover in resources/Items