#include "inputs.h"
 
 
 
 

registerint max_block;
registerint level_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	level_block = level_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
max_block = level_block; 
} 
 
