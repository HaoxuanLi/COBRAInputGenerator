#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int countnegative_seed_inputs[100] = {8780,-1244,-24331,-303,30763,7695,-9474,19249,1458,18645,-18803,-27809,-10494,-32350,11002,25876,-20389,-13333,-24090,-4639,28873,-29487,22857,18362,14898,-26518,-18595,-19341,-28553,10462,28452,-30700,-2526,-13249,20296,-8222,21731,-20258,9890,21656,-19578,11075,30452,14321,18469,21750,-19784,10195,-4341,-19895,18054,-31564,1732,-27524,7350,-22389,-22286,-5564,-24259,-8935,19848,16299,-9982,28368,-30345,8844,15051,3229,-12248,2808,-9830,12967,18322,837,24152,-2219,3753,4784,32191,26805,-7091,8419,28963,30628,1114,6086,15494,-1612,-14438,-32510,-13176,-17389,-2742,31921,-18166,19250,-18679,-3799,11193,11845};;
 
 
 

volatile int countnegative_seed_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	countnegative_seed_block = countnegative_seed_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
countnegative_seed_block = ( ( countnegative_seed_block * 133 ) + 81 ) % 8095; 
PORTC = (0 << PC4); 

} 
 
