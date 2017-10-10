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
H[ j ][ i ] = i * 32 + j * 8 + j + i + j + 1;
      F[ j ][ i ] = i * j + j * j + j + i;
}

int main(void)
{
	benchmarkBlock();
}
