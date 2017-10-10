#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
char bitmask_inputs[100] = {114,70,126,108,173,27,33,194,29,79,174,120,35,67,211,6,163,222,174,12,134,149,133,114,186,53,49,178,144,184,52,39,14,178,151,84,120,88,229,48,225,245,19,51,170,145,100,234,235,253,154,41,200,121,22,150,125,125,152,31,156,170,57,72,60,176,69,2,113,3,87,182,166,12,195,78,78,54,77,180,133,181,162,254,69,79,158,33,57,255,135,188,169,200,116,232,192,58,107,8};;
 
 
 

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
 
