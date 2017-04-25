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
bitonic_merge( lo, k, dir );
    bitonic_merge( lo + k, k, dir );
}

int main(void)
{
	benchmarkBlock();
}
