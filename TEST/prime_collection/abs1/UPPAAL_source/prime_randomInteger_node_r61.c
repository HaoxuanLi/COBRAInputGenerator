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
prime_seed = ( ( prime_seed * 133 ) + 81 ) % 8095;
}

int main(void)
{
	benchmarkNode();
}
