/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

public class Tile8x8{
	public final static String newLine=System.lineSeparator();
	public final static String asmLineStart="\tdc.l\t$";
	public final static String rleLineStart="\tdc.b\t$";

	
	public int[][] pixels;
	
	public Tile8x8(){this.pixels=new int[8][8];}
	
	public String toAsmLines(){
		StringBuffer lines=new StringBuffer();
		for(int x=0;x<8;x++){
			lines.append(asmLineStart);
			for(int y=0;y<8;y++){
				lines.append(Integer.toHexString(this.pixels[x][y]).toUpperCase());
			}
			lines.append(newLine);
		}
		return(lines.toString());
	}
	
	public String toRLELines(){
		StringBuffer lines=new StringBuffer();
		int currentPixel=this.pixels[0][0];
		int consecutiveCount=-1;//start at -1 because it will be incremented immediately
		lines.append(rleLineStart);
		int x=0;
		while(x<8){
			int y=0;
			while(y<8){
				if(this.pixels[x][y]!=currentPixel){//pixel has changed
					lines.append(Integer.toHexString(consecutiveCount).toUpperCase());
					lines.append(",");
					lines.append(Integer.toHexString(currentPixel).toUpperCase());
					lines.append(newLine);
					currentPixel=this.pixels[x][y];
					consecutiveCount=0;
					//start next line
					lines.append(rleLineStart);
				}else{
					//increment counter
					consecutiveCount++;
				}
				y++;
			}
			x++;
		}
		//write the final line
		lines.append(Integer.toHexString(consecutiveCount).toUpperCase());
		lines.append(",");
		lines.append(Integer.toHexString(currentPixel).toUpperCase());			
		return(lines.toString());
	}

	@Override
	public boolean equals(Object obj){
		try{
			Tile8x8 tobj=(Tile8x8)obj;
			for(int x=0;x<8;x++){
				for(int y=0;y<8;y++){
					if(this.pixels[x][y]!=tobj.pixels[x][y]){return(false);}
				}
			}
			return(true);
		}catch(Exception x){
			return(false);
		}
	}

	@Override
	public int hashCode(){
		int hash=0;
		for(int x=0;x<8;x++){
			int i=0;
			for(int y=0;y<8;y++){
				i+=this.pixels[x][y];
			}
			hash+=(x*10)+i;
		}
		return(hash);
	}

	@Override
	public String toString(){
		return(this.toAsmLines());
	}
}