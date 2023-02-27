from bitstring import BitArray
file1 = open('Output.txt', 'r')
valeur_d=["000","001","010","011","100","101","110","111"]


def binaire (x):
   if (int (x)==1) : 
      return ["000", "110"] 
   if (int (x)==2) : 
      return ["001", "111"] 
   return  [valeur_d  [int (x)-1]]


def liste_couple ():
    x0=""
    x1=""
    liste_x0  =[x0]      
    liste_x1  =[x1]  
    #LE 1ER Dé ddu fichier est a droit dans x0      
    #calcule de x0 
    sauvgarder=""
    for cpt in range(11):
        line = file1.readline() 
        if cpt !=10:
            if len(binaire(line))==1:
                sauvgarder=binaire(line)[0][0:2]
                for i in  range (len(liste_x0)): 
                    liste_x0 [i]=binaire(line)[0]+liste_x0[i]
            
            if len(binaire(line))==2 :   # si la liste [000,001]  alors on devra rajouter dans chaque element le premier et le 2eme 
                sauvgarder=binaire(line)[0][0:2]
                for j in  range (len(liste_x0)): 
                        liste_x0.append(binaire(line)[1]+liste_x0[j])
                        liste_x0 [j]=binaire(line)[0]+liste_x0[j]
        else : #juste 2 bit le 3 eme c'est un bit de x1 
            sauvgarder=(binaire(line)[0][0:1])
            
            if len(binaire(line))==1:
                
                for i in  range (len(liste_x0)): 
                    liste_x0 [i]=(binaire(line)[0])[1:3]+liste_x0[i]
            
            if len(binaire(line))==2 : 
                for j in  range (len(liste_x0)): 
                        liste_x0.append((binaire(line)[1])[1:3]+liste_x0[j])
                        liste_x0 [j]=(binaire(line)[0])[1:3]+liste_x0[j]
    #Calcule de x1
    for cpt in range(11):
                line = file1.readline()  
                if cpt==10:
                    if len(binaire(line))==1:
                        for i in  range (len(liste_x1)): 
                            liste_x1 [i]=binaire(line)[0][2:3]+liste_x1[i]
                    if len(binaire(line))==2 : 
                        for j in  range (len(liste_x1)): 
                                liste_x1.append(binaire(line)[1][2:3]+liste_x1[j])
                                liste_x1 [j]=binaire(line)[0][2:3]+liste_x1[j]
                else :
                    if len(binaire(line))==1:
                        for i in  range (len(liste_x1)): 
                            liste_x1 [i]=binaire(line)[0]+liste_x1[i]
                    if len(binaire(line))==2 : 
                        for j in  range (len(liste_x1)): 
                                liste_x1.append(binaire(line)[1]+liste_x1[j])
                                liste_x1 [j]=binaire(line)[0]+liste_x1[j]

    for j in range (len (liste_x1)):
        liste_x1 [j]=liste_x1[j]+sauvgarder
     #construction des couples 
    couple =[]
    for x0 in liste_x0:
        for x1 in liste_x1:
            couple.append((x0,x1) )
    return couple 

def completexo(xo,cpt):                  
    xo=xo<<16   #11101000100000000111110111100100000000000000000  c'est ok 
    new_xo=cpt+xo  
    return  new_xo

def conversion_neg(valeur ):
     resultat=""
     for i in range (len(valeur)):
          if valeur[i]=="1" :
               resultat=resultat+"0"
          else :
               resultat=resultat+"1"
     return int (resultat,2)+1
               
          
     

def couple_to_random(liste_couple):
    a =25214903917
    m = pow(2,48)     
    c =11       
    for couple in liste_couple :
        x0=int (couple[0],2)
        x1=int (couple[1],2)
        for cpt in range(2**16): # fonction 
            XO=completexo(x0,cpt)     
            X1=(a*XO+c)% m
            if((X1)//2**16==x1):   
                print("ok")
                return XO
    return -1


#genérer le reste des dés + rapport 




#Test
couple =liste_couple()
for c in couple :
     print (c[0]+" "+c[1])

val=couple_to_random( couple )
print (val )
