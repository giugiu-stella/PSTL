import  javax.swing.*;
import  java.awt.*;
import  java.awt.event.*;
import java.io.Console;

import javax.swing.JTextArea;
public class Game extends JFrame 
{
    private JButton but1,but2;  //les boutons
    public static JTextArea input_nb_joueur ;
    public Game()
    {
        //titre de la fenetre
        super("421");  
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
     
        int  nb_joueru = Integer.parseInt(input_nb_joueur.getText());
        System.out.println( nb_joueru);
        but1.setVisible(false);
        input_nb_joueur.setVisible(false);
        }
    }
    
     public  class   Commencer implements   ActionListener  //Pour lancer une premier fois les dés 
    {
        public  void    actionPerformed(ActionEvent e)
        {
          int de_1 ,de_2, de_3 ;
          de_1= (int ) ( Math.random() * (6-1+1))+ 1; // psk Math.random renvoie un double 
          de_2= (int ) ( Math.random() * (6-1+1))+ 1;
          de_3= (int ) ( Math.random() * (6-1+1))+ 1;
          System.out.println("la valeur du dé1" +de_1);
          System.out.println("la valeur du dé1" +de_2);
          System.out.println("la valeur du dé1" +de_3);
         }
    }





    public  static  void    main(String args[])
    { new Game();
    }
}