#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
int f_inputs[100] = {2886,-25318,19739,844,2167,1242,12918,-18028,14512,29994,-966,6389,18966,23289,26233,892,-9181,-7860,-28741,-25408,8687,1510,-13742,-32330,9904,15655,-12965,23764,19695,-16695,31048,14369,28447,-7721,2794,-16709,31740,-15779,14999,-18577,22531,15824,-31577,-15292,18157,14435,-14146,14930,-5617,-13294,10525,-8966,23762,-20694,-20517,-26404,32435,-15187,14092,14462,14627,-4711,-5624,-13449,-30522,1992,-25576,24794,32756,29197,-15928,-31234,3551,14682,-23053,30697,-25392,10276,-25114,24635,8360,-27036,26859,20315,6877,-17280,8229,-9805,-25490,667,-10579,-2068,-4510,-24321,23207,-17616,23447,-20686,17339,25738};;
 
 
 

int f_block;
volatile float iir_wi_block[2*4];
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	f_block = f_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( f_block = 0 ; f_block < 2 * 4; f_block++ ) 
{ 
	iir_wi_block[ f_block ] = 0; 
} 
PORTC = (0 << PC4); 

} 
 