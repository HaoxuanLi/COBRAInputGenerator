/*

  This program is part of the TACLeBench benchmark suite.
  Version V 2.0

  Name: binarysearch

  Author: Sung-Soo Lim <sslim@archi.snu.ac.kr>

  Function: binarysearch performs binary search in an array of 15 integer
    elements.
    This program is completely structured (no unconditional jumps, no exits
    from loop bodies), and does not contain switch statements, no do-while
    loops.

  Source: MRTC
          http://www.mrtc.mdh.se/projects/wcet/wcet_bench/bs/bs.c

  Original name: bs

  Changes: No major functional changes.

  License: May be used, modified, and re-distributed freely, but
           the SNU-RT Benchmark Suite must be acknowledged

*/

/*
  This program is derived from the SNU-RT Benchmark Suite for Worst
  Case Timing Analysis by Sung-Soo Lim
*/
#include "binarysearch.h"

/*
  Forward declaration of functions
*/

void binarysearch_initSeed( void );
long binarysearch_randomInteger( void );
void binarysearch_init( void );
int binarysearch_return( void );
int binarysearch_binary_search( int );
void binarysearch_main( void );
int main( void );


/*
  Declaration of global variables
*/

volatile int binarysearch_seed;






struct binarysearch_DATA binarysearch_data[ 15 ];

int binarysearch_result;


/*
  Initialization- and return-value-related functions
*/

/*
  binarysearch_initSeed initializes the seed used in the "random" number
  generator.
*/
void binarysearch_initSeed( void )
{
  binarysearch_seed = 0;
}


/*
  binarysearch_RandomInteger generates "random" integers between 0 and 8094.
*/
long binarysearch_randomInteger( void )
{
  binarysearch_seed = ( ( binarysearch_seed * 133 ) + 81 ) % 8095;
  return( binarysearch_seed );
}


void binarysearch_init( void )
{
  int i;

  binarysearch_initSeed();

  _Pragma( "loopbound min 15 max 15" )
  for ( i = 0; i < 15; ++i ) {
    binarysearch_data[ i ].key = binarysearch_randomInteger();
    binarysearch_data[ i ].value = binarysearch_randomInteger();
  }
}


int binarysearch_return( void )
{
  return( binarysearch_result );
}


/*
  Algorithm core functions
*/

int binarysearch_binary_search( int x )
{
  int fvalue, mid, up, low;

  low = 0;
  up = 14;
  fvalue = -1;

  _Pragma( "loopbound min 1 max 4" )
  while ( low <= up ) {
    mid = ( low + up ) >> 1;

    if ( binarysearch_data[ mid ].key == x ) {
      /* Item found */
	asm(
	/*store register values of the original code to register_address_org*/
	"sts	0x69c, r0\n\t"
	"sts	0x69d, r1\n\t"
	"sts	0x69e, r2\n\t"
	"sts	0x69f, r3\n\t"
	"sts	0x6a0, r4\n\t"
	"sts	0x6a1, r5\n\t"
	"sts	0x6a2, r6\n\t"
	"sts	0x6a3, r7\n\t"
	"sts	0x6a4, r8\n\t"
	"sts	0x6a5, r9\n\t"
	"sts	0x6a6, r10\n\t"
	"sts	0x6a7, r11\n\t"
	"sts	0x6a8, r12\n\t"
	"sts	0x6a9, r13\n\t"
	"sts	0x6aa, r14\n\t"
	"sts	0x6ab, r15\n\t"
	"sts	0x6ac, r16\n\t"
	"sts	0x6ad, r17\n\t"
	"sts	0x6ae, r18\n\t"
	"sts	0x6af, r19\n\t"
	"sts	0x6b0, r20\n\t"
	"sts	0x6b1, r21\n\t"
	"sts	0x6b2, r22\n\t"
	"sts	0x6b3, r23\n\t"
	"sts	0x6b4, r24\n\t"
	"sts	0x6b5, r25\n\t"
	"sts	0x6b6, r26\n\t"
	"sts	0x6b7, r27\n\t"
	"sts	0x6b8, r28\n\t"
	"sts	0x6b9, r29\n\t"
	"sts	0x6ba, r30\n\t"
	"sts	0x6bb, r31\n\t"
	/*store stack pointer to stackpointer_address_org*/
	"in	r14, 0x3d\n\t"
	"in	r15, 0x3e\n\t"
	"sts	0x06c9, r14\n\t"
	"sts	0x6ca, r15\n\t"
	/*store the stack to stack_address_org*/
	"ldi	r24, 0x00\n\t"
	"ldi	r25, 0x00\n\t"
	"lds	r26, 0x06c9\n\t"
	"lds	r27, 0x6ca\n\t"
	"ldi	r30, 0xc0\n\t"
	"ldi	r31, 0x06\n\t"
	"ld     r14, X+\n\t"
	"st     Z+,	r14\n\t"
	"adiw	r24, 0x01\n\t"
	"cpi	r24, 0x9\n\t"
	"brne	.-10\n\t"
	/*restore the values in registers*/
	"lds	r14, 0x6aa\n\t"
	"lds	r15, 0x6ab\n\t"
	"lds	r24, 0x6b4\n\t"
	"lds	r25, 0x6b5\n\t"
	"lds	r26, 0x6b6\n\t"
	"lds	r27, 0x6b7\n\t"
	"lds	r30, 0x6ba\n\t"
	"lds	r31, 0x6bb\n\t"
	);
	asm("Tic_flag:");
      up = low - 1;
      fvalue = binarysearch_data[ mid ].value;
	asm("Toc_flag:");
    } else

    if ( binarysearch_data[ mid ].key > x )
      /* Item not found */
      up = mid - 1;
    else
      low = mid + 1;
  }

  return( fvalue );
}


/*
  Main functions
*/

void _Pragma( "entrypoint" ) binarysearch_main( void )
{
  binarysearch_result = binarysearch_binary_search( 8 );
}
