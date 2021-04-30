/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlproject;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PHURITS
 */
public class GameController implements Initializable, Commons {

    private Stage stage;

    @FXML
    private AnchorPane background;

    @FXML
    private ImageView endGamePic;

    @FXML
    private Label endGameText;

    @FXML
    private Button backMainmenu;
    @FXML
    private Button bet1, bet10, bet50, bet100, bet500, bet1000, resetBet;
    @FXML
    private Button deal, hit, stand;
    @FXML
    private TextField betText;
    @FXML
    private TextField balanceText;

    @FXML
    private Text dealerText, playerText;

    @FXML
    private ImageView dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5, dealerCard6;

    @FXML
    private ImageView playerCard1, playerCard2, playerCard3, playerCard4, playerCard5, playerCard6;

    private int pCard[] = new int[6]; //Keep Value of player card
    private int dCard[] = new int[6]; //Keep Value of dealer card;

    private static int pCount = 0; //Count Hand size
    private static int dCount = 0;

    private int suit = 0;
    private int cardNum = 0;

    private int usedCard[][] = new int[4][13];

    private String[][] deckPath = {
        {"ace_of_spades", "2_of_spades", "3_of_spades", "4_of_spades", "5_of_spades", "6_of_spades", "7_of_spades",
            "8_of_spades", "9_of_spades", "10_of_spades", "jack_of_spades", "queen_of_spades",
            "king_of_spades"},
        {"ace_of_hearts", "2_of_hearts", "3_of_hearts", "4_of_hearts", "5_of_hearts", "6_of_hearts", "7_of_hearts",
            "8_of_hearts", "9_of_hearts", "10_of_hearts", "jack_of_hearts", "queen_of_hearts",
            "king_of_hearts"},
        {"ace_of_clubs", "2_of_clubs", "3_of_clubs", "4_of_clubs", "5_of_clubs", "6_of_clubs", "7_of_clubs",
            "8_of_clubs", "9_of_clubs", "10_of_clubs", "jack_of_clubs", "queen_of_clubs", "king_of_clubs"},
        {"ace_of_diamonds", "2_of_diamonds", "3_of_diamonds", "4_of_diamonds", "5_of_diamonds", "6_of_diamonds",
            "7_of_diamonds", "8_of_diamonds", "9_of_diamonds", "10_of_diamonds", "jack_of_diamonds",
            "queen_of_diamonds", "king_of_diamonds"}};

    private String cardBack = "back_cards";

    private int balance = MenuController.bank;  //Load balance from setup
    private int bet;

    private int dealerHand;
    private int playerHand;

    //Check when game is finished?
    private boolean canClicked;
    private boolean gameOver;

    //CODING TIME!!! --------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        balanceText.setText("$" + balance);
        hit.setDisable(true);
        stand.setDisable(true);
        canClicked = false;
        gameOver = false;
        endGamePic.setVisible(false);
        resetGame();

        //Set Value
        pCount = 0;
        dCount = 0;

