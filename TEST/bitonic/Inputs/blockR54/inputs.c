#include "inputs.h"
 
int i_inputs[100] = {19,26,4,21,10,20,1,3,13,29,29,17,25,9,3,6,10,1,30,7,10,11,12,24,16,19,8,13,8,6,29,10,10,15,17,27,1,26,0,28,9,18,31,16,27,25,19,15,18,24,31,2,26,24,4,26,1,5,12,26,11,14,5,5,22,7,23,16,2,26,23,20,29,9,17,14,2,16,10,5,7,7,8,0,14,15,27,22,24,27,4,3,3,4,27,13,4,26,14,30};;
 
 
 

int i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( i_block = 0; i_block < 32; i_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 
