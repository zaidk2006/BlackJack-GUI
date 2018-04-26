
import java.awt.Image;

class Card extends Link {

    private Image cardimage;

    private String cardType;
    int cardValue;

    public Card(int cardnum) {
        switch (cardnum % 13) {
            case 0:
                cardType = "Ace";
                cardValue = 11;
                break;
            case 1:
                cardType = "Two";
                cardValue = 2;
                break;
            case 2:
                cardType = "Three";
                cardValue = 3;
                break;
            case 3:
                cardType = "Four";
                cardValue = 4;
                break;
            case 4:
                cardType = "Five";
                cardValue = 5;
                break;
            case 5:
                cardType = "Six";
                cardValue = 6;
                break;
            case 6:
                cardType = "Seven";
                cardValue = 7;
                break;
            case 7:
                cardType = "Eight";
                cardValue = 8;
                break;
            case 8:
                cardType = "Nine";
                cardValue = 9;
                break;
            case 9:
                cardType = "Ten";
                cardValue = 10;
                break;
            case 10:
                cardType = "Jack";
                cardValue = 10;
                break;
            case 11:
                cardType = "Queen";
                cardValue = 10;
                break;
            case 12:
                cardType = "King";
                cardValue = 10;
                break;
        }

        cardimage = Project4.load_picture("images/gbCard" + cardnum + ".gif");
        // code ASSUMES there is an images sub-dir in your project folder
        if (cardimage == null) {
            System.out.println("Error - image failed to load: images/gbCard" + cardnum + ".gif");
            System.exit(-1);
        }
    }

    public void setCardValue(int value) {
        this.cardValue = value;
    }

    public Card getNextCard() {
        return (Card) next;
    }

    public Image getCardImage() {
        return cardimage;
    }

    public String getCardType() {
        return cardType;
    }

    public int getCardValue() {
        return cardValue;
    }

}  //end class Card
