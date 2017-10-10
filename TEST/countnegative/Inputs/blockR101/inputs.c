#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
 
 
 

 

void blockInputUpdate(int blockInputUpdate_i) 
{
	
} 
 
int Outer_g,Inner_g;
int Ptotal_g;
int Ntotal_g;
int Pcnt_g;
int Ncnt_g;

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
register int Outer, Inner; 
 
  int Ptotal_block = 0; /* changed these to locals in order to drive worst case */ 
  int Ntotal_block = 0; 
  int Pcnt_block = 0; 
  int Ncnt_block = 0; 
PORTC = (0 << PC4); 

Ptotal_g = Ptotal_block;	
Ntotal_g = Ntotal_block;	
Pcnt_g = Pcnt_block;	
Ncnt_g = Ncnt_block;	
} 
 
