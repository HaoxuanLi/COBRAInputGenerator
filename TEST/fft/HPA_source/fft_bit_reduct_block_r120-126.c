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

void benchmarkBlock(void);
int main(void);

void benchmarkBlock(void)
{
tmpr = *( int_pointer + j ) ;
      *( int_pointer + j ) = *( int_pointer + i ) ;
      *( int_pointer + i ) = tmpr ;

      tmpr = *( int_pointer + j + 1 ) ;
      *( int_pointer + j + 1 ) = *( int_pointer + i + 1 ) ;
      *( int_pointer + i + 1 ) = tmpr ;
}

int main(void)
{
	benchmarkBlock();
}
