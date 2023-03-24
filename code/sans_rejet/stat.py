import matplotlib.pyplot as plt
file= open('Output_sans_rejet.txt', 'r')
values=file.readlines()
x=[]
for v in values:
    x.append (int (v[0]))

plt.hist(x,6 ,edgecolor = 'white')
plt.show()
