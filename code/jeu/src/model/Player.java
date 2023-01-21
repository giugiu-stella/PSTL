package model;

public class Player {

    // Data Fields
    private String name;
    private int score;
    private int jetons;

    // Constructor
    public Player(String name) {
        this.name = name;
        score = 0;
        if (name =="Player 1") {
        	jetons =10;
        	
        }
        else{
        	jetons = 11;
        }
    }

    // Accessor Methods
    public String getName() {
        return name;
    }

    public int getJetons() {
        return jetons;
    }

    public int getScore() {
        return score;
    }

    // Game Play Methods
    public void resetScore() {
        score = 0;
    }

    public void updateTurn(int roll) {
        score += roll;
    }

    public void saveScore() {
        jetons -= jetons;
        resetScore();
    }

    public static void main(String[] args) {
        
    }
}
