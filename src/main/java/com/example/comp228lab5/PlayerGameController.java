package com.example.comp228lab5;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerGameController {
    @FXML
    private TextField playerIdField, firstNameField, lastNameField, addressField, postalCodeField, provinceField, phoneNumberField;
    @FXML
    private TextField gameIdField, gameTitleField;
    @FXML
    private TextField playingDateField, scoreField;
    @FXML
    private TextArea reportArea;
    @FXML
    private TableView<PlayerGameRecord> reportTable;

    @FXML
    private TableColumn<PlayerGameRecord, String> firstNameColumn;

    @FXML
    private TableColumn<PlayerGameRecord, String> lastNameColumn;

    @FXML
    private TableColumn<PlayerGameRecord, String> gameTitleColumn;

    @FXML
    private TableColumn<PlayerGameRecord, String> playingDateColumn;

    @FXML
    private TableColumn<PlayerGameRecord, Integer> scoreColumn;

    @FXML
    public void initialize() {
        // 设置每列与 PlayerGameRecord 对象属性的绑定
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        gameTitleColumn.setCellValueFactory(cellData -> cellData.getValue().gameTitleProperty());
        playingDateColumn.setCellValueFactory(cellData -> cellData.getValue().playingDateProperty());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
    }


    // Insert Player into the database
    @FXML
    private void insertPlayer() {
        String sql = "INSERT INTO Xiaoyu_Shi_player (player_id, first_name, last_name, address, postal_code, province, phone_number) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(playerIdField.getText()));
            pstmt.setString(2, firstNameField.getText());
            pstmt.setString(3, lastNameField.getText());
            pstmt.setString(4, addressField.getText());
            pstmt.setString(5, postalCodeField.getText());
            pstmt.setString(6, provinceField.getText());
            pstmt.setString(7, phoneNumberField.getText());
            pstmt.executeUpdate();
            reportArea.appendText("Player inserted successfully!\n");
        } catch (Exception e) {
            reportArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    // Insert Game into the database
    @FXML
    private void insertGame() {
        String sql = "INSERT INTO Xiaoyu_Shi_game (game_id, game_title) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(gameIdField.getText()));
            pstmt.setString(2, gameTitleField.getText());
            pstmt.executeUpdate();
            reportArea.appendText("Game inserted successfully!\n");
        } catch (Exception e) {
            reportArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void insertPlayerAndGame() {
        String sql = "INSERT INTO Xiaoyu_Shi_player_and_game (player_game_id, player_id, game_id, playing_date, score) " +
                "VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int playerGameId = Integer.parseInt(playerIdField.getText() + gameIdField.getText());
            int playerId = Integer.parseInt(playerIdField.getText());
            int gameId = Integer.parseInt(gameIdField.getText());
            String playingDate = playingDateField.getText();
            int score = Integer.parseInt(scoreField.getText());

            pstmt.setInt(1, playerGameId);
            pstmt.setInt(2, playerId);
            pstmt.setInt(3, gameId);
            pstmt.setString(4, playingDate);
            pstmt.setInt(5, score);

            pstmt.executeUpdate();
            reportArea.appendText("Player-Game relationship inserted successfully!\n");
        } catch (Exception e) {
            reportArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }


    // Query Players and Games
    @FXML
    private void queryPlayerAndGame() {
        String sql = "SELECT p.first_name, p.last_name, g.game_title, pg.playing_date, pg.score " +
                "FROM Xiaoyu_Shi_player_and_game pg " +
                "JOIN Xiaoyu_Shi_player p ON pg.player_id = p.player_id " +
                "JOIN Xiaoyu_Shi_game g ON pg.game_id = g.game_id " +
                "WHERE p.player_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int playerId = Integer.parseInt(playerIdField.getText());
            pstmt.setInt(1, playerId);

            ResultSet rs = pstmt.executeQuery();

            reportTable.getItems().clear();

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String gameTitle = rs.getString("game_title");
                String playingDate = rs.getString("playing_date");
                int score = rs.getInt("score");

                PlayerGameRecord record = new PlayerGameRecord(firstName, lastName, gameTitle, playingDate, score);
                reportTable.getItems().add(record);
            }

            if (!hasResults) {
                reportArea.appendText("No games found for the specified player ID.\n");
            }

        } catch (Exception e) {
            reportArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }


}
