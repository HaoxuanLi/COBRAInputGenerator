#include "inputs.h"
 
char mula_inputs[1] = {166};;
unsigned long n_inputs[1] = {1124035573};;
unsigned long es_inputs[1] = {2564542648};;
 
 
 

char *pi_block;
unsigned long es_block;
unsigned long n_block;
char a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	*a_block = mula_inputs[blockInputUpdate_i]; 
	n_block = n_inputs[blockInputUpdate_i]; 
	es_block = es_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
pi_block = a_block + ( n_block >> 1 ) * es_block; 
} 
 
