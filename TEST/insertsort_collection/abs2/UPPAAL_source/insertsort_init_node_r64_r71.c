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
unsigned int a[11] = {0, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    insertsort_iters_i = 0;
    insertsort_min_i = 100000;
	insertsort_max_i = 0;
    insertsort_iters_a = 0;
    insertsort_min_a = 100000;
    insertsort_max_a = 0;
}

int main(void)
{
	benchmarkNode();
}
