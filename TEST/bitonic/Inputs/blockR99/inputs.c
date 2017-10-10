#include "inputs.h"
 
int i_inputs[100] = {8,18,0,30,24,1,14,14,14,29,19,10,31,24,30,15,17,28,24,4,20,26,21,25,12,10,20,2,27,14,10,6,25,27,24,31,25,17,21,19,18,31,30,29,5,18,16,19,20,4,29,22,7,15,23,28,10,27,7,11,8,16,25,17,20,7,16,17,15,31,11,6,22,1,1,30,16,19,31,4,8,30,28,26,2,10,21,11,13,5,21,17,7,0,17,5,12,10,11,22};;
int dir_inputs[100] = {1,1,1,0,0,0,0,1,1,0,0,1,0,1,0,0,1,1,0,1,1,1,0,0,0,1,1,0,0,1,0,1,1,0,0,1,0,0,0,1,0,1,1,1,0,1,0,0,1,0,1,1,0,1,0,1,0,0,1,1,0,1,0,1,0,1,1,1,0,0,0,0,1,1,1,1,0,1,1,0,0,1,1,1,0,0,1,0,1,0,0,1,1,0,1,0,0,1,0,1};;
int k_inputs[100] = {3,13,3,1,5,8,5,10,11,15,3,9,7,14,9,7,4,3,6,16,13,1,13,5,2,10,2,14,0,5,9,10,11,0,6,13,14,8,16,0,8,0,5,11,8,2,5,16,3,2,1,16,4,1,3,3,2,9,12,2,13,3,6,9,4,3,13,5,14,7,16,14,16,1,3,2,6,8,2,4,3,8,14,9,6,8,15,1,13,5,14,10,6,16,4,6,5,5,14,6};;
 
 
 

int dir_block;
int k_block;
int i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	k_block = k_inputs[blockInputUpdate_i];
dir_block = dir_inputs[blockInputUpdate_i];
i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
bitonic_compare( i_block, i_block + k_block, dir_block ); 
{} 
PORTC = (0 << PC4); 

} 
 
