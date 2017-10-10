#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
 
 
 

volatile int countnegative_seed_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
countnegative_seed_block = 0; 
PORTC = (0 << PC4); 

} 
 
