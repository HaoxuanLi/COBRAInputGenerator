#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
int f_inputs[100] = {10,15,3,5,17,7,2,14,9,2,6,16,13,14,13,10,6,5,19,13,6,19,12,15,1,14,5,14,0,5,6,6,17,17,13,13,11,5,16,11,17,16,10,16,15,10,13,8,8,16,0,6,9,15,7,4,1,0,17,7,7,11,17,15,16,11,5,17,8,18,3,2,8,0,11,19,17,1,18,3,10,11,10,19,14,15,4,5,4,5,12,7,12,3,4,0,8,14,8,5};;
 
 
 

int f_block;
volatile float iir_coefficients_block[5*4];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	f_block = f_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
iir_coefficients_block[ f_block ] = 7; 
PORTC = (0 << PC4); 

} 
 
