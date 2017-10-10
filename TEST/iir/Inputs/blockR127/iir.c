/*

  This program is part of the TACLeBench benchmark suite.
  Version V 2.0

  Name: iir

  Author: Juan Martinez Velarde

  Function:
    The equations of each biquad section filter are:
      w(n) =    x(n) - ai1*w(n-1) - ai2*w(n-2)
      y(n) = b0*w(n) + bi1*w(n-1) + bi2*w(n-2)

    Biquads are sequentally positioned. Input sample for biquad i is
    xi-1(n). Output sample for biquad i is xi(n).
    System input sample is x0(n). System output sample is xN(n) = y(n)
    for N biquads.

    Each section performs following filtering (biquad i) :

                              wi(n)
      xi-1(n) ---(-)---------->-|->---bi0---(+)-------> xi(n)
                  A             |            A
                  |           |1/z|          |
                  |             | wi(n-1)    |
                  |             v            |
                  |-<--ai1----<-|->---bi1-->-|
                  |             |            |
                  |           |1/z|          |
                  |             | wi(n-2)    |
                  |             v            |
                  |-<--ai2----<--->---bi2-->-|

    The values wi(n-1) and wi(n-2) are stored in wi1 and wi2

  Source: DSPstone
          http://www.ice.rwth-aachen.de/research/tools-projects/entry/detail/dspstone

  Original name: iir_N_sections_float

  Changes:
           24-03-94 creation fixed-point (Martinez Velarde)
           16-03-95 adaption floating-point (Harald L. Schraut)

  License: may be used, modified, and re-distributed freely

*/


/*
  Forward declaration of functions
*/

void iir_init( void );
int iir_return( void );
void iir_main( void );
int main( void );


/*
  Declaration of global variables
*/

volatile float iir_wi[ 2 * 4 ];
volatile float iir_coefficients[ 5 * 4 ];
float iir_x;


/*
  Initialization- and return-value-related functions
*/

void iir_init( void )
{
  int f;
  unsigned int i;
  unsigned char *p;
  volatile char bitmask = 0;


  _Pragma( "loopbound min 20 max 20" )
  for ( f = 0 ; f < 5 * 4; f++ )
    iir_coefficients[ f ] = 7;

  _Pragma( "loopbound min 8 max 8" )
  for ( f = 0 ; f < 2 * 4; f++ )
    iir_wi[ f ] = 0;

  iir_x = ( float ) 1;

  /*
    Apply volatile XOR-bitmask to entire input array.
  */
  p = (unsigned char *) &iir_coefficients[ 0 ];
  _Pragma( "loopbound min 80 max 80" )
  for ( i = 0; i < sizeof( iir_coefficients ); ++i, ++p )
    *p ^= bitmask + *p;

  p = (unsigned char *) &iir_wi[ 0 ];
  _Pragma( "loopbound min 32 max 32" )
  for ( i = 0; i < sizeof( iir_wi ); ++i, ++p )
    *p ^= bitmask;
}


int iir_return( void )
{
  float checksum = 0.0;
  int f;


  _Pragma( "loopbound min 8 max 8" )
  for ( f = 0 ; f < 2 * 4; f++ )
    checksum += iir_wi[ f ];

  return( (int) checksum );
}


/*
  Main functions
*/

void _Pragma( "entrypoint" ) iir_main( void )
{
	asm(
	/*store register values of the original code to register_address_org*/
	"sts	0xb14, r0\n\t"
	"sts	0xb15, r1\n\t"
	"sts	0xb16, r2\n\t"
	"sts	0xb17, r3\n\t"
	"sts	0xb18, r4\n\t"
	"sts	0xb19, r5\n\t"
	"sts	0xb1a, r6\n\t"
	"sts	0xb1b, r7\n\t"
	"sts	0xb1c, r8\n\t"
	"sts	0xb1d, r9\n\t"
	"sts	0xb1e, r10\n\t"
	"sts	0xb1f, r11\n\t"
	"sts	0xb20, r12\n\t"
	"sts	0xb21, r13\n\t"
	"sts	0xb22, r14\n\t"
	"sts	0xb23, r15\n\t"
	"sts	0xb24, r16\n\t"
	"sts	0xb25, r17\n\t"
	"sts	0xb26, r18\n\t"
	"sts	0xb27, r19\n\t"
	"sts	0xb28, r20\n\t"
	"sts	0xb29, r21\n\t"
	"sts	0xb2a, r22\n\t"
	"sts	0xb2b, r23\n\t"
	"sts	0xb2c, r24\n\t"
	"sts	0xb2d, r25\n\t"
	"sts	0xb2e, r26\n\t"
	"sts	0xb2f, r27\n\t"
	"sts	0xb30, r28\n\t"
	"sts	0xb31, r29\n\t"
	"sts	0xb32, r30\n\t"
	"sts	0xb33, r31\n\t"
	/*store stack pointer to stackpointer_address_org*/
	"in	r14, 0x3d\n\t"
	"in	r15, 0x3e\n\t"
	"sts	0x0b41, r14\n\t"
	"sts	0xb42, r15\n\t"
	/*store the stack to stack_address_org*/
	"ldi	r24, 0x00\n\t"
	"ldi	r25, 0x00\n\t"
	"lds	r26, 0x0b41\n\t"
	"lds	r27, 0xb42\n\t"
	"ldi	r30, 0x38\n\t"
	"ldi	r31, 0x0b\n\t"
	"ld     r14, X+\n\t"
	"st     Z+,	r14\n\t"
	"adiw	r24, 0x01\n\t"
	"cpi	r24, 0x9\n\t"
	"brne	.-10\n\t"
	/*restore the values in registers*/
	"lds	r14, 0xb22\n\t"
	"lds	r15, 0xb23\n\t"
	"lds	r24, 0xb2c\n\t"
	"lds	r25, 0xb2d\n\t"
	"lds	r26, 0xb2e\n\t"
	"lds	r27, 0xb2f\n\t"
	"lds	r30, 0xb32\n\t"
	"lds	r31, 0xb33\n\t"
	);
	asm("Tic_flag:");
  register float w;
  int f;
  register volatile float *ptr_coeff, *ptr_wi1, *ptr_wi2;
  register float y;


  ptr_coeff = &iir_coefficients[ 0 ];
  ptr_wi1 = &iir_wi[ 0 ];
  ptr_wi2 = &iir_wi[ 1 ];

  y = iir_x ;

  _Pragma( "loopbound min 4 max 4" )
  for ( f = 0 ; f < 4 ; f++ ) {
    w = y - *ptr_coeff++ * *ptr_wi1;
    w -= *ptr_coeff++ * *ptr_wi2;

    y = *ptr_coeff++ * w;
    y += *ptr_coeff++ * *ptr_wi1;
    y += *ptr_coeff++ * *ptr_wi2;

    *ptr_wi2++ = *ptr_wi1;
    *ptr_wi1++ = w;

    ptr_wi2++;
    ptr_wi1++;
  }
}
