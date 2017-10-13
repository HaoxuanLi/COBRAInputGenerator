#include "inputs.h"
#include "C:\Users\HLi\AppData\Roaming\Local Libraries\Local Documents\workspaceJava\WCET\TEST\iir\iir.h"
 
/*Forward declaration*/

 

void blockInputUpdate(int blockInputUpdate_i) 
{
	 
	asm(
	/*store register values to register_address_block*/
	"sts	0x1bf, r0\n\t"
	"sts	0x1c0, r1\n\t"
	"sts	0x1c1, r2\n\t"
	"sts	0x1c2, r3\n\t"
	"sts	0x1c3, r4\n\t"
	"sts	0x1c4, r5\n\t"
	"sts	0x1c5, r6\n\t"
	"sts	0x1c6, r7\n\t"
	"sts	0x1c7, r8\n\t"
	"sts	0x1c8, r9\n\t"
	"sts	0x1c9, r10\n\t"
	"sts	0x1ca, r11\n\t"
	"sts	0x1cb, r12\n\t"
	"sts	0x1cc, r13\n\t"
	"sts	0x1cd, r14\n\t"
	"sts	0x1ce, r15\n\t"
	"sts	0x1cf, r16\n\t"
	"sts	0x1d0, r17\n\t"
	"sts	0x1d1, r18\n\t"
	"sts	0x1d2, r19\n\t"
	"sts	0x1d3, r20\n\t"
	"sts	0x1d4, r21\n\t"
	"sts	0x1d5, r22\n\t"
	"sts	0x1d6, r23\n\t"
	"sts	0x1d7, r24\n\t"
	"sts	0x1d8, r25\n\t"
	"sts	0x1d9, r26\n\t"
	"sts	0x1da, r27\n\t"
	"sts	0x1db, r28\n\t"
	"sts	0x1dc, r29\n\t"
	"sts	0x1dd, r30\n\t"
	"sts	0x1de, r31\n\t"
	/*Assign the inputs values to the block*/
	/*restore register values to register_address_block*/
	"lds	r0, 0x1bf\n\t"
	"lds	r1, 0x1c0\n\t"
	"lds	r2, 0x1c1\n\t"
	"lds	r3, 0x1c2\n\t"
	"lds	r4, 0x1c3\n\t"
	"lds	r5, 0x1c4\n\t"
	"lds	r6, 0x1c5\n\t"
	"lds	r7, 0x1c6\n\t"
	"lds	r8, 0x1c7\n\t"
	"lds	r9, 0x1c8\n\t"
	"lds	r10, 0x1c9\n\t"
	"lds	r11, 0x1ca\n\t"
	"lds	r12, 0x1cb\n\t"
	"lds	r13, 0x1cc\n\t"
	"lds	r14, 0x1cd\n\t"
	"lds	r15, 0x1ce\n\t"
	"lds	r16, 0x1cf\n\t"
	"lds	r17, 0x1d0\n\t"
	"lds	r18, 0x1d1\n\t"
	"lds	r19, 0x1d2\n\t"
	"lds	r20, 0x1d3\n\t"
	"lds	r21, 0x1d4\n\t"
	"lds	r22, 0x1d5\n\t"
	"lds	r23, 0x1d6\n\t"
	"lds	r24, 0x1d7\n\t"
	"lds	r25, 0x1d8\n\t"
	"lds	r26, 0x1d9\n\t"
	"lds	r27, 0x1da\n\t"
	"lds	r28, 0x1db\n\t"
	"lds	r29, 0x1dc\n\t"
	"lds	r30, 0x1dd\n\t"
	"lds	r31, 0x1de\n\t"
	);

} 
 
