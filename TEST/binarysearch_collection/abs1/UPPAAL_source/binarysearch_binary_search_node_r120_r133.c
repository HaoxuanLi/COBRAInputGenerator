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
while ( low <= up )
{
	mid = ( low + up ) >> 1;
	if ( binarysearch_data[ mid ].key == x )
	{
		up = low - 1;
		      fvalue = binarysearch_data[ mid ].value;
	}
	else
	{
		if ( binarysearch_data[ mid ].key > x )
		{
			up = mid - 1;
		}
		else
		{
			low = mid + 1;
		}
	}
}
}

int main(void)
{
	benchmarkNode();
}
