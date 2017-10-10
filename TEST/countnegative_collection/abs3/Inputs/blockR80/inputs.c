#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int OuterIndex_inputs[100] = {2,11,7,0,15,15,8,3,7,10,2,2,9,6,14,19,16,10,1,15,20,4,4,18,16,14,6,14,15,17,12,15,0,17,1,1,2,5,5,8,3,14,5,18,6,11,20,19,16,8,7,19,0,12,0,7,13,5,16,5,19,16,7,3,11,19,15,2,13,9,10,12,2,0,17,8,18,6,12,13,7,16,19,4,5,17,13,17,13,1,13,12,14,11,1,12,11,15,7,17};;
int InnerIndex_inputs[100] = {20,4,18,0,20,0,15,15,6,10,0,7,19,2,6,10,8,4,1,18,3,18,11,9,14,11,19,20,20,17,2,10,4,10,17,8,17,0,20,7,17,5,1,16,9,19,15,12,4,15,12,2,13,10,18,7,3,3,19,0,19,13,13,1,15,14,12,5,6,7,5,14,13,15,20,2,11,20,5,15,9,11,0,11,0,5,16,3,0,11,1,9,6,20,14,13,16,10,16,18};;
 
 
 

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
Array_block[OuterIndex_block][InnerIndex_block] =  countnegative_randomInteger(); 
PORTC = (0 << PC4); 

} 
 
