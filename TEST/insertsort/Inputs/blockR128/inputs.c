#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int insertsort_iters_i_inputs[100] = {-25764,24870,-18172,-3653,-14398,-3160,-28762,-28258,5156,-16312,11656,-22211,-2177,15442,-19858,-5574,19336,26827,-10807,13398,-2145,-2841,10188,-13212,20814,-31406,-10335,-623,-17608,-28831,-22026,-20518,9335,-11405,-26624,7795,3054,-31946,10537,-6176,-27556,-30623,-7598,-29754,18730,-6801,24957,-30064,-22878,-15879,-4086,-21498,-28805,7488,1705,6452,-19228,15577,22251,4360,-30960,-312,-28312,-5421,-15402,29693,20461,-3640,9440,-10270,23698,-16629,-17239,20838,11102,-18727,-29236,-27312,15781,2192,28384,-12719,876,-31717,-2584,13942,19783,-16941,18289,23287,27567,13669,-28519,8536,-26430,22516,23259,25982,-18193,-23137};;
 
 
 

int insertsort_iters_i_block;
int insertsort_min_i_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	insertsort_iters_i_block = insertsort_iters_i_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
insertsort_min_i_block = insertsort_iters_i_block; 
PORTC = (0 << PC4); 

} 
 
