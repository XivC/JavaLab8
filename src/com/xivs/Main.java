package com.xivs;

import com.xivs.client.gui.components.WorkersTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

class FuckMem{
    long[] solong = new long[1000000];
    long id;
    public FuckMem(long id){
        this.id = id;
        for(int i = 0; i < 1000000; i ++){
            solong[i] = new Random().nextLong();
        }
    }
}
public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<FuckMem> m = new ArrayList<>();

        while(true){
            m.add(new FuckMem(new Random().nextLong()));
        }
    }
}
