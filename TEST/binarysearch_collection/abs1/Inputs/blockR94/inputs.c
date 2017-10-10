#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int i_inputs[100] = {12,10,11,13,2,9,9,5,5,5,4,12,14,9,5,11,14,13,4,4,7,6,5,8,13,4,0,2,0,3,7,7,12,9,4,14,7,12,12,6,5,9,14,14,6,10,3,4,8,4,4,8,1,8,1,9,12,11,7,9,6,9,4,6,10,13,14,10,12,12,8,4,12,6,6,11,7,6,13,7,0,11,13,9,6,13,3,5,2,2,14,7,8,7,9,6,6,14,14,8};;
 
 
 

int i_block;
struct binarysearch_DATA binarysearch_data_block[15];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( i_block = 0; i_block < 15; ++i_block ) 
{ 
	binarysearch_data_block[ i_block ].key = binarysearch_randomInteger(); 
	binarysearch_data_block[ i_block ].value = binarysearch_randomInteger(); 
} 
PORTC = (0 << PC4); 

} 
 
