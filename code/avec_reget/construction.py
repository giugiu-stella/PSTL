def check(c,couple):

    for i in couple :
        if(i==c):
            return False
    return True

# fonction qui renvoie un couple en rajoutant 110 ou 111 à l'indice i de la chaine "chaine"
def aux(chaine,i,couple,rejets):
    max=len(chaine)
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

#Fonction qui calcul la liste des couples pour un certain nombre de rejet n 
def liste_couple_rejet_n(rejets,listecouple):
    if rejets == 0:
        return listecouple 
    else:
        couples = []
        # met à jour sa liste des couples
        listecouplenew=liste_couple_rejet_n(rejets-1,listecouple)
        #print (listecouplenew)
        for i in range(0,64,3):
            for (xo, x1) in listecouplenew:
                chaine=x1+xo
                aux(chaine,i,couples,rejets)
        
        return couples

#------------------------------------------------------------------------------------------------------------------------------------------------------
#Les fonctions qui vont suivre sont des fonction de tests 

#Fonction qui renvoie la liste des couple pour un rejet 
def liste_couple_rejet(listecouple):
    couple =[]
    for i in range(0, 64, 3):
        for (xo,x1) in listecouple:
            chaine=x1+xo
            aux(chaine,i,couple)
    return couple

#Fonction qui renvoie la liste des couple pour deux rejets 
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

#Fonction qui renvoie la liste des couple pour 3 rejets
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


# Fonction qui renvoie la liste des couple pour 4 rejets
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




