#include "inputs.h"
 
int mid_inputs[] = {5, 4, 4, 10, 0, 5, 10, 1, 9, 0, 4, 8, 0, 2, 14, 3, 13, 12, 14, 11, 2, 11, 7, 12, 11, 6, 9, 9, 14, 3, 8, 11, 5, 10, 3, 12, 3, 6, 12, 3, 12, 4, 5, 9, 12, 7, 9, 3, 8, 0, 2, 9, 5, 10, 1, 13, 3, 8, 5, 12, 5, 7, 9, 5, 14, 14, 2, 11, 9, 4, 11, 14, 7, 6, 1, 14, 4, 6, 4, 1, 9, 5, 10, 11, 9, 13, 9, 3, 10, 8, 7, 7, 12, 3, 4, 14, 1, 10, 2, 0};
 
 
 

int low_block;
int mid_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	mid_block = mid_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
low_block = mid_block + 1; 
} 
 
