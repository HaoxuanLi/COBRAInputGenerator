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
for ( f = 0 ; f < 2 * 4; f++ ){
	checksum += iir_wi[ f ];
}
}

int main(void)
{
	benchmarkNode();
}
