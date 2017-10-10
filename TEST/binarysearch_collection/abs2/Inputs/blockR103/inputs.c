#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int binarysearch_result_inputs[100] = {-5937,-15096,-1613,-27506,5997,-9535,-14,-22669,-32290,-27691,-5611,-1378,12055,12511,-1399,-32328,7895,-16244,-28236,-22303,26910,9465,-28733,-20750,341,13677,-11422,-16347,18276,-30159,12275,-16900,-3775,-22165,-22668,-31134,8319,-24275,4359,-31456,-25295,16165,-28545,-19595,28065,-12858,22995,17361,-27851,961,-31406,-13425,-21128,17762,23534,15923,-5971,-21331,-22481,-16532,-24443,-2351,-32199,-30380,13304,4121,-20075,-16567,-16780,-20324,-9640,6641,-6904,24299,-5689,11353,4983,-12879,-28781,19950,-18570,-29095,14214,-4675,1110,5718,-29185,-6881,14839,27809,-18098,-25791,-23621,-21374,28399,1570,32592,8083,-1818,-11648};;
 
 
 

int binarysearch_result_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	binarysearch_result_block = binarysearch_result_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
return( binarysearch_result_block ); 
PORTC = (0 << PC4); 

} 
 
