#include "inputs.h"
 
int k_inputs[100] = {5,9,14,13,4,14,5,11,0,12,14,5,0,7,0,8,16,0,11,12,4,12,11,1,1,14,16,14,12,8,9,7,2,4,3,7,6,0,10,14,5,9,15,1,0,8,8,4,11,6,16,0,10,9,15,5,12,12,16,16,2,13,15,6,13,1,8,14,5,9,15,9,3,11,6,10,16,8,6,3,6,8,10,2,13,7,15,5,8,15,12,9,5,15,6,14,12,15,5,15};;
int i_inputs[100] = {28,10,20,6,30,1,10,27,23,18,15,16,14,1,31,20,13,6,1,13,12,6,7,18,1,18,30,10,29,0,1,11,27,26,22,25,0,25,25,8,18,2,23,29,22,12,29,31,2,27,11,23,0,25,1,13,6,31,19,24,30,3,14,28,27,20,6,15,2,12,7,22,12,23,25,16,31,10,17,21,15,25,9,5,15,3,23,10,6,1,9,13,19,3,24,23,22,0,19,1};;
int lo_inputs[100] = {2,22,20,28,31,4,10,24,18,9,16,16,26,22,6,24,27,12,22,6,14,12,26,19,22,2,4,6,1,26,2,16,6,29,29,14,22,10,19,23,1,10,12,17,4,29,20,27,15,9,2,14,0,28,20,6,4,14,12,0,9,13,21,15,21,26,16,18,17,18,30,29,15,6,3,11,9,2,27,24,3,19,11,22,30,1,27,26,3,16,8,24,30,1,30,30,4,20,19,20};;
 
 
 

int k_block;
int lo_block;
int i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	lo_block = lo_inputs[blockInputUpdate_i];
i_block = i_inputs[blockInputUpdate_i];
k_block = k_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( i_block = lo_block; i_block < lo_block + k_block; i_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 
