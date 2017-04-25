#include "inputs.h"
 
unsigned long n_inputs[1] = {1734992913};;
char mula_inputs[1] = {71};;
char mulpi_inputs[1] = {224};;
unsigned long es_inputs[1] = {3384311573};;
 
 
 

char *pj_block;
char *pn_block;
char *pi_block;
char pn_block;
unsigned long es_block;
unsigned long n_block;
char a_block;
char pi_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	n_block = n_inputs[blockInputUpdate_i]; 
	*a_block = mula_inputs[blockInputUpdate_i]; 
	*pi_block = mulpi_inputs[blockInputUpdate_i]; 
	es_block = es_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
quicksort_swapi( a_block, pi_block, es_block ); 
    pi_block = a_block; 
    pn_block = a_block + n_block * es_block; 
    pj_block = pn_block; 
} 
 