/* Automatically-generated file. Do not edit! */ 

/*Include headerfiles*/
#include "main.h" 
#include "inputs.h" 
#include <avr/interrupt.h>

#define F_CPU 8000000UL
#define BAUD    9600UL
#define RBAUD   ((F_CPU/(16UL*BAUD)) - 1)
#define pointer_length   2
#define register_length 32
#define stack_length_org 9
#define stack_length_block 25

/*Forward declaration of functions.*/
int main(void);
int uart_putchar(char c, FILE *stream); 


/*Forward declaration of variables.*/
static int measurementRoundCounter=0;
volatile char register_address_org[register_length];
volatile char register_address_block[register_length];
volatile char stack_address_org[stack_length_org];
volatile char stack_address_block[stack_length_block];
volatile char register_address_r14r15[pointer_length];
volatile char framepointer_address[pointer_length];
volatile char stackpointer_address_org[pointer_length];
volatile char stackpointer_address_block[pointer_length];
FILE mystdout = FDEV_SETUP_STREAM(uart_putchar, NULL, _FDEV_SETUP_RW); 

/*main function*/
int main(void){

	/*Initialize the pointer and register address variables*/
	int x_registercounter;
	for(x_registercounter=0; x_registercounter < pointer_length; x_registercounter++){
		register_address_r14r15[x_registercounter]=0;
		framepointer_address[x_registercounter]=0;
		stackpointer_address_org[x_registercounter]=0;
		stackpointer_address_block[x_registercounter]=0 ;stackpointer_address_block[x_registercounter]=0 ;
	}
	for(x_registercounter=0; x_registercounter < register_length; x_registercounter++){
	register_address_org[x_registercounter];
	register_address_block[x_registercounter];
	}
	for(x_registercounter=0; x_registercounter < stack_length_org; x_registercounter++){
	stack_address_org[x_registercounter];
	}
	for(x_registercounter=0; x_registercounter < stack_length_block; x_registercounter++){
	stack_address_block[x_registercounter];
	}
	asm(
	"in	r14, 0x3d\n\t"
	"in	r15, 0x3e\n\t"
	"sts	0x1517, r14\n\t"
	"sts	0x1518, r15\n\t"
	);
	/*GPIO Pins*/
	DDRA = 0x00;
	DDRC = 0xF0;
	/*External interrupt settings*/
	PORTD = (1 << PD1);
	EICRA =	(1 << ISC11) | (0 << ISC10);
	EIMSK = (1 << INT1);
	/*Serial port configuration*/
	UBRR0H = (RBAUD >> 8);
	UBRR0L =  RBAUD;
	UCSR0B = (1 << TXEN0) | (1 << RXEN0);
	UCSR0C = (1 << UCSZ01) | (1 << UCSZ00);
	/*Execute the complete original code*/
	iir_init();
	iir_main();
	iir_return();
	/*Enable FPGA Interupt trigger*/
	PORTC = (1 << PC5);
	PORTC = (0 << PC5);
	/*Enable external interrupt handler*/
	sei();
	/*while loop*/
	while(measurementRoundCounter<5000){}
	/*Return*/
	return(1);
}

/*Interrupt handler*/
ISR(INT1_vect){

	/*Read input values*/
	blockInputUpdate(measurementRoundCounter);
	/*Measure the execution time of the code block*/
	benchmarkBlock();
	/*Update measurement round counter*/
	measurementRoundCounter++;
	/*Print pointer values*/
	if(measurementRoundCounter==1){
		fprintf(&mystdout,"%02x%02x\n",framepointer_address[1],framepointer_address[0]);
		fprintf(&mystdout,"%02x%02x\n",stackpointer_address_org[1],stackpointer_address_org[0]);
		fprintf(&mystdout,"%02x%02x\n",stackpointer_address_block[1],stackpointer_address_block[0]);
	}
}

/*USART*/
int uart_putchar(char c, FILE *stream)
{
	if (c == '\n') uart_putchar('\r', stream);
	while( ! ( UCSR0A & (1 << UDRE0)));
	UDR0 = c;
	return 0;
}