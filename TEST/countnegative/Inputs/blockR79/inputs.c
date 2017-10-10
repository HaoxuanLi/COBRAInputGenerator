#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int InnerIndex_inputs[100] = {7,17,20,9,10,8,8,8,1,16,3,14,3,10,17,2,12,20,10,11,9,13,8,12,4,6,18,1,4,3,19,7,13,8,14,0,20,18,5,17,4,0,1,11,10,13,10,16,17,2,9,10,4,8,2,20,0,6,9,10,9,0,19,1,4,10,6,7,17,6,18,1,3,19,17,11,11,9,2,11,8,5,5,20,8,8,10,20,10,17,13,16,10,14,16,5,1,10,1,16};;
int OuterIndex_inputs[100] = {0,12,8,0,16,1,19,10,4,19,19,10,10,5,13,2,13,9,12,18,5,8,8,6,9,9,8,6,1,11,6,17,13,17,13,5,18,10,9,3,11,9,10,12,18,5,16,10,0,12,9,7,2,19,11,6,4,2,5,1,5,20,13,0,6,18,17,1,11,14,3,16,12,10,18,4,10,18,20,2,14,4,11,13,20,16,11,7,4,9,17,18,11,19,4,4,7,8,7,0};;
 
 
 

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
 
