/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlproject;

import java.nio.file.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author PHURITS
 */
public class Main extends Application implements Commons{

    public static Window publicStage;

    @Override
    public void start(Stage primaryStage) {
        try {
           
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root,W_WIDTH,W_HEIGHT);
            Stage publicStage = primaryStage;
            
            
            
            publicStage.setScene(scene);
            publicStage.setResizable(false);
            publicStage.setTitle("BLACKJACK by OOPisHELL");
            publicStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
