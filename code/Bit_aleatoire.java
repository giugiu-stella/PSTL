import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.naming.spi.DirStateFactory.Result; 

public class Bit_aleatoire {
  private int cpt=-1 ;
  private String valeur_random="";
  private String[] valeur_d={"000","001","010","011","100","101","110","111"};
  public Bit_aleatoire(){
  }

  public String generate(){
     Random r = new Random();
     int valeur =r.nextInt();
     String result = Integer.toBinaryString(valeur);
     String resultWithPadding = String.format("%32s", result).replaceAll(" ", "0");
    
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
    for (int j=0; j<21; j++) {
    for(int i=0;i<3;i++){
      entier=rn.bit_aleatoire() + entier;
    }
    System.out.println(entier);
    int faces=rn.valeur_des(entier);
    System.out.println(faces);
    writer.println( String.valueOf(faces));
    entier="" ;
  }
  writer.close();
}
}
