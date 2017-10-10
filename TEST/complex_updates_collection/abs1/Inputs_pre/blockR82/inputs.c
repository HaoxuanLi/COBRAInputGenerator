#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
int i_inputs[100] = {15,0,7,4,7,4,1,0,14,14,0,6,11,3,3,0,3,12,8,15,1,2,6,8,1,0,13,3,14,5,2,14,14,5,13,10,7,6,9,13,2,8,14,14,12,13,9,9,14,11,5,15,4,9,14,10,7,7,10,3,4,15,6,4,8,14,1,14,3,6,6,5,7,3,3,9,14,2,13,13,6,11,1,0,8,6,10,13,9,12,15,9,0,6,13,8,7,0,11,10};;
 
 
 

int i_block;
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
	i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( i_block = 0; i_block < N; i_block++ ) 
{ 
	*pa_block++ = 2; 
	    *pa_block++ = 1; 
	    *pb_block++ = 2; 
	    *pb_block++ = 5; 
	    *pc_block++ = 3; 
	    *pc_block++ = 4; 
	    *pd_block++ = 0; 
	    *pd_block++ = 0; 
} 
PORTC = (0 << PC4); 

} 
 
