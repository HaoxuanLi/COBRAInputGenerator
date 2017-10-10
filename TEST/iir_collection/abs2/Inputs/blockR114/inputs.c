#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
int f_inputs[100] = {4,1,2,6,2,4,3,5,6,3,1,1,4,0,5,4,6,1,7,2,1,4,1,6,0,7,2,7,7,0,3,3,4,7,0,7,1,1,7,1,5,1,5,1,0,3,1,4,0,2,0,3,0,5,3,3,2,4,3,0,5,1,7,6,2,1,3,7,4,4,1,7,7,5,0,4,5,6,4,3,6,4,6,5,1,3,0,7,0,2,1,7,6,4,5,1,1,3,5,0};;
 
 
 

int f_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	f_block = f_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( f_block = 0 ; f_block < 2 * 4; f_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 
