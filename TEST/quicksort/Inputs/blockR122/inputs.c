#include "inputs.h"
 
unsigned long es_inputs[1] = {1029799689};;
unsigned long n_inputs[1] = {1387263176};;
char mula_inputs[1] = {206};;
 
 
 

char *pi_block;
unsigned long es_block;
unsigned long n_block;
char a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	es_block = es_inputs[blockInputUpdate_i]; 
	n_block = n_inputs[blockInputUpdate_i]; 
	*a_block = mula_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
pi_block = quicksort_pivot_strings( a_block, n_block, es_block ); 
} 
 
