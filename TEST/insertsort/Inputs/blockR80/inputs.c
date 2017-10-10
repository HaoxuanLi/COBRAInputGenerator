#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int i_inputs[100] = {6,4,1,5,2,3,7,0,8,0,7,0,7,9,4,6,5,9,8,8,10,1,10,5,8,5,5,3,0,1,10,2,6,4,1,9,5,2,10,9,8,0,6,4,7,2,1,7,8,8,9,7,5,9,7,10,5,2,3,4,0,9,8,4,9,4,10,2,5,10,6,6,9,10,0,7,6,6,6,10,2,8,9,10,5,8,10,0,0,8,5,1,1,3,4,6,9,8,10,1};;
 
 
 

int i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( i_block = 0; i_block < 10; i_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 
