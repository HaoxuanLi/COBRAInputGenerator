#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int binarysearch_seed_inputs[100] = {8393,-6862,-7169,-25534,-15979,-25318,21834,-28013,3610,-22821,-12342,-14435,-15944,31125,20423,30123,11230,2539,28543,28980,-10131,-28559,-9516,4284,17587,6877,-29864,7929,-18813,3322,-10323,12725,25997,8001,26467,-27696,-1883,-19329,-24246,16735,30549,-13740,22314,26220,-3844,-7351,2639,10721,20480,26373,-20836,-31564,-1056,-20147,20743,-4802,-18821,-873,21678,-10582,-20983,24735,9131,-23597,14379,-11058,-29414,12881,-626,7680,-1881,14185,-5270,3975,-3984,-28242,-26891,-21834,4445,-7029,10284,-8499,-26110,-2145,-5004,19699,26787,-4712,31793,25829,16050,-25878,-28912,-22473,22038,-25001,-2328,-19607,-419,596};;
 
 
 

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
 
