package com.xivs.common.Utils.Parser;

public class FloatParser{

    public static Float parse(String string, Float defaultValue) {

        try{
            return Float.valueOf(string);
        }
        catch(NumberFormatException ex){
            return defaultValue;
        }

    }
}