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
w = minver_a [k ][ i ];
        minver_a[ k ][ i ] = minver_a[ k ][ k ];
        minver_a[ k ][ k ] = w;
}

int main(void)
{
	benchmarkBlock();
}
