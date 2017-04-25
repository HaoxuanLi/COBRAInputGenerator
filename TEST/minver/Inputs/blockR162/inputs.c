#include "inputs.h"
 
double pivot_inputs[] = {286.81641868686245, 106.91138084520787, 26.150455053770422, 57.51156143737841, 23.83064874179092, 158.5710156658778, 143.06677041785247, 224.68000953907833, 5.43312175162717, 125.37378546266113, 89.17482588826506, 102.28170890848821, 221.87631891822045, 217.04306297058326, 111.60782209152909, 256.2895834920968, 289.2798995258954, 95.19068766279437, 19.90875859946299, 204.71253364185839, 50.358413860766966, 297.3850479398848, 142.53802914525426, 177.87877418677468, 199.39462619473298, 176.62450176650344, 241.56452124278414, 37.5192829825439, 188.40792850402585, 4.389008571917842, 186.9492468802686, 228.19955576553573, 178.16458222235377, 169.64280209915773, 289.90097937746407, 103.2548031349608, 195.6025150799108, 167.0690390565769, 267.5886538877615, 116.87824999350211, 218.6256205566632, 174.4717286193426, 148.57547284794174, 118.46990964907542, 274.02406689755645, 202.078218058359, 115.78362957181683, 290.45496066315326, 140.83239374987957, 60.68079769665624, 203.9403763217578, 213.52487646902455, 274.7553585109037, 309.0162979816865, 80.52819471773653, 206.49722878630942, 50.79045367525453, 206.76686557862485, 187.48088927923834, 287.2949927264933, 151.2310976894869, 1.0460341936921593, 285.0720757786735, 35.15724554415945, 160.04352346925106, 202.09142651225307, 197.2838064608539, 256.3287962963758, 42.51136993265397, 188.0762215985163, 85.1717074181126, 138.77461323110074, 270.4929685299653, 141.76944336867425, 64.35070703300435, 57.506082479869654, 149.97394635140827, 290.6504859887114, 213.6364457128841, 160.28791043709583, 33.47693077172028, 173.87267194697137, 115.76017192147525, 292.1337622851596, 177.20242858999293, 7.539452706393293, 106.05418731700472, 124.38556462948904, 152.7887242551297, 139.73834142393156, 66.42405917092232, 279.1054527521613, 61.10758804269363, 225.67642315517634, 266.0505582734203, 115.24384064332203, 268.07360888714516, 17.474079853035864, 257.43927224457525, 210.02957234248072};
int k_inputs[] = {19965, 28289, 2842, -16376, -25753, -11723, 1028, -19512, 24355, 12749, 8758, -28121, -23818, -577, -10386, 29098, -29631, -7271, 29540, 16867, 19410, -29944, -21502, -14993, 25535, -6291, -1750, 13016, 5518, 22938, -12538, -23568, -13255, 18354, -23195, -12733, 10783, 10651, 27709, -9098, 31181, 25503, 166, -9205, 31107, 11187, 11176, -29827, -14815, -24780, 31011, 10300, 30806, -32327, -24607, 26338, -3318, 8517, 19498, 4565, 3573, 20233, 18727, 22119, -11499, 30213, 23360, -4054, -4977, -12878, 29040, -646, 11887, -32035, 20793, -4767, 14099, -1537, 7104, 18861, 19326, -9341, -6914, 29011, -17732, 31, 8941, 17295, -15734, -14753, 4320, -4102, 24477, -10750, 17129, -29788, 10198, 11174, 32139, -1276};
 
 
 

double  minver_a_block[ 3 ][ 3 ] ;
double pivot_block;
int k_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	pivot_block = pivot_inputs[blockInputUpdate_i]; 
	k_block = k_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
minver_a_block[ k_block ][ k_block ] = 1.0 / pivot_block; 
} 
 
