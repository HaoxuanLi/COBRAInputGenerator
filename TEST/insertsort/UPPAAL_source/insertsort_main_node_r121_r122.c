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
if ( insertsort_iters_a > insertsort_max_a )
{
	insertsort_max_a = insertsort_iters_a;
}
}

int main(void)
{
	benchmarkNode();
}
