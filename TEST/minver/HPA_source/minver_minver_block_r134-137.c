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
w1 = -w;
      iw = work[ k ];
      work[ k ] = work[ r ];
      work[ r ] = iw;
}

int main(void)
{
	benchmarkBlock();
}
