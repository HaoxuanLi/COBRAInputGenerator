#include "inputs.h"
 
int checksum_inputs[1] = {7585};;
 
 
 

int checksum_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	checksum_block = checksum_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
return( checksum_block ); 
} 
 
