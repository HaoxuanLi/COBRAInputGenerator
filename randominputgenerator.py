# Automatically-generated file. Do not edit! 
 
 
import numpy as np 
import re as replace

np.set_printoptions(threshold=np.inf)



temp = str(np.random.randint(low=0,high=3+1,size=(1,20),dtype=np.int64)).replace("[[","{").replace("]]","};") 
temp = replace.sub("\s+|\n",",", temp).replace("{,","{").replace(",}","}").replace(",,",",") 
print('int f_inputs[20] = ' + temp + ';' ) 
temp = str(np.random.uniform(low=1.2e-38,high=3.4e+38+1,size=(1,20),)).replace("[[","{").replace("]]","};") 
temp = replace.sub("\s+|\n",",", temp).replace("{,","{").replace(",}","}").replace(",,",",") 
print('float y_inputs[20] = ' + temp + ';' ) 
temp = str(np.random.uniform(low=1.2e-38,high=3.4e+38+1,size=(20,5*4),)) 
temp = replace.sub("\s|\n",",",temp)
temp = replace.sub("\,+",",",temp)
temp = temp.replace("[,","[").replace(",]","]").replace("[","{").replace("]","}")
print('float iir_coefficients_inputs[20][5*4]= ' + temp + ';' ) 
temp = str(np.random.uniform(low=1.2e-38,high=3.4e+38+1,size=(20,2*4),)) 
temp = replace.sub("\s|\n",",",temp)
temp = replace.sub("\,+",",",temp)
temp = temp.replace("[,","[").replace(",]","]").replace("[","{").replace("]","}")
print('float iir_wi_inputs[20][2*4]= ' + temp + ';' ) 
