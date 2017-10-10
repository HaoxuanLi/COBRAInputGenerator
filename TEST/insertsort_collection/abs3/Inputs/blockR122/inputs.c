#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int insertsort_iters_a_inputs[100] = {7765,24736,-28038,-9966,-22034,-1837,18975,161,-4585,-14434,6142,-12581,14695,-29271,-26238,10057,1031,10771,1382,-17884,14329,4698,-32118,-3878,4953,-14379,10621,-32366,28716,14438,-24580,17402,-29645,-24000,-10877,7097,-14618,-24613,-26081,-2514,-14164,-32312,414,13558,-1666,29988,-18702,32533,25657,18849,23801,-5110,2680,-27370,-5845,-1150,20717,1905,12281,-1925,-19891,-15578,-13463,-3832,-27517,-27693,-28922,-28430,-29389,-14698,-32460,30826,11961,860,-14084,473,4828,5528,-9405,24142,15235,11565,21688,-173,28410,-561,3359,-13004,27439,-4281,21771,18027,-25015,28814,23208,-14389,30486,-19930,-8543,-30070};;
 
 
 

int insertsort_iters_a_block;
int insertsort_max_a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	insertsort_iters_a_block = insertsort_iters_a_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
insertsort_max_a_block = insertsort_iters_a_block; 
PORTC = (0 << PC4); 

} 
 
