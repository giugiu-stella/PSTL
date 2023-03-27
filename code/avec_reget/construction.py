def liste_couple_rejet(listecouple):
    couple =[]
    rejet0="110"
    rejet1="111"
    #18 psk les 3 derniers bits de x0 sans oublier
    for i in range(0, 63, 3):
        for (xo,x1) in listecouple:
            chaine=xo+x1
            if(i==0):
                newchaine0=rejet0+chaine
                newchaine1=rejet1+chaine
            else:
                newchaine0=chaine[0:i]+rejet0+chaine[i::]
                newchaine1=chaine[0:i]+rejet1+chaine[i::]

            newx0_0=newchaine0[0:31]
            newx0_1=newchaine1[0:31]

            newx1_0=newchaine0[31:63]
            newx1_1=newchaine1[31:63]
            couple.append((newx0_0,newx1_0))
            couple.append((newx0_1,newx1_1))

    return couple

def liste_couple_rejet_deux(listecouples):
    couple=[]
    rejet0="110"
    rejet1="111"

    for k in range(0, 63, 3):
        for i in range(0, 63-(k*2), 3):     # il y a 2^2 cas (2 rejets et chaque rejet a 2 possibilité)
            for (xo,x1) in listecouples:    # chaque cas ci dessous effectue 2 sous-cas donc on a bien 2 * 2 
                chaine=xo+x1
                fixe_0=chaine[0:k]+"110"+chaine[0:i]
                newchaine0_0=fixe_0+rejet0+chaine[i::]
                newchaine1_0=fixe_0+rejet1+chaine[i::]

                newx0_0_0=newchaine0_0[0:31]
                newx0_1_0=newchaine1_0[0:31]

                newx1_0_0=newchaine0_0[31:63]
                newx1_1_0=newchaine1_0[31:63]
                couple.append((newx0_0_0,newx1_0_0))
                couple.append((newx0_1_0,newx1_1_0))

                fixe_1=chaine[0:k]+"111"+chaine[0:i]
                newchaine0_1=fixe_1+rejet0+chaine[i::]
                newchaine1_1=fixe_1+rejet1+chaine[i::]

                newx0_0_1=newchaine0_1[0:31]
                newx0_1_1=newchaine1_1[0:31]

                newx1_0_1=newchaine0_1[31:63]
                newx1_1_1=newchaine1_1[31:63]
                couple.append((newx0_0_1,newx1_0_1))
                couple.append((newx0_1_1,newx1_1_1))
        
    return couple 

def liste_couple_rejet_trois(listecouple):
    couple=[]
    rejet0="110"
    rejet1="111"

    for v in range(0, 63, 3):
        for k in range(v, 63-(3), 3):
            for i in range(k, 63-(3*3-1), 3):   # il y a 8 cas possibles => 2^3 (3 rejets qui ont deux possibilités)
                for (xo,x1) in listecouple:     # chaque cas ci dessous effectue 2 sous-cas donc on a bien 4 * 2   
                    chaine=xo+x1

                    #cas 0
                    fixe_0=chaine[0:v]+"110"+chaine[v:k]+"111"+chaine[k:i]
                    newchaine0_0=fixe_0+rejet0+chaine[i::]
                    newchaine1_0=fixe_0+rejet1+chaine[i::]

                    newx0_0_0=newchaine0_0[0:31]
                    newx0_1_0=newchaine1_0[0:31]

                    newx1_0_0=newchaine0_0[31:63]
                    newx1_1_0=newchaine1_0[31:63]
                    couple.append((newx0_0_0,newx1_0_0))
                    couple.append((newx0_1_0,newx1_1_0))

                    #cas 1
                    fixe_1=chaine[0:v]+"111"+chaine[v:k]+"111"+chaine[k:i]
                    newchaine0_1=fixe_1+chaine[0:i]+rejet0+chaine[i::]
                    newchaine1_1=fixe_1+chaine[0:i]+rejet1+chaine[i::]

                    newx0_0_1=newchaine0_1[0:31]
                    newx0_1_1=newchaine1_1[0:31]

                    newx1_0_1=newchaine0_1[31:63]
                    newx1_1_1=newchaine1_1[31:63]
                    couple.append((newx0_0_1,newx1_0_1))
                    couple.append((newx0_1_1,newx1_1_1))

                    #cas 2
                    fixe_2=chaine[0:v]+"110"+chaine[v:k]+"111"+chaine[k:i]
                    newchaine0_2=fixe_2+chaine[0:i]+rejet0+chaine[i::]
                    newchaine1_2=fixe_2+chaine[0:i]+rejet1+chaine[i::]

                    newx0_0_2=newchaine0_2[0:31]
                    newx0_1_2=newchaine1_2[0:31]

                    newx1_0_2=newchaine0_2[31:63]
                    newx1_1_2=newchaine1_2[31:63]
                    couple.append((newx0_0_2,newx1_0_2))
                    couple.append((newx0_1_2,newx1_1_2))
                    
                    #cas 3
                    fixe_3=chaine[0:v]+"111"+chaine[v:k]+"110"+chaine[k:i]
                    newchaine0_3=fixe_3+chaine[0:i]+rejet0+chaine[i::]
                    newchaine1_3=fixe_3+chaine[0:i]+rejet1+chaine[i::]

                    newx0_0_3=newchaine0_3[0:31]
                    newx0_1_3=newchaine1_3[0:31]

                    newx1_0_3=newchaine0_3[31:63]
                    newx1_1_3=newchaine1_3[31:63]
                    couple.append((newx0_0_3,newx1_0_3))
                    couple.append((newx0_1_3,newx1_1_3))

    return couple

# utilisation de _ _ _ et . . . pour le visuel
# ___ => 110
# ... => 111
# 18 => 63
# 9 => 31
#test
couple=[("123456789","abcdefghi")]
couplenew=liste_couple_rejet_trois(couple)
#for i in couplenew:
  #  print(i)

            

    