import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;


public class Bit_aleatoire_avec_rejet {
    private int cpt=-1 ;
    private String valeur_random="";
    private String[] valeur_d={"000","001","010","011","100","101"};
     Random rn ;
  
    //Initialisation d'une variable Random   
    public Bit_aleatoire_avec_rejet(){
      rn=new Random();
     } 
      
     //Appelle la fonction nextInt() et renvoie une chaine de caractères de 32 bits 
     public String generate(){   
        int  valeur=  rn.nextInt()  ;  
        String result = Integer.toBinaryString( valeur);
        String resultWithPadding = String.format("%32s", result).replaceAll(" ", "0");
        //System.out.println(valeur +" " +result +  " " +resultWithPadding );
        return resultWithPadding;
    }

  //Renvoie un bit à partir de la suite de 32 bits 
  public String bit_aleatoire(){
          if(cpt==-1){
            valeur_random=generate();
            cpt=31;
          }
          char c=valeur_random.charAt(cpt);
          cpt=cpt-1;
          String res=""+c;
          return res;
       }

  public String getValeur_random(){
        return valeur_random;
    }

  public int getcpt(){
        return cpt;
    }


  //Fonction qui traduit une suite de 3 bits en une face(1..6)  si "valeur==111 ou 110" renvoie -1
  public int valeur_des(String valeur){
        for(int i=0;i<valeur_d.length;i++){
          if(valeur.equals(valeur_d[i])){
              return i+1;
            }
          }
        //-1 pour dire que c'est soit 110 ou 111 et donc rejet 
        return -1;
  }


  //Fonction qui renvoie une face à partir du générateur (fonction nextInt()) et le nombre de rejets avant de retrouver une valeur
  // [nb_rejet avant de retrouver la face, valeur face] 
  public List<Integer> getde ()
  {     String entier="";
         int val=-1;
         boolean fin=false ;
         int cpt=0 ;
         //while faces==-1 => c'est a dire, tant qu'on a des rejets on recommence
         List<Integer>list = new ArrayList<Integer > () ;
         while (! fin) 
         {list = new ArrayList<Integer > () ;
          for(int i=0;i<3;i++){
                entier=bit_aleatoire() + entier;
               }
            val=valeur_des(entier);
            entier="";
            if(val!=-1){ 
              fin=true;
              list.add(val);
              list.add(cpt);
              return list ;
            }
            cpt=cpt+1;
         }
         return list;
 }

   
    public static void main(String args[]) throws IOException{ 
        PrintWriter writer ;
        writer = new PrintWriter("Output_avec_rejet.txt");
        boolean rejet2 = false ;
        List <Integer> faces =new ArrayList<Integer>();
        while (!rejet2)
        { Bit_aleatoire_avec_rejet rn= new Bit_aleatoire_avec_rejet();
          int cpt = 0;
          faces.clear();
          for (int j=0; j<22; j++) {
              List<Integer> val =rn.getde();
              System.out.println(val);
              faces.add (val.get(0));
              cpt =cpt + val.get(1);
              if (cpt >3) { break ;}
          }
          //limte le nombre de rejets dans un output_avec_rejet à  3 pour facilité le calcul de la graine 
          if (cpt ==3)
          {   rejet2=true ;
              for (int j=0; j<faces.size(); j++)
              {
                writer.println( String.valueOf(faces.get(j)));
              }
              // writer.close();
              for (int j=0; j<22; j++) {
                List<Integer> val =rn.getde();
                writer.println( String.valueOf(val.get(0)));
                }
              writer.close();
            }

        }


      }    
}  