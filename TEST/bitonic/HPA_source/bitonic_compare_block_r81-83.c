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
int h = bitonic_a[ i ];
    bitonic_a[ i ] = bitonic_a[ j ];
    bitonic_a[ j ] = h;
}

int main(void)
{
	benchmarkBlock();
}
