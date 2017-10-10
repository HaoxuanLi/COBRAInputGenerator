#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int InnerIndex_inputs[100] = {7,10,14,6,6,11,11,9,4,19,4,18,15,4,19,20,3,18,18,6,6,10,18,2,0,11,6,0,8,15,18,11,6,6,2,19,5,20,20,12,1,20,3,4,2,5,5,19,1,7,4,7,10,8,7,18,13,1,13,19,9,13,4,5,1,2,0,0,15,15,17,12,20,6,3,7,6,2,19,0,6,0,14,13,7,12,5,20,11,15,12,17,20,16,6,18,9,10,20,18};;
int OuterIndex_inputs[100] = {6,0,18,3,3,10,11,16,20,3,4,15,9,0,6,15,0,11,5,18,11,2,12,0,3,16,20,7,4,7,17,20,14,1,4,19,2,0,1,15,11,4,15,19,18,17,12,4,0,10,19,2,16,19,9,17,9,13,9,2,2,16,1,20,13,3,7,2,15,15,7,10,18,13,10,20,11,17,2,10,15,5,3,8,15,7,1,17,13,12,20,4,5,16,2,6,5,8,2,8};;
 
 
 

int InnerIndex_block;
int OuterIndex_block;
int Array_block[MAXSIZE][MAXSIZE];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	InnerIndex_block = InnerIndex_inputs[blockInputUpdate_i];
OuterIndex_block = OuterIndex_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( InnerIndex_block = 0; InnerIndex_block < MAXSIZE; InnerIndex_block++ ) 
{ 
	Array_block[OuterIndex_block][InnerIndex_block] =  countnegative_randomInteger(); 
} 
PORTC = (0 << PC4); 

} 
 
