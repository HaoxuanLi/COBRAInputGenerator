#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
unsigned int array_i__inputs[100] = {56353,63650,62397,18310,38875,4805,63773,18,38338,28896,19705,36280,34776,53511,18216,40548,3237,7792,13619,42811,9305,50553,47745,34078,38638,25067,42871,28661,2670,25776,2679,6127,36465,9700,49481,17316,30258,14040,4214,21302,59886,550,27598,64775,7159,42353,52094,64653,35490,20291,30088,40066,51547,15110,24735,2644,51359,5584,54849,31319,54582,40694,65243,9193,589,10107,52220,64240,8402,13509,16852,58884,43393,13362,27703,61738,54996,11779,6681,43623,47709,21782,61057,60781,64770,31820,64913,11258,52974,4282,15172,29767,32971,45732,57890,2228,2390,25352,24666,23736};;
int i_inputs[100] = {1,1,0,10,0,2,4,4,2,3,4,7,5,2,8,0,1,7,6,0,8,3,1,7,8,8,7,6,8,5,10,4,4,2,8,7,5,4,9,9,5,5,10,10,0,10,8,3,0,5,7,7,10,8,4,0,4,3,4,7,7,0,6,9,6,2,5,2,7,3,6,3,4,6,6,4,10,1,6,1,2,9,7,8,8,1,6,10,1,5,7,8,8,3,8,8,6,6,1,8};;
 
 
 

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
for ( i_block = 0; i_block < 10; i_block++ ) 
{ 
	insertsort_a_block[i_block] = array_block[i_block]; 
} 
PORTC = (0 << PC4); 

} 
 
