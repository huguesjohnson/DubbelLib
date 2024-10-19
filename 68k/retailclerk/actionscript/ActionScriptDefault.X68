;*******************************************************************************
; used for lookup table conditions that are unreachable
;*******************************************************************************
ActionScriptNullEvent:
	rts

;*******************************************************************************
; used for lookup table conditions that only ever show default look text
;*******************************************************************************
ActionScriptDefaultInteract:
	bsr.w	ShowDefaultText
	rts

;*******************************************************************************
; default exit scene handler
;*******************************************************************************
DefaultExitScene:
	; trying to prevent NPC sprites from changing direction
	move.w	#$FFFF,(MEM_ACTION_TARGET_NPCID)
	; determine which exit is being used based on sprite direction
	move.w	(MEM_PLAYER_SPRITE_DIRECTION),d6	; copy player direction to d6
	mulu.w	#$2,d6	; multiply by 2 to get offset
	lea	MEM_ACTIVE_SCENE_EXIT_S,a6	; point a6 to the first exit
	adda.l	d6,a6	; add offset
	move.w	(a6),d6	; copy location to d6
	; this check shouldn't be needed unless I made a catastrophic error
	cmpi.w	#$FFFF,d6	; is this a valid exit?
	beq.w	ExitDefaultExit	; if not a valid exit then exit subroutine
DefaultExitSceneLoadNewScene:
	mulu.w	#$4,d6	; multiply by 4 to get offset
	lea	SceneDefinitionTable,a6	; point a6 to the scene definition table
	adda.l	d6,a6	; add offset
	move.l	(a6),a6	; have a6 point to the value at a6
	bsr.w	LoadScene	; branch to LoadScene subroutine
	bsr.w	LoadPlayerSprite	; load the player sprite
	; move player to the correct position
	move.w	(MEM_PLAYER_SPRITE_DIRECTION),d6	; copy player direction to d6
	;---------------------------------------------------------------------------
	; adjust player sprite x/y
	;---------------------------------------------------------------------------
DefaultExitSceneTestDown:
	cmpi.w	#DIRECTION_DOWN,d6	; test for down
	bne.s	DefaultExitSceneTestUp	; direction is not down, test for up
	move.w	#SCENE_ENTER_TOP,(MEM_PLAYER_SPRITE_Y)	; update y location
	bra.w	DefaultExitSceneFixSprites	; exit
DefaultExitSceneTestUp:
	cmpi.w	#DIRECTION_UP,d6	; test for up
	bne.s	DefaultExitSceneTestLeft	; direction is not up, test for left
	move.w	#SCENE_ENTER_BOTTOM,(MEM_PLAYER_SPRITE_Y)	; update y location
	bra.s	DefaultExitSceneFixSprites	; exit
DefaultExitSceneTestLeft:
	cmpi.w	#DIRECTION_LEFT,d6	; test for left
	bne.s	DefaultExitSceneTestRight	; direction is not left, test for right
	move.w	#SCENE_ENTER_RIGHT,(MEM_PLAYER_SPRITE_X)	; update x location
	bra.s	DefaultExitSceneFixSprites	; exit
DefaultExitSceneTestRight:
	cmpi.w	#DIRECTION_RIGHT,d6	; test for right
	bne.s	DefaultExitSceneFixSprites	; should be unreachable
	move.w	#SCENE_ENTER_LEFT,(MEM_PLAYER_SPRITE_X)	; update x location
DefaultExitSceneFixSprites:
	bsr.w	FixSprites ; fix all the sprites
ExitDefaultExit:
	bsr.w	FadeIn ; fade in now that everything is loaded
	rts

;*******************************************************************************
; default enter scene handler
;*******************************************************************************
DefaultEnterScene:
	rts

;*******************************************************************************
; show dialogs
;*******************************************************************************
ShowDialog:
	move.l	a6,MEM_DIALOG_TEXT ; copy address to MEM_DIALOG_TEXT	
	move.l	(MEM_DIALOG_FLAGS),d7 ; copy dialog flags to d7
	bclr.l	#DIALOG_FLAG_STYLE_TEXT_2CHOICE,d7 ; set text choice flag
	bset.l	#DIALOG_FLAG_TEXT_OPENING,d7 ; change state to opening
	bset.l	#DIALOG_FLAG_STYLE_TITLED,d7 ; set the titled flag
	move.l	d7,(MEM_DIALOG_FLAGS) ; save updated dialog flags
	; set dialog flags to display the dialog
	move.l	(MEM_GAME_STATE),d7	; copy current game state to d7
	bset.l	#STATE_FLAG_DIALOG,d7	; set the dialog bit
	move.l	d7,(MEM_GAME_STATE) ; copy game state back to d7
	rts
	
Show2ChoiceDialog:
	move.l	a6,MEM_DIALOG_TEXT ; copy address to MEM_DIALOG_TEXT	
	move.l	(MEM_DIALOG_FLAGS),d7 ; copy dialog flags to d7
	bset.l	#DIALOG_FLAG_STYLE_TEXT_2CHOICE,d7 ; set text choice flag
	bset.l	#DIALOG_FLAG_TEXT_OPENING,d7 ; change state to opening
	bset.l	#DIALOG_FLAG_STYLE_TITLED,d7 ; set the titled flag
	move.l	d7,(MEM_DIALOG_FLAGS) ; save updated dialog flags
	; set dialog flags to display the dialog
	move.l	(MEM_GAME_STATE),d7	; copy current game state to d7
	bset.l	#STATE_FLAG_DIALOG,d7	; set the dialog bit
	move.l	d7,(MEM_GAME_STATE) ; copy game state back to d7
	rts
	
Show4ChoiceDialog:
	move.l	a6,MEM_DIALOG_TEXT ; copy address to MEM_DIALOG_TEXT	
	move.l	(MEM_DIALOG_FLAGS),d7 ; copy dialog flags to d7
	bset.l	#DIALOG_FLAG_STYLE_TEXT_4CHOICE,d7 ; set text choice flag
	bset.l	#DIALOG_FLAG_TEXT_OPENING,d7 ; change state to opening
	bset.l	#DIALOG_FLAG_STYLE_TITLED,d7 ; set the titled flag
	move.l	d7,(MEM_DIALOG_FLAGS) ; save updated dialog flags
	; set dialog flags to display the dialog
	move.l	(MEM_GAME_STATE),d7	; copy current game state to d7
	bset.l	#STATE_FLAG_DIALOG,d7	; set the dialog bit
	move.l	d7,(MEM_GAME_STATE) ; copy game state back to d7
	rts	

