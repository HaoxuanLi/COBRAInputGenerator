#include "inputs.h"
 
 
 
 

registerint fr_block;
registerint k_block;
registerint l_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 
void benchmarkBlock(void) 
{ 
l_block = *( data_pointer + fr_block ); 
        k_block = *( data_pointer + fr_block + 1 ) ; 
        fr_block += 2 ; 
} 
 
