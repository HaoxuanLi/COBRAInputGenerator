#include "inputs.h"
 
unsigned int i_inputs[1] = {3987};;
 
 
 

char quicksort_strings_block[ 681 ][ 20 ];
unsigned int i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	i_block = i_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
quicksort_strings_block[ i_block ][ 20 - 1 ] = '\0'; 
} 
 
