package me.miles.matthew.SolitaireAnim;

import javax.swing.JFrame;

public class Runner {
	public static void main(String[] args) {
        new Runner();
    }

    public Runner() {
        // create jframe
        JFrame frame = new JFrame("Solitaire");

        // create animation
        Animation anim = new Animation();
        frame.add(anim);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setIconImage(Card.getRandomImage());
    }
}
