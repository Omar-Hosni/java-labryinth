package com.project;

import com.project.Models.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.sql.SQLException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GameController{

    @FXML
    private Label username;

    @FXML
    public AnchorPane GameScenePane;

    @FXML
    public GridPane gameGridPane;

    @FXML
    public Label stepsLabel = new Label("0");

    public int GRID_SIZE = 50;

    public Circle ball;
    public Circle targetBall;

    public int ballRow; // Initial row position of the ball
    public int ballCol; // Initial column position of the ball

    public int targetBallRow;
    public int targetBallCol;

    public int ballSteps = 0;

    public KeyCode currentDirection = null;
    public boolean isMoving = false;

    private static final Logger logger = LogManager.getLogger();

    public GameController(){}

    public void createGridPane(){
        int GRID_SIZE = 7;
        int CELL_SIZE = 50;

        for(int i=0; i < GRID_SIZE; i++)
        {
            for(int j=0; j < GRID_SIZE; j++){
                Pane cell = new Pane();
                cell.setStyle("-fx-background-color: white; -fx-border-color: black;");
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);
                gameGridPane.add(cell, j, i);
                logger.info("creating cell at index ({}, {})",i,j);
            }
        }
    }

    private Pane createWall() {
        Pane wall = new Pane();

        Random random = new Random();
        int r = random.nextInt(4);

        if(r == 0)
            wall.setStyle("-fx-border-color: black; -fx-border-width: 4px 1 1 1; -fx-background-color: white;");

        else if(r == 1)
            wall.setStyle("-fx-border-color: black; -fx-border-width: 1 4px 1 1; -fx-background-color: white;");

        else if(r == 2)
            wall.setStyle("-fx-border-color: black; -fx-border-width: 1 1 4px 1; -fx-background-color: white;");

        else if(r == 3)
            wall.setStyle("-fx-border-color: black; -fx-border-width: 1 1 1 4px; -fx-background-color: white;");


        wall.setPrefSize(50, 50);
        return wall;
    }

    /**
     * Adds walls to the game grid at random positions.
     * Walls are represented by custom wall nodes.
     * Generates 15 walls in total.
     */
    public void addWalls()
    {
        //lets make 10 walls
        for(int i=0; i < 40; i++)
        {
            Random random = new Random();
            int r1 = random.nextInt(7);
            int r2 = random.nextInt(7);

            //before you add a wall in the r1,r2 index, remove the normal pane TODO LATER IF prev approach NOT WORK
            for(Node node: gameGridPane.getChildren()){
                if(GridPane.getRowIndex(node) == r2 && GridPane.getColumnIndex(node) == r1){
                    gameGridPane.getChildren().remove(node);
                    break;
                }
            }
            logger.info("adding wall at cell of index ({}, {})",r1,r2);
            gameGridPane.add(createWall(), r1, r2);
        }

    }

    public void placeBall(){
        ball = new Circle(17, Color.BLUE);
        gameGridPane.add(ball, ballCol,ballRow);
        logger.info("placing ball at index ({}, {})",ballRow, ballCol);
    }

    /**
     * Places a goal ball in the game grid at a random position.
     * The goal ball is represented by a Circle object.
     * The position is randomly determined within the range of the game grid.
     */
    private void placeGoalBall()
    {
        targetBall = new Circle(18);
        targetBall.setFill(Color.TRANSPARENT);
        targetBall.setStroke(Color.BLACK);

        Random random = new Random();
        targetBallCol = random.nextInt(7);
        targetBallRow = random.nextInt(7);
        gameGridPane.add(targetBall, targetBallCol,targetBallRow);
    }

    /**
     * Returns the Node object located at the specified row and column indices in the game grid.
     *
     * @param row The row index of the Node in the game grid.
     * @param col The column index of the Node in the game grid.
     * @return The Node object at the specified row and column indices, or null if not found.
     */
    public Node getNodeByRowColumnIndex(int row, int col) {
        for (Node node : gameGridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }

    /**
     * Adds a key press handler to the game scene.
     * Handles the movement of the ball based on the arrow keys.
     */
    public void addKeyPressHandler() {

        GameScenePane.addEventFilter(KeyEvent.KEY_PRESSED,  event -> {
            KeyCode keyCode = event.getCode();
            int newRow = ballRow;
            int newCol = ballCol;

            isMoving = true;

            while(isMoving != false)
            {
                if (keyCode == KeyCode.UP ) {
                    Pane nextCell = (Pane) getNodeByRowColumnIndex(ballRow - 1, ballCol);
                    Pane currCell = (Pane) getNodeByRowColumnIndex(ballRow, ballCol);

                    if (nextCell.getBorder() == null || (nextCell.getBorder().getStrokes().get(0).getWidths().getBottom() != 4.0 && currCell.getBorder().getStrokes().get(0).getWidths().getTop() != 4.0)) {
                        ballSteps++;
                        newRow--;
                        logger.info("ball moved up");
                    }else{
                        isMoving = false;
                    }

                } else if (keyCode == KeyCode.DOWN && ballRow < GRID_SIZE) {
                    Pane nextCell = (Pane) getNodeByRowColumnIndex(ballRow + 1, ballCol);
                    Pane currCell = (Pane) getNodeByRowColumnIndex(ballRow, ballCol);

                    if (nextCell.getBorder() == null || (nextCell.getBorder().getStrokes().get(0).getWidths().getTop() != 4.0 && currCell.getBorder().getStrokes().get(0).getWidths().getBottom() != 4.0)) {
                        ballSteps++;
                        newRow++;
                        logger.info("ball moved down");
                    }else{
                        isMoving = false;
                    }

                } else if (keyCode == KeyCode.LEFT && ballRow < GRID_SIZE) {
                    Pane nextCell = (Pane) getNodeByRowColumnIndex(ballRow, ballCol - 1);
                    Pane currCell = (Pane) getNodeByRowColumnIndex(ballRow, ballCol);

                    if (nextCell.getBorder() == null || (nextCell.getBorder().getStrokes().get(0).getWidths().getRight() != 4.0 && currCell.getBorder().getStrokes().get(0).getWidths().getLeft() != 4.0)) {
                        ballSteps++;
                        newCol--;
                        logger.info("ball moved left");
                    }else{
                        isMoving = false;
                    }

                } else if (keyCode == KeyCode.RIGHT && ballRow < GRID_SIZE) {


                    Pane nextCell = (Pane) getNodeByRowColumnIndex(ballRow, ballCol + 1);

                    Pane currCell = (Pane) getNodeByRowColumnIndex(ballRow, ballCol);

                    if (nextCell.getBorder() == null || (nextCell.getBorder().getStrokes().get(0).getWidths().getLeft() != 4.0 && currCell.getBorder().getStrokes().get(0).getWidths().getRight() != 4.0)) {
                        ballSteps++;
                        newCol++;
                        logger.info("ball moved right");
                    }else{
                        isMoving = false;
                    }

                }

                gameGridPane.getChildren().remove(ball);
                gameGridPane.add(ball, newCol, newRow);
                ballRow = newRow;
                ballCol = newCol;
                stepsLabel.setText(String.valueOf(ballSteps));

                if(ballSteps == 150){
                    try {
                        canNotReachTarget();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(ballRow == targetBallRow && ballCol == targetBallCol){
                    try {
                        gameOver();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }



        });
    }
    public void initialize() {
        this.stepsLabel.setText("0");
        createGridPane();
        addWalls();
        gameGridPane.getStyleClass().add("thick-borders-gridpane");
        placeBall();
        placeGoalBall();
        addKeyPressHandler();
    }

    private void gameOver() throws SQLException {
        isMoving = false;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Game Over");
        dialog.setHeaderText("You reached the target!");
        dialog.setContentText("Congratulations!");

        ButtonType closeButton = new ButtonType("Close");
        dialog.getDialogPane().getButtonTypes().add(closeButton);
        dialog.showAndWait();

        AppQuery query = new AppQuery();
        Game game = new Game();
        game.setUsername(username.getText());
        game.setGameOver(true);
        game.setSteps(ballSteps);

        System.out.println(game.getUsername());
        System.out.println(game.getIsGameOver());
        System.out.println(game.getSteps());
        System.out.println("ADDING TO DATABASE...");
        query.addGameToDB(game);
    }

    private void canNotReachTarget() throws SQLException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Game Over");
        dialog.setHeaderText(":(");
        dialog.setContentText("Target is not reachable!");

        ButtonType closeButton = new ButtonType("Close");
        dialog.getDialogPane().getButtonTypes().add(closeButton);

        dialog.showAndWait();

        AppQuery query = new AppQuery();
        Game game = new Game();
        game.setUsername(username.getText());
        game.setGameOver(false);
        game.setSteps(ballSteps);

        query.addGameToDB(game);
    }

    public void btnGoBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(GameController.class.getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void setUserInformation(String n)
    {
        username.setText(n);
    }


}
