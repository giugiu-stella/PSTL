file1 = open('Output_sans_rejet.txt', 'r')
valeur_d=["000","001","010","011","100","101","110","111"]

def binaire (x):
   if (int (x)==1) : 
      return ["000", "110"] 
   if (int(x)==2) : 
      return ["001", "111"] 
   return  [valeur_d  [int (x)-1]]

# Fonction de reconstruction de la liste des couples à partir des faces qui se trouve dans output_sans_rejet
def liste_couple ():
    x0=""
    x1=""
    liste_x0  =[x0]      
    liste_x1  =[x1]  
    #Le 1e Dé du fichier est à droite dans x0      
    #calcul de x0 
    sauvgarder=""
    cas_spe=False
    
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
                cas_spe=True
                for j in  range (len(liste_x0)): 
                        liste_x0.append((binaire(line)[1])[1:3]+liste_x0[j])
                        liste_x0 [j]=(binaire(line)[0])[1:3]+liste_x0[j]
    # Calcul de x1
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
        if(cas_spe ):
            liste_x1.append(liste_x1[j]+"1")
            liste_x1[j]=liste_x1[j]+"0"
        else :
            liste_x1 [j]=liste_x1[j]+sauvgarder
    
     #construction des couples 
    fichier = open("data.txt", "a")
    couple =[]
    for x0 in liste_x0:
        for x1 in liste_x1:
            couple.append((x0,x1) )
            fichier.write("( "+str(x0) + " , "+ str(x1)+ " )")
            fichier.write("\n")
    
    fichier.close()
    file1.close()
    return couple 

#Remet un x0 (32 bits) sur 48 bits avec un décalage <<16
def completeX0(xo,cpt):                  
    xo=xo<<16  
    new_xo=cpt+xo  
    return  new_xo

#Equation (a*X+c)mod m            
def equation_X(X):
    a =25214903917
    m = pow(2,48)     
    c =11
    return (a*X+c)%m

#Fonction qui retrouve X0(48 bits) à partir de la liste des couples 
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

#Fonction qui vérfie si x est sur 32bits 
def verificationTailleX(X):
    if(len(X)<32):
        while(len(X)<32):
            X='0'+X
    return X


def Valeur_to_int(val):
    x=valeur_d.index(val) +1
    if(x == 7):
        x=1   
    if(x == 8):
        x=2
    return x

#Prédiction: Fonction qui vérifie si les faces >22 du output_sans_rejet sont compatiblees aux valeurs de X2 et X3 (X2 X3 valeurs calculé grace à la gaire et l'équation)
def verifoutput(X1,X2,numero,numerofin):
    filetest = open('Output_sans_rejet.txt', 'r')
    line = filetest.readlines() 
    entier=31

    # s'assurer que les chaînes sont sur 32 bits (si positif -> le bit 0 devant n'est pas présent)
    ValX2=verificationTailleX(X2)
    ValX1=verificationTailleX(X1)
    #print("valeur des chaines :")
    #print(ValX1)
    #print(ValX2)

    for i in range(numero,numerofin):
        valOutput=(line[i])[0]
        
        if(i==21): # ligne où la valeur est le resultat entre les bits de X1 et X2
            valXi=ValX2[entier-1] + ValX2[entier] +ValX1[0]
            entier =entier-2
        else : 
            valXi=ValX2[entier-2] + ValX2[entier-1] +ValX2[entier]
            entier=entier-3
        
        #print("valeur de valXi : ")
        #print(valXi)
        valIntXi = Valeur_to_int(valXi)
        if(int (valOutput) != valIntXi):
            print(i)
            print("valeur du dé théorique : ")
            print(valIntXi)
            return False
        
    return True

#Prediction: fonction qui fait appel à verifoutput
def testdes(liste): # test pour les 40 lancés de dés

    # calcul des couples : 
    X0=recuperer_X0(liste)
    X1=equation_X(X0)
    X2=equation_X(X1)
    X3=equation_X(X2)
    #print(X0)
    print("valeur de X1: " +str(X1))
    print("valeur de X2: " + str(X2))
    print("valeur de X3: " + str (X3))
    # transformation des entiers en chaîne binaire : 
    X2=f'{X2//2**16:08b}'
    X1=f'{X1//2**16:08b}'
    X3=f'{X3//2**16:08b}'
    vrai=verifoutput(X1,X2,21,32) # test pour les lignes 22 à 33
    #true=verifoutput(X2,X3,32,40) # test pour les lignes 33 à 40
    
    return vrai #(vrai and true)



#Test
couple =liste_couple()
print("la valeur de la graine(seed): " +str (recuperer_X0(couple)))
print("les 40 faces sont compatiblees? " + str (testdes(couple)))