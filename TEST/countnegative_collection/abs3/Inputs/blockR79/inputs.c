#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int InnerIndex_inputs[100] = {12,4,16,15,0,17,15,11,20,5,0,10,12,1,4,20,9,6,19,1,20,17,10,14,5,7,1,8,16,11,18,5,0,13,6,8,3,2,10,20,19,6,15,20,11,4,12,9,10,18,13,20,11,19,16,3,17,5,11,9,14,19,16,17,11,11,15,2,8,18,1,12,15,14,13,3,15,1,4,4,10,6,3,6,9,16,0,11,7,8,1,18,2,2,13,19,0,9,0,17};;
 
 
 

int InnerIndex_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	InnerIndex_block = InnerIndex_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( InnerIndex_block = 0; InnerIndex_block < MAXSIZE; InnerIndex_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 
