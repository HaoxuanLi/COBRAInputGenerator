#include "inputs.h"
 
char mulpj_inputs[1] = {17};;
char mula_inputs[1] = {135};;
unsigned long es_inputs[1] = {3892267288};;
unsigned long n_inputs[1] = {2286191937};;
 
 
 

unsigned long n_block;
unsigned long j_block;
unsigned long es_block;
char a_block;
char pj_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	*pj_block = mulpj_inputs[blockInputUpdate_i]; 
	*a_block = mula_inputs[blockInputUpdate_i]; 
	es_block = es_inputs[blockInputUpdate_i]; 
	n_block = n_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
quicksort_swapi( a_block, pj_block, es_block ); 
    j_block = ( pj_block - a_block ) / es_block; 
    n_block = n_block - j_block - 1; 
} 
 