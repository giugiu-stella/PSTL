package application;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Game;

import java.io.File;

public class MainSceneController {

    // Data Fields
    Game jeu;

    // FXML Connections
    @FXML
    ImageView dieImage1;
    @FXML
    ImageView dieImage2;
    @FXML
    ImageView dieImage3;
    private Boolean d1=false;
    private Boolean d2=false;
    private Boolean d3=false;
    
    @FXML
    Button rollButton;

    @FXML
    Button holdButton;

    @FXML
    TextField p1turn;

    @FXML
    TextField p2turn;

    @FXML
    TextField p1total;

    @FXML
    TextField p2total;

    @FXML
    VBox p1box;

    @FXML
    VBox p2box;

    @FXML
    Label title;

    private Roller clock;

    private class Roller extends AnimationTimer {

        private long FRAMES_PER_SEC = 50L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private int MAX_ROLLS = 20;

        private long last = 0;
        private int count = 0;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
            	int r1 = 2 + (int)(Math.random() * 5);
                int r2 = 2 + (int)(Math.random() * 5);
                int r3 = 2 + (int)(Math.random() * 5);
                setDieImage(r1,r2,r3);
                last = now;
                count++;
                if (count > MAX_ROLLS) {
                    clock.stop();
                    disableButtons(false);
                    roll();
                    count = 0;
                }
            }
        }
    }

    @FXML
    public void initialize() {
        clock = new Roller();
        jeu = new Game("Player 1", "Player 2");
        updateViews();
    }

    public void updateViews() {
        setDieImage(jeu.getDied1().getTop(),jeu.getDied2().getTop(),jeu.getDied3().getTop());
        p1turn.setText("" + jeu.getP1().getScore());
        p1total.setText("" + jeu.getP1().getJetons());
        p2turn.setText("" + jeu.getP2().getScore());
        p2total.setText("" + jeu.getP2().getJetons());
        if (jeu.p1Turn()) {
            p1box.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            p2box.setBackground(null);
        } else {
            p2box.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            p1box.setBackground(null);
        }
        if (jeu.gameOver()) {
            disableButtons(true);
            title.setText("Game Over! " + jeu.getCurrent().getName() + " wins!");
        }
    }

    public void setDieImage(int top1,int top2, int top3) {

        File f1 = new File("src/ressources/Dice" + top1+ ".png");
        System.out.println(f1.toURI().toString());
        dieImage1.setImage(new Image(f1.toURI().toString()));
        
        File f2 = new File("src/ressources/Dice" + top2 + ".png");
        System.out.println(f2.toURI().toString());
        dieImage2.setImage(new Image(f2.toURI().toString()));
        
        File f3 = new File("src/ressources/Dice" + top3 + ".png");
        System.out.println(f3.toURI().toString());
        dieImage3.setImage(new Image(f3.toURI().toString()));
    }
    
    public void setDieImage1(int top1) {

        File f1 = new File("src/ressources/Dice" + top1+ ".png");
        System.out.println(f1.toURI().toString());
        dieImage1.setImage(new Image(f1.toURI().toString()));
        
    }

    public void setDieImage2(int top1) {

        File f1 = new File("src/ressources/Dice" + top1+ ".png");
        System.out.println(f1.toURI().toString());
        dieImage2.setImage(new Image(f1.toURI().toString()));
        
    }
    public void setDieImage3(int top1) {

        File f1 = new File("src/ressources/Dice" + top1+ ".png");
        System.out.println(f1.toURI().toString());
        dieImage3.setImage(new Image(f1.toURI().toString()));
        
    }
    public void disableButtons(boolean disable) {
        rollButton.setDisable(disable);
        holdButton.setDisable(disable);
    }

    public void rollAnimation() {
        clock.start();
        disableButtons(true);
    }
    
    public void checkd1() {
    	d1=true;
    }
    
    public void checkd2() {
    	d2=true;
    }
    
    public void checkd3() {
    	d3=true;
    }
    
    public void roll() {
    	if (d1==true) {
    		jeu.roll(1);
    	}
    	if (d2==true) {
    		jeu.roll(2);
    	}
    	if (d3==true) {
    		jeu.roll(3);
    	}
        updateViews();
    }

    public void hold() {
    	jeu.hold();
        updateViews();
    }
}