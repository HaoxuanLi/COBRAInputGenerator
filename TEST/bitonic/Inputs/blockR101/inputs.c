#include "inputs.h"
 
int k_inputs[100] = {11,9,3,2,10,4,15,3,12,14,3,8,13,3,0,16,0,2,5,14,6,13,10,8,16,12,11,4,14,13,14,1,3,12,6,9,14,10,10,14,6,16,4,14,12,6,10,0,6,2,15,16,13,14,2,8,1,6,14,9,13,4,5,2,12,16,11,7,10,3,14,6,5,11,15,10,1,8,1,2,5,7,10,1,1,3,4,4,3,4,3,15,15,15,7,11,6,3,4,8};;
 
 
 

int k_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	k_block = k_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
if ( k_block > 1 ) 
{} 
PORTC = (0 << PC4); 

} 
 
