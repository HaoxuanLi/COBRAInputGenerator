/* 

	Created with COBRA-Framework Export Tool v0.1
	Developed by: Thomas Huybrechts - MOSAIC 2016

*/

/*
	Include section
*/

// Include header files here...

/*
	Forward declaration of functions
*/

void benchmarkNode(void);
int main(void);

void benchmarkNode(void)
{
register float w;
  int f;
  register volatile float *ptr_coeff, *ptr_wi1, *ptr_wi2;
  register float y;


  ptr_coeff = &iir_coefficients[ 0 ];
  ptr_wi1 = &iir_wi[ 0 ];
  ptr_wi2 = &iir_wi[ 1 ];

  y = iir_x ;
}

int main(void)
{
	benchmarkNode();
}
