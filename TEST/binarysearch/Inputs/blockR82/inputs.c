#include "inputs.h"
 
 
 
 

volatileint binarysearch_seed_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	binarysearch_seed_block = binarysearch_seed_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
binarysearch_seed_block = ( ( binarysearch_seed_block * 133 ) + 81 ) % 8095; 
} 
 
