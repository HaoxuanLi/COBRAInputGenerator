#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int insertsort_iters_a_inputs[100] = {-25322,27580,23809,-22803,-1036,21317,-30829,-27248,-30675,15037,-15031,12329,-174,3484,-11593,1647,24892,-30481,1897,-13121,-15296,7374,12694,-21574,-2223,-2634,-2128,-14197,-30262,-4460,27717,26635,22885,24849,-14488,-19859,-29994,11456,15933,-26474,26194,-12287,19089,5562,17475,13997,-4210,7537,-1285,-16723,-31846,30202,28457,-30551,-28678,8090,23809,16663,-14710,29974,17781,27211,-711,-8233,11122,3856,24490,-16519,28509,8240,-10005,-4154,-19222,-12902,4066,12201,19675,-31851,3876,23489,11476,-389,-26339,-18889,-21622,-14161,-17017,-26151,14037,24392,-24563,-24350,-14090,13419,-23398,-25501,-25893,-1555,-9413,-27555};;
 
 
 

int insertsort_iters_a_block;
int insertsort_min_a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	insertsort_iters_a_block = insertsort_iters_a_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
insertsort_min_a_block = insertsort_iters_a_block; 
PORTC = (0 << PC4); 

} 
 
