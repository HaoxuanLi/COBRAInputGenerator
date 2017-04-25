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
int checksum = 0;

  checksum += bitonic_a[ 0 ] + bitonic_a[ 21 ] + bitonic_a[ 31 ];
}

int main(void)
{
	benchmarkBlock();
}
