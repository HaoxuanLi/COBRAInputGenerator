#include "inputs.h"
 
int i_inputs[100] = {5,28,22,12,16,1,7,14,26,24,4,26,4,27,4,27,26,26,30,8,3,20,22,3,18,26,14,3,4,25,20,26,15,0,3,18,18,2,4,3,15,17,4,14,6,22,23,20,10,14,27,4,13,5,16,30,31,28,19,15,13,0,0,3,20,19,17,15,8,1,8,22,31,25,27,9,31,31,26,25,28,15,14,21,28,3,24,10,18,10,5,26,23,10,8,9,26,18,3,16};;
 
 
 

int bitonic_a_block[ 32 ];         
int i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
bitonic_a_block[ i_block ] = ( 32 - i_block ); 
{} 
PORTC = (0 << PC4); 

} 
 
