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
   private static final AtomicLong seedUniquifier= new AtomicLong((long) Math.pow (2,20));

  
  public Bit_aleatoire(){
         
  }
 /*  public void srand48(long seedval) {
    this.seed = seedval & 0xFFFFFFFF;
    this.seed = (this.seed << 16) | 0x330E;
  }*/
        
  /**
   * @return
   */
  public long  Rand48() {
    if (seed ==null )
    {  this.seed= new AtomicLong();
      this.seed.set(seedUniquifier.get());
      return (long) ((seed.get( ))/Math.pow(2,16));   //revoir le calcule de seed 
    }
    this.seed.set((long) ((0x5DEECE66DL * this.seed.get() + 11) % (Math.pow(2, 48)))); 
    System.out.println(this.seed.get());
    long valeur = (long) (this.seed.get() /Math.pow(2,16));   //atention bit de signe 
    return valeur ;
}

public String generate(){   
     long valeur=  Rand48();    //
     String result = Integer.toBinaryString((int) valeur);
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
 /*public string bit_aleatoire(){
  if(valeur_random.equals("")){
    valeur_random=generate();
  }
  int idernier=valeur_random.length()-1;
  char c=valeur_random.charAt(idernier);
  valeur_random= valeur_random.substring(0,idernier);
  return c;
 } */

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

  public static void main(String args[]) throws IOException{ 
    Bit_aleatoire rn= new Bit_aleatoire();
    String entier="";
    PrintWriter writer ;
    writer = new PrintWriter("Output.txt");
    for (int j=0; j<23; j++) {
    for(int i=0;i<3;i++){
      entier=rn.bit_aleatoire() + entier;
    }
    int faces=rn.valeur_des(entier);
    writer.println( String.valueOf(faces));
    entier="" ;
  }
  writer.close();
}
}
