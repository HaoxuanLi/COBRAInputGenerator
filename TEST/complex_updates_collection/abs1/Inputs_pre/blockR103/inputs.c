#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
float check_sum_inputs[100] = {7.30681636e-01,6.98607181e-02,9.16474218e-01,3.91477307e-02,6.56279394e-01,3.40110237e-01,1.21047668e-01,4.86579414e-01,6.50838532e-01,7.30456759e-01,8.91802929e-01,1.96925288e-01,5.62288881e-01,8.83250102e-01,1.50089737e-02,7.79522764e-01,2.48584566e-01,9.28680119e-01,5.69450283e-01,6.26445090e-01,7.61946081e-01,6.56410660e-01,1.03266810e-02,4.60984614e-01,3.50506471e-02,9.93260425e-02,9.09938313e-01,2.55971344e-01,7.09120033e-01,1.19976524e-01,9.10534105e-01,8.09405061e-01,4.02315006e-01,6.64185990e-04,3.62763555e-02,1.79028321e-01,1.08630688e-01,8.02855854e-01,2.34136326e-01,7.88345758e-01,2.94302511e-01,3.90451655e-01,3.39869777e-01,9.96351463e-01,8.05854397e-01,7.49335966e-01,3.56714711e-01,9.70672146e-02,9.64653858e-01,9.99989575e-01,3.40656097e-01,6.37585450e-01,5.65604510e-02,3.34279177e-01,8.99347922e-01,5.69602204e-01,3.26425907e-01,2.57722858e-01,6.22253014e-01,4.10121860e-01,8.84056218e-01,2.85245026e-01,4.01344555e-01,3.18566871e-02,7.35386574e-01,8.36435386e-01,3.71863636e-01,5.54633448e-01,5.70172453e-01,5.47201399e-01,8.69929351e-01,5.36317855e-03,2.76276157e-01,7.73407791e-01,3.93803447e-01,4.92562294e-01,9.28414859e-01,4.18217451e-01,5.23595277e-01,8.12302645e-01,1.94302138e-01,8.84419527e-01,8.22929051e-01,7.45075347e-01,6.62015200e-01,1.11405186e-01,6.05887728e-01,5.24475507e-01,4.74280173e-01,8.94391269e-01,2.06073100e-01,9.30700440e-01,6.55620343e-01,7.96285870e-01,3.65834890e-01,3.46798910e-01,7.41395602e-01,2.27443745e-01,5.11979013e-01,8.79128279e-02};;
 
 
 

float check_sum_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	check_sum_block = check_sum_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
return ( check_sum_block != 144.0f ); 
PORTC = (0 << PC4); 

} 
 