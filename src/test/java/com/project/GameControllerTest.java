package com.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    public void setUp() {
        gameController = new GameController();
    }

    @Test
    public void testInitialize() {
        gameController.initialize();

        // Verify if the grid pane is created
        assertNotNull(gameController.gameGridPane);

        // Verify if the walls are added
        int wallCount = gameController.gameGridPane.getChildren().stream()
                .filter(node -> node.getStyleClass().contains("wall"))
                .toArray().length;
        assertEquals(15, wallCount);

        // Verify if the ball is placed
        assertNotNull(gameController.ball);
        assertTrue(gameController.gameGridPane.getChildren().contains(gameController.ball));

        // Verify if the goal ball is placed
        assertNotNull(gameController.targetBall);
        assertTrue(gameController.gameGridPane.getChildren().contains(gameController.targetBall));

    }

    @Test
    public void testCreateGridPane() {
        gameController.GRID_SIZE = 3;
        gameController.createGridPane();

        // Verify if the grid pane is created with the correct number of rows and columns
        assertEquals(gameController.GRID_SIZE, gameController.gameGridPane.getRowCount());
        assertEquals(gameController.GRID_SIZE, gameController.gameGridPane.getColumnCount());

        // Verify if each cell in the grid pane has the correct properties set
        for (int i = 0; i < gameController.GRID_SIZE; i++) {
            for (int j = 0; j < gameController.GRID_SIZE; j++) {
                AnchorPane cell = (AnchorPane) gameController.gameGridPane.getChildren().get(i * gameController.GRID_SIZE + j);

                // Verify background color
                assertEquals(Color.WHITE, cell.getBackground().getFills().get(0).getFill());

                // Verify border color
                assertEquals(Color.BLACK, cell.getBorder().getStrokes().get(0).getBottomStroke());

                // Verify cell size
                assertEquals(50, cell.getPrefWidth(), 0);
                assertEquals(50, cell.getPrefHeight(), 0);
            }
        }
    }

    @Test
    public void testAddWalls() {
        gameController.GRID_SIZE = 3;
        gameController.addWalls();

        // Verify if the walls are added
        int wallCount = gameController.gameGridPane.getChildren().stream()
                .filter(node -> node.getStyleClass().contains("wall"))
                .toArray().length;
        assertEquals(15, wallCount);

        // Verify if the walls are placed at valid positions
        for (int i = 0; i < gameController.GRID_SIZE; i++) {
            for (int j = 0; j < gameController.GRID_SIZE; j++) {
                if (!(i == gameController.ballRow && j == gameController.ballCol)) {
                    assertFalse(gameController.gameGridPane.getChildren().contains(gameController.getNodeByRowColumnIndex(i, j)));
                }
            }
        }
    }

    @Test
    public void testPlaceBall() {
        gameController.placeBall();

        // Verify if the ball is placed
        assertNotNull(gameController.ball);
        assertTrue(gameController.gameGridPane.getChildren().contains(gameController.ball));
    }


}