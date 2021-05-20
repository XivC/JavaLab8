package com.xivs.common.Utils.Parser;

public class LongParser{

    public static Long parse(String string, Long defaultValue) {

        try{
            return Long.valueOf(string);
        }
        catch(NumberFormatException ex){
            return defaultValue;
        }

    }
}
