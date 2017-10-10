#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
int i_inputs[100] = {6,13,5,15,3,3,13,11,2,11,7,8,5,5,2,9,14,10,1,11,13,12,8,12,4,12,10,1,9,5,15,11,15,0,12,4,11,14,1,6,0,11,0,2,7,7,12,5,7,0,9,3,12,2,10,0,2,12,8,7,0,9,7,9,7,0,13,13,9,13,8,8,5,6,2,7,10,12,11,11,15,6,12,9,9,3,4,9,12,6,9,12,10,0,3,2,12,13,0,0};;
 
 
 

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
 
