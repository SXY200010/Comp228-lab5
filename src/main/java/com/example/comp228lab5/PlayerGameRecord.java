package com.example.comp228lab5;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.beans.property.*;

public class PlayerGameRecord {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty gameTitle;
    private final StringProperty playingDate;
    private final IntegerProperty score;

    public PlayerGameRecord(String firstName, String lastName, String gameTitle, String playingDate, int score) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.gameTitle = new SimpleStringProperty(gameTitle);
        this.playingDate = new SimpleStringProperty(playingDate);
        this.score = new SimpleIntegerProperty(score);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty gameTitleProperty() {
        return gameTitle;
    }

    public StringProperty playingDateProperty() {
        return playingDate;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }
}

