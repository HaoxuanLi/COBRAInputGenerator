#include "inputs.h"
 
 
 
 

registerint tmpi_block;
registerint tmpr_block;
registerint j_block;
registerint i_block;
registerint max_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	max_block = max_inputs[blockInputUpdate_i]; 
	i_block = i_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
j_block = i_block + max_block; 
          p = int_pointer + j_block; 
          q = int_pointer + i_block; 
 
          tmpr_block  = l * *( p - 1 ); 
          tmpr_block -= ( k * *p ); 
 
          tmpi_block  = l * *p; 
          tmpi_block += ( k * *( p - 1 ) ); 
 
          tmpr_block  = tmpr_block >> SHIFT ; 
          tmpi_block  = tmpi_block >> SHIFT ; 
 
          *( p - 1 ) = *( q - 1 ) - tmpr_block ; 
          *p     = *q - tmpi_block ; 
 
          *( q - 1 ) += tmpr_block ; 
          *q     += tmpi_block ; 
} 
 
