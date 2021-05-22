package com.xivs.client.gui.visualization;

import com.xivs.client.data.DataProvider;
import com.xivs.client.gui.windows.CreateUpdateObjectWindow;
import com.xivs.common.Utils.Hasher;
import com.xivs.common.Utils.WorkerContainer;
import com.xivs.common.lab.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.xivs.client.gui.windows.CreateUpdateObjectWindow.UPDATE;

public class VisualPanel extends JPanel{
    private ArrayList<WorkerCircle> entities;
    private final int tps =60;
    private Thread tickThread;
    private DataProvider<ArrayList<WorkerContainer>> provider;
    private Dimension size;
    final BufferedImage image;
    private void tick(){
        try {
            while (true) {
                Thread.sleep(1000/tps);
                for (WorkerCircle c: entities){
                    c.onTick();
                }
                repaint();
            }
        }
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
    }
    public void restart(){

        entities.clear();
        long max_x = 0L;
        for(WorkerContainer c: provider.getData()) {
            max_x = Math.max(max_x, c.x);
        }

        for(WorkerContainer c: provider.getData()){
            double radius = 10;
            switch (c.position){
                case NONE:
                    break;
                case LABORER:
                    radius = 50; break;
                case HEAD_OF_DEPARTMENT:
                    radius = 70; break;
                case BAKER:
                    radius = 30; break;
                case CLEANER:
                    radius = 20; break;
            }
            byte[] color_b = Hasher.getHash(c.owner);
            byte[] color_b_norm = new byte[3];
            for (int i = 0; i < 3; i ++){
                color_b_norm[i] = (byte)Math.abs(color_b[i]);
            }
            Color color = new Color(color_b_norm[0], color_b_norm[1], color_b_norm[2]);
            double velocity_x = -1 + Math.random() * (2);
            double velocity_y = -1 + Math.random() * (2);
            double norm_x = (double)(c.x)/(double)(max_x);

            WorkerCircle wc = new WorkerCircle((double)(size.width)/2, (double)size.height/2, radius, 70 + norm_x * (size.width - 140), c.y, velocity_x, velocity_y, c.key, color);

            entities.add(wc);
        }
        //tickThread.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (image != null){
            g2.drawImage(image, 0, 0, size.width, size.height, null);
        }
        for (WorkerCircle c: entities){
            g2.setColor(c.color);
            g2.fill(c);
            g2.draw(c);
            g2.setColor(Color.WHITE);
            g2.drawString(c.key, (int)(c.getX() + c.getWidth()/3), (int)(c.getY() + c.getHeight()/2));
        }

    }
    public VisualPanel(DataProvider<ArrayList<WorkerContainer>> provider, Dimension size){
        super();
        BufferedImage image_tmp;
        provider.addUpdateEvent(this::restart);
        this.entities = new ArrayList<>();
        this.tickThread = new Thread(this::tick);
        this.provider = provider;
        this.size = size;
        try {
            image_tmp = ImageIO.read(new File("resources/coords.png"));
        }
        catch(IOException ex){
            ex.printStackTrace();
            image_tmp = null;
        }
        image = image_tmp;
        setBackground(Color.WHITE);
        tickThread.start();

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("pressed");
                int m_x = e.getX();
                int m_y = e.getY();

                for (WorkerCircle c: entities){

                    if(c.contains(m_x, m_y)){
                        new CreateUpdateObjectWindow(UPDATE, c.key);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        setPreferredSize(size);
        setVisible(true);

    }



}
