#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int Inner_inputs[100] = {6,6,5,18,13,18,18,15,16,14,1,10,15,8,5,18,2,4,13,12,2,13,0,2,8,1,11,10,16,18,18,11,19,20,18,2,13,8,3,8,5,10,15,13,19,4,4,4,20,20,1,7,19,14,10,1,17,12,1,15,4,8,6,12,0,18,17,5,11,20,3,15,4,13,8,2,7,12,11,6,6,19,8,17,7,15,17,20,20,11,6,3,4,20,17,17,1,1,12,3};;
 
 
 

int Inner_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	Inner_block = Inner_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( Inner_block = 0; Inner_block < MAXSIZE; Inner_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 
