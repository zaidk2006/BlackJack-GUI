
/*
 * ****************************************************************************
 * Name: Zaid Khoury
 * Date: 11/11/2015 
 * Project Info: CS 182 Lab Project 3
 * Description: This is a project that functions like the famous Blackjack cards
 * game. It works perfectly fine. If there happens to be an Ace in a deck, and
 * the cards add up to be over 21, the value of the ace becomes a 1. There is
 * score keeping in the game. If you want to restart everything in the game, hit
 * the reset button.
 * *****************************************************************************
 *
 *
 * *************************************************************
 * Project Number 4 - Comp Sci 182 - Data Structures (w/ Swing) Start Code -
 * Build your program starting with this code Card Game Copyright 2005
 * Christopher C. Ferguson This code may only be used with the permission of
 * Christopher C. Ferguson
 * *************************************************************
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Project4 extends JFrame implements ActionListener {

    private static int winxpos = 0, winypos = 0;
    private static CardList playerHand = null;
    private static CardList dealerHand = null;
    private static CardList theDeck = null;
    private boolean dealerTurn = false;
    private boolean gameStart = false;
    private static boolean deals = false;
    private JButton resetButton, exitButton, newButton, hitButton, dealButton, stayButton;
    private JPanel northPanel;
    private MyPanel centerPanel;
    private static JFrame myFrame = null;
    private static int winStatus = -1;
    private static JTextField codeField;
    private static String player = "Player Cards :";
    private static String dealer = "Dealer Cards :";
    private static String dealerWin = "";
    private static String playerWin = "";
    private static String start = "";
    private static String push = "";
    private static String value = "";
    private static String dealerValue = "";
    private static String deal = "";
    private static int playerScore = 0;
    private static int dealerScore = 0;
    private static int pushTie = 0;

    ////////////              MAIN      ////////////////////////
    public static void main(String[] args) {
        Project4 tpo = new Project4();
    }

    ////////////            CONSTRUCTOR   /////////////////////
    public Project4() {
        myFrame = this;                 // need a static variable reference to a JFrame object
        northPanel = new JPanel();
        northPanel.setBackground(Color.white);
        resetButton = new JButton("Reset");
        northPanel.add(resetButton);
        resetButton.addActionListener(this);
        hitButton = new JButton("Hit");
        northPanel.add(hitButton);
        hitButton.addActionListener(this);
        dealButton = new JButton("Deal");
        northPanel.add(dealButton);
        dealButton.addActionListener(this);
        stayButton = new JButton("Stay");
        northPanel.add(stayButton);
        stayButton.addActionListener(this);
        exitButton = new JButton("Exit");
        northPanel.add(exitButton);
        exitButton.addActionListener(this);
        getContentPane().add("North", northPanel);
        centerPanel = new MyPanel();
        getContentPane().add("Center", centerPanel);
        theDeck = new CardList(52);
        playerHand = new CardList(0);
        dealerHand = new CardList(0);
        theDeck.shuffle();
        setSize(800, 700);
        setLocation(winxpos, winypos);
        setVisible(true);
    }

    ////////////   BUTTON CLICKS ///////////////////////////
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton) {
            dispose();
            System.exit(0);
        }
        if (e.getSource() == resetButton) {
            theDeck = new CardList(52);
            theDeck.shuffle();
            playerHand = new CardList(0);
            dealerHand = new CardList(0);
            gameStart = false;
            dealerTurn = false;
            deals = false;
            winStatus = -1;
            deal = "";
            dealerWin = "";
            playerWin = "";
            start = "";
            push = "";
            value = "";
            dealerValue = "";
            pushTie = 0;
            dealerScore = 0;
            playerScore = 0;
            repaint();
        }
        if (e.getSource() == hitButton) {
            if (deals == true) {
                deal = "";
                repaint();
            }
            repaint();
            if (gameStart == false) {
                System.out.println("Game not started");
                start = "Hit the Deal Button";
                repaint();
                return;
            }
            if (dealerTurn == false) {
                playerDraw();
                value = "Your Cards add up to: " + playerHand.handValue();
                System.out.println("Player Cards add up to: " + playerHand.handValue());
                if (playerHand.handValue() > 21) {
                    System.out.println("Player Busted");
                    winStatus = 1;
                    dealerWin = "You lose, Dealer Wins!";
                    gameStart = false;
                }
                System.out.println("Computer Cards add up to: " + dealerHand.handValue());
            }
            win();
            repaint();
        }
        if (e.getSource() == dealButton) {
            if (gameStart == true && deals == true) {
                deal = "Game in progress...";
            }
            if (gameStart == false) {
                theDeck = new CardList(52);
                theDeck.shuffle();
                playerHand = new CardList(0);
                dealerHand = new CardList(0);
                gameStart = false;
                dealerWin = "";
                playerWin = "";
                start = "";
                push = "";
                value = "";
                deal = "";
                dealerValue = "";
                dealerTurn = false;
                deal();
                value = "Your Cards add up to: " + playerHand.handValue();
                gameStart = true;
                deals = true;
            } else {
            }
            repaint();
        }
        if (e.getSource() == stayButton) {
            if (gameStart == false) {
                System.out.println("Game not started");
                start = "Hit the Deal Button";
                repaint();
                return;
            }
            if (deals == true) {
                deal = "";
                repaint();
            }
            dealTurn();
            dealerDraw();
            dealerValue = "Dealer's Cards add up to: " + dealerHand.handValue();
            System.out.println("Player Cards add up to: " + playerHand.handValue());
            System.out.println("Computer Cards add up to: " + dealerHand.handValue());
            checkWin();
            win();
            repaint();
        }
    }

    public static void deal() {

        for (int i = 0; i < 2; i++) {
            playerDraw();
            Card drawnCard = theDeck.getFirstCard();
            theDeck.deleteCard(0);
            dealerHand.insertCard(drawnCard);
            if (dealerHand.handValue() > 21 && dealerHand.getAceNum() > 0) {
                dealerHand.switchAce();
            }
        }
        if (playerHand.handValue() == 21 && dealerHand.handValue() == 21) {
            winStatus = 2;
        }
        System.out.println("Player Cards add up to: " + playerHand.handValue());
        System.out.println("Computer Cards add up to: " + dealerHand.handValue());
    }

    public static void playerDraw() {
        Card drawnCard = theDeck.getFirstCard();
        theDeck.deleteCard(0);
        playerHand.insertCard(drawnCard);
        if (playerHand.handValue() > 21 && playerHand.getAceNum() > 0) {
            playerHand.switchAce();
        }
    }

    public static void dealerDraw() {
        while (dealerHand.handValue() < 17) {
            Card drawnCard = theDeck.getFirstCard();
            theDeck.deleteCard(0);
            dealerHand.insertCard(drawnCard);
            if (dealerHand.handValue() > 21 && dealerHand.getAceNum() > 0) {
                dealerHand.switchAce();
            }
        }
        if (dealerHand.handValue() > 21) {
            System.out.println("Dealer Busted");
            playerWin = "YOU WIN!!";
            winStatus = 0;
        }
    }

// This routine will load an image into memory
//
    public static Image load_picture(String fname) {
        // Create a MediaTracker to inform us when the image has
        // been completely loaded.
        Image image;
        MediaTracker tracker = new MediaTracker(myFrame);

        // getImage() returns immediately.  The image is not
        // actually loaded until it is first used.  We use a
        // MediaTracker to make sure the image is loaded
        // before we try to display it.
        image = myFrame.getToolkit().getImage(fname);

        // Add the image to the MediaTracker so that we can wait
        // for it.
        tracker.addImage(image, 0);
        try {
            tracker.waitForID(0);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        if (tracker.isErrorID(0)) {
            image = null;
        }
        return image;
    }
// --------------   end of load_picture ---------------------------

    class MyPanel extends JPanel {

        ////////////    PAINT   ////////////////////////////////
        public void paintComponent(Graphics g) {
            g.drawString(value, 25, 150);
            g.drawString(dealerValue, 25, 315);
            g.drawString(player, 25, 15);
            g.drawString(dealer, 25, 335);
            g.drawString(dealerWin, 250, 250);
            g.drawString(playerWin, 250, 250);
            g.drawString(start, 250, 300);
            g.drawString(push, 250, 250);
            g.drawString(deal, 250, 250);
            g.drawString("SCORE", 630, 100);
            g.drawString("PLAYER: " + playerScore, 525, 125);
            g.drawString("DEALER: " + dealerScore, 625, 125);
            g.drawString("PUSH: " + pushTie, 725, 125);
            int xpos = 25, ypos = 30;
            if (theDeck == null) {
                return;
            }
            Card current = playerHand.getFirstCard();
            while (current != null) {
                Image tempimage = current.getCardImage();
                g.drawImage(tempimage, xpos, ypos, this);
                // note: tempimage member variable must be set BEFORE paint is called
                xpos += 80;
                if (xpos > 700) {
                    xpos = 25;
                    ypos += 105;
                }
                current = current.getNextCard(); //while
            }
            {
                //
                int newXpos = 25, newYpos = 350;
                current = dealerHand.getFirstCard();
                while (current != null) {
                    Image tempimage = current.getCardImage();
                    Image flippedCard = Project4.load_picture("images/gbCard52.gif");
                    g.drawImage(tempimage, newXpos, newYpos, this);
                    if (dealerTurn == false && newXpos > 25) {
                        g.drawImage(flippedCard, newXpos, newYpos, this);
                    }
// note: tempimage member variable must be set BEFORE paint is called
                    newXpos += 80;
                    if (newXpos > 700) {
                        newXpos = 25;
                        newYpos += 105;
                    }
                    current = current.getNextCard(); //while
                }
            }
        }

    }

    public void dealTurn() {
        if (dealerTurn == false) {
            dealerTurn = true;
        } else {
            dealerTurn = false;
        }
    }

    public void checkWin() {
        if (playerHand.handValue() > 21) {
            dealerWin = "You lose, Dealer Wins!";
            winStatus = 1;
            gameStart = false;
            return;
        }
        if (dealerHand.handValue() > 21) {
            playerWin = "YOU WIN!!";
            winStatus = 0;
            gameStart = false;
            return;
        }
        if (playerHand.handValue() > dealerHand.handValue()) {
            playerWin = "YOU WIN!!";
            winStatus = 0;
            gameStart = false;
        } else if (playerHand.handValue() < dealerHand.handValue()) {
            dealerWin = "You lose, Dealer Wins!";
            winStatus = 1;
            gameStart = false;
        } else {
            push = "It's a Push! no one wins";
            winStatus = 2;
            gameStart = false;
        }
    }

    public void win() {
        if (winStatus == 0) {
            playerWin = "YOU WIN!!";
            System.out.println("Player Wins");
            playerScore++;
            winStatus = -1;
        } else if (winStatus == 1) {
            dealerWin = "You lose, Dealer Wins!";
            System.out.println("Dealer Wins");
            dealerScore++;
            winStatus = -1;
        } else if (winStatus == 2) {
            push = "It's a Push! no one wins";
            System.out.println("Push");
            pushTie++;
            winStatus = -1;
        }
    }
}
