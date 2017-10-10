#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
float ptr_coeff_inputs[25] = {1.28415022e+38,5.58985837e+37,3.19387000e+38,4.63142342e+37,2.49559911e+38,3.07615522e+38,2.53878313e+38,1.15727829e+37,6.12730651e+36,3.33674672e+38,1.55784714e+38,2.66253860e+38,1.39821466e+38,1.81771694e+38,8.08630219e+37,1.73022500e+38,4.63749071e+37,2.17863978e+38,2.38728446e+38,3.21914917e+38,2.92330521e+38,3.60763975e+37,2.45431447e+38,1.98271779e+38,2.69326845e+38};;
float y_inputs[25] = {1.45863358e+38,7.54144211e+37,2.91597959e+38,5.69181156e+36,7.76077810e+37,3.38441708e+38,2.39923621e+38,2.48261690e+38,2.10161587e+38,2.19103133e+38,1.83866664e+38,6.14387024e+37,2.42138110e+38,4.04942338e+37,3.26057361e+38,4.04878172e+37,2.21188940e+38,2.53281010e+38,2.16593888e+38,9.36422541e+37,1.75547956e+38,1.99175533e+38,1.13311584e+38,1.15750358e+38,2.31872440e+38};;
float ptr_wi1_inputs[25] = {1.60505951e+38,4.96499927e+37,2.76485992e+37,3.27746586e+37,1.71036374e+37,2.29282383e+38,6.16552955e+37,2.22580971e+38,4.12369409e+37,1.77069797e+37,3.27455959e+38,3.06183431e+38,4.47068043e+37,2.00105945e+38,2.92642903e+38,3.11621417e+38,3.02772854e+38,1.06184975e+38,4.07754030e+37,9.05656949e+37,2.95586390e+38,1.16118161e+37,2.88187307e+38,1.99068235e+38,2.87587166e+38};;
float ptr_wi2_inputs[25] = {2.17733015e+38,2.53101465e+37,2.04053098e+38,7.41834166e+37,2.47857807e+38,2.68333222e+38,2.31253189e+38,1.36920973e+38,9.38438427e+37,5.94851629e+37,1.21058986e+37,1.42919470e+37,2.88483919e+38,1.78664009e+38,4.22513180e+37,3.39041077e+38,8.72452765e+37,2.24588972e+38,2.26557527e+38,8.61501260e+37,2.10160104e+38,1.00054834e+38,2.51180734e+38,2.30851219e+38,1.73972808e+38};;
 
 
 

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
y_block = y_inputs[blockInputUpdate_i];
ptr_wi1_block = &ptr_wi1_inputs[blockInputUpdate_i];
ptr_wi2_block = &ptr_wi2_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
w_block = y_block - *ptr_coeff_block++ * *ptr_wi1_block; 
    w_block -= *ptr_coeff_block++ * *ptr_wi2_block; 
 
    y_block = *ptr_coeff_block++ * w_block; 
    y_block += *ptr_coeff_block++ * *ptr_wi1_block; 
    y_block += *ptr_coeff_block++ * *ptr_wi2_block; 
 
    *ptr_wi2_block++ = *ptr_wi1_block; 
    *ptr_wi1_block++ = w_block; 
 
    ptr_wi2_block++; 
    ptr_wi1_block++; 
PORTC = (0 << PC4); 

} 
 
