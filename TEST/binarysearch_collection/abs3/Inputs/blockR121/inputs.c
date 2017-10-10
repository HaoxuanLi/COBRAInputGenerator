#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int up_inputs[100] = {6,6,2,0,0,10,8,3,13,13,1,10,10,10,11,1,7,8,0,2,8,14,5,14,5,5,3,14,2,1,0,6,4,5,4,9,3,10,12,11,11,5,11,11,6,2,7,8,0,13,5,9,2,3,0,0,9,10,3,1,8,13,3,12,7,7,2,4,0,12,4,14,14,2,0,11,6,0,8,11,12,1,14,3,1,0,14,11,2,10,14,10,10,1,11,14,8,13,9,5};;
int low_inputs[100] = {4,11,11,5,6,13,7,7,3,4,1,7,12,9,9,5,8,5,3,0,3,3,9,1,5,2,7,9,14,1,3,6,1,4,1,10,11,6,4,6,8,3,9,7,0,10,11,2,6,7,14,14,6,3,0,9,12,2,10,2,2,13,10,11,14,13,2,7,9,5,13,3,2,14,12,11,4,14,1,13,9,13,3,14,14,14,14,12,3,6,5,0,0,0,12,9,2,5,2,12};;
 
 
 

int low_block;
int up_block;
int mid_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	up_block = up_inputs[blockInputUpdate_i];
low_block = low_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
mid_block = ( low_block + up_block ) >> 1; 
PORTC = (0 << PC4); 

} 
 
