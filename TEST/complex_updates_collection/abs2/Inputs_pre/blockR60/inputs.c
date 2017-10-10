#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
 
 
 

 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 
int i_g;
float x_g;

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
int i; 
  volatile float x_block = 0; 
PORTC = (0 << PC4); 

x_g = x_block;	
} 
 
