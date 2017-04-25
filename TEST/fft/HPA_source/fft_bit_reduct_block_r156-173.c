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
j = i + max;
          p = int_pointer + j;
          q = int_pointer + i;

          tmpr  = l * *( p - 1 );
          tmpr -= ( k * *p );

          tmpi  = l * *p;
          tmpi += ( k * *( p - 1 ) );

          tmpr  = tmpr >> SHIFT ;
          tmpi  = tmpi >> SHIFT ;

          *( p - 1 ) = *( q - 1 ) - tmpr ;
          *p     = *q - tmpi ;

          *( q - 1 ) += tmpr ;
          *q     += tmpi ;
}

int main(void)
{
	benchmarkBlock();
}
