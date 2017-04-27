package carracing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Moses Muigai Gitau
 */
public class PlayerVehicle extends Vehicle implements KeyListener, TiltListener {

    private double degrees = 0;

    private static final int FINAL_ANGLE = 20;

    /**
     *
     * @param window
     * @param road
     * @param color
     * @param width
     */
    public PlayerVehicle(MainWindow window, Road road, Color color, int width) {
        super(window, road, color, width);
    }

    @Override
    public void drawVehicle(Graphics2D g) {
        g.rotate(Math.toRadians(degrees), vehicleWidth, vehicleHeight / 2);
        g.drawImage(vehicleIcon.getImage(), vehicleWidth / 2, 0, vehicleWidth, vehicleHeight, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                moveRight();
                break;
            case KeyEvent.VK_LEFT:
                moveLeft();
                break;
            default:
                break;
        }
        increment++;
    }

    private void move(final int x) {
        int _x = x;
        if (x + getWidth() - vehicleWidth / 2 > road.getWidth()) {
            _x = road.getWidth() - getWidth() + vehicleWidth / 2;
            degrees = 0;
        } else if (x + vehicleWidth / 2 < 0) {
            _x = -vehicleWidth / 2;
            degrees = 0;
        }
        setLocation(_x, getY());
    }

    private void moveLeft() {
        degrees -= increment;
        if (degrees < -FINAL_ANGLE) {
            degrees = -FINAL_ANGLE;
        }
        move(getX() - increment);
    }

    private void moveRight() {
        degrees += increment;
        if (degrees > FINAL_ANGLE) {
            degrees = FINAL_ANGLE;
        }
        move(getX() + increment);
    }

    private void resetDegrees() {
        Thread release = new Thread() {
            @Override
            public void run() {
                if (degrees > 0) {
                    while (degrees > 0) {
                        degrees--;
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                        }
                    }
                } else {
                    while (degrees < 0) {
                        degrees++;
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        };
        release.start();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        resetDegrees();
        increment = 3;
    }

    public void setAngle(double angle) {
        this.degrees = angle;
    }

    private double prevY = 0;
    private int direction;
    private static final int INCREASING = 1;
    private static final int DECREASING = 2;
    private static final int STAGNANT = 3;

    double value = getX();

    @Override
    public void angleChanged(double x, double y, double z) {
        double angle = y * 4;
        if (angle > 20) {
            angle = 20;
        } else if (angle < -20) {
            angle = -20;
        }
        degrees = angle;
        repaint();
    }
}
