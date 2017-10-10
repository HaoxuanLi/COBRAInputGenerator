#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int binarysearch_seed_inputs[100] = {-25381,22376,-19999,7353,19727,4866,-15644,-4183,-10647,-30317,-26002,-16662,-18433,-3960,-21583,-19398,2340,-2307,-17151,5029,7723,-1292,9874,-27126,19336,7441,16736,4180,4454,-18106,-6845,-11291,3389,19141,27408,-30082,19525,-23897,31275,20114,17014,32747,20411,-11408,15487,-8298,25560,28104,2816,-27438,22465,-12615,-4171,433,30787,13540,28793,-11317,-21750,27318,3530,-23806,-16908,-22007,-23363,14859,-6627,18439,13349,14624,-26490,-25889,15827,-21951,14431,-9634,-22364,-26894,18699,-31935,-1974,-5647,13100,-9163,25826,-9186,13637,385,28746,-27797,-9317,32285,-18093,1575,-752,-11481,-16191,22266,8942,10236};;
 
 
 

volatile int binarysearch_seed_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	binarysearch_seed_block = binarysearch_seed_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
binarysearch_seed_block = ( ( binarysearch_seed_block * 133 ) + 81 ) % 8095; 
PORTC = (0 << PC4); 

} 
 
