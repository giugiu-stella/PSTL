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
    
for  c in couple :
   print(c ,len(c[0]) ,len(c[1]) )

def completexo(xo,cpt):
    new_xo=xo+ str(0)*(16-len(str(format(cpt,"b"))))+format(cpt,"b")
    return new_xo

#print(completexo("11001111111110010000111100110001",11))
#print(int(completexo("1010",11), 2))
#print(len(completexo("",11)))
#couple=[("11001111111110010000111100110001","11010111010110111000101010010100")]  #Je sais que c'est mon couple il fait partie de la liste des tuples 
test_simple=[("00000000000000000000000000000001", format(25214903928,"b")[0:32])]
def couple_to_random(liste_couple):
    XO=0
    X1=0
    a =25214903917
    m = 2 ** 48
    c =11
    for couple in liste_couple :
        for cpt in range(2**16):
            XO=int(completexo(couple[0],cpt), 2)
            print(XO)
            X1=(a*XO+c)%m
            print(int(format((X1),"b")[0:32],2) ,int(couple[1],2) )
            if(int(format((X1),"b")[0:32],2)==int(couple[1],2)):
                return XO

    return -1

print(couple_to_random(test_simple))

