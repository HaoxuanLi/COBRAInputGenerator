#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
int f_inputs[100] = {-29624,3519,-6620,-21049,-12237,7085,26791,-9583,-20209,-5841,13291,308,-11327,-13165,-3515,24006,-14474,1570,31717,-12668,-6434,31018,-1460,-18204,26111,-1279,-27362,-14696,-4838,-9190,-12988,-20876,19991,14571,-9589,-21757,-23679,10015,-22766,-9632,16347,4084,30744,32619,-16863,1017,18039,4610,5325,-19434,8819,-32232,7628,-30479,-19046,29696,6530,-13856,-23462,-5479,5199,-2731,-5174,-30858,15500,-3831,-11323,-31994,-11816,21129,-1009,-31290,-31819,12267,-3213,-23034,-3066,-9266,-27882,18313,2360,-22871,21379,26716,16791,32672,-20346,12162,20041,30953,-17608,16484,-31273,21097,5045,4321,-2369,874,-1087,-28150};;
 
 
 

int f_block;
volatile float iir_coefficients_block[5*4];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	f_block = f_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( f_block = 0 ; f_block < 5 * 4; f_block++ ) 
{ 
	iir_coefficients_block[ f_block ] = 7; 
} 
PORTC = (0 << PC4); 

} 
 
