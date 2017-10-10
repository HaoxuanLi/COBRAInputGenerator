#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\complex_updates\complex_updates.h"
 
float p_c_inputs[25] = {2.12044376e+38,2.69038846e+37,3.02197191e+37,8.99456786e+36,2.52346708e+38,8.12449056e+36,2.70559935e+38,1.79497625e+38,2.35918572e+37,2.26999367e+38,2.71780810e+38,6.11876920e+37,2.26173217e+38,1.83093671e+38,1.51976742e+35,7.08194565e+37,4.12885418e+36,9.52161908e+37,2.63137001e+38,3.30280132e+38,1.68590457e+38,7.28894913e+37,1.80821979e+38,2.42718447e+38,2.64038789e+38};;
float p_a_inputs[25] = {1.55264765e+38,3.18234924e+37,1.21358022e+38,7.46063211e+37,2.72781389e+38,1.64938560e+38,2.56823528e+38,2.85447634e+38,2.32325553e+38,2.47678983e+38,3.10615664e+38,2.44844420e+38,3.31461405e+38,3.22101439e+38,1.25226982e+38,2.00565059e+38,1.73846757e+38,1.69909561e+38,2.62189701e+38,2.81778145e+38,3.70839699e+37,1.71165439e+38,2.92157033e+38,2.41847980e+38,2.80572514e+38};;
int i_inputs[25] = {6,15,0,1,5,3,0,16,9,14,9,15,5,0,4,0,4,7,11,11,5,15,7,13,8};;
float p_b_inputs[25] = {1.07531473e+38,5.46895768e+37,2.13885890e+38,1.66353199e+38,2.55809301e+38,1.14657770e+38,1.18747120e+38,2.35793428e+37,1.28633053e+38,1.61343795e+38,1.60632727e+38,2.80786484e+38,9.71108153e+37,3.21260389e+36,1.07432204e+38,1.15921943e+38,2.89750137e+38,1.89153278e+38,1.80173719e+38,6.15956030e+37,2.31717129e+38,2.06569897e+37,9.96463704e+36,1.96394728e+38,1.07872498e+37};;
 
 
 

int i_block;
float *p_c_block;
float p_a_address;
float *p_a_block=&p_a_address;
float p_b_address;
float *p_b_block=&p_b_address;
float *p_a_block;
float p_d_address;
float *p_d_block=&p_d_address;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	p_c_block = &p_c_inputs[blockInputUpdate_i];
p_a_block = &p_a_inputs[blockInputUpdate_i];
i_block = i_inputs[blockInputUpdate_i];
p_b_block = &p_b_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( i_block = 0 ; i_block < N ; i_block++, p_a_block++ ) 
{ 
	*p_d_block    = *p_c_block++ + *p_a_block++ * *p_b_block++ ; 
	    *p_d_block++ -=          *p_a_block   * *p_b_block-- ; 
	 
	    *p_d_block    = *p_c_block++ + *p_a_block-- * *p_b_block++ ; 
	    *p_d_block++ +=          *p_a_block++ * *p_b_block++ ; 
} 
PORTC = (0 << PC4); 

} 
 
