/*

This program is part of the TACLeBench benchmark suite.
Version V 1.x

Name: powerwindow

Author: CoSys-Lab, University of Antwerp

Function: powerwindow implement the powerwindow that can be seen in cars nowadays.
The window can be controlled by either driver or passenger. When an object is
detected between the window frame and the glass during the raising of the glass,
the glass will lower down for some distance. This benchmark contains 4 tasks which includes the
driver side powerwindow, front passenger side powerwindow, back-left passenger side powerwindow,
back-right passenger side powerwindow. These 4 tasks can be easily adjusted to execute in
sequential order parallel on single or muti core.

Source: https://github.com/tacle/tacle-bench/blob/master/bench/app/PowerWindow/powerwindow.c

Changes: a brief summary of major functional changes and formatting)

License: GNU General Public License

*/

#include "powerwindow_HeaderFiles/powerwindow.h"
#include "powerwindow_HeaderFiles/powerwindow_PW_Control_DRV.h"
#include "powerwindow_HeaderFiles/powerwindow_debounce.h"
#include "powerwindow_HeaderFiles/powerwindow_controlexclusion.h"       /* Control Model's header file */
#include "powerwindow_HeaderFiles/powerwindow_model_reference_types.h"
#include "powerwindow_HeaderFiles/powerwindow_powerwindow_control.h" 	/* PW passenger control Model's header file */
#include "powerwindow_HeaderFiles/powerwindow_rtwtypes.h"
#include "powerwindow_HeaderFiles/powerwindow_model_reference_types.h"
#include "powerwindow_HeaderFiles/main.h" 


/* This ert_main.c example uses printf/fflush */
//#include <util/delay.h>
/*
Forward declaration of functions
*/


// PSG_DRV
void powerwindow_init_DRV(void);
void powerwindow_input_initialize_DRV(void);
void powerwindow_initialize_DRV(void);
int powerwindow_return_DRV(void);
void powerwindow_DRV_main(void);

/*
Declaration of global variables
*/

/* External inputs (root inport signals with auto storage) */

extern powerwindow_ExternalInputs_powerwindow_PW_C powerwindow_PW_Control_DRV_U;
extern powerwindow_ExternalOutputs_powerwindow_PW_ powerwindow_PW_Control_DRV_Y;


int powerwindow_main_inputcyclecounter_DRV;

/*
Initialization- and return-value-related functions
*/

void powerwindow_init_DRV(void)
{

	powerwindow_PW_Control_DRV_U.In1  = (bit_is_clear(PINC,PC2) == 0);			/* endofrange The when the window reaches the end of the range, the endofdetectionrange changes to 0. */
	
	powerwindow_PW_Control_DRV_U.In3  = ((bit_is_clear(PINC,PC1) == 0) * 200 + (bit_is_clear(PINC,PC0) == 0) * 100);		/* currentsense When the currentsense is higher than 92 (based on experiments), one object is stuck between the window and the frame. Pinch is set to True.*/

	powerwindow_PW_Control_DRV_U.In2  = (bit_is_clear(PINA,PA0) == 0);			/* DRV_Up			The debounced control signal from the driver. 1 when the button is not pressed, change to 0 to lift the window. */
	powerwindow_PW_Control_DRV_U.In4  = (bit_is_clear(PINA,PA1) == 0);			/* DRV_Down			The debounced control signal from the driver. 1 when the button is not pressed, change to 0 to lift the window. */

	powerwindow_PW_Control_DRV_U.In5  = (bit_is_clear(PINA,PA2) == 0);			/* PSG_Front_Up		The debounced control signal from the driver. 1 when the button is not pressed, change to 0 to lift the window. */
	powerwindow_PW_Control_DRV_U.In6  = (bit_is_clear(PINA,PA3) == 0);			/* PSG_Front_Down	The debounced control signal from the driver. 1 when the button is not pressed, change to 0 to lift the window. */

	powerwindow_PW_Control_DRV_U.In9  = (bit_is_clear(PINA,PA4) == 0);			/* PSG_BackL_Up		The debounced control signal from the driver. 1 when the button is not pressed, change to 0 to lift the window. */
	powerwindow_PW_Control_DRV_U.In10 = (bit_is_clear(PINA,PA5) == 0);			/* PSG_BackL_Down	The debounced control signal from the driver. 1 when the button is not pressed, change to 0 to lift the window. */

	powerwindow_PW_Control_DRV_U.In7  = (bit_is_clear(PINA,PA6) == 0);			/* PSG_BackR_Up		The debounced control signal from the driver. 1 when the button is not pressed, change to 0 to lift the window. */
	powerwindow_PW_Control_DRV_U.In8  = (bit_is_clear(PINA,PA7) == 0);			/* PSG_BackR_Down	The debounced control signal from the driver. 1 when the button is not pressed, change to 0 to lift the window. */
}



void powerwindow_initialize_DRV(void)
{
	/* Initialize model */
	powerwindow_PW_Control_DRV_initialize();
	powerwindow_main_inputcyclecounter_DRV=0;
}

int powerwindow_return_DRV(void)
{
	/* Terminate model */
	powerwindow_PW_Control_DRV_terminate();
	return 0;
}

/*
  Initialization- and return-value-related functions
*/



/*
Main functions
*/

/*
* Associating powerwindow_main with a real-time clock or interrupt service routine
* is what makes the generated code "real-time".  The function powerwindow_main is
* always associated with the base rate of the model.  Subrates are managed
* by the base rate from inside the generated code.  Enabling/disabling
* interrupts and floating point context switches are target specific.  This
* example code indicates where these should take place relative to executing
* the generated code step function.  Overrun behavior should be tailored to
* your application needs.  This example simply sets an error status in the
* real-time model and returns from powerwindow_main.
*/



/*
* The example "main" function illustrates what is required by your
* application code to initialize, execute, and terminate the generated code.
* Attaching powerwindow_main to a real-time clock is target specific.  This example
* illustates how you do this relative to initializing the model.
*/

void powerwindow_DRV_main(void)
{

	static powerwindow_boolean_T OverrunFlag = 0;

	/* Disable interrupts here */

	/* Check for overrun */
	if (OverrunFlag) {
		powerwindow_PW_DRV_rtmSetErrorStatus(powerwindow_PW_Control_DRV_M, "Overrun");

		return;
	}


	OverrunFlag = true;

	/* Save FPU context here (if necessary) */
	/* Re-enable timer or interrupt here */
	/* Set model inputs here */


	powerwindow_PW_Control_DRV_main();

	/* Get model outputs here */


	/* Indicate task complete */
	OverrunFlag = false;

	/* Disable interrupts here */
	/* Restore FPU context here (if necessary) */
	/* Enable interrupts here */

}

/*
* File trailer for generated code.
*
* [EOF]
*/



