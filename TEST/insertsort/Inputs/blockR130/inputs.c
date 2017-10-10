#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int insertsort_iters_i_inputs[100] = {20009,-7422,-8163,-6695,-21628,23031,-23905,15876,20114,-21809,7735,-18797,-6368,18538,-17055,-6220,26817,-31245,-3687,-8616,5998,-22292,-7015,29974,17750,13983,-6034,-15118,-8506,-12039,25994,-23893,-18208,4495,23672,-5265,1727,10384,3608,9042,-10850,29928,5564,-31453,-21210,8192,-29111,-15917,-13076,21560,-27228,28997,7867,-32719,-16269,-19683,-16051,6238,4269,27832,-5072,22824,9941,-5922,27914,27846,-31216,4314,6339,29876,-7676,12858,32481,21207,-681,30263,27393,210,14697,-19621,-30616,24080,17429,-29306,-3286,26137,-12657,-3992,-10201,-23058,-13285,-25599,-23172,15719,12462,19081,9428,29345,-20895,-25603};;
 
 
 

int insertsort_iters_i_block;
int insertsort_max_i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	insertsort_iters_i_block = insertsort_iters_i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
insertsort_max_i_block = insertsort_iters_i_block; 
PORTC = (0 << PC4); 

} 
 
