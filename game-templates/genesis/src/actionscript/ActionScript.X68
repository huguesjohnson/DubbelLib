;*******************************************************************************
; custom exit scene handlers
;*******************************************************************************
;-------------------------------------------------------------------------------
; examples of:
; 1) handling a scene exit event
; 2) ending the demo and showing the credits
;-------------------------------------------------------------------------------
Room00Exit:
	; this demo ends if the player exits the first room to the south
	move.w	(MEM_PLAYER_SPRITE_DIRECTION),d7
	cmpi.w	#DIRECTION_DOWN,d7
	beq.w	SetupEndGame
	bra.w	DefaultExitScene

;-------------------------------------------------------------------------------
; examples of:
; 1) handing an interact event
; 2) showing a 2-choice dialog
;-------------------------------------------------------------------------------
Room01Interact:
	move.l	(MEM_ROOMS_VISITED),d7
	btst.l	#1,d7
	bne.s	.default
	bset.l	#1,d7
	move.l	d7,(MEM_ROOMS_VISITED)
	; setup the 2-choice dialog
	lea	DialogText2ChoiceExample,a6 ; load dialog text	
	bsr.w	Show2ChoiceDialog
	rts
.default
	bsr.w	ShowDefaultText	
	rts

;-------------------------------------------------------------------------------
; example of:
; 1) responding to a selection from a 2-choice dialog
;-------------------------------------------------------------------------------
Room01Respond:
	bsr.w	ResetDialog
	cmpi.w	#$0000,(MEM_MENU_RESPONSE) ; is the menu selection 0?
	bne.s	.response1
	lea ExamplePaletteChange0Start,a6 ; point a6 to event to load
	bsr.w QueueScriptedEvent ; queue the event
	lea	DialogText2ChoiceResponse0,a6
	bra.s	.showdialog
.response1
	lea ExamplePaletteChange1Start,a6 ; point a6 to event to load
	bsr.w QueueScriptedEvent ; queue the event
	lea	DialogText2ChoiceResponse1,a6
.showdialog
	bsr.w	ShowDialog
	rts

;-------------------------------------------------------------------------------
; examples of:
; 1) handling a scene enter event
; 2) launching a scripted event with various scripted event features
;-------------------------------------------------------------------------------
Room02Enter:
	move.l	(MEM_ROOMS_VISITED),d7
	btst.l	#2,d7
	bne.s	.default
	bset.l	#2,d7
	move.l	d7,(MEM_ROOMS_VISITED)
	lea ExampleDialogWithMovementStart,a6 ; point a6 to event to load
	bsr.w QueueScriptedEvent ; queue the event
	rts	
.default
	bra.w	DefaultEnterScene

;-------------------------------------------------------------------------------
; examples of:
; 1) handling a scene exit event
; 2) launching a scripted event with various scripted event features
;-------------------------------------------------------------------------------
Room02Exit:
	lea ExampleExitEventStart,a6 ; point a6 to event to load
	bsr.w QueueScriptedEvent ; queue the event
	rts	

;-------------------------------------------------------------------------------
; examples of:
; 1) handing an interact event
; 2) showing a 4-choice dialog
;-------------------------------------------------------------------------------
Room03Interact:
	move.l	(MEM_ROOMS_VISITED),d7
	bset.l	#3,d7
	move.l	d7,(MEM_ROOMS_VISITED)
	; test if the player has all the items
	move.w	(MEM_PLAYER1_ITEM_COUNT),d7
	cmpi.w	#$0004,d7	
	bge.s	.default	
	; setup the 4-choice dialog
	lea	DialogText4ChoiceExample,a6 ; load dialog text	
	bsr.w	Show4ChoiceDialog
	rts
.default
	bsr.w	ShowDefaultText	
	rts

;-------------------------------------------------------------------------------
; examples of:
; 1) responding to a selection from a 4-choice dialog
; 2) inventory management
;-------------------------------------------------------------------------------
Room03Respond:
	bsr.w	ResetDialog
	; put the item id in d7
	move.w	#OBJ_ITEM_BASE,d7 ; start with base item
	addq	#$1,d7 ; add 1 since MEM_MENU_RESPONSE is 0 indexed
	add.w	(MEM_MENU_RESPONSE),d7 ; add the item id
	; test if the player has that item
	lea	(MEM_PLAYER1_ITEM_COUNT),a2
	bsr.w	HasItem
	cmpi.w	#$FFFF,d6
	bne.s	.hasitem	
.additem
	lea	(MEM_PLAYER1_ITEM_COUNT),a1
	bsr.w	AddItem
	lea	DialogText4ChoiceResponse0,a6
	bra.s	.showdialog	
.hasitem	
	lea	DialogText4ChoiceResponse1,a6
.showdialog	
	bsr.w	ShowDialog
	rts

;*******************************************************************************
; end the game
;*******************************************************************************
SetupEndGame:
	; test if the player did all required interactions
	move.l	(MEM_ROOMS_VISITED),d7
	cmpi.l	#$0000000E,d7
	blt.s	.badending
	; test if the player has all the items
	move.w	(MEM_PLAYER1_ITEM_COUNT),d7
	cmpi.w	#$0004,d7
	blt.s	.goodending
.perfectending
	lea	ScriptedEventPerfectEndingStart,a6	; point a6 to event to load
	bra.s	.queue
.goodending
	; queue scripted event
	lea	ScriptedEventGoodEndingStart,a6	; point a6 to event to load
	bra.s	.queue
.badending
	lea ScriptedEventBadEndingStart,a6	; point a6 to event to load
.queue
	bsr.w QueueScriptedEvent	; queue the event
	rts

EndGame:
	; set the end game flag
	move.l	(MEM_GAME_STATE),d7	; copy game state
	and.l	#$0000,d7	; clear all other flags
	bset.l	#STATE_FLAG_END_SCREEN,d7 ; set end screen state flag	
	move.l	d7,(MEM_GAME_STATE)	; save updated game state
	rts

