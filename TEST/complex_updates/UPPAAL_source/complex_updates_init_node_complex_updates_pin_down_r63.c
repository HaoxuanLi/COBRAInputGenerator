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
complex_updates_pin_down( &complex_updates_A[0], &complex_updates_B[0],
                            &complex_updates_C[0], &complex_updates_D[0] );
}

int main(void)
{
	benchmarkNode();
}
