import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;


public class Bit_aleatoire_avec_rejet {
    private int cpt=-1 ;
    private String valeur_random="";
    private String[] valeur_d={"000","001","010","011","100","101"};
     AtomicLong seed;
     Random rn ;
     private static final AtomicLong seedUniquifier= new AtomicLong(181783497276652981L*System.nanoTime());
  
    
    public Bit_aleatoire_avec_rejet(){
      rn=new Random();
     } 
     
     public String generate(){   
        int  valeur=  rn.nextInt()  ;  
        String result = Integer.toBinaryString( valeur);
        String resultWithPadding = String.format("%32s", result).replaceAll(" ", "0");
        System.out.println(valeur +" " +result +  " " +resultWithPadding );
        return resultWithPadding;
    }

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

       //changement ici
       public int valeur_des(String valeur){
        for(int i=0;i<valeur_d.length;i++){
          if(valeur.equals(valeur_d[i])){
              return i+1;
            }
          }
        //-1 pour dire que c'est soit 110 ou 111 et donc rejet 
        return -1;
       }

       public int getde ()
       { String entier="";
         int val=-1;
         boolean fin=false ;
         //while faces==- c'est a dire while on des rejets on recomance 
         while (! fin) 
         {  for(int i=0;i<3;i++){
                entier=bit_aleatoire() + entier;
               }
            val=valeur_des(entier);
            entier="";
            if(val!=-1){
              fin=true;
              return val;
            }
         }
         return val;
       }

    public static void main(String args[]) throws IOException{ 
        Bit_aleatoire_avec_rejet rn= new Bit_aleatoire_avec_rejet();
        PrintWriter writer ;
        writer = new PrintWriter("Output_avec_rejet.txt");
        //On a besoins que de 22 ligne pour trouver X0
        for (int j=0; j<22; j++) {
        int faces=rn.getde();
        writer.println( String.valueOf(faces));
      }
      writer.close();
    }


      
}
