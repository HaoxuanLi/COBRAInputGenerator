#include "inputs.h"
 
const int bitonic_ASCENDING_block = 1;
 
 
 

 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 
int i_g;

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
int i; 
 
  /** When called with parameters lo = 0, cnt = a.length() and dir = 
    ASCENDING, procedure bitonicSort sorts the whole array a. **/ 
  _Pragma( "marker recSort" ) 
  bitonic_sort( 0, 32, bitonic_ASCENDING_block ); 
{} 
PORTC = (0 << PC4); 

} 
 
