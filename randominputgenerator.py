# Automatically-generated file. Do not edit! 
 
 
import numpy as np 
import re as replace

np.set_printoptions(threshold=np.inf)

temp = str(np.random.randint(low=-32768,high=32767+1,size=(1,32),dtype=np.int64)) 
temp = replace.sub("\s|\n",",",temp)
temp = replace.sub("\,+",",",temp)
temp = temp.replace("[,","[").replace(",]","]").replace("[","{").replace("]","}")
print('int bitonic_a_32__inputs[1][32]= ' + temp + ';' ) 
