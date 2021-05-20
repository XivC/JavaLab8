package com.xivs.common.Utils.Parser;

public class IntegerParser{

    public static Integer parse(String string, Integer defaultValue) {

        try{
            return Integer.valueOf(string);
        }
        catch(NumberFormatException ex){
            return defaultValue;
        }

    }
}