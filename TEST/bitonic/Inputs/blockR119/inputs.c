#include "inputs.h"
 
const int bitonic_ASCENDING_block = 1;
int lo_inputs[100] = {1,9,15,8,21,27,15,3,12,4,12,24,26,2,9,16,31,16,22,23,1,17,31,31,3,22,21,18,13,6,25,14,23,5,20,15,26,21,27,30,27,9,1,17,24,11,24,3,15,0,17,29,2,26,8,11,9,29,9,0,6,15,28,27,14,17,25,29,23,15,5,15,14,20,19,27,26,29,2,20,27,2,16,1,7,26,15,27,7,24,28,3,3,0,18,7,16,24,6,0};;
int k_inputs[100] = {15,16,5,17,11,4,4,12,30,20,30,29,11,8,24,2,6,14,11,28,2,20,32,21,29,12,10,6,15,4,30,10,13,15,20,19,21,6,15,11,27,2,18,26,12,25,6,19,6,29,6,0,8,21,1,2,1,22,21,30,1,10,10,19,18,15,7,7,31,25,10,1,27,1,22,23,28,25,0,29,1,22,6,26,2,0,11,20,0,9,17,19,30,4,29,17,29,4,9,11};;
const int bitonic_DESCENDING_block = 0;
 
 
 

int k_block;
int lo_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	k_block = k_inputs[blockInputUpdate_i];
lo_block = lo_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
bitonic_sort( lo_block, k_block, bitonic_ASCENDING_block ); 
    bitonic_sort( lo_block + k_block, k_block, bitonic_DESCENDING_block ); 
{} 
PORTC = (0 << PC4); 

} 
 
