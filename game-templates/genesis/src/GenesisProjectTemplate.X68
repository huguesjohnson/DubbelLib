;*******************************************************************************
; Main code file for [project template]
;
; Visit http://HuguesJohnson.com/ for random articles about this project.
;*******************************************************************************

;*******************************************************************************
; constants
;*******************************************************************************
;-------------------------------------------------------------------------------
; memory map
;-------------------------------------------------------------------------------
	include 'const/const_MemoryMap.X68'
;-------------------------------------------------------------------------------
; general and game-specific constants
;-------------------------------------------------------------------------------
	include 'inc_Constants.X68'	
	include 'const/const_CharacterIDs.X68'	

;*******************************************************************************
; start of program
;*******************************************************************************

	org	$000000

;*******************************************************************************
; vectors
;*******************************************************************************
	include '../../../68k/retailclerk/init/init_Vectors.X68'

;*******************************************************************************
; ROM header
; auto-generated by build tools
;*******************************************************************************
	include 'init_Header.X68'

;*******************************************************************************
; entry point & initialization
;*******************************************************************************
	include '../../../68k/retailclerk/init/init_MDInit.X68'

;*******************************************************************************
; main game program
;*******************************************************************************

Main:

	include	'init_NewGame.X68'

	include	'sub_MainGameLoop.X68'

;-------------------------------------------------------------------------------
; WaitVBlank
;-------------------------------------------------------------------------------
WaitVBlank:
	btst	#STATE_FLAG_VBLANK,(MEM_GAME_STATE)	; test vblank flag
	beq.s	WaitVBlank	; loop until flag changes
	bclr	#STATE_FLAG_VBLANK,(MEM_GAME_STATE)	; clear vblank flag
	rts	; exit

;*******************************************************************************
; traps
;*******************************************************************************

ExtInt:
	rte

HBlank:
	rte

VBlank:
	bset	#STATE_FLAG_VBLANK,(MEM_GAME_STATE)	; set vblank flag
	addq	#$1,(MEM_VBLANK_COUNTER) ; increment debug counter 
	addq	#$1,(MEM_FRAME_COUNTER) ; increment frame counter
	bpl.s	VBlankReadJoypads
	and.w	#$0000,(MEM_FRAME_COUNTER)

VBlankReadJoypads:
	bsr.w	ReadJoypads ; read controllers

VBlankOrderSprites:
	; test if sprites need to be re-ordered
	tst.w	(MEM_SPRITE_Y_ORDER_CHANGED) ; has the sprite Y order changed?
	beq.s	VBlankExit ; hasn't changed, no need to order sprites
	move.l	(MEM_GAME_STATE),d0 ; copy current game state to d0
	btst.l	#STATE_FLAG_EXPLORING,d0 ; test game state
	beq.s	VBlankExit ; not exploring, no need to order sprites
	; else order sprites and loop
	bsr.w	OrderSprites ; reorder the sprites

VBlankExit:
	rte

TrapException:
	rte

;*******************************************************************************
; mostly stable subroutines
;*******************************************************************************
	include 'inc_Subroutines.X68'

;*******************************************************************************
; code to handle player actions based on game state
;*******************************************************************************
	include	'../../../68k/retailclerk/actionscript/ActionScriptDefault.X68'
	include	'actionscript/ActionScript.X68'

;*******************************************************************************
; echo sound driver (https://github.com/sikthehedgehog/Echo)
;*******************************************************************************
	include 'lib/echo/echo_main.X68'

	include	'lib/echo/echo_list.X68'

;*******************************************************************************
; initialization data
;*******************************************************************************
	include '../../../68k/retailclerk/data/data_Z80Init.X68'

	include '../../../68k/retailclerk/data/data_VDPInit.X68'

;*******************************************************************************
; palettes
;*******************************************************************************
	;-------------------------------------	
	; auto-generated include file
	;-------------------------------------	
	include 'inc_Palettes.X68'

;*******************************************************************************
; patterns
;*******************************************************************************
	include 'inc_Patterns.X68'

;*******************************************************************************
; scenes
;*******************************************************************************
	include 'inc_Scenes.X68'

;*******************************************************************************
; collision maps
;*******************************************************************************
	include	'inc_Collision.X68'

;*******************************************************************************
; sprite table definitions
;*******************************************************************************
	include '../../../68k/retailclerk/data/data_SpriteDefinitions.X68'

;*******************************************************************************
; character definitions
;*******************************************************************************
	include 'data/data_CharacterDefinitions.X68'

;*******************************************************************************
; sprite movement
;*******************************************************************************
	include	'data/data_SpriteMovementPatterns.X68'

;*******************************************************************************
; scripted events
;*******************************************************************************
	include	'data/data_ScriptedEvents.X68'

;*******************************************************************************
; scene definition table
;*******************************************************************************
	include	'scenes/table_SceneDefinitions.X68'

;*******************************************************************************
; lookup tables for text
;*******************************************************************************
	include	'text/table_Text.X68'

;*******************************************************************************
; action table
;*******************************************************************************
	include	'actionscript/table_Actions.X68'

;*******************************************************************************
; status map icon location
;*******************************************************************************
	include	'table_StatusMenuLocations.X68'

	org	ROM_ADDR_RESOURCES

;*******************************************************************************
; text
;*******************************************************************************
	include 'text/en-us/strings.X68'
	
;*******************************************************************************
; background music
;*******************************************************************************
BGM_Title:
	incbin	'music/track0.esf'
BGM_Intro:
	incbin	'music/track1.esf'
BGM_Main:
	incbin	'music/track2.esf'
BGM_End:
	incbin	'music/track3.esf'

	align 2

;*******************************************************************************
; tiles
;*******************************************************************************
	;--------------------------------------	
	; both are auto-generated include files
	;--------------------------------------	
	include 'inc_Tiles.X68'
	include	'inc_SpriteTiles.X68'

;*******************************************************************************
; echo instruments
;*******************************************************************************
	org	ROM_ADDR_INSTRUMENTS

	include 'lib/echo/echo_instruments.X68'

;*******************************************************************************
; end of rom
;*******************************************************************************

RomEnd:
	ORG ROM_ADDR_END
	dc.b	%11111111
    end 0
