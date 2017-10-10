#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int OuterIndex_inputs[100] = {20,16,7,5,1,10,15,9,15,20,8,16,3,0,9,14,8,4,13,8,1,20,1,14,7,11,9,11,16,3,9,13,16,2,17,14,13,19,5,3,20,19,6,10,20,16,18,18,8,6,9,20,19,4,8,1,12,8,11,17,18,11,0,20,11,3,17,0,4,3,20,11,8,15,12,18,6,14,8,2,13,20,14,6,8,0,6,6,17,20,8,9,17,7,10,16,1,10,2,5};;
int InnerIndex_inputs[100] = {0,2,6,16,1,11,10,14,6,11,18,15,4,10,18,15,7,3,1,17,3,4,10,8,8,15,10,2,9,18,10,15,15,10,3,5,14,14,8,20,11,17,15,13,18,18,9,9,11,20,3,20,11,11,7,3,0,0,10,18,6,11,7,3,4,11,8,9,17,12,16,0,17,3,18,19,1,1,14,12,20,8,16,8,9,20,12,6,18,13,12,15,19,16,20,5,19,15,15,17};;
 
 
 

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
 
