#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
 
 
 

float iir_x_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
iir_x_block = ( float ) 1; 
PORTC = (0 << PC4); 

} 
 
