import time
import matplotlib.pyplot as plt
import numpy as np
largeur_barre=0.2
#file= open('Output_avec_rejet.txt', 'r')
#values=file.readlines()
#x=[]
#histogramme des dé 
#for v in values:
    #x.append (int (v[0]))
#plt.hist(x,6 ,edgecolor = 'white')
#plt.show()
#file.close()
#Histogramme  pour les rejets 
file= open('data.txt', 'r')

values=file.readlines()
print(len(values))
x=[0,0]
param=[6 ,7]
for v in values:
    if int(v)==6:
        x[0]=x[0]+1
    else:
        x[1]=x[1]+1
plt.bar(param, x, width = largeur_barre, color = 'orange', edgecolor = 'black', linewidth = 2)
plt.show()
