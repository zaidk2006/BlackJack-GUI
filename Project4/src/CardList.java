
class CardList {

    private Card firstcard = null;
    private int numcards = 0;
    private int aceNum = 0;

    public int getAceNum() {
        return aceNum;
    }

    public void setAceNum(int aceNum) {
        this.aceNum = aceNum;
    }

    public void switchAce() {
        Card current = firstcard;
        if (aceNum > 0) {
            while (current != null) {
                if (current.getCardValue() == 11 && current.getCardType().equals("Ace")) {
                    current.setCardValue(1);
                    return;
                }
                current = current.getNextCard();
            }
        }
    }

    public CardList(int num) {
        numcards = num;   //set numcards in the deck
        for (int i = 0; i < num; i++) {  // load the cards
            Card temp = new Card(i);
            if (firstcard != null) {
                temp.setNext(firstcard);
            }
            firstcard = temp;
        }
    }

    public Card getFirstCard() {
        return firstcard;
    }

    public Card deleteCard(int cardnum) {
        Card target, targetprevious;
        if (cardnum > numcards) {
            return null;   // not enough cards to delete that one
        } else {
            numcards--;
        }
        target = firstcard;
        targetprevious = null;
        while (cardnum-- > 0) {
            targetprevious = target;
            target = target.getNextCard();
            if (target == null) {
                return null;  // error, card not found
            }
        }
        if (targetprevious != null) {
            targetprevious.setNext(target.getNextCard());
        } else {
            firstcard = target.getNextCard();
        }
        return target;
    }

    public void insertCard(Card target) {
        numcards++;
        if (target.getCardType().equals("Ace")) {
            aceNum++;
        }
        if (firstcard != null) {
            target.setNext(firstcard);
        } else {
            target.setNext(null);
        }
        firstcard = target;
    }

    public void shuffle() {
        for (int i = 0; i < 300; i++) {
            int rand = (int) (Math.random() * 100) % numcards;
            Card temp = deleteCard(rand);
            if (temp != null) {
                insertCard(temp);
            }
        }  // end for loop
    }   // end shuffle

    public int handValue() {
        int count = 0;
        Card current = getFirstCard();
        while (current != null) {
            count += current.getCardValue();
            current = current.getNextCard();
        }
        return count;
    }

}    // end class CardList
