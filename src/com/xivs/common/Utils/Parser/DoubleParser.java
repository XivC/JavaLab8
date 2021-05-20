package com.xivs.common.Utils.Parser;

public class DoubleParser{

    public static Double parse(String string, Double defaultValue) {

        try{
            return Double.valueOf(string);
        }
        catch(NumberFormatException ex){
            return defaultValue;
        }

    }
}