#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\insertsort\insertsort.h"
 
int returnValue_inputs[100] = {-28300,-4120,21130,-9581,-9234,-23901,-6024,-17243,-22315,-5988,-13807,4890,-14303,-30248,-9987,14434,9055,26296,12930,19868,25284,-28490,-26258,29741,4938,7888,-2262,-30609,21414,22713,17348,-30196,13046,18519,-15864,-20994,6879,14796,22476,29548,-25338,-6982,-15234,-15406,-17309,19835,19358,-24073,-7560,-26838,31309,-30595,-14815,22579,-1488,-10628,23912,-25735,-28306,-2538,-30728,-26029,22329,21327,24646,27618,8112,-3714,-20718,21844,-8243,21498,23488,-15211,-2338,23516,-9676,-22844,-3450,26767,-15361,18922,30537,27279,25389,-5040,-12215,3913,2075,-23533,-12592,-4659,9575,-32018,29301,-16874,-6106,16333,13881,-27089};;
 
 
 

int returnValue_block;
 

void blockInputUpdate(int blockInputUpdate_i) 
{
	returnValue_block = returnValue_inputs[blockInputUpdate_i];

} 
 

void benchmarkBlock(void) 
 {
PORTC = (1 << PC4); 
return (returnValue_block + (-52) ) != 0; 
PORTC = (0 << PC4); 

} 
 