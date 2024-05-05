package com.project.Models;

public class Game {

    private Integer id;
    private String username;
    private int steps;
    private boolean isGameOver;

    public Game(){}

    public Game(Integer id, String un, int s, boolean go){
        this.id = id;
        this.username = un;
        this.steps = s;
        this.isGameOver = go;
    }

    public Game( String un, int s, boolean go){

        this.username = un;
        this.steps = s;
        this.isGameOver = go;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getSteps() {
        return steps;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}

/*
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GridExample extends Application {
    private Circle ball;
    private int ballX = 0;
    private int ballY = 0;

    @Override
    public void start(Stage stage) throws Exception {
        GridPane grid = new GridPane();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Circle circle = new Circle(25);
                circle.setFill(Color.WHITE);
                circle.setStroke(Color.BLACK);
                GridPane.setRowIndex(circle, row);
                GridPane.setColumnIndex(circle, col);
                grid.getChildren().add(circle);
            }
        }

        ball = new Circle(25, Color.BLUE);
        ball.setStroke(Color.BLACK);
        GridPane.setRowIndex(ball, ballY);
        GridPane.setColumnIndex(ball, ballX);
        grid.getChildren().add(ball);

        Scene scene = new Scene(grid, 400, 400);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    moveBall(0, -1);
                    break;
                case DOWN:
                    moveBall(0, 1);
                    break;
                case LEFT:
                    moveBall(-1, 0);
                    break;
                case RIGHT:
                    moveBall(1, 0);
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    private void moveBall(int deltaX, int deltaY) {
        int newX = ballX + deltaX;
        int newY = ballY + deltaY;

        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return; // don't move outside the grid
        }

        GridPane.setRowIndex(ball, newY);
        GridPane.setColumnIndex(ball, newX);
        ballX = newX;
        ballY = newY;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 */