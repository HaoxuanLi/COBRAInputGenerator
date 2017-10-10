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
w = y - *ptr_coeff++ * *ptr_wi1;
    w -= *ptr_coeff++ * *ptr_wi2;

    y = *ptr_coeff++ * w;
    y += *ptr_coeff++ * *ptr_wi1;
    y += *ptr_coeff++ * *ptr_wi2;

    *ptr_wi2++ = *ptr_wi1;
    *ptr_wi1++ = w;

    ptr_wi2++;
    ptr_wi1++;
}

int main(void)
{
	benchmarkNode();
}
