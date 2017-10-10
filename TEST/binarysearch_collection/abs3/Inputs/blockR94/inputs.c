#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int i_inputs[100] = {8,7,2,12,0,10,11,6,0,5,8,10,11,4,10,8,0,7,12,13,10,0,9,13,2,13,5,13,5,13,3,7,13,1,13,4,3,9,6,11,0,7,10,2,9,13,0,2,1,2,3,6,11,9,2,7,13,2,12,7,8,14,3,5,2,7,2,8,0,3,13,1,8,14,1,0,6,7,8,5,2,8,12,11,7,8,13,6,9,5,0,10,10,9,13,13,6,9,4,14};;
 
 
 

int i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( i_block = 0; i_block < 15; ++i_block ) 
{} 
PORTC = (0 << PC4); 

} 
 
