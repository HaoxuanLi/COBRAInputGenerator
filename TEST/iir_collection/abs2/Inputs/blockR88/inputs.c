#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
int f_inputs[100] = {6,1,5,3,4,3,7,1,5,6,4,0,4,4,4,3,4,1,1,7,2,6,7,6,7,6,0,5,2,0,1,6,7,3,5,5,6,1,0,7,3,3,1,4,5,6,7,0,4,7,3,5,5,6,0,6,5,1,0,2,4,3,0,2,2,0,1,7,2,6,2,6,4,6,2,0,7,0,0,5,3,6,5,7,7,5,3,1,7,5,4,1,6,6,6,1,4,0,7,5};;
 
 
 

int f_block;
volatile float iir_wi_block[2*4];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	f_block = f_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
iir_wi_block[ f_block ] = 0; 
PORTC = (0 << PC4); 

} 
 
