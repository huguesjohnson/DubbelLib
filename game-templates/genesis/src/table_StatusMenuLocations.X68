;*********************************************************
; THESE WILL CHANGE IF THE MENU SCREEN CHANGES
;*********************************************************
; the maximum number of navigation items per menu page
MenuPageMaxNavItem:
	; Map
	dc.w	$000B
	; Status
	dc.w	$0007
	; Data
	dc.w	$000B
	; Help
	dc.w	$0003

StatusNavigationTable:
	; item 0
	dc.w	$0080+$00B0	; y
	dc.w	$0080+$0010	; x
	; item 1
	dc.w	$0080+$00B8	; y
	dc.w	$0080+$0010	; x
	; item 2
	dc.w	$0080+$00C0	; y
	dc.w	$0080+$0010	; x
	; item 3
	dc.w	$0080+$00C8	; y
	dc.w	$0080+$0010	; x

; locations for the navigation menu selector
MenuNavigationTable:
	; Map
	dc.w	$0080+$0008	; y
	dc.w	$0080+$0018	; x
	; Status
	dc.w	$0080+$0008	; y
	dc.w	$0080+$0060; x
	; Data
	dc.w	$0080+$0008	; y
	dc.w	$0080+$00B0; x
	; Help
	dc.w	$0080+$0008	; y
	dc.w	$0080+$0100	; y

DataNavigationTable:
	; save 0
	dc.w	$0080+$0038	; y
	dc.w	$0080+$0048	; x
	; save 1
	dc.w	$0080+$0040	; y
	dc.w	$0080+$0048	; x
	; save 2
	dc.w	$0080+$0048	; y
	dc.w	$0080+$0048	; x
	; save 3
	dc.w	$0080+$0050	; y
	dc.w	$0080+$0048	; x
	; load 0
	dc.w	$0080+$0088	; y
	dc.w	$0080+$0048	; x
	; load 1
	dc.w	$0080+$0090	; y
	dc.w	$0080+$0048	; x
	; load 2
	dc.w	$0080+$0098	; y
	dc.w	$0080+$0048	; x
	; load 3
	dc.w	$0080+$00A0	; y
	dc.w	$0080+$0048	; x

MapNavigationTable:
	; fast travel 0
	dc.w	$0080+$0000	; y
	dc.w	$0080+$0000	; x

FastTravelSceneIDTable:
	dc.l	$00000000

