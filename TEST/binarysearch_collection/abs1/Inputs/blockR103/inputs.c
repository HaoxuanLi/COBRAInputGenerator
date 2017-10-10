#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int binarysearch_result_inputs[100] = {12441,9428,5714,-19944,3456,-17568,3416,26606,26536,-31169,-24982,-32342,2995,-13235,-6802,-25515,8931,20277,12335,-20553,-31567,-10742,3232,26520,13591,-15371,-24994,14331,5159,2439,-3057,12989,22100,11676,4229,-21574,-1393,25953,11633,27373,-4886,-30117,24081,-17486,-17103,1835,22076,-30282,-1743,29941,26203,13529,12557,-1112,-30361,5466,-26435,15202,-20740,20450,32274,29332,17872,-30081,-18304,25288,-14420,-21149,-32077,455,28116,-11738,24351,-15649,-26528,10420,-8903,-11957,4165,20559,-15070,-3483,-28696,5699,-29516,14105,5105,-23196,-29769,6911,-12987,25412,-3538,-15268,9397,12678,28719,-14894,20464,5296};;
 
 
 

int binarysearch_result_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	binarysearch_result_block = binarysearch_result_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
return( binarysearch_result_block ); 
PORTC = (0 << PC4); 

} 
 
