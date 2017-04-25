#include "inputs.h"
 
 
 
 

registerint j_block;
registerint m_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	m_block = m_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
j_block += m_block ; 
} 
 
