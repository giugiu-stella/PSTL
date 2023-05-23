import matplotlib.pyplot as plt
file= open('Output_sans_rejet.txt', 'r')
values=file.readlines()
x=[]
for v in values:
    x.append (int (v[0]))
 #Histogramme de la distribution des faces de dés pour 1000 lancés pour un générateur sans rejet d’information
plt.hist(x,6 ,edgecolor = 'white')
plt.show()
