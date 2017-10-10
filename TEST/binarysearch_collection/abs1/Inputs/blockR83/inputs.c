#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int binarysearch_seed_inputs[100] = {1500,-18383,-21933,-4705,27445,-17955,-23224,3350,-25808,-5426,22723,-14554,1424,-28265,19245,21684,-26196,-5115,8755,-26747,27585,22495,-6313,30465,-1498,-12656,10061,11507,11768,-28877,9365,-9540,-27274,18096,5077,-1867,4082,19865,11172,-23404,-10924,25257,3272,-29359,26522,-14076,-11125,-29761,19734,-18885,-16160,5849,-14661,21183,-14587,-13474,4237,9417,28601,28834,7347,20740,-12123,20580,18856,-27193,29956,21981,9487,-7190,2221,12600,-21980,21942,18698,-11198,-17099,-30495,23280,26008,15425,27814,-32286,-5548,9808,-22814,-14956,-12812,27685,28164,-29696,3234,-12314,319,-26298,-15076,-11091,-25042,-18477,2142};;
 
 
 

volatile int binarysearch_seed_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	binarysearch_seed_block = binarysearch_seed_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
return( binarysearch_seed_block ); 
PORTC = (0 << PC4); 

} 
 
