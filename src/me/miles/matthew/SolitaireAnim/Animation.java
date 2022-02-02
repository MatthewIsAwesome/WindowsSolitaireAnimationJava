package me.miles.matthew.SolitaireAnim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Animation extends JPanel implements ActionListener {
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Timer timer = new Timer(25, this);
    // private Long lastFrameTime;
    private Long startTime;
    private BufferedImage previousFrame;

    public Animation() {
        super();
        previousFrame = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = previousFrame.createGraphics();
        g2.setColor(new Color(15, 107, 40));
        g2.fillRect(0, 0, 800, 600);
        startTime = System.currentTimeMillis();
        // lastFrameTime = startTime;
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (previousFrame.getWidth() != this.getWidth() || previousFrame.getHeight() != this.getHeight()) {
//        	System.out.println("Resized");
    		previousFrame = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = previousFrame.createGraphics();
            g2.setColor(new Color(15, 107, 40));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        // draw previous frame
        g2.drawImage(previousFrame, 0, 0, null);
        
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).draw(g2);
        }

        g.drawImage(img, 0, 0, null);
        // record current frame for next frame
        previousFrame = img;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Long timePassedMillis = System.currentTimeMillis() - lastFrameTime;
        Long timeSinceStartMillis = System.currentTimeMillis() - startTime;
        // lastFrameTime = System.currentTimeMillis();
        if (ae.getSource() == timer) {
            int i = 0;
            boolean cond = i < cards.size();
            while (cond) {
                cards.get(i).update(0.1d, getSize());
                if (cards.get(i).getX() - cards.get(i).getWidth() > getSize().width) {
                    cards.remove(i);
                    // System.out.println("Removed");
                } else if (cards.get(i).getX() + cards.get(i).getWidth() < 0) {
                    cards.remove(i);
                    // System.out.println("Removed");
                }
                i++;
                cond = i < cards.size();
            }

            if (new Random().nextInt(100) < Math.log(1 + timeSinceStartMillis/1000d)) {
                Card card = new Card(new Random().nextInt(getWidth()), new Random().nextInt(getHeight()), new Dimension(140, 202), 0.8);
                cards.add(card);
                card.applyForce(new Point2D.Double(0, /*new Random().nextInt(400) - 200*/ 0*100));
                card.addVelocity(new Point2D.Double(new Random().nextInt(200)-100, new Random().nextInt(200)-100));
            }

            repaint();
        }
    }
}
