package com.xivs;

import com.xivs.client.gui.components.WorkersTable;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        JFrame f = new JFrame(){};
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(1000,1000));
        f.setJMenuBar(new JMenuBar());
        f.pack(); // установка размеров фрейма
        f.setVisible(true);
    }
}
