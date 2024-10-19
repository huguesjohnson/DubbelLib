	;*******************************************************************************
	; reusable code for the retail clerk series of games
	;*******************************************************************************
	;---------------------------------------------------------------------------
	; i/o
	;---------------------------------------------------------------------------
	include	'../../../68k/retailclerk/io/sub_ReadJoypads.X68'
	include '../../../68k/retailclerk/io/sub_SaveLoad.X68'	
	;---------------------------------------------------------------------------
	; graphics
	;---------------------------------------------------------------------------
	include	'../../../68k/retailclerk/graphics/sub_BuildVRAMWriteAddress.X68'
	include	'../../../68k/retailclerk/graphics/sub_ClearVRAM.X68'
	include	'../../../68k/retailclerk/graphics/sub_DrawTileset.X68'
	include	'../../../68k/retailclerk/graphics/sub_FadeInFadeOut.X68'
	include	'../../../68k/retailclerk/graphics/sub_LoadPalettes.X68'
	include	'../../../68k/retailclerk/graphics/sub_LoadTiles.X68'
	;---------------------------------------------------------------------------
	; event handling
	;---------------------------------------------------------------------------
	include	'../../../68k/retailclerk/event/table_ScriptedEventActions.X68'
	include	'../../../68k/retailclerk/event/sub_BuildActionTableOffset.X68'
	include '../../../68k/retailclerk/event/sub_FindActionTarget.X68'
	include	'../../../68k/retailclerk/event/sub_ProcessAction.X68'
	include	'../../../68k/retailclerk/event/sub_ProcessIntroScreen.X68'
	include '../../../68k/retailclerk/event/sub_ProcessScriptedEvent.X68'
	include	'../../../68k/retailclerk/event/sub_QueueScriptedEvent.X68'
	include	'../../../68k/retailclerk/event/sub_ShowDefaultText.X68'
	;---------------------------------------------------------------------------
	; sprite handling
	;---------------------------------------------------------------------------
	include	'../../../68k/retailclerk/sprite/sub_FixSprites.X68'
	include	'../../../68k/retailclerk/sprite/sub_FlipSpriteMapPosition.X68'
	include	'../../../68k/retailclerk/sprite/sub_LoadPlayerSprite.X68'
	include	'../../../68k/retailclerk/sprite/sub_LoadSpriteTiles.X68'
	include	'../../../68k/retailclerk/sprite/sub_MovePlayer.X68'
	include	'../../../68k/retailclerk/sprite/sub_MoveSprite.X68'
	include	'../../../68k/retailclerk/sprite/sub_OrderSprites.X68'
	include	'../../../68k/retailclerk/sprite/sub_ReloadSpriteTiles.X68'
	include	'../../../68k/retailclerk/sprite/sub_SetActiveSpriteMapRowCol.X68'
	include	'../../../68k/retailclerk/sprite/sub_SetDefaultSpriteOrder.X68'
	include	'../../../68k/retailclerk/sprite/sub_SetSpriteLink.X68'
	include	'../../../68k/retailclerk/sprite/sub_SetSpriteLinks.X68'
	include	'../../../68k/retailclerk/sprite/sub_SetSpritePattern.X68'
	include	'../../../68k/retailclerk/sprite/sub_StopSprite.X68'
	include	'../../../68k/retailclerk/sprite/sub_TestSpriteCollision.X68'
	include	'../../../68k/retailclerk/sprite/sub_UpdateSpriteSortTable.X68'
	;---------------------------------------------------------------------------
	; scene handling
	;---------------------------------------------------------------------------
	include '../../../68k/retailclerk/scene/sub_BuildNPCObjectList.X68'
	include '../../../68k/retailclerk/scene/sub_DrawScene.X68'
	include '../../../68k/retailclerk/scene/sub_LoadFontTiles.X68'
	include '../../../68k/retailclerk/scene/sub_LoadScene.X68'
	include '../../../68k/retailclerk/scene/sub_ReloadCurrentScene.X68'
	;---------------------------------------------------------------------------
	; dialog handling
	;---------------------------------------------------------------------------
	include	'../../../68k/retailclerk/dialog/sub_ClearDialog.X68'
	include	'../../../68k/retailclerk/dialog/sub_ProcessDialog.X68'
	include	'../../../68k/retailclerk/dialog/sub_ResetDialog.X68'
	include	'../../../68k/retailclerk/dialog/sub_SetDialogOpening.X68'
	include	'../../../68k/retailclerk/dialog/sub_ShowInteractDialog.X68'
	;---------------------------------------------------------------------------
	; inventory handling
	;---------------------------------------------------------------------------
	include	'../../../68k/retailclerk/inventory/sub_ClearPlayerInventory.X68'
	include	'../../../68k/retailclerk/inventory/sub_Inventory.X68'
	;---------------------------------------------------------------------------
	; utility
	;---------------------------------------------------------------------------
	include	'../../../68k/retailclerk/util/sub_PseudoRandomWord.X68'
	
	;*******************************************************************************
	; things specific to this demo
	;*******************************************************************************
	include	'sub_BuildLoadScreen.X68'
	include	'sub_BuildStatusScreen.X68'
	include	'sub_DrawSaveTitles.X68'
	include	'sub_EndDemo.X68'
	include 'sub_InitNPCLocations.X68'
	include	'sub_ProcessLoadScreen.X68'
	include 'sub_ProcessStatusScreen.X68'
	include	'sub_ProcessTitleScreen.X68'

	if _DEBUG_
	include	'init_Debug.X68'
	endif
