#include "inputs.h"
 
int cnt_inputs[100] = {5776,-29035,31190,-13233,21687,-27166,-863,27217,18290,26477,-17115,14317,-25565,-9650,27051,16679,-3498,32055,-16991,30874,-10838,-919,10949,-22167,12178,-29993,5867,-14574,4983,-4894,-7189,-3091,-7932,-9997,-23962,11564,8356,9626,19254,14071,-15297,-11228,15850,12099,-1400,16803,-6752,2272,21319,-18043,8050,-8289,-6349,27660,24046,-30740,21585,-14822,-18607,5896,1372,-29384,-15503,6335,-27753,-9673,-18191,-30258,-28515,25427,27506,17538,8701,15084,-22983,-7182,3274,2856,-12201,-29542,-21801,-14973,-2158,-8326,-2815,-22677,-4112,-27858,-18956,-24635,432,-14991,21501,11401,-30201,-14761,-27047,-15510,24779,-9928};;
 
 
 

int cnt_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	cnt_block = cnt_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
int k_block = cnt_block / 2; 
  int i; 
} 
 
