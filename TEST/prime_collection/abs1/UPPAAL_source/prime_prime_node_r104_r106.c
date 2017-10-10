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
for ( i = 3; i * i <= n; i += 2 )
{
	if ( prime_divides ( i, n ) )
	{
		return 0;
	}
}
}

int main(void)
{
	benchmarkNode();
}
