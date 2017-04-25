#include "inputs.h"
 
int col_b_inputs[] = {25721, -26798, 31500, -18842, -19773, 21724, -4000, 32071, 32207, 14602, -25397, -32263, -9949, 18908, 29646, -11704, 32221, -16448, 1806, 31857, 15910, -20626, 27612, 14911, -24750, 17627, 29101, -19308, 22100, -7838, -1391, 21001, 3862, 13182, 5892, 10760, -15228, -25693, 1934, 10588, 8286, 9011, 115, 15627, 31985, 10191, 18600, 884, 9553, 31382, 8262, 32011, 16467, -23706, -32759, 27938, 14962, 23862, -21244, -12047, 29276, -10610, -21370, 30264, 12741, -26045, 28582, -6266, 2157, -17635, -31536, -30238, -9462, -20166, 22957, -30616, 32743, -8113, 24187, -18450, 10442, -3315, -25896, 14915, -4502, -7214, -7487, 4203, 8069, -6867, 18691, -3087, 9827, 29233, 16325, 20214, 605, -32689, 23468, -32676};
int row_a_inputs[] = {29185, -26833, -29766, -10624, -11605, -7395, 16163, 5820, 14335, -30341, 14407, -32334, 24239, 16870, -11307, -17993, 10378, 12302, 30203, 10166, -32351, 20693, -17412, 22178, 27725, 14327, -20814, 17751, -9763, -1407, -6240, 15034, 3133, 3098, -31190, -32285, -26983, -21978, -6615, -28781, -27066, 30660, -23919, -11801, 6769, 5732, -2067, 23355, 16380, 12544, -30875, 401, 30889, 464, -31957, 23445, -19883, 9188, 24206, 12780, -24528, 2074, -21625, 22819, -23349, 14987, -2156, -20019, 30852, 5629, -14546, 16525, -11443, 13780, -9174, -18980, 31600, 20122, 19688, -5354, 31600, 4305, 19467, -11333, 13970, 21049, 27003, 8348, -20802, 16944, -137, 20569, 32652, -11744, 31514, -15342, -8456, 19627, 773, -6810};
 
 
 

int col_b_block;
int row_a_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	col_b_block = col_b_inputs[blockInputUpdate_i]; 
	row_a_block = row_a_inputs[blockInputUpdate_i]; 
	
} 
 
void benchmarkBlock(void) 
{ 
int i, j, k, row_c_block, col_c_block; 
  double w; 
 
  row_c_block = row_a_block; 
  col_c_block = col_b_block; 
} 
 
