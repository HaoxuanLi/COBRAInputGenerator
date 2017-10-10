#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int insertsort_min_a_inputs[100] = {-27024,-23004,-14062,1597,7849,-16309,30985,8060,-17452,31444,11210,10921,22531,10020,-32574,7717,9599,-9635,-3959,28850,-9998,22292,-22109,-20890,2356,-10538,24643,32015,-6365,18571,3340,24508,-1150,5836,-13852,14396,10624,-27358,29156,27978,10314,6111,-14087,11711,-82,-12477,-29206,9146,29028,14658,-24909,-25264,13697,17405,14232,15028,-30038,22377,-5921,-14054,6966,16212,10533,-19650,619,3771,6280,-27625,23149,7274,-32078,25842,4639,-1178,10480,23450,10064,-21100,32156,-18628,7623,8763,-31834,8118,16479,-7608,-14205,32650,-31481,23676,23209,31953,-13118,-1763,-8234,9067,3236,-7714,23971,6904};;
int insertsort_iters_a_inputs[100] = {22945,30860,20868,-30283,12755,-25277,23412,1893,4661,11162,-28082,-15630,19105,-14354,21049,-773,-5449,7969,13800,-28461,-16091,-21726,-15445,18831,9917,20329,20967,-10141,24233,2868,-21773,18464,-2931,30661,25990,17719,18685,4031,22809,-27243,5403,30666,21208,-12894,-22871,-12748,-20955,12100,-7226,-4215,5761,10643,30908,-2161,-20881,31446,-32040,6379,26340,5048,-23337,31260,-24131,-24138,16168,971,25266,15574,8739,26415,-18436,530,9905,-728,-23606,-22899,2855,11130,8939,-16678,-23733,12752,-7411,-7946,13654,-26093,27849,-11311,2281,-3884,-7413,-5561,-24999,21177,-4749,1324,12471,21609,2847,31913};;
 
 
 

int insertsort_iters_a_block;
int insertsort_min_a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	insertsort_min_a_block = insertsort_min_a_inputs[blockInputUpdate_i];
insertsort_iters_a_block = insertsort_iters_a_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
if ( insertsort_iters_a_block < insertsort_min_a_block ) 
{ 
	insertsort_min_a_block = insertsort_iters_a_block; 
} 
PORTC = (0 << PC4); 

} 
 