/*BLock execution time measurement*/
void benchmarkBlock(void)
{
	asm(
	/*store register values to register_address_block*/
	"sts	0x1bf, r0\n\t"
	"sts	0x1c0, r1\n\t"
	"sts	0x1c1, r2\n\t"
	"sts	0x1c2, r3\n\t"
	"sts	0x1c3, r4\n\t"
	"sts	0x1c4, r5\n\t"
	"sts	0x1c5, r6\n\t"
	"sts	0x1c6, r7\n\t"
	"sts	0x1c7, r8\n\t"
	"sts	0x1c8, r9\n\t"
	"sts	0x1c9, r10\n\t"
	"sts	0x1ca, r11\n\t"
	"sts	0x1cb, r12\n\t"
	"sts	0x1cc, r13\n\t"
	"sts	0x1cd, r14\n\t"
	"sts	0x1ce, r15\n\t"
	"sts	0x1cf, r16\n\t"
	"sts	0x1d0, r17\n\t"
	"sts	0x1d1, r18\n\t"
	"sts	0x1d2, r19\n\t"
	"sts	0x1d3, r20\n\t"
	"sts	0x1d4, r21\n\t"
	"sts	0x1d5, r22\n\t"
	"sts	0x1d6, r23\n\t"
	"sts	0x1d7, r24\n\t"
	"sts	0x1d8, r25\n\t"
	"sts	0x1d9, r26\n\t"
	"sts	0x1da, r27\n\t"
	"sts	0x1db, r28\n\t"
	"sts	0x1dc, r29\n\t"
	"sts	0x1dd, r30\n\t"
	"sts	0x1de, r31\n\t"
	/*store stack pointer to stackpointer_address_block*/
	"in	r14, 0x3d\n\t"
	"in	r15, 0x3e\n\t"
	"sts	0x01b2, r14\n\t"
	"sts	0x1b3, r15\n\t"
	/*store the stack to stack_address_block*/
	"ldi	r24, 0x00\n\t"
	"ldi	r25, 0x00\n\t"
	"lds	r26, 0x01b2\n\t"
	"lds	r27, 0x1b3\n\t"
	"ldi	r30, 0xdf\n\t"
	"ldi	r31, 0x01\n\t"
	"ld     r14, X+\n\t"
	"st     Z+,	r14\n\t"
	"adiw	r24, 0x01\n\t"
	"cpi	r24, 0x25\n\t"
	"brne	.-10\n\t"
	/*set the stack to stack_address_org*/
	"ldi	r24, 0x00\n\t"
	"ldi	r25, 0x00\n\t"
	"lds	r26, 0x01bd\n\t"
	"lds	r27, 0x1be\n\t"
	"ldi	r30, 0xb4\n\t"
	"ldi	r31, 0x01\n\t"
	"ld     r14, Z+\n\t"
	"st     X+,	r14\n\t"
	"adiw	r24, 0x01\n\t"
	"cpi	r24, 0x9\n\t"
	"brne	.-10\n\t"
	/*set register values to register_address_org*/
	"lds	r0, 0x190\n\t"
	"lds	r1, 0x191\n\t"
	"lds	r2, 0x192\n\t"
	"lds	r3, 0x193\n\t"
	"lds	r4, 0x194\n\t"
	"lds	r5, 0x195\n\t"
	"lds	r6, 0x196\n\t"
	"lds	r7, 0x197\n\t"
	"lds	r8, 0x198\n\t"
	"lds	r9, 0x199\n\t"
	"lds	r10, 0x19a\n\t"
	"lds	r11, 0x19b\n\t"
	"lds	r12, 0x19c\n\t"
	"lds	r13, 0x19d\n\t"
	"lds	r14, 0x19e\n\t"
	"lds	r15, 0x19f\n\t"
	"lds	r16, 0x1a0\n\t"
	"lds	r17, 0x1a1\n\t"
	"lds	r18, 0x1a2\n\t"
	"lds	r19, 0x1a3\n\t"
	"lds	r20, 0x1a4\n\t"
	"lds	r21, 0x1a5\n\t"
	"lds	r22, 0x1a6\n\t"
	"lds	r23, 0x1a7\n\t"
	"lds	r24, 0x1a8\n\t"
	"lds	r25, 0x1a9\n\t"
	"lds	r26, 0x1aa\n\t"
	"lds	r27, 0x1ab\n\t"
	"lds	r28, 0x1ac\n\t"
	"lds	r29, 0x1ad\n\t"
	"lds	r30, 0x1ae\n\t"
	"lds	r31, 0x1af\n\t"
	/*Tic*/
	"ldi	r24, 0x10\n\t"
	"out	0x08, r24\n\t"
	"lds	r24, 0x1a8\n\t"
	/*Block content*/
	"BlockContent_Start:\n\t"
	"std Y+1, r1\n\t"
	"BlockContent_End:\n\t"
	/*Toc*/
	"out	0x08, r1\n\t"
	/*set stack back to stack_address_block*/
	"ldi	r24, 0x00\n\t"
	"ldi	r25, 0x00\n\t"
	"lds	r26, 0x01b2\n\t"
	"lds	r27, 0x1b3\n\t"
	"ldi	r30, 0xdf\n\t"
	"ldi	r31, 0x01\n\t"
	"ld     r14, Z+\n\t"
	"st     X+,	r14\n\t"
	"adiw	r24, 0x01\n\t"
	"cpi	r24, 0x25\n\t"
	"brne	.-10\n\t"
	/*restore register values to register_address_block*/
	"lds	r0, 0x1bf\n\t"
	"lds	r1, 0x1c0\n\t"
	"lds	r2, 0x1c1\n\t"
	"lds	r3, 0x1c2\n\t"
	"lds	r4, 0x1c3\n\t"
	"lds	r5, 0x1c4\n\t"
	"lds	r6, 0x1c5\n\t"
	"lds	r7, 0x1c6\n\t"
	"lds	r8, 0x1c7\n\t"
	"lds	r9, 0x1c8\n\t"
	"lds	r10, 0x1c9\n\t"
	"lds	r11, 0x1ca\n\t"
	"lds	r12, 0x1cb\n\t"
	"lds	r13, 0x1cc\n\t"
	"lds	r14, 0x1cd\n\t"
	"lds	r15, 0x1ce\n\t"
	"lds	r16, 0x1cf\n\t"
	"lds	r17, 0x1d0\n\t"
	"lds	r18, 0x1d1\n\t"
	"lds	r19, 0x1d2\n\t"
	"lds	r20, 0x1d3\n\t"
	"lds	r21, 0x1d4\n\t"
	"lds	r22, 0x1d5\n\t"
	"lds	r23, 0x1d6\n\t"
	"lds	r24, 0x1d7\n\t"
	"lds	r25, 0x1d8\n\t"
	"lds	r26, 0x1d9\n\t"
	"lds	r27, 0x1da\n\t"
	"lds	r28, 0x1db\n\t"
	"lds	r29, 0x1dc\n\t"
	"lds	r30, 0x1dd\n\t"
	"lds	r31, 0x1de\n\t"
	/*restore the values in registers r14 and r15*/
	"lds	r14, 0x1cd\n\t"
	"lds	r15, 0x1ce\n\t"
	);


}
