#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
unsigned int array_i__inputs[100] = {60265,1146,64257,10093,30793,37318,4642,2177,65272,33345,50834,59089,39985,45890,16434,51386,1835,30452,2938,45936,34758,5033,52813,820,31729,64665,29234,24116,11918,5137,9199,64364,3349,54882,10969,35139,36527,50635,5388,48612,46637,8160,30255,2327,23590,25105,19620,39419,15991,23435,52663,55700,16860,8548,53915,51936,9104,60196,28150,28917,59683,7204,25282,40825,22109,61165,54061,37561,10261,4875,32033,20294,64857,19356,34764,26594,24044,59806,20930,23496,27214,4027,8963,46602,53,47129,13338,31688,15720,47124,44580,61239,23162,59623,50328,10841,58025,60160,5441,61850};;
int i_inputs[100] = {6,2,6,2,8,8,2,2,9,5,1,2,9,9,8,7,5,4,5,2,1,7,0,3,9,6,4,1,2,10,7,7,6,8,4,4,10,10,2,4,4,10,2,1,5,7,0,8,3,0,0,9,5,6,8,8,1,3,6,4,9,4,10,3,4,10,5,6,1,9,7,2,0,3,7,9,2,9,10,0,2,7,7,4,9,4,1,1,5,8,7,8,8,2,7,8,10,1,10,10};;
 
 
 

volatile int i_block;
unsigned int *array_block;
unsigned int insertsort_a_block[11];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	array_block[i_block] = array_i__inputs[blockInputUpdate_i];
i_block = i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
insertsort_a_block[i_block] = array_block[i_block]; 
PORTC = (0 << PC4); 

} 
 
