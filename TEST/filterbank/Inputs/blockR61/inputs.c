#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\filterbank\filterbank.h"
 
static int filterbank_return_value_inputs[100] = {-451,6377,7262,25950,11515,29402,-970,-25890,-28277,11299,-20606,28824,-26672,30036,20683,3957,-15002,12104,-16436,-28810,-751,-611,-1951,16184,-13572,-373,-8838,14794,-8429,12039,6215,-31907,26519,-45,-4830,-24959,11154,11904,-877,-19355,-14273,20718,4615,-5268,-27206,-11061,-12833,13925,30614,17623,32428,5665,10993,15586,-25775,-8099,26821,-28487,-11507,24663,-20763,-16618,11746,27580,-11366,-27998,18236,570,1663,27114,29635,-24677,20722,25347,-18207,24846,23718,-25508,-8653,-7820,29412,9428,-30559,-3843,-2689,-23802,12494,6864,-8491,15568,-3603,-29417,9315,-26498,16161,31800,12592,20866,577,32129};;
 
 
 

static int filterbank_return_value_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	filterbank_return_value_block = filterbank_return_value_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
return filterbank_return_value_block; 
PORTC = (0 << PC4); 

} 
 
