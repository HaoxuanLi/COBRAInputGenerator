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
for ( i = 0 ; i < N ; i++, p_a++ )
{
	*p_d    = *p_c++ + *p_a++ * *p_b++ ;
	    *p_d++ -=          *p_a   * *p_b-- ;
	
	    *p_d    = *p_c++ + *p_a-- * *p_b++ ;
	    *p_d++ +=          *p_a++ * *p_b++ ;
}
}

int main(void)
{
	benchmarkNode();
}
