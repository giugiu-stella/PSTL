import  javax.swing.*;
import  java.awt.*;
import  java.awt.event.*;
import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.swing.JTextArea;
public class Game extends JFrame 
{  HashMap<Integer, Tuple<Integer ,Integer>> joueurs = new HashMap<>();   //1er nb_j //2eme valeur dés //3eme nb_jeton
    ArrayList<Integer> Ordre = new ArrayList<Integer>();
    int  nb_joueru;
    int[] Jetons= {10,7,6,6,5,5,4,4,3,3,2,2,2,2,2,2};  //les autres cas sont des 1 
    private JButton but1,but2;  //les boutons
    public static JTextArea input_nb_joueur ;

    public Game( ArrayList<Integer> Ordre,  HashMap<Integer, String> joueurs)
    { 
        //titre de la fenetre
        super("421");  
        this.Ordre=Ordre;
        this.joueurs=joueurs;
        //panel
        JPanel  pan=new JPanel();
        //Input 
        input_nb_joueur = new JTextArea("Indiquer le nombre de jrs ");
        pan.add(input_nb_joueur );
        //bouton ici
        but1=new JButton("Valider le nombre de jrs ");
        //ajoute un listener : ici le listener est cette classe (une action a But1 )
        but1.addActionListener(new Valider());
        
        //ajoute le boutton dans le panel
        pan.add(but1);
        
        //2eme Bouton
        but2=new JButton("Commencer !");
        but2.addActionListener(new Commencer());
        pan.add(but2);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pan);
        pack(); //permet de mettre une bonne dimension a la fenetre
        setVisible(true);
     
    }
        
       
    public  class   Valider implements   ActionListener
    { public  void    actionPerformed(ActionEvent e)
        { 
           nb_joueru = Integer.parseInt(input_nb_joueur.getText());
        System.out.println( nb_joueru);
        but1.setVisible(false);
        input_nb_joueur.setVisible(false);
        }
    }
    
     public  class   Commencer implements   ActionListener  //Pour lancer une premier fois les dés 
    {
        public  void    actionPerformed(ActionEvent e)
        {  //1er joueur 
          int de_1 ,de_2, de_3 ;
          de_1= (int ) ( Math.random() * (6-1+1))+ 1; // psk Math.random renvoie un double 
          de_2= (int ) ( Math.random() * (6-1+1))+ 1;
          de_3= (int ) ( Math.random() * (6-1+1))+ 1;
          System.out.println("la valeur du de1 " +String.valueOf(de_1));
          System.out.println("la valeur du de2 " +String.valueOf (de_2));
          System.out.println("la valeur du de3 " +String.valueOf ( de_3));
          int [] liste = {de_1, de_2, de_3};
          Arrays.sort(liste);
          int valeur = liste[0]*100+liste[1]*10+liste[2];
          int Score = calculescore (de_1,de_2,de_3) ;  //[1........16]
          System.out.print(Score);

          //Valider ou relancer 
         // si on valide on passe au 2eme joueur 
       //  int Jeton = Update_jeton (score   ) ; ///le nombre de jetons 
         }
    }
    
    public int  calculescore (int de1, int de2, int de3 ){
     int [] liste = {de1, de2, de3};
     Arrays.sort(liste);
     int valeur = liste[0]*100+liste[1]*10+liste[2];
    int index= Ordre.indexOf(valeur);
     return index;    
    }


    public  static  void    main(String args[])
    { 
        ArrayList<Integer> Ordre = new ArrayList<Integer>();
        HashMap<Integer,Tuple<Integer, Integer>> joueurs = new HashMap<>();  
        Ordre.add(421);
        Ordre.add(111);
        Ordre.add(611);
        Ordre.add(666);
        Ordre.add(511);
        Ordre.add(555);
        Ordre.add(411);
        Ordre.add(444);
        Ordre.add(511);
        Ordre.add(555);
        Ordre.add(411);
        Ordre.add(444);
        Ordre.add(311);
        Ordre.add(333);
        Ordre.add(211);
        Ordre.add(222);

        new Game(Ordre , joueurs);
    }
}