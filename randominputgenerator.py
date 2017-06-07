# Automatically-generated file. Do not edit! 
 
 
import numpy as np 
import re as replace

np.set_printoptions(threshold=np.inf)

MAXSIZE=20


temp = str(np.random.randint(low=-32768,high=32767+1,size=(100,MAXSIZE,MAXSIZE),dtype=np.int64)) 
temp = replace.sub("\s|\n",",",temp)
temp = replace.sub("\,+",",",temp)
temp = temp.replace("[,","[").replace(",]","]").replace("[","{").replace("]","}")
print('int countnegative_array_MAXSIZE__MAXSIZE__inputs[100][MAXSIZE][MAXSIZE]= ' + temp + ';' ) 
