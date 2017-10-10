#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
unsigned int array_i__inputs[20] = {62125,44073,37787,2399,21872,18469,11884,18785,28492,50224,34322,49218,64105,21082,156,50447,10771,46970,22065,10423};;
int i_inputs[20] = {0,6,5,8,4,3,5,2,1,3,0,2,7,1,9,10,1,4,5,0};;
 
 
 

volatile int i_block;
unsigned int array_block[11];
unsigned int insertsort_a_block[11];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	array_block[i_block] = array_i__inputs[blockInputUpdate_i];
i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
insertsort_a_block[i_block] = array_block[i_block]; 
PORTC = (0 << PC4); 

} 
 
