#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\binarysearch\binarysearch.h"
 
int low_inputs[20] = {5,5,3,2,6,1,0,2,2,5,5,3,0,3,3,2,3,3,4,3};;
int mid_inputs[20] = {5,1,6,6,1,0,3,2,4,7,0,2,1,6,5,0,6,5,5,4};;
int binarysearch_data_key_inputs[20][15]= {{-18113,-11051,13967,19840,-22315,-1959,-19329,4237,-13796,-27700,6310,-22206,26085,-8372,-7305},{2020,-19936,14274,-31891,-31714,-5377,22465,11608,-9617,-25938,-10765,-17353,14608,19408,13536},{7758,27896,-28801,27962,-19671,20908,-17364,-5058,-31871,-25543,32237,5341,-21088,-30670,-25625},{-18744,-17601,-25968,30095,-13360,-8881,-23172,17266,-492,-23252,-10334,20776,9763,8860,-8210},{27918,17246,-7577,-23712,25903,-22391,27055,-28992,15472,23460,-5465,-18003,12722,-22569,16339},{21379,15345,4784,7489,22292,-4081,-26718,-6727,26048,23255,-27354,-6616,7689,24518,26953},{-21770,-7510,-28797,12489,-10405,-7768,27328,-2464,-3402,18264,1612,-29002,-7833,-29013,31293},{-29097,-522,-10873,-10592,-28002,20060,13957,15444,15684,-24567,356,-24695,30850,25007,4771},{-25568,-29857,-25033,13707,28284,28537,697,-30742,6374,5485,19670,9820,-5845,16166,-6904},{-28868,-3689,12892,21576,22430,-31771,4589,-29482,-18462,-30546,-26058,9152,-25013,846,1786},{-7607,-16174,-9294,-23708,11434,30549,-3490,2596,-7475,25637,27723,-13447,-11270,8743,-31739},{27594,21916,4343,24006,-12026,25073,32384,-7779,4673,7825,-13986,25550,-8655,-2394,6475},{-12883,25003,-15576,9098,32094,-21873,5360,8048,-26409,2631,6488,28027,30093,-21046,21367},{14193,-13121,18591,-16877,4479,-3724,9716,1795,31106,-29772,3166,-6519,-4703,-9576,-5921},{-24354,3731,-8605,-13446,-9956,18545,15536,27712,-28183,-30168,27104,-885,-19489,30749,9700},{4105,-261,-14884,-8905,-30858,-6647,-21012,23428,24301,-9422,19851,11383,-16468,3406,-6260},{13401,-167,-16608,-18683,-13944,21608,12483,18505,25348,18033,-12206,-29856,-7384,-544,-1522},{5255,-16935,-9115,19695,32501,-15810,-19120,-9188,-2554,29817,-25354,-28912,13511,15387,3453},{-9299,-24477,3593,-31783,32418,-4213,-31787,10921,-32205,25449,15063,23337,-13714,28536,-22210},{22339,1703,4714,-29248,-14225,-1000,-15686,23972,-19290,-25643,20763,-10559,26240,-7239,-2073}};
int binarysearch_data_value_inputs[20][15]= {{-28442,20693,31266,-12810,8416,29050,-12637,-22745,21837,-15106,-11321,31886,8136,-30656,-24609},{-31879,-29028,9301,-2131,-1452,10171,22631,-30665,-27854,25378,29610,-25301,10123,12843,-18966},{-23464,26812,12710,-9070,15731,-29212,-27903,-2682,-30730,10782,-3842,227,20126,32474,-203},{-29145,-2847,13427,-17560,16746,-22613,-756,7768,-16569,-4696,-11686,-6549,-3559,-5180,-637},{-6897,29989,11995,328,-12489,6674,738,-5851,19247,-24060,27406,-24824,3943,-10585,2415},{-28281,11356,27669,-31502,-10043,31663,-27938,-13324,-22434,-21442,-8904,5747,-32686,1660,20708},{-10956,-4496,-28214,4374,342,5689,3413,-17752,-32310,10502,15427,26420,-16586,22198,763},{21529,27835,-31618,-15638,-16163,15199,11320,15343,531,31592,-21149,-19349,11777,25768,4587},{-13967,14417,-14333,21707,-10741,24998,29720,-12680,-25625,-32611,-19779,-13420,-4437,-9008,27087},{-19899,-22927,-2405,6843,15262,-9829,-17834,-31656,14928,15837,9026,14910,-20250,16827,-27927},{31806,-31588,21996,27867,-5213,13948,30425,4701,843,9271,23036,-20681,31645,24908,17357},{30380,-13413,-25997,-28562,20229,-11367,14644,14564,23631,-17672,31629,748,-32450,-23370,-27181},{-7138,-11265,13566,30537,9429,-21000,-7570,-25067,1676,-12073,9960,-32624,-10718,18237,2140},{15323,4144,25752,-30777,-20584,6054,-12555,-15285,24073,-5614,12170,7282,-17513,-2235,-25535},{30538,-15897,20386,-6931,-26695,-1124,-31479,14993,-22555,-13857,-20151,-23294,3282,6556,-21430},{-4276,-2286,-7261,10188,8683,10191,-15391,-13298,-28883,16801,20106,-13252,-12085,25707,6539},{-25041,5553,10297,10771,-30268,-1174,-8160,28316,3920,14645,-9469,32580,1254,-25100,-5500},{-31681,-22562,-9259,-11722,-30657,21541,-15363,30183,17800,6507,16865,22960,-26692,18922,24081},{-7948,18205,-22999,284,27970,23132,-25433,-29847,11055,22770,-6172,24592,27711,23400,8790},{11747,10952,21378,30844,24846,-29049,-20362,-11658,-8264,-22408,-11624,-10499,-17349,-6249,21306}};
 
 
 
