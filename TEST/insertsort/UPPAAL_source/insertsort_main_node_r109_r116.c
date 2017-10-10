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
while (insertsort_a[j] < insertsort_a[j-1])
{
	insertsort_iters_a++;
	
	            temp = insertsort_a[j];
	            insertsort_a[j] = insertsort_a[j-1];
	            insertsort_a[j-1] = temp;
	            j--;
}
}

int main(void)
{
	benchmarkNode();
}
