import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;


public class Bit_aleatoire {
  private int cpt=-1 ;
  private String valeur_random="";
  private String[] valeur_d={"000","001","010","011","100","101","110","111"};
   AtomicLong seed;
   private static final AtomicLong seedUniquifier= new AtomicLong(181783497276652981L*System.nanoTime());

  
  public Bit_aleatoire(){
   }
     
  public int  Rand48() {
    if (seed ==null )
    {  this.seed= new AtomicLong();
      this.seed.set(seedUniquifier.get());
      return (int)(this.seed.get() >>> (48 - 32)) ;    //revoir le calcule de seed 
    }
    this.seed.set((long) ((0x5DEECE66DL * this.seed.get() + 11) % (Math.pow(2, 48)))); 
    int valeur =   (int)(this.seed.get() >>> (48 - 32)) ;  //atention bit de signe 
    return valeur ;
}

public String generate(){   
     int  valeur=  Rand48();    
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

 public int valeur_des(String valeur){
  for(int i=0;i<valeur_d.length;i++){
    if(valeur.equals(valeur_d[i])){
      if(i==6){
        return 1;
      }
      if(i==7){
        return 2;
      }
      else{
        return i+1;
      }
    }
  }
  return 0;
 }
 public int getde ()
{String entier="";
  
  for(int i=0;i<3;i++){
    entier=bit_aleatoire() + entier;
  }
  int faces=valeur_des(entier);
  return faces;
}

  public static void main(String args[]) throws IOException{ 
    Bit_aleatoire rn= new Bit_aleatoire();
    PrintWriter writer ;
    writer = new PrintWriter("Output.txt");
    //On a besoins que de 22 ligne pour trouver X0
    for (int j=0; j<22; j++) {
    int faces=rn.getde();
    writer.println( String.valueOf(faces));
  }
  writer.close();
}
}
