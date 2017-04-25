#include "inputs.h"
 
unsigned long n_inputs[1] = {2855361384};;
unsigned long es_inputs[1] = {955272717};;
unsigned long j_inputs[1] = {2308868540};;
char mula_inputs[1] = {48};;
 
 
 

unsigned long n_block;
unsigned long j_block;
unsigned long es_block;
char a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	n_block = n_inputs[blockInputUpdate_i]; 
	es_block = es_inputs[blockInputUpdate_i]; 
	j_block = j_inputs[blockInputUpdate_i]; 
	*a_block = mula_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
quicksort_vec( a_block + ( j_block + 1 ) * es_block, n_block, es_block ); 
      n_block = j_block; 
} 
 
