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
while(i <= 10)
{
	insertsort_iters_i++;
	
	        j = i;
	
	        insertsort_iters_a = 0;
	while (insertsort_a[j] < insertsort_a[j-1])
	{
		insertsort_iters_a++;
		
		            temp = insertsort_a[j];
		            insertsort_a[j] = insertsort_a[j-1];
		            insertsort_a[j-1] = temp;
		            j--;
	}
	if ( insertsort_iters_a < insertsort_min_a )
	{
		insertsort_min_a = insertsort_iters_a;
	}
	if ( insertsort_iters_a > insertsort_max_a )
	{
		insertsort_max_a = insertsort_iters_a;
	}
	i++;
}
}

int main(void)
{
	benchmarkNode();
}
