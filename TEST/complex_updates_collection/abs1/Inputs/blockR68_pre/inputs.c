#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
int i_inputs[25] = {1,9,3,11,14,6,10,14,9,5,0,14,9,4,13,8,4,5,10,15,1,13,10,7,5};;
float x_inputs[25] = {3.42086158e+35,3.30126728e+38,1.53263281e+38,6.46639111e+37,2.02504686e+38,2.91131434e+38,3.26883847e+38,1.40675865e+38,2.07723278e+36,1.63999474e+37,1.94205401e+38,6.39592116e+37,2.38760015e+38,5.05572763e+36,3.33441918e+38,1.41054412e+38,4.05961581e+37,1.57988404e+38,2.42876700e+38,7.35564172e+37,1.31963562e+38,3.17321367e+38,2.18785034e+38,8.01007342e+37,3.24288804e+38};;
 
 
 

int i_block;
volatile float x_block;
float complex_updates_A_block[2*N];
float complex_updates_B_block[2*N];
float complex_updates_C_block[2*N];
float complex_updates_D_block[2*N];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	i_block = i_inputs[blockInputUpdate_i];
x_block = x_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( i_block = 0 ; i_block < N ; i_block++ ) 
{ 
	complex_updates_A_block[i_block] += x_block; 
	    complex_updates_B_block[i_block] += x_block; 
	    complex_updates_C_block[i_block] += x_block; 
	    complex_updates_D_block[i_block] += x_block; 
} 
PORTC = (0 << PC4); 

} 
 
