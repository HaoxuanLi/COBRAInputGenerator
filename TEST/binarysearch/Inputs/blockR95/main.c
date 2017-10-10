/* Automatically-generated file. Do not edit! */ 

/*Include headerfiles*/
#include "main.h" 
#include "inputs.h" 
#include <avr/interrupt.h>

/*Forward declaration of functions.*/
int main(void); 


/*Forward declaration of variables.*/
static int i=0; 

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
	 
	/*Enable FPGA Interupt trigger*/
	PORTC = (1 << PC5);
	PORTC = (0 << PC5);
	/*Initialize input update counter*/
	/*Enable interrupt*/
	sei();
	/*while loop*/
	while(i<1000){}
	/*Return*/
	return(1);
}

/*Interrupt handler*/
ISR(INT1_vect){

	/*Read input values*/
	blockInputUpdate(i);
	/*Pull up output pin*/
	/*Measure the execution time of the code block*/
	benchmarkBlock();
	/*Pull down output pin*/PORTC = (0 << PC4); 

	i++;
}