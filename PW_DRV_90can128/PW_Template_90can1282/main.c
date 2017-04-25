/* Automatically-generated file. Do not edit! */ 

/*Include headerfiles*/
#include "powerwindow_HeaderFiles/main.h" 
#include <avr/interrupt.h>

/*Forward declaration of functions.*/
void powerwindow_init_DRV(void);
void powerwindow_initialize_DRV(void);
int powerwindow_return_DRV(void);
void powerwindow_DRV_main(void);
int main(void);
int bitonic_a_i_inputs[] = {10118, -10060, -1603, -17461, -19395, 11824, 11934, 14281, -11405, -10111};
int i_inputs[] = {-32340, -18578, -9247, 16431, 29042, -9473, 29163, 20551, -12933, 16596};
int j_inputs[] = {-1587, -19197, 12534, 1372, 28211, 15784, -9450, 16170, -22603, 23769};



void blockInputUpdate(int i);
void benchmarkBlock(void);
int blockMain(void);


int j;
int i;
int bitonic_a[];


void blockInputUpdate(int blockInputUpdate_i)
{
	j = j_inputs[blockInputUpdate_i];
	i = i_inputs[blockInputUpdate_i];
	bitonic_a[i] = bitonic_a_i_inputs[blockInputUpdate_i];
	
}

void benchmarkBlock(void)
{
	int h = bitonic_a[ i ];
	bitonic_a[ i ] = bitonic_a[ j ];
	bitonic_a[ j ] = h;
}

int blockMain(void)
{
	for(int update_counter=0; update_counter<10; update_counter++){
		blockInputUpdate(update_counter);
		benchmarkBlock();
	}
	
}



/*main function*/
int main(void){

	/*GPIO Pins*/
	DDRA = 0x00;
	DDRC = 0xF0;
	/*External interrupt settings*/
	PORTD = (1 << PD1);
	EICRA =	(1 << ISC11) | (0 << ISC10);
	EIMSK = (1 << INT1);
	/*Initialize function*/
	powerwindow_initialize_DRV();
	/*Enable interrupt*/
	sei();
	/*while loop*/
	while(1){}
	/*Return*/
	return(powerwindow_return_DRV());
}

/*Interrupt handler*/
ISR(INT1_vect){

	/*Read input values*/
	powerwindow_init_DRV();
	/*Pull up output pin*/
	PORTC = (1 << PC4);
	/*Measure the execution time of the code block*/
	benchmarkBlock();
	/*Pull down output pin*/
	PORTC = (0 << PC4);
}