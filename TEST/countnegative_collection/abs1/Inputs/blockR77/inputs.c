#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int OuterIndex_inputs[50] = {5,1,2,1,12,1,2,19,10,17,19,5,20,3,6,12,18,12,1,15,12,20,19,8,15,20,5,11,15,16,1,0,3,4,13,11,2,8,18,4,12,2,15,14,20,19,13,11,5,16};;
int InnerIndex_inputs[50] = {12,11,7,20,18,7,12,14,19,13,16,17,8,17,20,3,17,3,9,19,14,4,14,0,5,1,9,5,12,16,10,14,10,12,16,19,6,0,12,9,1,4,19,11,5,13,14,9,11,12};;
 
 
 

int OuterIndex_block;
int InnerIndex_block;
int Array_block[MAXSIZE][MAXSIZE];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	OuterIndex_block = OuterIndex_inputs[blockInputUpdate_i];
InnerIndex_block = InnerIndex_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( OuterIndex_block = 0; OuterIndex_block < MAXSIZE; OuterIndex_block++ ) 
{ 
	for ( InnerIndex_block = 0; InnerIndex_block < MAXSIZE; InnerIndex_block++ ) 
	{ 
		Array_block[OuterIndex_block][InnerIndex_block] =  countnegative_randomInteger(); 
	} 
} 
PORTC = (0 << PC4); 

} 
 
