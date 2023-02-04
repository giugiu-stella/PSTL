file1 = open('Output.txt', 'r')
valeur_d=["000","001","010","011","100","101","110","111"]

def binaire (x):
   if (int (x)==1) : 
      return ["000", "110"] 
   
   if (int (x)==2) : 
      return ["001", "111"] 
   return  [valeur_d  [int (x)-1]]

x0=""
x1=""
liste_x0  =[x0]      
liste_x1  =[x1]  
#LE 1ER Dé ddu fichier est a droit dans x0 
#nb_bit(x0)=33==> x0+1bit de x1     

#calcule de x0 
for cpt in range(11):
    line = file1.readline() 
    if cpt !=10:
        if len(binaire(line))==1:
            for i in  range (len(liste_x0)): 
                liste_x0 [i]=binaire(line)[0]+liste_x0[i]
        
        if len(binaire(line))==2 :   # si la liste [000,001]  alors on devra rajouter dans chaque element le premier et le 2eme 
            for j in  range (len(liste_x0)): 
                    liste_x0.append(binaire(line)[1]+liste_x0[j])
                    liste_x0 [j]=binaire(line)[0]+liste_x0[j]
    else : #juste 2 bit le 3 eme c'est un bit de x1 
        if len(binaire(line))==1:
            for i in  range (len(liste_x0)): 
                liste_x0 [i]=(binaire(line)[0])[1:3]+liste_x0[i]
        
        if len(binaire(line))==2 :    
            for j in  range (len(liste_x0)): 
                    liste_x0.append((binaire(line)[1])[1:3]+liste_x0[j])
                    liste_x0 [j]=(binaire(line)[0])[1:3]+liste_x0[j]


#Calcule de x1
for cpt in range(11,23):
    line = file1.readline() 
    if cpt==11 : #uniquement 1 bit 
        if len(binaire(line))==1:
            for i in  range (len(liste_x1)): 
                liste_x1 [i]=binaire(line)[0][0:1]+liste_x1[i]
        if len(binaire(line))==2 : 
            for j in  range (len(liste_x1)): 
                    liste_x1.append(binaire(line)[1][0:1]+liste_x1[j])
                    liste_x1 [j]=binaire(line)[0][0:1]+liste_x1[j]
    else :
        if cpt==22:#uniquement 1 bit daonc on aura 2 bits inutiles 
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

#construction des couples 
couple =[]
for x0 in liste_x0:
    for x1 in liste_x1:
        couple.append((x0,x1) )
    
for  c in couple :
    print(c ,len(c[0]) ,len(c[1]) )

def completexo(xo,cpt):
    new_xo= xo+str(0)*(16-len(str(format(cpt,"b"))))+str(format(cpt,"b"))
    return new_xo

#print(completexo("",11))
#print(int(completexo("1010",11), 2))
#print(len(completexo("",11)))

def couple_to_random(couple):
    XO=0
    X1=0
    x1_essai=""
    a =25214903917
    m = 2 ** 48
    c =11

    for (xo,x1) in couple :
        for cpt in range(2**16):
            XO=int(completexo(xo,cpt), 2)
            X1=(a*XO+c)%m
            X1=format(X1,"b")
            x1_essai=str(X1)
            if (len(x1_essai)!=48):
                x1_essai=str(0)*(48-len(x1_essai))+x1_essai
            x1_essai=x1_essai[0:32]
            
            if(x1_essai==x1):
                return XO

    return -1

print(couple_to_random(couple))

