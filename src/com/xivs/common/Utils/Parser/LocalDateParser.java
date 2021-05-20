package com.xivs.common.Utils.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateParser{

    public static LocalDate parse(String string, LocalDate defaultValue) {

        try{
            return LocalDate.parse(string);
        }
        catch(DateTimeParseException ex){
            return defaultValue;
        }

    }
}