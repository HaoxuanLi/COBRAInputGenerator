#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int i_inputs[100] = {4,7,0,10,6,1,8,1,8,10,10,2,2,8,7,6,5,8,10,1,8,3,3,1,6,8,5,6,6,8,7,1,0,3,10,0,9,6,2,3,6,4,6,7,5,4,5,5,1,5,1,8,8,7,5,0,1,1,8,8,9,8,1,5,5,6,6,8,0,10,0,1,10,2,0,6,3,8,6,4,9,5,7,9,2,7,6,10,10,2,4,3,0,2,6,8,2,7,9,6};;
 
 
 

volatile int i_block;
 

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
 
