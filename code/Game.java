import  javax.swing.*;
import  java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List; 
import java.io.IOException;
public class Game extends JFrame 
{ HashMap<Integer, List<Integer >> joueurs = new HashMap<>();   //1er nb_j //2eme valeur dés //3eme nb_jeton
    ArrayList<Integer> Ordre = new ArrayList<Integer>();
    int  nb_joueur, cpt_joueur=1,valeur;
    Boolean Debut =true ;
    int[] Table_Jetons= {10,7,6,6,5,5,4,4,3,3,2,2,2,2,2,2};  //les autres cas sont des 1 
    private JButton but1,commencer ,Relancer,Sauvgarder,Decharge ; 
    private JCheckBox check_de1 ,check_de2,check_de3;
    public static JTextArea input_nb_joueur ;
    Boolean etape_decharge=false  ;
    int max_relancer=3;
     JButton Sauvgarder_decharge;
    int nb_relance_1j=0,   max=3 ;
    //Le nombre de relances.
    int nb_relancer=0,tour_j; 
    JPanel  pan ;
    int de_1 ,de_2, de_3 ;  int banque_jetons= 1;
    
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
        commencer=new JButton("Commencer !");
        commencer.addActionListener(new Commencer());
        pan.add(commencer);    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pan);
        pack(); //permet de mettre une bonne dimension a la fenetre
        setVisible(true);  
    }
   
    public void iniciliastion_joueur()
    { if (! etape_decharge) {
      for (int i=1 ; i<= nb_joueur; i++) {joueurs.put(i,Arrays.asList(0 ,0,0) );}
     }
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
        { //Inicialisation dela structure joueurs 
          if (Debut ) {iniciliastion_joueur() ; Debut=false;}
          de_1= (int ) ( Math.random() * (6-1+1))+ 1;  
          de_2= (int ) ( Math.random() * (6-1+1))+ 1;
          de_3= (int ) ( Math.random() * (6-1+1))+ 1;
          System.out.println("la valeur du de1 " +String.valueOf(de_1));
          System.out.println("la valeur du de2 " +String.valueOf (de_2));
          System.out.println("la valeur du de3 " +String.valueOf ( de_3));
          //Sauvgrader 
          //1 Relancer: 
          //Uniquement lors de la Decharge:Le joueur devra choisir les dés qu'il veu relancer puis sur le bouton Relancer 
          if (etape_decharge) {
            check_de1 = new JCheckBox("Relancer de1"); 
            check_de2 = new JCheckBox("Relancer de2"); 
            check_de3= new JCheckBox("Relancer de3"); 
            pan.add(check_de1); pan.add(check_de2); pan.add(check_de3);
            Relancer=new JButton("Relancer !");
            pan.add(Relancer);
            Relancer.addActionListener(new Relancer()); //ajouter une action au bouton Relancer //atention pas plus de 3fois 
          }
          //2 Sauvgarder:
          // si on sauvgarde on passe au 2eme joueur 
          //La sauvgarde lors de la charge et la decharge ne sont pas les mm (cause gestion joueur + condition d'arret )
          if (!etape_decharge) {
          Sauvgarder=new JButton("sauvgarder !");
          pan.add(Sauvgarder);
          Sauvgarder.addActionListener(new sauvgarder());
          }else {
            Sauvgarder_decharge=new JButton("sauvgarder decharge!");
            pan.add(Sauvgarder_decharge);
            Sauvgarder_decharge.addActionListener(new sauvgarder_decharge());
          }
         }
    }
    

    public  class   Relancer implements   ActionListener  //Pour Relancer les dés (on doit verifier les cases qui sont cocher)
    { public  void    actionPerformed(ActionEvent e){
      if (cpt_joueur==1) {nb_relance_1j++;} 
      
      if (nb_relancer!=max ){ 
         if (check_de1.isSelected()){de_1= (int ) ( Math.random() * (6-1+1))+ 1;} //si la case de dé1 est cocheé alors on calcule une nouvelle valeur 
         
         if (check_de2.isSelected()){de_2= (int ) ( Math.random() * (6-1+1))+ 1; } 

         if (check_de3.isSelected()){de_3= (int ) ( Math.random() * (6-1+1))+ 1; } 
         System.out.println("\nla valeur des des apres la relance ");
         System.out.println("la valeur du de1 " +String.valueOf(de_1));
         System.out.println("la valeur du de2 " +String.valueOf (de_2));
         System.out.println("la valeur du de3 " +String.valueOf ( de_3));
         nb_relancer++;
        }
        else{ System.out.println("Impossible de Relancer");
         }
        }
    }
    //sauvgarde le resultat du joueur courant et passe au suivant+ supprime les checkbox et relancer (car le prochaine joueur doit ne peut pas relancer directement)
    //ATTENTION: si le dérnier joueur qui valide il faut mettre a jours les jetons 
    public  class  sauvgarder implements   ActionListener  
    { public  void    actionPerformed(ActionEvent e){
        int [] liste = {de_1, de_2, de_3};
        Arrays.sort(liste);
        int valeur = liste[2]*100+liste[1]*10+liste[0];
        //Il reste encore des jetons  
        if(banque_jetons!=0)
        { //il reste encore des joueurs
          if (cpt_joueur <nb_joueur ){   
            joueurs.get(cpt_joueur).set(0,valeur);
            System.out.println("Table des scores==>" +joueurs);
            cpt_joueur++;
            //Reinicialisation du nombre de Relanées pour le jr svt 
            nb_relancer=1; 
            System.out.println("joueur suivant ");
           }else//Mise a jours des jetons Avec la fonction update jetons + recomancer le jeu a partir du 1er  jr 
            { cpt_joueur=1;
              nb_relancer=1;
              int [] tab = liste_ordre(nb_joueur);
              int [] winner=trouver_gagnant(tab);
              int looser=trouver_perdant(tab);
              update_jetons(winner[0],looser, winner[1]);         
            }
          //Hide les chechbox et le bouton relancer
          check_de1.setVisible(false); check_de1.repaint();
          check_de2.setVisible(false); check_de2.repaint();
          check_de3.setVisible(false); check_de3.repaint();
          Relancer.setVisible(false);  Relancer.repaint();
        }else{
               //Decharge 
          //Mise a jours des jetons Avec la fonction update jetons + recomancer le jeu a partir du 1er  jr 
            {  joueurs.get(cpt_joueur).set(0,valeur);
               cpt_joueur=1;
                /*for (int i=1; i<=nb_joueur;i++)
                { int ordre_table=calcule_ordre(joueurs.get(i).get(0));
                  joueurs.get(i).set(1,ordre_table);
                }
              System.out.println("Table des scores==>" +joueurs);

              int ordre1=(joueurs.get(1)).get(1);
              int ordre2=(joueurs.get(2)).get(1);
              int ordre3=(joueurs.get(3)).get(1);
              int jgagnant=0;
              int gagnant=0;
              int jperdant=0;
              if(ordre1!=-1 && ordre2!=-1 && ordre3!=-1){ // cas 1
                int [] liste_ordre = {ordre1, ordre2, ordre3};
                Arrays.sort(liste_ordre);
                gagnant = liste_ordre[0];
                int perdant= liste_ordre[2];
                if(gagnant==ordre1){    jgagnant=1;  }
                if(gagnant==ordre2){jgagnant=2;  }
                if(gagnant==ordre3){ jgagnant=3; }
                if(perdant==ordre1){  jperdant=1;   }
                if(perdant==ordre2){  jperdant=2;  }
                if(perdant==ordre3){ jperdant=3; }
              }
              if(ordre1==-1 && ordre2==-1 && ordre3==-1){ //cas 2
                int score1=somme((joueurs.get(1)).get(0));
                int score2=somme((joueurs.get(2)).get(0));
                int score3=somme((joueurs.get(3)).get(0));
                int [] liste_score = {score1, score2, score3};
                Arrays.sort(liste_score);
                gagnant = liste_score[2];
                int perdant= liste_score[0];
                if(gagnant==score1){   jgagnant=1;  }
                if(gagnant==score2){  jgagnant=2; }
                if(gagnant==score3){    jgagnant=3;   }
                if(perdant==score1){    jperdant=1; }
                if(perdant==score2){  jperdant=2; }
                if(perdant==score3){   jperdant=3;  }
                gagnant=-1;
              }*/
              int [] tabbis = liste_ordre(nb_joueur);
              int [] gagnant=trouver_gagnant(tabbis);
              int perdant=trouver_perdant(tabbis);
              update_jetons(gagnant[0],perdant, gagnant[1]);   
              //update_jetons(jgagnant,jperdant,gagnant); ///le nombre de jetons 
              System.out.println("Table des scores==>" +joueurs);
              for (int i=1; i<=nb_joueur;i++)
              {  joueurs.get(i).set(0,0);
                 joueurs.get(i).set(1,0);
              }  
              if (banque_jetons==0) {Sauvgarder.setVisible(false);  
                                     commencer.setVisible(false);     
                                     Decharge=new JButton("Dechrger !");
                                     pan.add(Decharge);
                                     nb_relance_1j++;
                                     etape_decharge=true ;//pour dire que on traite la decharge 
                                     cpt_joueur=1; //pour caculer les joueurq quit ont deja jouer 
                                     tour_j=jperdant;//Joueur qui commence la Decharge 
                                     Decharge.addActionListener(new Commencer());
                                     System.out.println("Decharge"); 
                                    }
            }
          Sauvgarder.setVisible(false); 
        }
     }
   }
   
  public int  calcule_ordre (int valeur){
    int index= Ordre.indexOf(valeur);
     return index;    
    }

  public void update_jetons(int j_g,int jp,int index_g)  //index_g est la valeur retourner par 
  {  if (index_g==-1){joueurs.get(jp).set(2,1+joueurs.get(jp).get(2));
                      banque_jetons--;}
    else{ joueurs.get(jp).set(2 ,Table_Jetons[index_g]+joueurs.get(jp).get(2));
          banque_jetons=banque_jetons-Table_Jetons[index_g];
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


   /*public int[] liste_triee(int ordre1, int ordre2,int ordre3){
    int []list = {ordre1, ordre2, ordre3};
    Arrays.sort(list);
    return list;
   }*/

   public int[] liste_ordre(int nb_joueur){
    int []liste=new int[nb_joueur];
    for(i=0;i<nb_joueur;i++){
      liste[i]=(joueurs.get(i+1)).get(1);
    }
    return liste;
   }
   
   public int[] liste_somme(int[] liste_ordre, int nb_joueur){
    int []liste=new int[nb_joueur];
    for(int i=0;i<nb_joueur;i++){
      if(liste_ordre[i]==-1){
        liste[i]=somme((joueur.get(i+1)).get(0));
      }
      else{
        liste[i]=0;//on s'en fout !
      }
    }
    return liste;
   }

   public int[] trouver_gagnant(int[] liste_ordre){
    int jgagnant=0;
    int igagnant=0;
    boolean found= Arrays.steam(liste_ordre).anyMatch(x-> x!=-1);
    if(found==true){
      for(int i=0;i<liste_ordre.length;i++){
        if(liste_ordre[i]>igagnant){
          igagnant=liste_ordre[i];
          jgagnant=i+1;
        }
      }
    }
    else{
      int [] liste=liste_somme(liste_ordre, liste_ordre.length);
      for(int i=0;i<liste.length;i++){
        if(liste[i]!=0 && liste[i]>igagnant){
          igagnant=liste[i];
          jgagnant=i+1;
        }
      }

    }
    int [] res={jgagnant,igagnant};
    return res;
   }

   public int trouver_perdant(int[] liste_ordre){
    boolean found= Arrays.steam(liste_ordre).anyMatch(x-> x==-1);
    int jperdant=0;
    int iperdant=17;
    if(found ==true){
      int [] liste=liste_somme(liste_ordre, liste_ordre.length);
      
      for(int i=0;i<liste.length;i++){
        if(liste[i]!=0 && liste[i]<iperdant){
          iperdant=liste[i];
          jperdant=i+1;
        }
      }
    }
    else{
      for(int i=0;i<liste_ordre.length;i++){
        if(liste_ordre[i]<iperdant){
          iperdant=liste_ordre[i];
          jperdant=i+1;
        }
      }
    }
    return jperdant;
   }

   public void aficher_image_des (int de1,int de2,int de3)
   {     this.add(Labels.get(de1-1));
         this.add(Labels.get(de_2-1));
         this.add(Labels.get(de_3-1));
   }


   //La fonction sauvrgarder lors de la decharge verifie si un joueru n'a plus de jetons et 
   public  class   sauvgarder_decharge implements   ActionListener 
   { public  void    actionPerformed(ActionEvent e){
     int [] liste = {de_1, de_2, de_3};
     Arrays.sort(liste);
     int valeur = liste[2]*100+liste[1]*10+liste[0];   
    if (cpt_joueur <nb_joueur ){   
        joueurs.get(tour_j).set(0,valeur);
        System.out.println("Table des scores==>" +joueurs);
        cpt_joueur++;
        //Joueur suivant 
        tour_j++;
        if (tour_j>nb_joueur) {tour_j=1;} //  si depace le nombre de joueurs alors on revient au premier 
        //Reinicialisation du nombre de Relanées pour le jr svt 
        nb_relancer=1; 
        System.out.println("joueur suivant ");
    }else{      //si tous les joueurs on jouer une manche alors on Update les jetons si le dernier valide 
                joueurs.get(tour_j).set(0,valeur);
                cpt_joueur=1;
                nb_relancer=1;
                for (int i=1; i<=nb_joueur;i++)
                { int ordre_table=calcule_ordre(joueurs.get(i).get(0));
                  joueurs.get(i).set(1,ordre_table);
                }
                System.out.println("Table des scores==>" +joueurs);
                System.out.println("Update Jetons ");
                //Update jetons (le perdant recoi les jetons du ganiant suivant les score)
                //Verifier si a la fin de la manche il existe un joueur sans Jetons 
                //if(il existe un joueur avec 0 jeton on arrete )
           }
      check_de1.setVisible(false); check_de1.repaint();
      check_de2.setVisible(false); check_de2.repaint();
      check_de3.setVisible(false); check_de3.repaint();
      Relancer.setVisible(false);  Relancer.repaint();
      Sauvgarder_decharge.setVisible(false);
  }}

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