/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;

public class Tile8x8 implements Serializable{
	private static final long serialVersionUID=113970L;

	private final static String newLine=System.lineSeparator();

	public int[][] pixels;
	
	public Tile8x8(){this.pixels=new int[8][8];}
	
	public String toAsmLines(){
		StringBuffer lines=new StringBuffer();
		for(int x=0;x<8;x++){
			lines.append("\tdc.l\t$");
			for(int y=0;y<8;y++){
				lines.append(Integer.toHexString(this.pixels[x][y]).toUpperCase());
			}
			lines.append(newLine);
		}
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