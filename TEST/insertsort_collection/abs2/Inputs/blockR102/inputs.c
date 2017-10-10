#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int i_inputs[100] = {9,6,7,11,10,11,7,7,5,9,4,7,9,7,11,2,2,5,5,3,7,11,6,8,11,10,3,8,9,5,4,6,8,5,7,6,9,6,5,4,8,6,3,11,2,6,3,11,2,6,9,5,7,2,8,5,6,5,7,8,6,3,9,9,3,11,4,9,10,7,8,11,11,2,4,3,8,6,3,3,4,4,11,10,11,11,8,6,10,8,10,11,2,11,8,2,6,11,5,11};;
 
 
 

int i_block;
int insertsort_iters_i_block;
int j_block;
int insertsort_iters_a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
insertsort_iters_i_block++; 
 
        j_block = i_block; 
 
        insertsort_iters_a_block = 0; 
PORTC = (0 << PC4); 

} 
 
