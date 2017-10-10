#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
int f_inputs[100] = {0,3,2,2,3,0,3,3,3,2,2,3,3,2,0,2,0,2,1,3,2,3,3,0,0,2,2,1,2,2,0,3,1,3,3,1,1,0,1,0,1,0,0,2,3,0,2,2,1,1,2,2,0,1,2,3,1,3,2,1,3,1,1,2,2,3,3,2,1,0,3,0,3,2,1,1,3,0,3,0,1,2,2,0,2,1,2,0,0,2,3,1,1,1,0,3,2,2,3,0};;
 
 
 

int f_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	f_block = f_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( f_block = 0 ; f_block < 4 ; f_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 
