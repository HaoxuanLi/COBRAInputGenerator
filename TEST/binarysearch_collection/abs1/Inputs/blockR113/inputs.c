#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
 
 
 

 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 
int fvalue_g,mid_g,up_g,low_g;

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
int fvalue_block, mid, up_block, low_block; 
 
  low_block = 0; 
  up_block = 14; 
  fvalue_block = -1; 
PORTC = (0 << PC4); 

low_g = low_block;	
up_g = up_block;	
fvalue_g = fvalue_block;	
} 
 
