package model;

public class Game {

    public static final int MAX_SCORE = 100;

    // Data Fields
    private Die d1;
    private Die d2;
    private Die d3;
    private Die d;
    private Player p1;
    private Player p2;
    private Player current;

    // Constructor
    public Game(String p1name, String p2name) {
        d1 = new Die();
        d2=new Die();
        d3= new Die();
        p1 = new Player(p1name);
        p2 = new Player(p2name);
        current = p1;
    }

    // Accessor methods

    public Die getDied1() {
        return d1;
    }
    
    public Die getDied2() {
        return d2;
    }
    
    public Die getDied3() {
        return d3;
    }

    public Player getCurrent() {
        return current;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    // Status Methods
    public boolean gameOver() {
        return current.getJetons() >= MAX_SCORE;
    }

    public boolean p1Turn() {
        return current == p1;
    }

    // Game Play Methods
    public void switchTurn() {
        if (p1Turn()) {
            current = p2;
        } else {
            current = p1;
        }
    }

    public void roll(int i) {
        if(i==1) {
        	d=d1;
        }
        if(i==2) {
        	d=d2;
        }
        if(i==3) {
        	d=d3;
        }
        d.roll();
        int t = d.getTop();
        current.updateTurn(t);
        if (t == 1) {
            current.resetScore();
            switchTurn();
        }
    }

    public void hold() {
        current.saveScore();
        if (!gameOver()) {
            switchTurn();
            d1.setTop(0);
            d2.setTop(0);
            d3.setTop(0);
        }
    }

    public static void main(String[] args) {
        
    }
}