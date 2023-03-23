import  javax.swing.*;
import  java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List; 
import java.io.IOException;
import java.io.PrintWriter;

public class Game extends JFrame{ 
    HashMap<Integer, List<Integer >> joueurs = new HashMap<>();   //1er nb_j //2eme valeur dés //3eme nb_jeton
    ArrayList<Integer> Ordre = new ArrayList<Integer>();
    int  nb_joueur, cpt_joueur=1,valeur;
    Boolean Debut =true ;
    int[] Table_Jetons= {10,7,6,6,5,5,4,4,3,3,2,2,2,2,2,2};  //les autres cas sont des 1 
    private JButton but1,commencer ,Relancer,Sauvgarder,Decharge ,NewPartie ;
    Boolean debut=true;; 
    private JCheckBox check_de1 ,check_de2,check_de3;
    public static JTextArea input_nb_joueur ;
    Boolean etape_decharge=false  ;
    int max_relancer=3;
     JButton Sauvgarder_decharge;
    int nb_relance_1j=1,   max=3 ;
    //Le nombre de relances.
    int nb_relancer=0,tour_j; 
    JPanel  pan ;
    int de_1 ,de_2, de_3 ;  int banque_jetons= 1;
    Bit_aleatoire_sans_reget generateur ; 
    //fichier 
    PrintWriter writer ;
    public Game( ArrayList<Integer> Ordre,  HashMap<Integer , List<Integer >> j) throws IOException
    {   
        super("421"); 
        generateur=new Bit_aleatoire_sans_reget();
        writer = new PrintWriter("Output.txt");
        this.Ordre=Ordre;
        this.joueurs=j;
        pan=new JPanel();
        input_nb_joueur = new JTextArea("Indiquer le nombre de jrs ");
        pan.add(input_nb_joueur );
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
          de_1=generateur.getde();
           writer.println( String.valueOf(de_1));
          de_2= generateur.getde(); 
          writer.println( String.valueOf(de_2));
          de_3=generateur.getde();
          writer.println( String.valueOf(de_3));
          System.out.println("la valeur du de1 " +String.valueOf(de_1));
          System.out.println("la valeur du de2 " +String.valueOf (de_2));
          System.out.println("la valeur du de3 " +String.valueOf ( de_3));
          //Sauvgrader 
          //1 Relancer: 
          //Uniquement lors de la Decharge:Le joueur devra choisir les dés qu'il veu relancer puis sur le bouton Relancer 
          if (etape_decharge) {
            if((nb_relancer<nb_relance_1j)|| cpt_joueur ==1){
              check_de1 = new JCheckBox("Relancer de1"); 
              check_de2 = new JCheckBox("Relancer de2"); 
              check_de3= new JCheckBox("Relancer de3"); 
              pan.add(check_de1); pan.add(check_de2); pan.add(check_de3);
              Relancer=new JButton("Relancer !");
              pan.add(Relancer);
              Relancer.addActionListener(new Relancer());
            } //ajouter une action au bouton Relancer //atention pas plus de 3fois 
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
         if (check_de1.isSelected()){de_1= generateur.getde();writer.println( String.valueOf(de_1));} //si la case de dé1 est cocheé alors on calcule une nouvelle valeur 
         
         if (check_de2.isSelected()){de_2= generateur.getde();writer.println( String.valueOf(de_2)); } 

         if (check_de3.isSelected()){de_3=generateur.getde(); writer.println( String.valueOf(de_3));} 
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
            {  joueurs.get(cpt_joueur).set(0,valeur);
               cpt_joueur=1;
                for (int i=1; i<=nb_joueur;i++)
                { int ordre_table=calcule_ordre(joueurs.get(i).get(0));
                  joueurs.get(i).set(1,ordre_table);
                }
              System.out.println("Table des scores==>" +joueurs);

              int [] tab = liste_ordre(nb_joueur);
              int [] winner=trouver_gagnant(tab);
              int looser=trouver_perdant(tab);

              update_jetons(winner[0],looser, winner[1]); 

              System.out.println("Table des scores==>" +joueurs);
              for (int i=1; i<=nb_joueur;i++)
              {  joueurs.get(i).set(0,0);
                 joueurs.get(i).set(1,0);
              }  
              if (banque_jetons<=0) {Sauvgarder.setVisible(false);  
                                     commencer.setVisible(false);     
                                     Decharge=new JButton("Commencer  Dechrger !");
                                     pan.add(Decharge);
                                     etape_decharge=true ;//pour dire que on traite la decharge 
                                     cpt_joueur=1; //pour caculer les joueurq quit ont deja jouer 
                                     tour_j=looser;//Joueur qui commence la Decharge 
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

  { System.out.println("jr_g "+j_g);
    System.out.println("jr_p "+jp);
    System.out.println("index_g "+index_g);
     if (joueurs.get(j_g).get(1)  ==-1){ 
      joueurs.get(jp).set(2,1+joueurs.get(jp).get(2));
      banque_jetons--;
    }
    else{ 
      joueurs.get(jp).set(2 ,Table_Jetons[index_g]+joueurs.get(jp).get(2));
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

   public int[] liste_ordre(int nb_joueur){
    int []liste=new int[nb_joueur];
    for(int i=0;i<nb_joueur;i++){
      liste[i]=(joueurs.get(i+1)).get(1);
    }
    return liste;
   }
   
   public int[] liste_somme(int[] liste_ordre, int nb_joueur){
    int []liste=new int[nb_joueur];
    for(int i=0;i<nb_joueur;i++){
      if(liste_ordre[i]==-1){
        liste[i]=somme((joueurs.get(i+1)).get(0));
      }
      else{
        liste[i]=0;//on s'en fout !
      }
    }
    return liste;
   }

   public boolean recherche_atleast(int [] liste, int valeur){
    for(int i=0;i<liste.length;i++){
      if(liste[i]!=-1){
        return true;
      }
    }
    return false;
   }

   public int[] trouver_gagnant(int[] liste_ordre){
    int jgagnant=0;
    int igagnant=0;
    //boolean found= Arrays.steam(liste_ordre).anyMatch(x-> x!=-1);
    //pb avec Arrays.steam donc on fait une fonction
    boolean found = recherche_atleast(liste_ordre,-1);
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
    //boolean found= Arrays.steam(liste_ordre).anyMatch(x-> x==-1);
    //pb avec Arrays.steam donc on fait une fonction
    boolean found = recherche_any(liste_ordre,-1);
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
 
   public boolean recherche_any(int [] liste, int valeur){
    for(int i=0;i<liste.length;i++){
      if(liste[i]==-1){
        return true;
      }
    }
    return false;
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
        if (cpt_joueur==1) {max=nb_relance_1j;} 
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
                int [] tab = liste_ordre(nb_joueur);
                int [] winner=trouver_gagnant(tab);
                int looser=trouver_perdant(tab);
                update_jetons_decharge(winner[0], looser, winner[1]);
                 System.out.println("Table des scores==>" +joueurs);

                 Boolean sortie =false;
                 int grand_gagnant=0;
                 for (int i=1; i<=nb_joueur;i++)
                 {  joueurs.get(i).set(0,0);
                   joueurs.get(i).set(1,0);
                   if(joueurs.get(i).get(2)<=0){
                     sortie=true;
                     grand_gagnant=i;
                   }
                 }
                  //Verifier si a la fin de la manche il existe un joueur sans Jetons 
                 //if(il existe un joueur avec 0 jeton on arrete )
                 if (sortie) {
                  Sauvgarder.setVisible(false);  
                  commencer.setVisible(false);    
                  System.out.println("Le joueur numero "+grand_gagnant+ "a gagne la partie !"); 
                  NewPartie=new JButton("Nouvelle Partie");
                  pan.add(NewPartie);
                  etape_decharge=false;
                  NewPartie.addActionListener(new Commencer());
                  System.out.println("Fin de partie"); 
                  //Ferme le fichier 
                  writer.close();
              }
           }
      check_de1.setVisible(false); check_de1.repaint();
      check_de2.setVisible(false); check_de2.repaint();
      check_de3.setVisible(false); check_de3.repaint();
      Relancer.setVisible(false);  Relancer.repaint();
      Sauvgarder_decharge.setVisible(false);
  }}
  
  public void update_jetons_decharge(int j_g,int jp,int index_g)
  {  System.out.println(index_g);
     if (joueurs.get(j_g).get(1)==-1){ 
      joueurs.get(jp).set(2,1+joueurs.get(jp).get(2));
      joueurs.get(j_g).set(2,joueurs.get(j_g).get(2)-1);
    } 
    else{ 
      joueurs.get(jp).set(2 ,Table_Jetons[index_g]+joueurs.get(jp).get(2));
      joueurs.get(j_g).set(2 ,joueurs.get(j_g).get(2)-Table_Jetons[index_g]);
      }
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