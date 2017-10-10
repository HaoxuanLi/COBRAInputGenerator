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
for ( i = 0; i < 15; ++i )
{
	binarysearch_data[ i ].key = binarysearch_randomInteger();
	binarysearch_data[ i ].value = binarysearch_randomInteger();
}
}

int main(void)
{
	benchmarkNode();
}
