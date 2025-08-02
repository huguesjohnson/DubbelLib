/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/*
 * Based off 'The "Complete" XM module format specification v0.81' by by Matti "ccr" Hamalainen
 * Copy of the file is here: 
 *   https://github.com/milkytracker/MilkyTracker/blob/master/resources/reference/xm-form.txt
 * Although there are many other locations that host it
 */

package com.huguesjohnson.dubbel.audio.xm;

public abstract class XMConstants{

	public static abstract class Offsets{
		public static int PATTERN_PACKING_TYPE=FieldLengths.PATTERN_HEADER;
		public static int PATTERN_NUM_ROWS=Offsets.PATTERN_PACKING_TYPE+FieldLengths.PATTERN_PACKING_TYPE;
		public static int PATTERN_DATA_SIZE=PATTERN_NUM_ROWS+FieldLengths.PATTERN_NUM_ROWS;
		public static int INSTRUMENT_NAME=FieldLengths.INSTRUMENT_HEADER_SIZE;
		public static int INSTRUMENT_TYPE=INSTRUMENT_NAME+FieldLengths.INSTRUMENT_NAME;
		public static int INSTRUMENT_NUM_SAMPLES=INSTRUMENT_TYPE+FieldLengths.INSTRUMENT_TYPE;
	}

	public static abstract class FieldLengths{
		public static int HEADER_ID=17;
		public static int HEADER_MODULENAME=20;
		public static int HEADER_TRACKERNAME=20;
		public static int HEADER_VERSION=2;
		public static int HEADER_SIZE=4;
		public static int HEADER_SONG_LENGTH=2;
		public static int HEADER_SONG_RESTART=2;
		public static int HEADER_SONG_NUM_CHANNELS=2;
		public static int HEADER_SONG_NUM_PATTERNS=2;
		public static int HEADER_SONG_NUM_INSTRUMENTS=2;
		public static int HEADER_SONG_FLAGS=2;
		public static int HEADER_SONG_TEMPO=2;
		public static int HEADER_SONG_BPM=2;
		public static int HEADER_SONG_PATTERN_ORDER=256;
		public static int PATTERN_HEADER=4;
		public static int PATTERN_PACKING_TYPE=1;
		public static int PATTERN_NUM_ROWS=2;
		public static int PATTERN_DATA_SIZE=2;
		public static int INSTRUMENT_HEADER_SIZE=4;
		public static int INSTRUMENT_NAME=22;
		public static int INSTRUMENT_TYPE=1;
		public static int INSTRUMENT_NUM_SAMPLES=2;
		public static int INSTRUMENT_SAMPLEHEADER_SIZE=4;
		public static int INSTRUMENT_SAMPLENUMBER_SIZE=96;
		public static int INSTRUMENT_ENVELOPEPOINTS_SIZE=48;
		public static int INSTRUMENT_VOLUMEFADEOUT_SIZE=2;
		public static int INSTRUMENT_RESERVED_SIZE=22;
		public static int SAMPLE_HEADER_LENGTH=4;
		public static int SAMPLE_HEADER_LOOPSTART=4;
		public static int SAMPLE_HEADER_LOOPLENGTH=4;
		public static int SAMPLE_HEADER_NAME=22;
	}
}