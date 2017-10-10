#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
 
 
 

int insertsort_iters_i_block;
int insertsort_min_i_block;
int insertsort_max_i_block;
int insertsort_iters_a_block;
int insertsort_min_a_block;
int insertsort_max_a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 
unsigned int a_g[11]={0,11,10,9,8,7,6,5,4,3,2};

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
unsigned int a[11] = {0, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2}; 
 
    insertsort_iters_i_block = 0; 
    insertsort_min_i_block = 100000; 
	insertsort_max_i_block = 0; 
    insertsort_iters_a_block = 0; 
    insertsort_min_a_block = 100000; 
    insertsort_max_a_block = 0; 
PORTC = (0 << PC4); 

} 
 
