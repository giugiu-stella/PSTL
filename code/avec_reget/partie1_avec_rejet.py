from construction import liste_couple_rejet_n
file1 = open('Output_avec_rejet.txt', 'r')
valeur_d=["000","001","010","011","100","101"]


def binaire (x):
   if (int (x)==1) : 
      return ["000"] 
   if (int (x)==2) : 
      return ["001"] 
   return  [valeur_d  [int (x)-1]]

#on sait que on aura un unique couple 
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
        
        else : #juste 2 bit le 3 eme c'est un bit de x1 
            sauvgarder=(binaire(line)[0][0:1])
            
            if len(binaire(line))==1:
                
                for i in  range (len(liste_x0)): 
                    liste_x0 [i]=(binaire(line)[0])[1:3]+liste_x0[i]           
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
    file1.close
    return couple 

def completeX0(xo,cpt):                  
    xo=xo<<16  
    new_xo=cpt+xo  
    return  new_xo
             
def equation_X(X):
    a =25214903917
    m = pow(2,48)     
    c =11
    return (a*X+c)%m

def recuperer_X0(liste_couple):
    for couple in liste_couple :
        x0=int (couple[0],2)
        x1=int (couple[1],2)
        for cpt in range(2**16): 
            X0=completeX0(x0,cpt)     
            X1=equation_X(X0)
            if((X1)//2**16==x1):
                return X0
    return -1


def calcul_X0_avec_rejet(couple):
    rejets=0
    val_X0=-1
    nb_rejet=1
    while(val_X0==-1):
         #rejets=0 au debut donc la valeur de couple ==couplenew
         if rejets==0:
              couplenew=liste_couple_rejet_n(rejets,couple)
              val_X0=recuperer_X0(couplenew)
              print("la valeur de x0: "+ str ( val_X0)+" pour rejets= "+ str(rejets))
              couple=couplenew
              rejets=rejets+1
         else : 
                couplenew=liste_couple_rejet_n(rejets,couple)    #rejets restera tjr a 1 pour le suite 
                val_X0=recuperer_X0(couplenew)
                print("la valeur de x0: "+ str ( val_X0)+" pour rejets= "+ str(nb_rejet))   
                nb_rejet=nb_rejet+1
                couple=couplenew  
    print ("j'ai touve x0 :"+ str(val_X0)+ "\n" )
    return val_X0


#prediction de la suite des faces 
def verificationTailleX(X):
    if(len(X)<32):
        while(len(X)<32):
            X='0'+X
    return X

def Valeur_to_int(val):
    if(val == "110" or val=="111"):
        return -1
    x=valeur_d.index(val) +1
    return x

def verifoutput(X0,X1,X2,X3,numero,numerofin):
    filetest = open('Output_avec_rejet.txt', 'r')
    line = filetest.readlines() 
    # s'assurer que les chaînes sont sur 32 bits (si positif -> le bit 0 devant n'est pas présent)
    ValX3=verificationTailleX(X3)
    ValX2=verificationTailleX(X2)
    ValX1=verificationTailleX(X1)
    ValX0=verificationTailleX(X0)
    chaine =ValX3+ValX2+ValX1+ValX0
    entier=len(chaine)-1
    print(" la valeur dee X1: "+ ValX1)
    print(" la valeur dee X2: "+ ValX2)
    for i in range(numero,numerofin):
            valOutput=(line[i])[0]    
            valXi=chaine[entier-2] + chaine[entier-1] +chaine[entier]
            entier=entier-3   
            valIntXi = Valeur_to_int(valXi)
            while  (valIntXi==-1):  #si rejet alors on avance dans la lecture de la chaine 
                  valXi=chaine[entier-2] + chaine[entier-1] +chaine[entier]
                  print ("je suis là ")
                  valIntXi = Valeur_to_int(valXi)
                  entier=entier-3
            print ("valeur ligne="+ str (i+1)+" valeur output:"+ valOutput+ " valeur calcul:"+ str(valIntXi))
            if(int (valOutput) != valIntXi):
                    return False    
    return True

#Test sur la prediction de la suite 
def testdes(liste): # test pour les 40 lancés de dés
    # calcul des couples : 
    X0=calcul_X0_avec_rejet(liste)
    X1=equation_X(X0)
    X2=equation_X(X1)
    X3=equation_X(X2)
    # transformation des entiers en chaîne binaire : 
    X2=f'{X2//2**16:08b}'
    X1=f'{X1//2**16:08b}'
    X3=f'{X3//2**16:08b}'
    X0=f'{X0//2**16:08b}'
    #IMPORTANT: inclure X0 pour recommencer du debut et prendre en concideration les rejets de X0 SINON GROS DECALAGE
    vrai_0_40=verifoutput(X0,X1,X2,X3,0,32) # test pour les lignes 0 à 40 //il se peut qu'on obtient False a partir d'une certaine ligne si il y a eu plusieurs rejet dans x2 et x3 donc on aura beoin de X4 ou voir meme X
    return vrai_0_40 


#Test
couple =liste_couple()
for c in couple :
     print ("couple_liste : "+c[0]+" "+c[1]+ "\n" )
#calcul_X0_avec_rejet(couple)
print ("la prediction est correcte ==>" + str (testdes(couple)))
