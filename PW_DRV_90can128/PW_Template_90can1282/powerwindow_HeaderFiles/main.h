/* Automatically-generated file. Do not edit! */ 

/*Include headerfiles*/
#include "powerwindow.h"
#include "powerwindow_controlexclusion.h"
#include "powerwindow_controlexclusion_private.h"
#include "powerwindow_controlexclusion_types.h"
#include "powerwindow_debounce.h"
#include "powerwindow_debounce_private.h"
#include "powerwindow_debounce_types.h"
#include "powerwindow_model_reference_types.h"
#include "powerwindow_powerwindow_control.h"
#include "powerwindow_powerwindow_control_private.h"
#include "powerwindow_powerwindow_control_types.h"
#include "powerwindow_PW_Control_DRV.h"
#include "powerwindow_PW_Control_DRV_private.h"
#include "powerwindow_PW_Control_DRV_types.h"
#include "powerwindow_rtwtypes.h"
#include "powerwindow_rtw_continuous.h"
#include "powerwindow_rtw_solver.h"
#include "powerwindow_tmwtypes.h"
#include "powerwindow_zero_crossing_types.h"
#include <stdio.h>
#include <avr/io.h>
/*Define macros*/
#define bit_is_clear(sfr,bit) (!(_SFR_BYTE(sfr) & _BV(bit)))