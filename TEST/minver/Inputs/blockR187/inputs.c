#include "inputs.h"
 
double w1_inputs[] = {259.8400271665497, 168.8047552081369, 95.5502556632228, 214.9745399276559, 305.4836910829912, 298.634982424182, 291.284826733522, 300.9675370108284, 56.70645368565801, 184.0576552980246, 283.82147876244545, 153.10792259265776, 132.4039633240019, 174.02751894494466, 17.354192169686588, 232.9043407805934, 228.3766005898957, 153.9005548434205, 2.515331472013993, 225.8355217596571, 159.27307790919951, 249.90301120804858, 68.47986898023285, 268.94798402415813, 33.23699988705007, 129.9079758957065, 143.6466756464128, 89.66725839898365, 64.01155329022379, 275.7195894879328, 54.89819348444777, 96.16261178463033, 37.26643721547282, 177.76471638454063, 150.67642428453036, 296.1886436229331, 104.53145144396984, 132.88310510905953, 125.70661429226507, 3.1562991783788967, 21.612907467632308, 36.135260848941954, 114.65976155012497, 216.8738821775983, 286.8327641348857, 122.7269543814546, 197.189399075318, 227.93039844204887, 233.49054038463046, 207.66836398499927, 32.142726783510064, 244.4005440601134, 102.81021032649734, 145.74968008020988, 38.952411731794555, 220.75667627353337, 259.84989244538644, 208.306753431023, 38.759892759033804, 91.12924054347091, 185.76447947690525, 184.94914446748177, 189.22719779358383, 155.39232685740382, 268.6890489255684, 149.50789223818643, 49.36130082844756, 131.40133256943895, 11.84911490103865, 79.70104835792901, 261.61980136879663, 40.88694662780114, 32.74927490129445, 228.9801662771058, 206.01487093380294, 263.131263056558, 216.54830943891312, 287.06836296991094, 3.337411870406428, 293.50089782364375, 301.4355605928796, 207.89298628307668, 44.264961633126866, 275.8638835169008, 174.58369024619776, 132.68647840206822, 179.48207938555032, 163.74599908850092, 161.99598916373634, 59.22539542015074, 90.53768293088471, 146.5191900670826, 124.81469209658246, 158.44643557433878, 99.25163783913173, 203.24583076874706, 189.3884598108289, 50.98783000381475, 181.82489781802315, 210.48954552138193};
 
 
 

double minver_det_block;
double w1_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	w1_block = w1_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
minver_det_block = w1_block; 
} 
 
