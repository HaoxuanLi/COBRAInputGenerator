#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
int f_inputs[100] = {10572,19224,1073,23130,1019,23212,-27946,-26262,24610,-27752,-4528,30552,-6854,9030,13525,9303,21994,-11001,7087,-4035,-1201,23215,20275,-11435,-9808,16154,3900,-27029,23262,4527,-7184,-30531,-19390,-23053,16736,-25963,3331,-15571,24829,11516,-15820,-24442,16932,19875,24327,-31797,-17157,-12259,-4832,-8813,-4209,-30442,28795,4551,-4860,-29154,-12245,-27379,23450,-32010,13421,-13350,-2878,26136,-17831,-21603,-15171,4574,4163,22177,22945,-10882,29700,-25385,-2900,-12538,24687,12908,-12563,-21192,-15807,-13971,2055,7637,23387,15861,31672,27659,-15864,4660,29997,2376,-13285,23813,27438,-3153,22385,-14387,25318,-10182};;
 
 
 

int f_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	f_block = f_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( f_block = 0 ; f_block < 5 * 4; f_block++ ) 
{} 
PORTC = (0 << PC4); 

} 
 