#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
float x_inputs[25] = {2.42418112e+38,1.78233848e+38,1.80656479e+38,3.28569475e+38,2.48452586e+38,1.07320536e+38,5.21428845e+37,2.47445519e+38,3.36243776e+38,8.64302623e+37,3.13635277e+37,1.84508074e+38,1.42548225e+38,1.26752828e+38,9.76858659e+37,7.16643408e+37,5.96534626e+37,6.25534871e+37,2.86240971e+38,6.30919141e+37,1.14037618e+38,1.70693496e+38,2.32213492e+38,2.80297134e+38,2.25360887e+38};;
int i_inputs[25] = {14,1,13,8,12,4,6,13,10,14,13,2,4,2,6,2,4,8,5,4,3,7,12,5,11};;
 
 
 

int i_block;
volatile float x_block;
float complex_updates_A_block[2*N];
float complex_updates_B_block[2*N];
float complex_updates_C_block[2*N];
float complex_updates_D_block[2*N];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	x_block = x_inputs[blockInputUpdate_i];
i_block = i_inputs[blockInputUpdate_i];

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
 
