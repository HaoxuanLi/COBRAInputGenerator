#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int OuterIndex_inputs[100] = {5,11,12,15,7,6,0,18,11,1,7,7,4,6,13,18,13,15,1,19,11,3,6,20,13,0,8,4,9,2,15,7,10,11,4,11,0,11,17,12,13,5,17,8,3,13,20,10,14,15,15,17,14,5,2,11,10,16,9,7,1,13,14,19,2,16,11,0,14,14,14,4,11,3,19,13,15,14,14,18,18,11,20,6,1,20,13,13,12,18,15,14,19,1,8,17,2,1,20,15};;
 
 
 

int OuterIndex_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	OuterIndex_block = OuterIndex_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( OuterIndex_block = 0; OuterIndex_block < MAXSIZE; OuterIndex_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 
