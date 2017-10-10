#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\fac\fac.h"
 
 
 
 

int fac_s_block;
volatile int fac_n_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
fac_s_block = 0; 
  fac_n_block = 5; 
PORTC = (0 << PC4); 

} 
 
