#include "inputs.h"
 
int mid_inputs[] = {3, 3, 8, 14, 10, 8, 9, 7, 2, 7, 1, 3, 11, 4, 14, 13, 3, 5, 6, 7, 1, 6, 13, 11, 8, 9, 9, 7, 14, 5, 1, 13, 12, 5, 5, 2, 6, 2, 3, 13, 11, 4, 3, 7, 1, 10, 7, 4, 2, 6, 6, 6, 4, 3, 13, 7, 10, 10, 2, 12, 14, 8, 6, 1, 13, 10, 9, 13, 3, 6, 14, 8, 10, 9, 14, 10, 5, 9, 8, 1, 13, 2, 14, 12, 12, 11, 13, 13, 6, 13, 5, 1, 5, 2, 10, 1, 8, 2, 4, 13};
 
 
 

int up_block;
int mid_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	mid_block = mid_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
up_block = mid_block - 1; 
} 
 
