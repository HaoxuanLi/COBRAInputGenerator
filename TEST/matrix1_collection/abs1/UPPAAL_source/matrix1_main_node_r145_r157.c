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
for ( k = 0; k < Z; k++ )
{
	p_a = &matrix1_A[0];
	for ( i = 0; i < X; i++ )
	{
		p_b = &matrix1_B[k * Y];          /* take next column */
		
		      *p_c = 0;
		for ( f = 0; f < Y; f++ )
		{
			*p_c += *p_a++ * *p_b++;
		}
		p_c++;
	}
}
}

int main(void)
{
	benchmarkNode();
}
