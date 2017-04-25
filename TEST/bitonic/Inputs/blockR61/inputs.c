#include "inputs.h"
 
int bitonic_a_32__inputs[1][32]= {{18451,-425,-20891,-3574,-19886,12695,-14294,-12727,16049,7378,21444,13525,-27893,-13019,24693,-21559,5496,-9986,-617,-9337,1014,28753,14858,-24869,27180,-5635,-14894,217,26991,-6087,29471,-21620}};
 
 
 

int bitonic_a_block[ 32 ];         
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
	int bitonic_a_block_a;
	for(bitonic_a_block_a=0;bitonic_a_block_a<32;bitonic_a_block_a++){
		bitonic_a_block[bitonic_a_block_a]=bitonic_a_32__inputs[blockInputUpdate_i][bitonic_a_block_a];
	}
	
} 
 
void benchmarkBlock(void) 
{ 
int checksum_block = 0; 
 
  checksum_block += bitonic_a_block[ 0 ] + bitonic_a_block[ 21 ] + bitonic_a_block[ 31 ]; 
} 
 
