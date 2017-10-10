#include "inputs.h"
 
int i_inputs[100] = {22,20,0,14,22,7,27,23,12,25,15,9,20,22,0,29,26,0,15,23,4,21,8,24,5,18,7,16,9,19,8,15,9,0,4,24,4,12,5,5,24,9,11,16,9,15,29,23,4,18,2,28,30,27,23,25,19,22,26,16,25,3,20,18,25,15,11,20,4,26,13,0,16,12,4,0,5,6,21,15,19,0,20,14,20,15,9,16,27,9,18,12,15,24,27,15,1,11,20,20};;
 
 
 

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
 
