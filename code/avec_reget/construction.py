def check(c,couple):

    for i in couple :
        if(i==c):
            return False
    return True

def aux(chaine,i,couple,rejets):
    max=len(chaine)
   # print (chaine[max-i::])
    chaine_110=chaine[0:max-i]+"110"+chaine[max-i::]
    chaine_111=chaine[0:max-i]+"111"+chaine[max-i::]
    taille=len(chaine_110)

    x0_110=chaine_110[taille-32:taille]
    x1_110=chaine_110[taille-64:taille-32]

    x0_111=chaine_111[taille-32:taille]
    x1_111=chaine_111[taille-64:taille-32]
    c1=(x0_110,x1_110)
    c2=(x0_111,x1_111)
    # if(check(c1,couple)):
    #if(check(c2,couple)):
    couple.append((x0_111,x1_111))
    couple.append(c1)
    return couple

def liste_couple_rejet(listecouple):
    couple =[]
    for i in range(0, 64, 3):
        for (xo,x1) in listecouple:
            chaine=x1+xo
            aux(chaine,i,couple)
    return couple

def liste_couple_rejet_deux(listecouples):
    couple=[]
    for k in range(0, 64, 3):
        for i in range(k+3, 64, 3):     # il y a 2^2 cas (2 rejets et chaque rejet a 2 possibilité)
            for (xo,x1) in listecouples:    # chaque cas ci dessous effectue 2 sous-cas donc on a bien 2 * 2 
                chaine=x1+xo
                fixe=chaine[0:64-k]+"___"+chaine[64-k::]
                aux(fixe,i,couple)
                fixe=chaine[0:64-k]+"..."+chaine[64-k::]
                aux(fixe,i,couple)
    return couple 

def liste_couple_rejet_trois(listecouple):
    couple=[]
    for v in range(0, 64, 3):
        for k in range(v+3, 64, 3):
            for i in range(k+3, 64, 3):   # il y a 8 cas possibles => 2^3 (3 rejets qui ont deux possibilités)
                for (xo,x1) in listecouple:     # chaque cas ci dessous effectue 2 sous-cas donc on a bien 4 * 2   
                    chaine=xo+x1
                    #cas 1
                    fixe=chaine[0:i]+"___"+chaine[i:64-v]+"___"+chaine[64-v::]
                    aux(fixe,i,couple)

                    #cas 2
                    fixe=chaine[0:i]+"..."+chaine[i:64-v]+"..."+chaine[64-v::]
                    aux(fixe,i,couple)

                    #cas 3
                    fixe=chaine[0:i]+"___"+chaine[i:64-v]+"..."+chaine[64-v::]
                    aux(fixe,i,couple)
                    
                    #cas 4
                    fixe=chaine[0:i]+"..."+chaine[i:64-v]+"___"+chaine[64-v::]
                    aux(fixe,i,couple)

    return couple

def liste_couple_rejet_n(rejets,listecouple):
    if rejets == 0:
        return listecouple 
    else:
        couples = []
        #oublie la liste des couples
        listecouplenew=liste_couple_rejet_n(rejets-1,listecouple)
        #print (listecouplenew)
        for i in range(0,64,3):
            for (xo, x1) in listecouplenew:
                chaine=x1+xo
                aux(chaine,i,couples,rejets)
        
        return couples


def liste_couple_rejet_general(listecouples):
    resultat_couple =[]
    resultat_final=[]
    for i in range (4):
        ##i==0 donc un seul rejet 
        if i==0:
            resultat_couple=liste_couple_rejet(listecouples)
            resultat_final=resultat_couple
        else :
            #pour calculer le rejet i il nous faut la liste de rejet (i-1)
            listecouples=resultat_couple
            resultat_couple=liste_couple_rejet(listecouples)
            resultat_final=resultat_final+resultat_couple
    return resultat_final 



# utilisation de _ _ _ et . . . pour le visuel
# ___ => 110
# ... => 111
# 18 => 64
# 9 => 32
#test
#couple=[("01000101011011010001000011011010","10101010110010010011000111000111")]
#couplenew=liste_couple_rejet_n(2,couple)
#fichier = open("essay.txt", "a")
#for (x0 ,x1 ) in couplenew:
      # couple.append((x0,x1) )
 #      fichier.write("( "+str(x0) + " , "+ str(x1)+ " )")
  #     fichier.write("\n")


