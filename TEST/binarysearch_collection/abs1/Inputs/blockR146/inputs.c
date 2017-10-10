#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
 
 
 

int binarysearch_result_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
binarysearch_result_block = binarysearch_binary_search( 8 ); 
PORTC = (0 << PC4); 

} 
 
