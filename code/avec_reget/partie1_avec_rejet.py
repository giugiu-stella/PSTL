from bitstring import BitArray
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

def liste_couple_rejet_un(listecouple):
    couple =[]
    rejet0="110"
    rejet1="111"
    for i in range(0, 64, 3):
        for (xo,x1) in listecouple:
            chaine=xo+x1
            if(i==0):
                newchaine0=rejet0+chaine
                newchaine1=rejet1+chaine
            else:
                newchaine0=chaine[0:(i-1)]+rejet0+chaine[i::]
                newchaine1=chaine[0:(i-1)]+rejet1+chaine[i::]

            newx0_0=newchaine0[0:31]
            newx0_1=newchaine1[0:31]

            newx1_0=newchaine0[32:63]
            newx1_1=newchaine1[32:63]
            couple.append((newx0_0,newx1_0))
            couple.append((newx1_0,newx1_1))

    return couple

def liste_couple_rejet_deux(listecouples):
    couple=[]
    
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

def Recuperer_X0(listecouple):
    for couple in listecouple :
        x0=int (couple[0],2)
        x1=int (couple[1],2)
        for cpt in range(2**16): 
            X0=completeX0(x0,cpt)     
            X1=equation_X(X0)
            if((X1)//2**16==x1):
                return X0
    return -1

def deviner_X0(couple):
    listecouple=couple
    sortie=False
    rejet=1
    max=22
    while(not sortie or rejet==max):
        X0=Recuperer_X0(listecouple)
        if(X0 !=-1):
            return X0
        else:
            listecouple=liste_couple_rejet(listecouple,rejet)
            rejet=rejet+1

    return -1


#Test
couple =liste_couple()
for c in couple :
     print (c[0]+" "+c[1])