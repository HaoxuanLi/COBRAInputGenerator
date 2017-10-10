#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\countnegative\countnegative.h"
 
int countnegative_seed_inputs[100] = {-9733,28903,-2505,11895,21551,-6721,-20893,-23394,3305,15218,-804,-13595,-2897,9555,-23474,20979,-30837,-31160,5247,31429,25326,-24088,16531,-3920,-5102,28329,-11234,2400,-21634,24642,-28669,-16427,28685,-13734,-4895,-31027,24575,-24411,17324,-17466,-9556,30267,11149,30358,-4508,-6662,25738,15216,-28286,-8988,-29109,18585,29816,26670,-18672,16004,5880,-10134,-32550,-16747,-16930,9479,26359,12685,17451,-22451,1807,8523,-13581,76,-7917,29624,23402,15191,-31319,2327,-12486,-17475,-1883,-18289,3814,-27109,-7181,-30908,-4030,-22560,15902,-10145,25600,-25801,-20115,23314,-19036,12457,-18653,-4085,-19057,-21459,9300,-8811};;
 
 
 

volatile int countnegative_seed_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	countnegative_seed_block = countnegative_seed_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
return  countnegative_seed_block; 
PORTC = (0 << PC4); 

} 
 
