#include "inputs.h"
 
int cnt_inputs[100] = {28,19,13,4,13,0,13,21,15,15,7,12,32,9,10,16,21,30,6,25,23,22,29,17,26,0,10,25,18,29,29,25,9,16,26,13,0,24,25,0,28,13,23,13,17,16,28,7,30,24,23,24,7,31,6,6,20,28,30,14,15,16,15,32,29,15,12,26,23,29,4,31,1,21,24,17,18,14,22,10,4,13,17,16,5,2,6,0,5,19,25,19,17,21,31,26,5,20,12,10};;
 
 
 

int cnt_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	cnt_block = cnt_inputs[blockInputUpdate_i];

} 
 
int k_g;
int i_g;

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
int k_block = cnt_block / 2; 
  int i; 
{} 
PORTC = (0 << PC4); 

k_g = k_block;	
} 
 
