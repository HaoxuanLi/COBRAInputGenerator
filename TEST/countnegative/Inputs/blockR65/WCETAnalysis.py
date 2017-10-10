import matplotlib.pyplot as plt

f = open(r"C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\Inputs\blockR65\log.txt")
lines = f.readlines()
f.close()
data_hex = [line.strip().strip('\x00') for line in lines]
data = [int(n,16) for n in data_hex]
plt.plot(range(1,len(data)+1), data, 'ro')
plt.show()
print(max(data))