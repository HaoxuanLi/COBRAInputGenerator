#include "inputs.h"
 
int cnt_inputs[100] = {7,29,20,0,24,30,3,2,16,21,20,25,17,26,26,28,8,28,18,13,3,3,16,27,31,3,23,18,24,19,3,13,4,17,10,27,27,10,25,25,30,16,26,1,3,14,26,3,17,19,5,1,29,20,7,0,2,32,9,8,17,22,1,19,10,3,22,27,10,32,26,4,1,10,25,28,0,0,13,0,21,27,17,17,6,8,32,5,8,18,16,10,9,18,26,1,17,22,9,15};;
 
 
 

int cnt_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	cnt_block = cnt_inputs[blockInputUpdate_i];

} 
 
int c_g,a_g,i_g;
int k_g;

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
	int c=100, a=100,i; 
	 
int k_block = cnt_block; 
  k_block /= 2; 
{} 
PORTC = (0 << PC4); 

k_g = k_block;	
} 
 
