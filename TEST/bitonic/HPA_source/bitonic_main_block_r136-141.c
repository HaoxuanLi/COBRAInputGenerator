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

void benchmarkBlock(void);
int main(void);

void benchmarkBlock(void)
{
int i;

  /** When called with parameters lo = 0, cnt = a.length() and dir =
    ASCENDING, procedure bitonicSort sorts the whole array a. **/
  _Pragma( "marker recSort" )
  bitonic_sort( 0, 32, bitonic_ASCENDING );
}

int main(void)
{
	benchmarkBlock();
}
