#include "inputs.h"
 
unsigned long j_inputs[1] = {2050968297};;
char mula_inputs[1] = {101};;
unsigned long es_inputs[1] = {813432979};;
 
 
 

char *a_block;
unsigned long es_block;
unsigned long j_block;
char a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	j_block = j_inputs[blockInputUpdate_i]; 
	*a_block = mula_inputs[blockInputUpdate_i]; 
	es_block = es_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
quicksort_vec( a_block, j_block, es_block ); 
      a_block += ( j_block + 1 ) * es_block; 
} 
 
