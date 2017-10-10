#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
int i_inputs[100] = {3,4,1,5,2,12,13,13,10,4,10,14,5,4,3,4,12,12,8,5,0,10,7,10,3,6,1,6,6,13,15,12,10,9,7,8,6,10,1,11,4,7,1,12,14,10,7,2,1,14,8,6,8,2,9,13,1,9,13,12,1,15,4,1,8,12,11,10,15,9,14,13,4,1,9,4,1,2,14,11,9,0,15,2,2,5,4,12,3,6,2,4,9,3,12,12,2,7,11,7};;
 
 
 

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
 
