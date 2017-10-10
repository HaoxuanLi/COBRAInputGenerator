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
prime_result = !( !prime_prime( prime_x ) && !prime_prime( prime_y ) );
}

int main(void)
{
	benchmarkNode();
}
