#include "inputs.h"
 
unsigned long es_inputs[1] = {2031239254};;
 
 
 

char *pj_block;
unsigned long es_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	es_block = es_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
pj_block -= es_block; 
} 
 