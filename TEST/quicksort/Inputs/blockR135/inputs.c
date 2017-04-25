#include "inputs.h"
 
unsigned long es_inputs[1] = {477754769};;
 
 
 

char *pi_block;
unsigned long es_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	es_block = es_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
pi_block += es_block; 
} 
 
