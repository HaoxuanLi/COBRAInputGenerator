#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int returnValue_inputs[100] = {14872,-3778,-32321,-1696,-28826,-6937,-31547,-13310,-2289,-31756,10509,9933,-2825,-30694,12628,-20880,7221,2144,-4508,9199,-24059,24984,30947,8236,28706,-29156,27376,-15462,-30675,-6779,-1220,-9850,22005,-14468,-32721,29154,-10556,9469,-7066,-9291,16783,-18380,27414,-20659,27618,3149,-30190,10193,-7326,-10584,-10271,31388,-11419,3125,30817,-14602,26817,-8677,10511,-27621,7596,31857,-3445,-25782,-6074,-18916,-5701,5545,-1256,24943,18578,-12645,-9156,390,23696,-412,-9619,-2423,21730,-15379,28008,7038,-27572,29343,2408,21821,-7432,26654,-14342,29327,-14571,30357,-7397,25917,-1794,22894,17655,441,19086,6721};;
 
 
 

int returnValue_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	returnValue_block = returnValue_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
return (returnValue_block + (-52) ) != 0; 
PORTC = (0 << PC4); 

} 
 
