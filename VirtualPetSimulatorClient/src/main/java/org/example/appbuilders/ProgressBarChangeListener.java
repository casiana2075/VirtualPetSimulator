package org.example.appbuilders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressBar;

public final class ProgressBarChangeListener implements ChangeListener<Number> {
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
