#include "inputs.h"
 
 
 
 

registerint tmpr_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 
void benchmarkBlock(void) 
{ 
tmpr_block = *( int_pointer + j ) ; 
      *( int_pointer + j ) = *( int_pointer + i ) ; 
      *( int_pointer + i ) = tmpr_block ; 
 
      tmpr_block = *( int_pointer + j + 1 ) ; 
      *( int_pointer + j + 1 ) = *( int_pointer + i + 1 ) ; 
      *( int_pointer + i + 1 ) = tmpr_block ; 
} 
 
