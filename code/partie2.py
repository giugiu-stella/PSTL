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
#calcule de x0
for cpt in range(11):
    line = file1.readline() 
    if len(binaire(line))==1:
        for i in  range (len(liste_x0)): 
            liste_x0 [i]=binaire(line)[0]+liste_x0[i]
    
    if len(binaire(line))==2 :   # si la liste [000,001]  alors on devra rajouter dans chaque element le premier et le 2eme 
        for j in  range (len(liste_x0)): 
                liste_x0.append(binaire(line)[1]+liste_x0[j])
                liste_x0 [j]=binaire(line)[0]+liste_x0[j]
        
#Calcule de x1
for cpt in range(11,22):
    line = file1.readline() 
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
    print(c )