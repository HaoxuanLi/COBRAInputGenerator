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
matrix1_pin_down( &matrix1_A[0], &matrix1_B[0], &matrix1_C[0] );
}

int main(void)
{
	benchmarkNode();
}
