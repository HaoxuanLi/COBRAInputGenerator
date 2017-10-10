#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int binarysearch_seed_inputs[100] = {-23748,-16288,29432,-10899,-3831,6500,27029,2545,-25507,-840,20028,7181,21828,-11987,-568,31506,-11849,1848,4444,-20116,-15747,-9058,3001,1452,-16693,-6504,27243,-23559,14742,26250,10060,25919,25749,-26531,-27133,-31795,-2517,-31213,-28026,1341,-15846,9147,20898,32232,10895,-13449,-29186,-3922,20311,4221,7876,-1653,11628,-7490,-23429,-4302,8653,-21087,15824,6574,-5320,29379,10119,30882,-16122,13317,20684,-2177,10660,6585,-24377,-12926,-12038,18137,-11569,-15336,-20249,-24184,-25042,18466,21338,18732,29303,-15547,17176,18228,3940,18166,8892,31028,30449,30182,14687,-22485,6000,-20972,30608,21371,-26199,-11071};;
 
 
 

volatile int binarysearch_seed_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	binarysearch_seed_block = binarysearch_seed_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
binarysearch_seed_block = ( ( binarysearch_seed_block * 133 ) + 81 ) % 8095; 
PORTC = (0 << PC4); 

} 
 
