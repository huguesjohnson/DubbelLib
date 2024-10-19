InitNPCLocations:
	lea MEM_NPC_LOCATIONS,a0
	move.l	#$03000000,(a0)+	; location 00 (room00)
	move.l	#$04000000,(a0)+	; location 01 (room01)
	move.l	#$05000000,(a0)+	; location 02 (room02)
	move.l	#$06000000,(a0)+	; location 03 (room03)
	rts

