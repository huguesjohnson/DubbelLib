/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

public enum Endianness{
    LITTLE_ENDIAN,
    BIG_ENDIAN;

    @Override
    public String toString(){
        if(this==LITTLE_ENDIAN){return("Little-endian");}
        if(this==BIG_ENDIAN){return("Big-endian");}
        return(this.name());
    }
}