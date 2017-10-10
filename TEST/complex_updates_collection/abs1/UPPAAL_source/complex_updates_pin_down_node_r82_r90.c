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
for ( i = 0; i < N; i++ )
{
	*pa++ = 2;
	    *pa++ = 1;
	    *pb++ = 2;
	    *pb++ = 5;
	    *pc++ = 3;
	    *pc++ = 4;
	    *pd++ = 0;
	    *pd++ = 0;
}
}

int main(void)
{
	benchmarkNode();
}
