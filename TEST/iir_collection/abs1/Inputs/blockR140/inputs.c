#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
float ptr_coeff_inputs[25] = {2.04426986e+38,2.88548054e+38,2.12855905e+38,1.24455182e+38,3.25252896e+38,2.64368692e+38,7.08836446e+37,6.21937288e+37,3.16218824e+38,1.89778984e+38,3.29666003e+38,1.30564280e+37,2.44348707e+38,3.24532705e+38,2.13928394e+38,3.13052982e+37,3.31655350e+38,2.86997524e+37,4.20155850e+37,3.37348817e+38,2.30846038e+38,7.08690947e+36,2.53065800e+38,1.01175610e+38,1.79480066e+38};;
float ptr_wi1_inputs[25] = {2.93671295e+38,2.17823119e+38,2.74010300e+38,4.08302502e+37,1.12682763e+38,1.91311430e+37,6.56138639e+37,8.02443578e+36,1.49698202e+38,8.60735115e+37,6.41201366e+35,2.57443674e+38,1.58444464e+38,2.35227911e+38,1.65068378e+38,2.14683225e+37,2.21939740e+37,1.50143928e+38,2.17729057e+38,8.56414745e+37,3.37451990e+38,1.39696460e+38,2.86896839e+38,1.68145297e+38,2.21485982e+38};;
float y_inputs[25] = {3.36644835e+38,1.92368461e+38,6.29139828e+37,1.22481726e+38,1.22631988e+38,1.87647337e+38,3.14624751e+38,1.23346473e+38,1.67089283e+37,3.17161962e+38,2.03855324e+38,3.26243568e+38,3.77563326e+37,2.71072856e+38,2.38194496e+38,1.07605482e+38,2.89375992e+38,3.30026598e+38,3.22043315e+36,1.75073451e+38,4.38371595e+37,2.50646092e+38,2.24808146e+38,1.24312749e+38,9.31481151e+37};;
float ptr_wi2_inputs[25] = {2.00659707e+38,9.99569348e+37,2.48015723e+38,5.82751139e+37,2.75689607e+38,2.24730954e+38,3.10401339e+37,2.69906911e+38,5.04935316e+37,3.30450951e+38,2.05505249e+38,1.04205228e+38,1.76344594e+38,3.37244969e+36,7.48854088e+37,1.84615107e+38,3.11273725e+38,8.06325397e+37,1.44828732e+38,2.16156796e+38,2.95145727e+38,2.90516674e+38,1.56037941e+38,3.83488124e+37,1.18006052e+38};;
int f_inputs[25] = {3,1,1,0,0,3,3,1,2,3,1,3,2,1,2,3,3,2,2,3,2,0,1,2,0};;
 
 
 

int f_block;
float y_block;
volatile float ptr_coeff_address;
volatile float *ptr_coeff_block=&ptr_coeff_address;
volatile float ptr_wi1_address;
volatile float *ptr_wi1_block=&ptr_wi1_address;
volatile float *ptr_coeff_block;
volatile float ptr_wi2_address;
volatile float *ptr_wi2_block=&ptr_wi2_address;
float w_block;
volatile float *ptr_wi1_block;
volatile float *ptr_wi2_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	ptr_coeff_block = &ptr_coeff_inputs[blockInputUpdate_i];
ptr_wi1_block = &ptr_wi1_inputs[blockInputUpdate_i];
y_block = y_inputs[blockInputUpdate_i];
ptr_wi2_block = &ptr_wi2_inputs[blockInputUpdate_i];
f_block = f_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
for ( f_block = 0 ; f_block < 4 ; f_block++ ) 
{ 
	w_block = y_block - *ptr_coeff_block++ * *ptr_wi1_block; 
	    w_block -= *ptr_coeff_block++ * *ptr_wi2_block; 
	 
	    y_block = *ptr_coeff_block++ * w_block; 
	    y_block += *ptr_coeff_block++ * *ptr_wi1_block; 
	    y_block += *ptr_coeff_block++ * *ptr_wi2_block; 
	 
	    *ptr_wi2_block++ = *ptr_wi1_block; 
	    *ptr_wi1_block++ = w_block; 
	 
	    ptr_wi2_block++; 
	    ptr_wi1_block++; 
} 
PORTC = (0 << PC4); 

} 
 
