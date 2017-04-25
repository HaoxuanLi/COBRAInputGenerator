#include "inputs.h"
 
double eps_inputs[] = {127.32397491474788, 119.72954647884573, 176.05471857585056, 295.6654996280333, 7.568636916072653, 188.9264889414287, 184.9124692210844, 26.117675426750118, 280.0701847339116, 254.62381171558926, 152.42245078778635, 35.29095378510462, 60.87236655382184, 106.35258797373028, 160.10247335378844, 249.13751360159966, 196.73623344682522, 141.11334804069452, 149.71225011651532, 278.7430783259363, 128.79865286021575, 160.5621497989753, 129.2982778857234, 196.70287014809233, 260.85628909432114, 70.72236422950247, 158.3255261248097, 242.29961117667102, 158.0001370848947, 153.54502821895724, 84.46529911291607, 214.93843302561964, 179.7976995620071, 173.04748947060463, 123.9973382776652, 81.89809245403659, 279.60975911767144, 220.9752485689459, 138.95324091184835, 215.67705269681554, 118.93814763752067, 307.81419877481204, 53.40609992507819, 36.83052162787446, 39.47884081321019, 225.87237675289418, 51.768049736445896, 144.5880743192309, 226.79190937712127, 83.13714888528722, 279.3679178274092, 54.359814352855146, 74.14827202020977, 42.64311801614681, 137.7616273646828, 27.667359698101002, 89.00059814976508, 189.07721248852963, 174.23584816025246, 121.35908566799326, 121.85770483115971, 133.14131739185402, 286.49204392011865, 130.88879137139654, 93.31183930714194, 81.20913314211545, 178.6244746059503, 154.34019708199432, 253.40060781604214, 287.00685101026943, 137.27888121459665, 71.13866959898921, 289.4722657799846, 68.55286337006467, 243.78131034765266, 234.37415948365523, 57.095534904239294, 184.47419446597118, 111.98909879122954, 273.07474285970983, 159.4502143599827, 88.4443403068357, 9.657977323393576, 188.51660678907712, 153.5940662853566, 90.63293779702447, 7.538501712707382, 103.04015612811943, 166.58207222544766, 119.50819426975316, 246.43436471838777, 129.6893686287032, 64.1729642120518, 23.28774073073549, 270.0112617875132, 132.30442198156072, 25.570720451635943, 46.15456268137318, 176.45475696752928, 209.887130613262};
 
 
 

double eps_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	eps_block = eps_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
minver_minver( 3, eps_block ); 
} 
 
