#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int i_inputs[100] = {9,11,7,10,5,2,2,3,11,9,2,11,5,3,7,3,9,4,9,11,5,6,7,9,3,4,9,6,3,4,8,2,3,10,3,8,7,7,8,7,10,10,5,2,5,6,5,3,9,11,6,9,2,11,3,3,7,4,4,10,6,3,8,7,6,4,5,3,6,9,7,11,10,8,7,6,3,2,11,2,6,4,8,7,6,7,9,10,5,10,6,5,4,6,5,6,9,9,2,2};;
 
 
 

int i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
while(i_block <= 10) 
{break;} 
PORTC = (0 << PC4); 

} 
 
