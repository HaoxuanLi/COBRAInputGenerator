#include "inputs.h"
 
double minver_a_i__j__inputs[] = {256.1948985171823, 265.9355504744944, 65.13618613186497, 237.46285245406563, 47.029897841954465, 166.79286116629794, 182.63947034825975, 164.4408084310752, 70.08423542911483, 269.77615530334623, 56.51894779437608, 18.21266090746613, 51.563628497151065, 294.1154971405717, 49.3318875271952, 99.94875271807143, 104.16260410506223, 224.92919213121513, 5.2774061253603906, 122.61389491045142, 159.9788907834209, 223.89884021287108, 48.0758543653411, 219.54162028855774, 282.1434665224476, 269.59373754534397, 185.49475941090333, 176.22997319353763, 160.4869465740504, 194.93411079247488, 234.9255801803022, 5.8104945510699135, 192.24776054073493, 176.76904978384005, 15.283823858258955, 121.57481226606629, 241.87444917199167, 160.7917031207596, 24.099348618837602, 86.08186600700958, 119.87999350674221, 268.80802103861515, 51.45215069957692, 11.656699355540841, 210.05885187580384, 148.80729463604968, 3.1235254364253886, 190.3713413337314, 39.465820718375156, 23.156192886675356, 218.27358412524515, 78.43878397161122, 128.16203590523895, 35.567643002229744, 217.09559312687287, 126.11637461089636, 112.66868081127348, 103.20710096636482, 25.85953764736341, 113.84945438949622, 151.83800167055892, 48.76496566862206, 78.63615798038879, 296.79739752684543, 74.31608247214423, 62.003146323148044, 236.06759064040332, 46.939642052346656, 58.280157533959716, 93.7946878920392, 27.781106407927737, 147.36962108383958, 227.73298540389897, 253.56870618870855, 103.08670868703447, 72.64482189090113, 264.767703280566, 2.726158586763518, 233.12251054935015, 300.39454786059105, 45.32013954984019, 153.76030430273977, 222.5328825132127, 268.98214165736533, 243.4665181325537, 168.49286842724595, 59.04991690784653, 128.57262593062802, 60.59146537965476, 166.69364838980928, 20.932249043793078, 69.37459131558144, 192.02452636094907, 308.9530715679126, 262.3211953480454, 41.11024466619704, 286.16303022342504, 199.4996254180647, 222.6175576408982, 147.96069109778963};
int j_inputs[] = {7450, 22813, 906, -18646, 3391, 7427, -8265, -12922, 8144, 6065, -14926, 30033, 9214, -29020, -32256, -7146, 6609, 30759, 20922, -16211, -4407, -24648, -21627, -26281, 9284, 8025, -19428, 10000, -24015, 5992, 24662, 18682, -20366, -9539, 11395, -3791, -21929, 3073, 5362, -13870, 6556, 12194, -3028, 14278, 12054, -28032, 15468, 11007, -29788, 1035, 23363, -23061, -6542, -27791, -10840, -15821, 31764, -11451, 2862, 14800, 382, 7356, 23049, 17685, 24941, 31476, 10820, -22345, 28470, 7518, -6268, -5155, 17190, -6134, -259, 14102, -14651, 11493, -31651, 11767, 21516, -22146, -22994, 16150, 22794, -7664, 22955, 27106, 16266, 7002, -17063, -13840, 30380, -25316, -27960, 26954, 14621, 25801, -31712, -28081};
int i_inputs[] = {-1036, -18399, -16066, 30082, -32739, 24718, -11006, 30903, -11590, 20078, 17833, -27360, 26384, 4941, 26239, -16408, -9831, 14938, -14654, -26424, 19794, -1383, 20306, 30912, -7266, -2640, -10033, 5147, 30179, 2655, -29531, -21568, -16955, -30246, -17509, 28746, -28639, -22188, -16578, -13001, -5566, -3557, -8341, -21096, -16572, -12627, -26860, 22923, -19397, -26734, 16991, 8969, -32512, -11545, 14594, 1081, -25337, -5256, -25117, -7218, 28732, 28108, -21591, -17914, 20056, -11523, -7289, 2373, 6247, 28658, -18776, -7409, 1675, 17102, 9022, 11432, -2737, -11698, -19477, -31663, 8882, -8669, -9578, -11463, -28599, 15028, 717, 29569, 15986, 21931, -6361, -30650, 11063, 8303, 11961, 833, -31065, 14226, 18800, 17612};
 
 
 

double minver_aa_block[ 3 ][ 3 ];
int j_block;
int i_block;
double  minver_a_block[ 3 ][ 3 ] ;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	minver_a_block[i_block][j_block] = minver_a_i__j__inputs[blockInputUpdate_i]; 
	j_block = j_inputs[blockInputUpdate_i]; 
	i_block = i_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
minver_aa_block[ i_block ][ j_block ] = minver_a_block[ i_block ][ j_block ]; 
} 
 