        endGameText.setVisible(false);

    }

    @FXML
    public void backMainmenu() {
        stage = (Stage) backMainmenu.getScene().getWindow();
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
    public void addbet(ActionEvent e) {
        if (e.getSource() == bet1) {
            bet++;
            betText.setText("$" + bet);
        } else if (e.getSource() == bet10) {
            bet = bet + 10;
            betText.setText("$" + bet);
        } else if (e.getSource() == bet50) {
            bet = bet + 50;
            betText.setText("$" + bet);
        } else if (e.getSource() == bet100) {
            bet = bet + 100;
            betText.setText("$" + bet);
        } else if (e.getSource() == bet500) {
            bet = bet + 500;
            betText.setText("$" + bet);
        } else if (e.getSource() == bet1000) {
            bet = bet + 1000;
            betText.setText("$" + bet);
        } else if (e.getSource() == resetBet) {
            bet = 0;
            betText.setText("$" + bet);
        } else {
            System.out.println("IMPOSSIBLE TO ASSIGN");
        }
        if (bet > balance) {
            bet = balance;
            betText.setText("$" + bet);
        }
    }

    @FXML
    public void deal() {
        if (bet == 0) {
            System.out.println("BET MONEY CAN'T BE ZERO");
        } else {
            //Update Balance
            balance = balance - bet;
            balanceText.setText("$" + balance);

            //Disable Buttons
            startGame(true);
            hit.setDisable(false);
            stand.setDisable(false);

            playerDrawCard(playerCard1);
            playerDrawCard(playerCard2);

            dealerDrawCard(dealerCard1);
            dealerCard2.setVisible(true);
            dealerCard2.setImage(new Image(getClass().getResourceAsStream("images/" + cardBack + ".png")));

            if (playerHand == 21) {
                Blackjack();
            }
        }
    }

    public int checkUsedCard(int suit, int cardNum) {
        if (usedCard[suit][cardNum] == 0) {
            usedCard[suit][cardNum] = usedCard[suit][cardNum] + 1;
            System.out.println("Check Used = 0");
            return 0;

        }
        System.out.println("Check Used = 1");
        return usedCard[suit][cardNum];
    }

    public void calculatePlayerHand() {
        playerHand = 0;
        for (int i = 0; i < pCount; i++) {
            playerHand = playerHand + pCard[i];
            if (playerHand > 21) {
                if (pCard[i] == 11) {
                    pCard[i] = 1;
                    playerHand = playerHand - 10;
                }
            }
        }
        System.out.println("Player Hand = " + playerHand);
        if (playerHand > 21) {
            Bust();
        }
        playerText.setText("Your Hand : " + playerHand);
    }

    public void calculateDealerHand() {
        dealerHand = 0;
        for (int i = 0; i < dCount; i++) {
            dealerHand = dealerHand + dCard[i];
            if (dealerHand > 21) {
                if (dCard[i] == 11) {
                    dCard[i] = 1;
                    dealerHand = dealerHand - 10;
                }
            }
        }

        dealerText.setText("Dealer's Hand : " + dealerHand);
    }

    public void playerDrawCard(ImageView card) {
        Random rand = new Random();

        do {
            suit = rand.nextInt(4);
            cardNum = rand.nextInt(13);

        } while (checkUsedCard(suit, cardNum) == 1);

        //create card
        createCard(card, suit, cardNum);

        //Give value to card;
        int temp = 0;
        if (cardNum == 0) {
            temp = 11;
        } else if (cardNum == 10 || cardNum == 11 || cardNum == 12) {
            temp = 10;
        } else {
            temp = cardNum + 1;
        }

        pCard[pCount] = temp;

        System.out.println("playerDraw");

        pCount = pCount + 1;

        calculatePlayerHand();

    }

    public void dealerDrawCard(ImageView card) {
        Random rand = new Random();

        do {
            suit = rand.nextInt(4);
            cardNum = rand.nextInt(13);

        } while (checkUsedCard(suit, cardNum) == 1);

        //create card
        createCard(card, suit, cardNum);

        int temp = 0;
        if (cardNum == 0) {
            temp = 11;
        } else if (cardNum == 10 || cardNum == 11 || cardNum == 12) {
            temp = 10;
        } else {
            temp = cardNum + 1;
        }

        dCard[dCount] = temp;

        dCount = dCount + 1;

        calculateDealerHand();
    }

    public void createCard(ImageView card, int suit, int cardNum) {
        Image image = new Image(getClass().getResourceAsStream("images/" + deckPath[suit][cardNum] + ".png"));
        card.setImage(image);
        card.setVisible(true);
    }

    public void computerDealer() {

        while (dealerHand < 17) {
            if (dCount == 1) {
                dealerDrawCard(dealerCard2);
            } else if (dCount == 2) {
                dealerDrawCard(dealerCard3);
            } else if (dCount == 3) {
                dealerDrawCard(dealerCard4);
            } else if (dCount == 4) {
                dealerDrawCard(dealerCard5);
            } else if (dCount == 5) {
                dealerDrawCard(dealerCard6);
            }
            calculateDealerHand();
        }
    }

    @FXML
    public void hit() {

        if (pCount == 2) {
            playerDrawCard(playerCard3);
        } else if (pCount == 3) {
            playerDrawCard(playerCard4);
        } else if (pCount == 4) {
            playerDrawCard(playerCard5);
        } else if (pCount == 5) {
            playerDrawCard(playerCard6);
        }

        System.out.println("hitClicked");
    }

    @FXML
    public void stand() {

        computerDealer();

        hit.setDisable(true);
        stand.setDisable(true);

        canClicked = true;

        calculatePoints();

    }

    public void calculatePoints() {
        if (dealerHand > 21) {
            System.out.println("Player WIN");
            endGameText.setText("PLAYER WIN");
            endGamePic.setImage(new Image(getClass().getResourceAsStream("images/win.png")));
            endGamePic.setVisible(true);
            bet = bet * 2;
            balance = balance + bet;
            bet = 0;
            betText.setText("$" + bet);
            balanceText.setText("$" + balance);
        } else if (playerHand > dealerHand) {
            System.out.println("Player WIN");
            endGameText.setText("PLAYER WIN");
            endGamePic.setImage(new Image(getClass().getResourceAsStream("images/win.png")));
            endGamePic.setVisible(true);
            bet = bet * 2;
            balance = balance + bet;
            bet = 0;
            betText.setText("$" + bet);
            balanceText.setText("$" + balance);
        } else if (playerHand == dealerHand) {
            System.out.println("Draw");
            endGameText.setText("DEALER WIN");
            endGamePic.setImage(new Image(getClass().getResourceAsStream("images/lose.png")));
            endGamePic.setVisible(true);
            bet = 0;
            betText.setText("$" + bet);
            balanceText.setText("$" + balance);
        } else {
            System.out.println("Delaer WIN");
            endGameText.setText("DEALER WIN");
            endGamePic.setImage(new Image(getClass().getResourceAsStream("images/lose.png")));
            endGamePic.setVisible(true);
            bet = 0;
            betText.setText("$" + bet);
            balanceText.setText("$" + balance);
        }
        if (balance == 0) {
            System.out.println("GAMEOVER");
            endGameText.setText("GAMEOVER");
            endGamePic.setImage(new Image(getClass().getResourceAsStream("images/gameover.png")));
            endGamePic.setVisible(true);
            gameOver = true;
        }
        //endGameText.setVisible(true);
    }

    public void Bust() {
        System.out.println("BUST");
        endGameText.setText("BUST");
        //endGameText.setVisible(true);
        endGamePic.setImage(new Image(getClass().getResourceAsStream("images/lose.png")));
        endGamePic.setVisible(true);
        bet = 0;
        hit.setDisable(true);
        stand.setDisable(true);

        canClicked = true;
        if (balance == 0) {
            System.out.println("GAMEOVER");
            endGameText.setText("GAMEOVER");
            endGamePic.setImage(new Image(getClass().getResourceAsStream("images/gameover.png")));
        endGamePic.setVisible(true);
            gameOver = true;
        }
    }

    public void Blackjack() {
        System.out.println("BLACKJACK");
        endGameText.setText("BLACKJACK");
        //endGameText.setVisible(true);
        endGamePic.setImage(new Image(getClass().getResourceAsStream("images/blackjack.png")));
        endGamePic.setVisible(true);
        hit.setDisable(true);
        stand.setDisable(true);
        bet = bet * 3;
        balance = balance + bet;
        bet = 0;
        betText.setText("$" + bet);
        balanceText.setText("$" + balance);

        canClicked = true;
    }

    @FXML
    public void clickedContinue() {
        if (gameOver == true) {
            canClicked = false;
            stage = (Stage) background.getScene().getWindow();
            try {
                Parent root = (Parent) FXMLLoader.load(getClass().getResource("Menu.fxml"));
                Scene scene = new Scene(root, W_WIDTH, W_HEIGHT);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (canClicked == true) {

            betText.setText("$" + 0);
            balanceText.setText("$" + balance);
            hit.setDisable(true);
            stand.setDisable(true);
            canClicked = false;
            endGameText.setVisible(false);
            endGamePic.setVisible(false);

            resetGame();

            pCount = 0;
            dCount = 0;
            playerHand = 0;
            dealerHand = 0;
            playerText.setText("Your Hand : " + playerHand);
            dealerText.setText("Dealer's Hand : " + dealerHand);

            startGame(false);

        }
    }

    private void startGame(boolean a) {
        //Disable Action
        hit.setDisable(true);
        stand.setDisable(true);

        //Disable Bet Buttons
        bet1.setDisable(a);
        bet10.setDisable(a);
        bet50.setDisable(a);
        bet100.setDisable(a);
        bet500.setDisable(a);
        bet1000.setDisable(a);
        deal.setDisable(a);
        resetBet.setDisable(a);

    }

    private void resetGame() {
        playerCard1.setVisible(false);
        playerCard2.setVisible(false);
        playerCard3.setVisible(false);
        playerCard4.setVisible(false);
        playerCard5.setVisible(false);
        playerCard6.setVisible(false);

        dealerCard1.setVisible(false);
        dealerCard2.setVisible(false);
        dealerCard3.setVisible(false);
        dealerCard4.setVisible(false);
        dealerCard5.setVisible(false);
        dealerCard6.setVisible(false);

    }

}
