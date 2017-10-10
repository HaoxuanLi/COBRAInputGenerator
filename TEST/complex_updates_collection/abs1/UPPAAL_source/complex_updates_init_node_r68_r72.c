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
for ( i = 0 ; i < N ; i++ )
{
	complex_updates_A[i] += x;
	    complex_updates_B[i] += x;
	    complex_updates_C[i] += x;
	    complex_updates_D[i] += x;
}
}

int main(void)
{
	benchmarkNode();
}
