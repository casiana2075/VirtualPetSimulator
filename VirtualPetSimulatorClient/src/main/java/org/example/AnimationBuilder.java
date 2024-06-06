package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class AnimationBuilder {
    public static Timeline createIdleAnimation(ClientApplication clientApplication, ImageView catImageView) {
        Image catTailUpImage = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catTailUp.png"));
        Image catTailDownImage = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catTailDown.png"));
        Timeline catIdleAnimation = new Timeline(
                new KeyFrame(Duration.seconds(1.0), event -> catImageView.setImage(catTailUpImage)),
                new KeyFrame(Duration.seconds(1.5), event -> catImageView.setImage(catTailDownImage))
        );
        catIdleAnimation.setCycleCount(Timeline.INDEFINITE);
        return catIdleAnimation;
    }

    public static Timeline createEatingAnimation(ClientApplication clientApplication, ImageView catImageView) {
        Image catEating1Image = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catEating1.png"));
        Image catEating2Image = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catEating2.png"));
        Timeline catEatingAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.7), event -> catImageView.setImage(catEating1Image)),
                new KeyFrame(Duration.seconds(1.0), event -> catImageView.setImage(catEating2Image))
        );
        catEatingAnimation.setCycleCount(5);
        return catEatingAnimation;
    }

    public static Timeline createWashingAnimation(ClientApplication clientApplication, ImageView catImageView) {
        Image catBath1Image = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catBath1.png"));
        Image catBath2Image = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catBath2.png"));
        Image catBath3Image = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catBath3.png"));
        Image catBath4Image = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catBath3.1.png"));
        Timeline catWashingAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.0), event -> catImageView.setImage(catBath1Image)),
                new KeyFrame(Duration.seconds(0.5), event -> catImageView.setImage(catBath2Image)),
                new KeyFrame(Duration.seconds(1.5), event -> catImageView.setImage(catBath3Image)),
                new KeyFrame(Duration.seconds(2.5), event -> catImageView.setImage(catBath4Image))
        );
        catWashingAnimation.setCycleCount(3);
        return catWashingAnimation;
    }

    public static Timeline createPlayingAnimation(ClientApplication clientApplication, ImageView catImageView) {
        Image catPlaying1Image = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catPlaying1.png"));
        Image catPlaying2Image = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catPlaying2.png"));
        Image catPlaying3Image = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catPlaying3.png"));
        Image catPlaying4Image = new Image(clientApplication
                .getClass()
                .getResourceAsStream("/CatAnimations/catPlaying4.png"));

        Timeline catPlayingAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.0), event -> catImageView.setImage(catPlaying1Image)),
                new KeyFrame(Duration.seconds(1.0), event -> catImageView.setImage(catPlaying2Image)),
                new KeyFrame(Duration.seconds(2.0), event -> catImageView.setImage(catPlaying3Image)),
                new KeyFrame(Duration.seconds(2.5), event -> catImageView.setImage(catPlaying4Image)),
                new KeyFrame(Duration.seconds(3.0), event -> catImageView.setImage(catPlaying3Image)),
                new KeyFrame(Duration.seconds(3.5), event -> catImageView.setImage(catPlaying4Image)),
                new KeyFrame(Duration.seconds(4.0), event -> catImageView.setImage(catPlaying3Image)),
                new KeyFrame(Duration.seconds(4.5), event -> catImageView.setImage(catPlaying4Image)),
                new KeyFrame(Duration.seconds(5.0), event -> catImageView.setImage(catPlaying3Image)),
                new KeyFrame(Duration.seconds(5.5), event -> catImageView.setImage(catPlaying4Image))
        );
        catPlayingAnimation.setCycleCount(1);
        return catPlayingAnimation;
    }
}
