/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlproject;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PHURITS
 */
public class MenuController implements Initializable, Commons {

    private Stage stage;
    //MainMenu
    @FXML
    private Button startBTN, aboutBTN, quitBTN;

    //About
    @FXML
    private Button aboutBackBTN;

    //Setup
    @FXML
    private Button add1, add10, add50, add100, add500, add1000, setupBackBTN, startGame, reset;

    @FXML
    private TextField bankText = new TextField();

    @FXML
    private MediaPlayer mp;

    static int bank;

    private String musicButton = "buttonclick.wav";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //MAINMENU
    @FXML
    public void startBTN() {
        stage = (Stage) startBTN.getScene().getWindow();
        bank = 0;

        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("Setup.fxml"));
            Scene scene = new Scene(root, W_WIDTH, W_HEIGHT);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void aboutBTN() {
        stage = (Stage) aboutBTN.getScene().getWindow();

        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("About.fxml"));
            Scene scene = new Scene(root, W_WIDTH, W_HEIGHT);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void quitBTN() {
        stage = (Stage) quitBTN.getScene().getWindow();
        stage.close();

    }

    //ABOUT
    @FXML
    public void aboutBackBTN() {
        stage = (Stage) aboutBackBTN.getScene().getWindow();
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root, W_WIDTH, W_HEIGHT);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SETUP
    @FXML
    public void addMoney(ActionEvent e) {
        if (e.getSource() == add1) {
            bank++;
            bankText.setText("$" + bank);
        } else if (e.getSource() == add10) {
            bank = bank + 10;
            bankText.setText("$" + bank);
        } else if (e.getSource() == add50) {
            bank = bank + 50;
            bankText.setText("$" + bank);
        } else if (e.getSource() == add100) {
            bank = bank + 100;
            bankText.setText("$" + bank);
        } else if (e.getSource() == add500) {
            bank = bank + 500;
            bankText.setText("$" + bank);
        } else if (e.getSource() == add1000) {
            bank = bank + 1000;
            bankText.setText("$" + bank);
        } else if (e.getSource() == reset) {
            bank = 0;
            bankText.setText("$" + bank);
        } else {
            System.out.println("IMPOSSIBLE TO ASSIGN");
        }
    }

    @FXML
    public void setupBackBTN() {
        stage = (Stage) setupBackBTN.getScene().getWindow();
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root, W_WIDTH, W_HEIGHT);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void startGame() {
        if (bank != 0) {
            stage = (Stage) startGame.getScene().getWindow();
            try {
                Parent root = (Parent) FXMLLoader.load(getClass().getResource("Game.fxml"));
                Scene scene = new Scene(root, W_WIDTH, W_HEIGHT);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
