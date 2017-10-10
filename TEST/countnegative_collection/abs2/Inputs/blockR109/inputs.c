#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int Outer_inputs[100] = {19,14,0,6,12,13,14,12,16,7,7,7,5,6,17,17,18,11,15,2,8,15,15,19,17,4,10,18,18,4,20,20,17,3,10,12,5,18,3,15,7,9,5,17,12,11,8,8,8,15,11,13,14,10,12,19,8,6,5,18,8,3,4,3,16,6,15,0,8,0,0,2,15,13,18,5,2,8,18,3,11,16,9,6,5,19,9,9,16,20,1,13,9,0,4,10,2,15,3,4};;
 
 
 

int Outer_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	Outer_block = Outer_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( Outer_block = 0; Outer_block < MAXSIZE; Outer_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 
