#include "inputs.h"
 
unsigned long n_inputs[1] = {2724291122};;
char mula_inputs[1] = {6};;
unsigned long es_inputs[1] = {666176885};;
 
 
 

char *pi_block;
unsigned long es_block;
unsigned long n_block;
char a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	n_block = n_inputs[blockInputUpdate_i]; 
	*a_block = mula_inputs[blockInputUpdate_i]; 
	es_block = es_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
pi_block = quicksort_pivot_vectors( a_block, n_block, es_block ); 
} 
 
