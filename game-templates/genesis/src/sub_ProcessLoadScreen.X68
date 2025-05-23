ProcessLoadScreen:
	; process b press
	move.b	(MEM_CONTROL_PRESSED),d6	; copy pressed buttons to d6
	andi.b	#BUTTON_B,d6	; test if the b button was pressed
	beq.s	ProcessLoadScreenTestStart	; b not pressed, branch
	move.w	#$0000,(MEM_MENU_SELECTION)	; prevents a bug on the title screen
	bra.w	NewGame	; kick-off new game out of laziness
ProcessLoadScreenTestStart:
	; process start press
	move.b	(MEM_CONTROL_PRESSED),d6	; copy pressed buttons to d6
	andi.b	#BUTTON_START,d6	; test if the start button was pressed
	beq.w	ProcessLoadScreenTestDpad	; start not pressed, branch
	; make sure this is a valid save game
	move.w	(MEM_MENU_SELECTION),d7	; lookup if this is a valid save slot
	bsr.w	SaveGameLookup	; get save title into d6
	cmpi.w	#MAX_OBJECTIVE,d6	; is this > MAX_OBJECTIVE?
	bgt.w	ExitProcessProcessLoadScreen	; exit if bad save game
	;---------------------------------------------------------------------------	
	; load game	
	;---------------------------------------------------------------------------	
	move.w	(MEM_MENU_SELECTION),d7	; lookup if this is a valid save slot
	bsr.w	LoadGame ;	load the game from SRAM
	bsr.w	ReloadCurrentScene ; reload current scene (needed to fix sprites)
	bra.w	ExitProcessProcessLoadScreen ;	exit	
ProcessLoadScreenTestDpad:
	; process dpad press
	move.b	(MEM_CONTROL_PRESSED),d6	; copy pressed buttons to d6
	andi.b	#BUTTON_UP,d6	; test if the up button was pressed
	bne.s	ProcessLoadScreenDecrementSelector	; up pressed, branch
	move.b	(MEM_CONTROL_PRESSED),d6	; copy pressed buttons to d6
	andi.b	#BUTTON_DOWN,d6	; test if the down button was pressed
	bne.s	ProcessLoadScreenIncrementSelector	; down pressed, branch
	move.b	(MEM_CONTROL_PRESSED),d6	; copy pressed buttons to d6
	andi.b	#BUTTON_LEFT,d6	; test if the left button was pressed
	bne.s	ProcessLoadScreenDecrementSelector	; left pressed, branch
	move.b	(MEM_CONTROL_PRESSED),d6	; copy pressed buttons to d6
	andi.b	#BUTTON_RIGHT,d6	; test if the right button was pressed
	bne.s	ProcessLoadScreenIncrementSelector	; right pressed, branch
	bra.w	ProcessLoadScreenMoveSelector
ProcessLoadScreenIncrementSelector:
	cmpi.w	#$003,(MEM_MENU_SELECTION)
	bne.s	.1
	move.w	#$0000,(MEM_MENU_SELECTION)
	bra.w	ProcessLoadScreenMoveSelector
.1
	add.w	#$0001,(MEM_MENU_SELECTION)
	bra.w	ProcessLoadScreenMoveSelector
ProcessLoadScreenDecrementSelector:
	cmpi.w	#$0000,(MEM_MENU_SELECTION)
	bne.s	.1
	move.w	#$003,(MEM_MENU_SELECTION)
	bra.w	ProcessLoadScreenMoveSelector
.1
	sub.w	#$0001,(MEM_MENU_SELECTION)
	bra.w	ProcessLoadScreenMoveSelector
ProcessLoadScreenMoveSelector:
	; move selector sprite based on menu selection
	move.w	(MEM_MENU_SELECTION),d7	; copy current value to d7
	mulu.w	#$0008,d7	; rows are 8 apart
	add.w	#$00D0,d7	; first row is D0
	move.l	#VDP_VRAM_WRITE_SPRITE,d6	; add to sprite table address
	move.l	d6,(VDP_CONTROL)	; set write location in VDP
	move.w	d7,(VDP_DATA)	; copy the new y-coordinate
	add.l	#$00060000,d6	; move to x-coordinate
	move.l	d6,(VDP_CONTROL)	; set write location in VDP
	move.w	#$00C8,(VDP_DATA)	; copy the new x-coordinate
ExitProcessProcessLoadScreen:
	rts
