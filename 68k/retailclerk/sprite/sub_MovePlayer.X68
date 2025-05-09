;-------------------------------------------------------------------------------
; MovePlayer
; moves the player sprite if a d-pad direction is being pressed
; calls MoveSprite to actually move the sprite
; d7 is used to test controller input and scrolling
; d6 is used to test for scrolling
; a6 is used to store the start address of the sprite info if there is movement
;-------------------------------------------------------------------------------
MovePlayer:
	andi.l	#$00000000,d7	; lazy way to prevent issues
	move.b	(MEM_CONTROL_HELD),d7	; copy button held value to d7 for eor
	beq.w	StopPlayerSprite		; branch if a direction is being held

SetPlayerDirection:
; map key press to direction
TestUpHeld:
	move.b	(MEM_CONTROL_HELD),d7	; copy button held value to d7 for andi
	andi.b	#BUTTON_UP,d7	; test if the up button is held
	beq.s	TestDownHeld			; branch if not
	move.w	#DIRECTION_UP,(MEM_PLAYER_SPRITE_DIRECTION)	; set direction
	; test if player is exiting top edge of scene
	cmpi.w	#SCENE_EXIT_TOP,(MEM_PLAYER_SPRITE_Y)	; test if at exit edge
	bgt.s	MovePlayerSprite	; branch if not
	bra.s	QueueExitAction	; queue an exit scene event
TestDownHeld:
	move.b	(MEM_CONTROL_HELD),d7	; copy button held value to d7 for andi
	andi.b	#BUTTON_DOWN,d7	; test if the down button is held
	beq.s	TestLeftHeld			; branch if not
	move.w	#DIRECTION_DOWN,(MEM_PLAYER_SPRITE_DIRECTION)	; set direction
	; test if player is exiting bottom edge of scene
	cmpi.w	#SCENE_EXIT_BOTTOM,(MEM_PLAYER_SPRITE_Y)	; test if at exit edge
	blt.s	MovePlayerSprite	; branch if not
	bra.s	QueueExitAction	; queue an exit scene event
TestLeftHeld:
	move.b	(MEM_CONTROL_HELD),d7	; copy button held value to d7 for andi
	andi.b	#BUTTON_LEFT,d7	; test if the left button is held
	beq.s	TestRightHeld			; branch if not
	move.w	#DIRECTION_LEFT,(MEM_PLAYER_SPRITE_DIRECTION)	; set direction
	; test if player is exiting left edge of scene
	cmpi.w	#SCENE_EXIT_LEFT,(MEM_PLAYER_SPRITE_X)	; test if at exit edge
	bgt.s	MovePlayerSprite	; branch if not
	bra.s	QueueExitAction	; queue an exit scene event
TestRightHeld:
	move.b	(MEM_CONTROL_HELD),d7	; copy button held value to d7 for andi
	andi.b	#BUTTON_RIGHT,d7	; test if the right button is held
	beq.w	StopPlayerSprite			; non-directional button held
	move.w	#DIRECTION_RIGHT,(MEM_PLAYER_SPRITE_DIRECTION)	; set direction
	cmpi.w	#SCENE_EXIT_RIGHT,(MEM_PLAYER_SPRITE_X)	; test if at exit edge
	blt.s	MovePlayerSprite	; branch if not
	bra.s	QueueExitAction	; queue an exit scene event
MovePlayerSprite:
	lea (MEM_PLAYER_SPRITE_ID),a6	; setup call to MoveSprite
	bsr.w	MoveSprite				; branch to move MoveSprite
	bra.s	EndMovePlayer			; exit
QueueExitAction:
	move.l	(MEM_GAME_STATE),d7	; copy game state to d7
	bset.l	#STATE_FLAG_ACTION,d7	; set the action flag
	move.l	d7,(MEM_GAME_STATE)	; save it back
	move.w	#ACTION_EXIT_SCENE,(MEM_ACTION_ID)	; set the action id
	; trying to prevent NPC sprites from changing direction
	move.w	#$FFFF,(MEM_ACTION_TARGET_NPCID)
	; test if the exits are blocked
	btst.l	#STATE_FLAG_EXITS_BLOCKED,d7	; test exits blocked flag
	bne.s	StopPlayerSprite	; if set stop the sprite
	bra.s	MovePlayerSprite	; move the player sprite
StopPlayerSprite:
	lea (MEM_PLAYER_SPRITE_ID),a6	; setup call to StopSprite
	bsr.w	StopSprite				; reset the sprite frame
EndMovePlayer:
	rts
