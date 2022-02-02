package me.miles.matthew.SolitaireAnim;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class Card {
    private Point2D.Double position;
    private Point2D.Double velocity;
    private Point2D.Double acceleration;
    private Dimension size;
    private Double coRest;
    private BufferedImage texture;
    private static final String[] Cards = {
        "2_of_clubs.png",
        "2_of_diamonds.png",
        "2_of_hearts.png",
        "2_of_spades.png",
        "3_of_clubs.png",
        "3_of_diamonds.png",
        "3_of_hearts.png",
        "3_of_spades.png",
        "4_of_clubs.png",
        "4_of_diamonds.png",
        "4_of_hearts.png",
        "4_of_spades.png",
        "5_of_clubs.png",
        "5_of_diamonds.png",
        "5_of_hearts.png",
        "5_of_spades.png",
        "queen_of_clubs.png",
        "queen_of_diamonds.png",
        "queen_of_hearts.png",
        "queen_of_spades.png",
        "red_joker.png"
    };

    public Card(double x, double y, Dimension size, double coefficientOfRestitution) {
        position = new Point2D.Double(x, y);
        velocity = new Point2D.Double(0, 0);
        acceleration = new Point2D.Double(0, 0);
        this.size = size;
        this.coRest = coefficientOfRestitution;
        this.texture = getRandomImage();
    }

    public static BufferedImage getRandomImage() {
        // returns a random image from the Images folder
        Random rand = new Random();
        //URL res = Card.class.getResource("Images"); // located in /src/.../Images
        //File f = new File(res.getFile());
        // File f = null;
        try {
            //f = new File(Card.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            InputStream iconstream = Card.class.getResourceAsStream("res/Images/"+Cards[rand.nextInt(Cards.length)]);
            BufferedImage img = ImageIO.read(iconstream);
            return img;
        } catch (Exception e) {
            e.printStackTrace();
            BufferedImage img = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
            img.setRGB(0, 1, 0xFF00FF);
            img.setRGB(1, 0, 0xFF00FF);
            return img;
        }

        // if (f == null || !f.exists()) {
        // 	return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        // }
        // File[] files = f.listFiles();
        // int random = rand.nextInt(files.length);
        // BufferedImage img = null;
        // try {
        //     img = ImageIO.read(files[random]);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // return img;
    }

    /**
     * Updates the card's physical state.
     * @param timePassed The time passed since the last frame in seconds.
     * @param boundaries The boundaries of the panel.
     */
    public void update(Double timePassed, Dimension boundaries) {
        velocity.x += acceleration.x*timePassed;
        velocity.y += acceleration.y*timePassed;
        position.x += velocity.x*timePassed;
        position.y += velocity.y*timePassed;

        // if (position.x - size.width/2 < 0) {
        //     position.x = size.width/2;
        //     velocity.x *= -1;
        // }

        // if (position.x + size.width/2 > boundaries.width) {
        //     position.x = boundaries.width - size.width/2;
        //     velocity.x *= -1;
        // }

        if (position.y - size.height/2 < 0) {
            position.y = size.height/2;
            velocity.y *= -coRest;
        }

        if (position.y + size.height/2 > boundaries.height) {
            position.y = boundaries.height - size.height/2;
            velocity.y *= -coRest;
        }

        if (velocity.x == 0) {
            velocity.x = 0.5;
        }

        // this.texture = getRandomImage(); 
    }

    public void applyForce(Point2D.Double force) {
        acceleration.x += force.x;
        acceleration.y += force.y;
    }

    public void addVelocity(Point2D.Double velocity) {
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;
    }

    public double getX() {
        return position.x;
    }

    public double getY() {
        return position.y;
    }

    public int getWidth() {
        return size.width;
    }

    public int getHeight() {
        return size.height;
    }

    public void draw(Graphics2D g2) {
        
        int xOrigin = (int) Math.round(position.x-(size.getWidth()/2));
        int yOrigin = (int) Math.round(position.y-(size.getHeight()/2));
        // g2.setColor(Color.BLACK);
        // g2.fillRect(xOrigin, yOrigin, size.width, size.height);
        // g2.setColor(Color.WHITE);
        // g2.fillRect(xOrigin+1, yOrigin+1, size.width-2, size.height-2);
        g2.drawImage(texture.getScaledInstance(size.width, size.height, 0), xOrigin, yOrigin, null);
    }
}
