#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
float x_inputs[25] = {5.64739759e+37,3.05652677e+38,2.33862624e+38,9.90733488e+37,1.97738109e+38,1.98920459e+38,2.30443400e+38,1.47003573e+38,5.51216584e+37,2.55999699e+38,2.01888942e+38,2.71978170e+38,5.00276225e+37,1.79525237e+38,2.72671910e+38,8.55786997e+37,2.87620902e+38,2.10065635e+38,2.21973243e+37,9.77338171e+37,3.93723150e+37,3.33882261e+38,2.86165348e+38,6.76878894e+36,1.97547044e+38};;
int i_inputs[25] = {5,4,13,2,14,5,15,3,6,11,8,6,11,5,13,10,1,5,11,6,0,12,6,3,2};;
 
 
 

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
 
