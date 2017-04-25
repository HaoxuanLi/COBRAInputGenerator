#include "inputs.h"
 
 
 
 

registerint m_block;
registerint j_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	m_block = m_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
j_block -= m_block ; 
      m_block >>= 1; 
} 
 
