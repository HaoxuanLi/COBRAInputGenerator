#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int up_inputs[100] = {6,9,12,12,3,6,3,2,2,9,10,3,4,0,1,10,9,13,4,10,4,12,0,8,11,4,1,9,7,5,11,9,5,0,10,3,10,7,6,9,10,5,4,1,6,4,11,1,1,8,12,7,0,3,5,8,11,6,9,0,14,12,9,14,1,3,11,13,1,6,6,4,1,4,6,9,4,10,10,12,11,9,7,14,9,13,7,7,10,8,13,11,8,5,4,12,1,4,9,1};;
int low_inputs[100] = {2,9,7,14,1,2,9,2,2,4,7,11,9,3,14,6,8,4,11,0,10,12,10,14,5,12,4,5,3,5,5,1,10,3,1,12,2,14,14,7,2,13,0,7,2,7,10,5,6,9,6,2,4,4,2,5,0,3,0,9,12,7,3,12,3,4,14,0,8,3,3,6,9,9,10,4,1,11,8,2,7,14,2,4,0,14,3,4,10,10,2,4,9,1,12,6,10,10,12,12};;
 
 
 

int low_block;
int up_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	up_block = up_inputs[blockInputUpdate_i];
low_block = low_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
while ( low_block <= up_block ) 
{break;} 
PORTC = (0 << PC4); 

} 
 
