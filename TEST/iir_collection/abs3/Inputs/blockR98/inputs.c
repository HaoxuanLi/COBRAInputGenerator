#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
char bitmask_inputs[100] = {43,188,43,46,251,232,110,135,172,11,215,228,66,250,199,213,154,118,130,158,32,196,252,175,109,134,162,97,159,15,59,239,103,35,1,49,125,194,154,136,138,144,229,167,67,5,255,121,147,27,119,13,35,188,123,254,206,204,211,252,236,192,81,84,112,116,241,154,170,196,175,250,143,35,173,220,238,187,84,125,79,27,224,112,138,162,93,221,234,42,84,1,115,225,62,140,73,66,208,45};;
 
 
 

volatile char bitmask_block;
unsigned char p_address;
unsigned char *p_block=&p_address;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	bitmask_block = bitmask_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
*p_block ^= bitmask_block; 
PORTC = (0 << PC4); 

} 
 
