package com.xivs.common.dataTransfer;

import com.xivs.common.io.OutputManager;

public class Utils {
    public static void printResponseMessages(Response resp) {
        try {
            for (String msg : resp.messages) {
                System.out.println(msg);
            }
        }
        catch(NullPointerException ex){
            return;
        }
    }
    public static void printResponseMessages(Response resp, OutputManager manager) {
        try {
            for (String msg : resp.messages) {
                manager.println(msg);
            }
        }
        catch(NullPointerException ex){
            return;
        }
    }
}
