package carracing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;

/**
 *
 * @author Moses Muigai Gitau
 */
public class AutomaticVehicle extends Vehicle {

    public static final int MOVING_UP = 1;
    public static final int MOVING_DOWN = 2;
    public static final int RANDOM_DIRECTION = 3;
    private int direction;

    private boolean moving = true;

    public AutomaticVehicle(MainWindow window, Road road, Color color, int width, int direction) {
        super(window, road, color, width);

        if (direction == RANDOM_DIRECTION) {
            direction = createRandomDirection();
        }
        if (direction == MOVING_DOWN) {
            changeDirection();
        }
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void startMotion(int speed, int threshold) {
        try {
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (moving) {
                        if (direction == MOVING_DOWN) {
                            setLocation(getX(), getY() + threshold);
                        } else {
                            setLocation(getX(), getY() + threshold);
                        }
                    }
                }
            }, 0, speed);
        } catch (Exception e) {
        }
    }

    private void changeDirection() {
        BufferedImage image = new BufferedImage(vehicleIcon.getIconWidth(), vehicleIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(vehicleIcon.getImage(), 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();

        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(image.getRGB(i, j), true);
                int height = copy.getHeight();
                copy.setRGB(i, height - j - 1, c.getRGB());
            }
        }
        vehicleIcon = new ImageIcon(copy);
    }

    private int createRandomDirection() {
        int random = (int) (Math.random() * 100);
        return random % 2 == 0 ? MOVING_DOWN : MOVING_UP;
    }
}
