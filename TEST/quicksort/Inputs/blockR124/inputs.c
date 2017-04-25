#include "inputs.h"
 
unsigned long es_inputs[1] = {925273917};;
char mula_inputs[1] = {209};;
unsigned long n_inputs[1] = {1838267275};;
 
 
 

char *pi_block;
unsigned long es_block;
unsigned long n_block;
char a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	es_block = es_inputs[blockInputUpdate_i]; 
	*a_block = mula_inputs[blockInputUpdate_i]; 
	n_block = n_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
pi_block = a_block + ( n_block >> 1 ) * es_block; 
} 
 
