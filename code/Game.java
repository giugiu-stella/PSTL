import  javax.swing.*;
import  java.awt.*;
import  java.awt.event.*;
import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
public class Game extends JFrame 
{ HashMap<Integer, List<Integer >> joueurs = new HashMap<>();   //1er nb_j //2eme valeur dés //3eme nb_jeton
    ArrayList<Integer> Ordre = new ArrayList<Integer>();
    int  nb_joueur, cpt_joueur=1,valeur;
    int[] Jetons= {10,7,6,6,5,5,4,4,3,3,2,2,2,2,2,2};  //les autres cas sont des 1 
    private JButton but1,but2 ,Relancer,Sauvgarder; 
    private JCheckBox check_de1 ,check_de2,check_de3;
    public static JTextArea input_nb_joueur ;
    int max_relancer=3;
    //Labels des images 
    JLabel picLabel1, picLabel2, picLabel3,picLabel4,picLabel5,picLabel6;
    List<JLabel> Labels ;
    int nb_relance_1j=0;
    int max=3 ;
    //Le nombre de relances.
    int nb_relancer=0;
    JPanel  pan ;
    int de_1 ,de_2, de_3 ;
    int banque_jetons= 21;
    public Game( ArrayList<Integer> Ordre,  HashMap<Integer , List<Integer >> j) throws IOException
    {   //titre de la fenetre
        super("421"); 
        this.Ordre=Ordre;
        this.joueurs=j;
        //panel
          pan=new JPanel();
        //Input 
        input_nb_joueur = new JTextArea("Indiquer le nombre de jrs ");
        pan.add(input_nb_joueur );
        //bouton ici
        but1=new JButton("Valider le nombre de jrs ");
        //ajoute un listener : ici le listener est cette classe (une action a But1 )
        but1.addActionListener(new Valider());
        
        //ajoute le boutton dans le panel
        pan.add(but1);
        but2=new JButton("Commencer !");
        but2.addActionListener(new Commencer());
        pan.add(but2);        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pan);
        pack(); //permet de mettre une bonne dimension a la fenetre
        setVisible(true);  
        picLabel1 = new JLabel(new ImageIcon(ImageIO.read(new File("dice/dice1.png"))));
        picLabel2 = new JLabel(new ImageIcon( ImageIO.read(new File("dice/dice2.png"))));
        picLabel3 = new JLabel(new ImageIcon( ImageIO.read(new File("dice/dice3.png"))));
        picLabel4 = new JLabel(new ImageIcon( ImageIO.read(new File("dice/dice4.png"))));
        picLabel5 = new JLabel(new ImageIcon( ImageIO.read(new File("dice/dice5.png"))));
        picLabel6 = new JLabel(new ImageIcon( ImageIO.read(new File("dice/dice6.png"))));
        Labels= Arrays.asList(picLabel1 ,picLabel2,picLabel3,picLabel4,picLabel5,picLabel6);
    }

    public  class   Valider implements   ActionListener
    { public  void    actionPerformed(ActionEvent e)
        { 
        nb_joueur = Integer.parseInt(input_nb_joueur.getText());
        System.out.println( nb_joueur);
        but1.setVisible(false);
        input_nb_joueur.setVisible(false);
        }
    }


     public  class   Commencer implements   ActionListener  //Pour lancer une premier fois les dés 
    {  public  void    actionPerformed(ActionEvent e)
        { nb_relance_1j++;
          de_1= (int ) ( Math.random() * (6-1+1))+ 1; // psk Math.random renvoie un double 
          de_2= (int ) ( Math.random() * (6-1+1))+ 1;
          de_3= (int ) ( Math.random() * (6-1+1))+ 1;
                        
          System.out.println("la valeur du de1 " +String.valueOf(de_1));
          System.out.println("la valeur du de2 " +String.valueOf (de_2));
          System.out.println("la valeur du de3 " +String.valueOf ( de_3));
          //Affichage d'un panel avec les iamges des dés 
          //aficher_image_des(de_1, de_2, de_3);

          int Score = calculescore (de_1,de_2,de_3) ;  //[1........16]
          System.out.print("score"+String.valueOf (Score));
          //Sauvgrader ou relancer 
          //1 Relancer: 
          //Lejoueur devra choisir les dés qu'il veu relancer puis sur le bouton Relancer 
          check_de1 = new JCheckBox("Relancer de1"); 
          check_de2 = new JCheckBox("Relancer de2"); 
          check_de3= new JCheckBox("Relancer de3"); 
          pan.add(check_de1); pan.add(check_de2); pan.add(check_de3);
          Relancer=new JButton("Relancer !");
          pan.add(Relancer);
          Relancer.addActionListener(new Relancer()); //ajouter une action au bouton Relancer //atention pas plus de 3fois 
          //2 Sauvgarder:
          // si on sauvgarde on passe au 2eme joueur 
          repaint();
          Sauvgarder=new JButton("sauvgarder !");
          pan.add(Sauvgarder);
          Sauvgarder.addActionListener(new sauvgarder());
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

    public  class   Relancer implements   ActionListener  //Pour Relancer les dés (on doit verifier les cases qui sont cocher)
    { public  void    actionPerformed(ActionEvent e){
      if (cpt_joueur==1) {nb_relance_1j++;}
      
      if (nb_relancer!=max ){ 
        boolean state = check_de1.isSelected();
         if (state){de_1= (int ) ( Math.random() * (6-1+1))+ 1;} //si la case de dé1 est cocheé alors on calcule une nouvelle valeur 
         
         state = check_de2.isSelected();
         if (state){de_2= (int ) ( Math.random() * (6-1+1))+ 1; } 

         state=check_de3.isSelected();
         if (state){de_3= (int ) ( Math.random() * (6-1+1))+ 1; } 
         System.out.println(" \n la valeur des des apres la relance ");
         System.out.println("la valeur du de1 " +String.valueOf(de_1));
         System.out.println("la valeur du de2 " +String.valueOf (de_2));
         System.out.println("la valeur du de3 " +String.valueOf ( de_3));
         //Affichage d'un panel avec les iamges des nouvelles valeurs 
        // aficher_image_des(de_1, de_2, de_3);
         nb_relancer++;
        }
        else{ System.out.println("Impossible de Relancer");
              //Hide les xheckbox et le bouton relancer 
         }
        }
    }
    //sauvgarde le resultat du joueur courant et passe au suivant+ supprime les checkbox et relancer (car le prochaine joueur doit ne peut pas relancer directement)
    //ATTENTION: si le dérnier joueur qui valide il faut mettre a jours les jetons 
    public  class  sauvgarder implements   ActionListener  
    { public  void    actionPerformed(ActionEvent e){
      if (cpt_joueur==1){max=nb_relance_1j; nb_relance_1j=0;}
    
       //Calcule la valeur des dés uen fois la validation
        valeur = de_1*100+de_2*10+de_3;
        //Il reste encore des jetons  
        if(banque_jetons!=0)
        { //il reste encore des joueurs
          if (cpt_joueur <= nb_joueur){   
            joueurs.put(cpt_joueur,Arrays.asList(valeur ,0,0) );
            //Joueur_suivant
            cpt_joueur++;
            //Reinicialisation du nombre de Relanées pour le jr svt 
            nb_relancer=1; 
            System.out.println("Table des scores==>" +joueurs);
            System.out.println("joueur suivant ");
           }else//Mise a jours des jetons Avec la fonction update jetons + recomancer le jeu a partir du 1er  jr 
            { cpt_joueur=1;
              nb_relancer=1;
              int ordre1=(Ordre.get(1)).get(1);
              int ordre2=(Ordre.get(2)).get(1);
              int ordre3=(Ordre.get(3)).get(1);
              int jgagnant;
              int gagnant=0;
              int jperdant;

              if(ordre1!=-1 && ordre2!=-1 && ordre3!=-1){ // cas 1
                int [] liste = {ordre1, ordre2, ordre3};
                Arrays.sort(liste);
                gagnant = liste[0];
                int perdant= liste[2];
                if(gagnant==ordre1){
                  jgagnant=1;
                }
                if(gagnant==ordre2){
                  jgagnant=2;
                }
                if(gagnant==ordre3){
                  jgagnant=3;
                }
                if(perdant==ordre1){
                  jperdant=1;
                }
                if(perdant==ordre2){
                  jperdant=2;
                }
                if(perdant==ordre3){
                  jperdant=3;
                }
              }

              if(ordre1==-1 && ordre2==-1 && ordre3==-1){ //cas 2
                int score1=somme((Ordre.get(1)).get(0));
                int score2=somme((Ordre.get(2)).get(0));
                int score3=somme((Ordre.get(3)).get(0));
                int [] liste = {score1, score2, score3};
                Arrays.sort(liste);
                gagnant = liste[2];
                int perdant= liste[0];
                if(gagnant==score1){
                  jgagnant=1;
                }
                if(gagnant==score2){
                  jgagnant=2;
                }
                if(gagnant==score3){
                  jgagnant=3;
                }
                if(perdant==score1){
                  jperdant=1;
                }
                if(perdant==score2){
                  jperdant=2;
                }
                if(perdant==score3){
                  jperdant=3;
                }
               
              }
              update_jetons(jgagnant,jperdant,gagnant);
            }
          //Hide les chechbox et le bouton relancer
          check_de1.setVisible(false); check_de1.repaint();
          check_de2.setVisible(false); check_de2.repaint();
          check_de3.setVisible(false); check_de3.repaint();
          Relancer.setVisible(false);  Relancer.repaint();
        }else{
               //Decharge 
              }  
     }
   }
   public int somme(int score){
    int total=0;
    total=total+score/100;
    int r = score%100;
    total=total+r/10;
    r=r%10;
    return total+r;
   }

   public void aficher_image_des (int de1,int de2,int de3)
   {     this.add(Labels.get(de1-1));
         this.add(Labels.get(de_2-1));
         this.add(Labels.get(de_3-1));
   }


   public  static  void    main(String args[]) throws IOException
    {   ArrayList<Integer> Ordre = new ArrayList<Integer>();
        HashMap<Integer,List<Integer >> joueurs = new HashMap<>();  
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