#include "inputs.h"
 
 
 
 

 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
bitonic_init(); 
  bitonic_main(); 
{} 
PORTC = (0 << PC4); 

} 
 
