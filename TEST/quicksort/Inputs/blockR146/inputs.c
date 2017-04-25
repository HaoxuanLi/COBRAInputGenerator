#include "inputs.h"
 
unsigned long es_inputs[1] = {58612278};;
char mulpj_inputs[1] = {153};;
char mulpi_inputs[1] = {248};;
 
 
 

unsigned long es_block;
char pj_block;
char pi_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	es_block = es_inputs[blockInputUpdate_i]; 
	*pj_block = mulpj_inputs[blockInputUpdate_i]; 
	*pi_block = mulpi_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
quicksort_swapi( pi_block, pj_block, es_block ); 
} 
 
