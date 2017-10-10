#include "inputs.h"
 
int cnt_inputs[100] = {8,14,14,28,25,23,5,25,28,19,12,1,23,25,1,30,29,25,16,15,16,29,8,11,23,29,15,4,4,1,18,8,5,17,22,7,13,8,16,20,2,4,21,1,22,19,31,24,31,7,6,30,32,6,31,6,26,17,20,1,6,1,13,28,12,5,29,27,16,10,26,25,20,4,8,0,19,10,5,3,31,32,0,9,18,17,13,30,14,2,32,13,10,2,21,5,8,5,21,2};;
 
 
 

int cnt_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	cnt_block = cnt_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
if ( cnt_block > 1 ) 
{} 
PORTC = (0 << PC4); 

} 
 