/*Input update*/

int low_block;
struct binarysearch_DATA binarysearch_data_block[15];
int mid_block;
 
/*Block input update*/

void blockInputUpdate(int blockInputUpdate_i) 
{
	low_block = low_inputs[blockInputUpdate_i];
	mid_block = mid_inputs[blockInputUpdate_i];
	int binarysearch_data_block_counter0;
	for(binarysearch_data_block_counter0=0;binarysearch_data_block_counter0<15;binarysearch_data_block_counter0++){
		binarysearch_data_block[binarysearch_data_block_counter0].key=binarysearch_data_key_inputs[blockInputUpdate_i][binarysearch_data_block_counter0];
		binarysearch_data_block[binarysearch_data_block_counter0].value=binarysearch_data_value_inputs[blockInputUpdate_i][binarysearch_data_block_counter0];
	}

	 

} 
 
/*BLock execution time measurement*/
void benchmarkBlock(void)
{
	asm(
	/*store register values to register_address_block*/
	"sts	0x6cb, r0\n\t"
	"sts	0x6cc, r1\n\t"
	"sts	0x6cd, r2\n\t"
	"sts	0x6ce, r3\n\t"
	"sts	0x6cf, r4\n\t"
	"sts	0x6d0, r5\n\t"
	"sts	0x6d1, r6\n\t"
	"sts	0x6d2, r7\n\t"
	"sts	0x6d3, r8\n\t"
	"sts	0x6d4, r9\n\t"
	"sts	0x6d5, r10\n\t"
	"sts	0x6d6, r11\n\t"
	"sts	0x6d7, r12\n\t"
	"sts	0x6d8, r13\n\t"
	"sts	0x6d9, r14\n\t"
	"sts	0x6da, r15\n\t"
	"sts	0x6db, r16\n\t"
	"sts	0x6dc, r17\n\t"
	"sts	0x6dd, r18\n\t"
	"sts	0x6de, r19\n\t"
	"sts	0x6df, r20\n\t"
	"sts	0x6e0, r21\n\t"
	"sts	0x6e1, r22\n\t"
	"sts	0x6e2, r23\n\t"
	"sts	0x6e3, r24\n\t"
	"sts	0x6e4, r25\n\t"
	"sts	0x6e5, r26\n\t"
	"sts	0x6e6, r27\n\t"
	"sts	0x6e7, r28\n\t"
	"sts	0x6e8, r29\n\t"
	"sts	0x6e9, r30\n\t"
	"sts	0x6ea, r31\n\t"
	/*store stack pointer to stackpointer_address_block*/
	"in	r14, 0x3d\n\t"
	"in	r15, 0x3e\n\t"
	"sts	0x06be, r14\n\t"
	"sts	0x6bf, r15\n\t"
	/*store the stack to stack_address_block*/
	"ldi	r24, 0x00\n\t"
	"ldi	r25, 0x00\n\t"
	"lds	r26, 0x06be\n\t"
	"lds	r27, 0x6bf\n\t"
	"ldi	r30, 0xeb\n\t"
	"ldi	r31, 0x06\n\t"
	"ld     r14, X+\n\t"
	"st     Z+,	r14\n\t"
	"adiw	r24, 0x01\n\t"
	"cpi	r24, 0x25\n\t"
	"brne	.-10\n\t"
	/*set the stack to stack_address_org*/
	"ldi	r24, 0x00\n\t"
	"ldi	r25, 0x00\n\t"
	"lds	r26, 0x06c9\n\t"
	"lds	r27, 0x6ca\n\t"
	"ldi	r30, 0xc0\n\t"
	"ldi	r31, 0x06\n\t"
	"ld     r14, Z+\n\t"
	"st     X+,	r14\n\t"
	"adiw	r24, 0x01\n\t"
	"cpi	r24, 0x9\n\t"
	"brne	.-10\n\t"
	/*set register values to register_address_org*/
	"lds	r0, 0x69c\n\t"
	"lds	r1, 0x69d\n\t"
	"lds	r2, 0x69e\n\t"
	"lds	r3, 0x69f\n\t"
	"lds	r4, 0x6a0\n\t"
	"lds	r5, 0x6a1\n\t"
	"lds	r6, 0x6a2\n\t"
	"lds	r7, 0x6a3\n\t"
	"lds	r8, 0x6a4\n\t"
	"lds	r9, 0x6a5\n\t"
	"lds	r10, 0x6a6\n\t"
	"lds	r11, 0x6a7\n\t"
	"lds	r12, 0x6a8\n\t"
	"lds	r13, 0x6a9\n\t"
	"lds	r14, 0x6aa\n\t"
	"lds	r15, 0x6ab\n\t"
	"lds	r16, 0x6ac\n\t"
	"lds	r17, 0x6ad\n\t"
	"lds	r18, 0x6ae\n\t"
	"lds	r19, 0x6af\n\t"
	"lds	r20, 0x6b0\n\t"
	"lds	r21, 0x6b1\n\t"
	"lds	r22, 0x6b2\n\t"
	"lds	r23, 0x6b3\n\t"
	"lds	r24, 0x6b4\n\t"
	"lds	r25, 0x6b5\n\t"
	"lds	r26, 0x6b6\n\t"
	"lds	r27, 0x6b7\n\t"
	"lds	r28, 0x6b8\n\t"
	"lds	r29, 0x6b9\n\t"
	"lds	r30, 0x6ba\n\t"
	"lds	r31, 0x6bb\n\t"
	/*Tic*/
	"ldi	r24, 0x10\n\t"
	"out	0x08, r24\n\t"
	"lds	r24, 0x6b4\n\t"
	/*Block content*/
	"BlockContent_Start:\n\t"
	"movw r20, r22\n\t"
	"subi r20, 0x01\n\t"
	"sbc r21, r1\n\t"
	"add r18, r18\n\t"
	"adc r19, r19\n\t"
	"add r18, r18\n\t"
	"adc r19, r19\n\t"
	"movw r30, r18\n\t"
	"subi r30, 0xE6\n\t"
	"sbci r31, 0xF9\n\t"
	"ldd r26, Z+2\n\t"
	"ldd r27, Z+3\n\t"
	"BlockContent_End:\n\t"
	/*Toc*/
	"out	0x08, r1\n\t"
	/*set stack back to stack_address_block*/
	"ldi	r24, 0x00\n\t"
	"ldi	r25, 0x00\n\t"
	"lds	r26, 0x06be\n\t"
	"lds	r27, 0x6bf\n\t"
	"ldi	r30, 0xeb\n\t"
	"ldi	r31, 0x06\n\t"
	"ld     r14, Z+\n\t"
	"st     X+,	r14\n\t"
	"adiw	r24, 0x01\n\t"
	"cpi	r24, 0x25\n\t"
	"brne	.-10\n\t"
	/*restore register values to register_address_block*/
	"lds	r0, 0x6cb\n\t"
	"lds	r1, 0x6cc\n\t"
	"lds	r2, 0x6cd\n\t"
	"lds	r3, 0x6ce\n\t"
	"lds	r4, 0x6cf\n\t"
	"lds	r5, 0x6d0\n\t"
	"lds	r6, 0x6d1\n\t"
	"lds	r7, 0x6d2\n\t"
	"lds	r8, 0x6d3\n\t"
	"lds	r9, 0x6d4\n\t"
	"lds	r10, 0x6d5\n\t"
	"lds	r11, 0x6d6\n\t"
	"lds	r12, 0x6d7\n\t"
	"lds	r13, 0x6d8\n\t"
	"lds	r14, 0x6d9\n\t"
	"lds	r15, 0x6da\n\t"
	"lds	r16, 0x6db\n\t"
	"lds	r17, 0x6dc\n\t"
	"lds	r18, 0x6dd\n\t"
	"lds	r19, 0x6de\n\t"
	"lds	r20, 0x6df\n\t"
	"lds	r21, 0x6e0\n\t"
	"lds	r22, 0x6e1\n\t"
	"lds	r23, 0x6e2\n\t"
	"lds	r24, 0x6e3\n\t"
	"lds	r25, 0x6e4\n\t"
	"lds	r26, 0x6e5\n\t"
	"lds	r27, 0x6e6\n\t"
	"lds	r28, 0x6e7\n\t"
	"lds	r29, 0x6e8\n\t"
	"lds	r30, 0x6e9\n\t"
	"lds	r31, 0x6ea\n\t"
	/*set stack pointer to stackpointer_address_block*/
	/*restore the values in registers r14 and r15*/
	"lds	r14, 0x6d9\n\t"
	"lds	r15, 0x6da\n\t"
	);


}