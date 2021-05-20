package com.xivs.common.Utils.Parser;

public class ByteParser {

    public static Byte parse(String string, Byte defaultValue) {

        try{
            return Byte.valueOf(string);
        }
        catch(NumberFormatException ex){
            return defaultValue;
        }

    }
}