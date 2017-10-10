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
for ( Inner = 0; Inner < MAXSIZE; Inner++ )
{
	if ( Array[Outer][Inner] >= 0 )
	{
		Ptotal += Array[Outer][Inner];
		        Pcnt++;
	}
	else
	{
		Ntotal += Array[Outer][Inner];
		        Ncnt++;
	}
}
}

int main(void)
{
	benchmarkNode();
}
