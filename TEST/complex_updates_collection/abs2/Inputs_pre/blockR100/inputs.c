#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
int i_inputs[100] = {3,12,8,1,3,3,9,0,11,5,5,6,4,1,10,1,3,9,9,5,4,12,0,6,7,0,11,10,0,7,13,3,7,8,7,5,12,13,10,12,8,4,3,15,12,15,12,13,0,13,2,7,8,10,3,13,11,11,1,0,7,14,3,1,0,2,1,5,5,5,1,8,9,0,0,13,9,9,10,4,9,15,10,6,2,1,5,11,0,4,2,12,7,0,3,3,14,2,9,10};;
 
 
 

int i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( i_block = 0; i_block < N; i_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 
