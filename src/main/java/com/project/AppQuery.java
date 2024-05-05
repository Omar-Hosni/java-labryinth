package com.project;

import com.project.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.project.Models.Game;

public class AppQuery {

    private DBConnection c = new DBConnection();

    public void switchScene(ActionEvent event, String fxml, String name, String title)
    {
        Parent root = null;
        if(name != null)
        {
            try{
                FXMLLoader loader = new FXMLLoader(AppQuery.class.getResource(fxml));
                root = loader.load();
                GameController gameController = loader.getController();
                gameController.setUserInformation(name);
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                root = FXMLLoader.load(AppQuery.class.getResource(fxml));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void signUpQuery(User user)
    {
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet rs = null;

        try{
            c.getDBConn();
            psCheckUserExist = c.getCon().prepareStatement("SELECT * FROM USER WHERE EMAIL=?");
            psCheckUserExist.setString(1, user.getEmail());
            rs = psCheckUserExist.executeQuery();

            //if user exists then alert
            if(rs.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You can not use this email");
                alert.show();
            }else{
                psInsert = c.getCon().prepareStatement("INSERT INTO USER(name,email,password) VALUES(?,?,?)");
                psInsert.setString(1,user.getName());
                psInsert.setString(2,user.getEmail());
                psInsert.setString(3, user.getPassword());
                psInsert.executeUpdate();

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{

            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }

            if(psInsert != null){
                try{
                    psInsert.close();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }

            if(psCheckUserExist != null){
                try{
                    psCheckUserExist.close();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }

            if(c != null){
                c.closeConnection();
            }

        }
    }

    public void loginUserQuery(ActionEvent event, String email, String password)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBConnection c = new DBConnection();

        try
        {
            c.getDBConn();
            ps = c.getCon().prepareStatement("SELECT name, email, password FROM USER WHERE email=? AND password=?");
            ps.setString(1,email);
            ps.setString(2,password);
            rs = ps.executeQuery();

            if(!rs.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User does not exist");
                alert.show();
            }
            else{
                while(rs.next()){
                    String retreivedPassword = rs.getString("password");
                    if(retreivedPassword.equals(password)){
                        String retrievedName = rs.getString("name");
                        switchScene(event, "Game.fxml", retrievedName, "Game");

                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password is incorrect");
                        alert.show();

                    }
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{

            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }

            if(c != null){
                c.closeConnection();
            }

        }

    }


    public void addGameToDB(Game game) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{

            DBConnection c = new DBConnection();
            c.getDBConn();
            ps = c.getCon().prepareStatement("INSERT INTO GAME(username, steps, target) VALUES(?,?,?)");
            ps.setString(1,game.getUsername());
            ps.setInt(2,game.getSteps());
            ps.setString(3, game.getIsGameOver() == true ? "true" : "false");
            ps.executeUpdate();

            rs = ps.getResultSet();

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        finally
        {
            if(ps != null)
                ps.close();

            if(rs!= null)
                rs.close();

            c.closeConnection();

        }
    }
}

