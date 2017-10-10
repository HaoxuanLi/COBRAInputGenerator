#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
 
 
 

float pa_address;
float *pa_block=&pa_address;
float pb_address;
float *pb_block=&pb_address;
float pc_address;
float *pc_block=&pc_address;
float pd_address;
float *pd_block=&pd_address;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
*pa_block++ = 2; 
    *pa_block++ = 1; 
    *pb_block++ = 2; 
    *pb_block++ = 5; 
    *pc_block++ = 3; 
    *pc_block++ = 4; 
    *pd_block++ = 0; 
    *pd_block++ = 0; 
PORTC = (0 << PC4); 

} 
 
