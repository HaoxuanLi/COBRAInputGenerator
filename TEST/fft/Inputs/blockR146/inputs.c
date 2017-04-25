#include "inputs.h"
 
 
 
 

registerint level_block;
registerint max_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	max_block = max_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
level_block = max_block << 1; 
} 
 
