package carracing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 *
 * @author Moses Muigai Gitau
 */
public class Vehicle extends MComponent {

    public static final Color RED = Color.red;
    public static final Color BLUE = Color.blue;
    public static final Color GREEN = Color.green;
    public static final Color CYAN = Color.cyan;
    public static final Color WHITE = Color.white;
    public static final Color BLACK = Color.black;
    public static final Color YELLOW = Color.yellow;
    public static final Color RANDOM = getRandomColor();

    private Color vehicleColor = Color.red;
    protected ImageIcon vehicleIcon = null;

    protected int vehicleWidth;
    protected int vehicleHeight;

    protected int increment = 3;
    protected int speed = 10;
    protected final Road road;

    /**
     *
     * @param window
     * @param road
     * @param color
     * @param width
     */
    public Vehicle(MainWindow window, Road road, Color color, int width) {
        super(window);
        this.vehicleColor = color;
        createVehicle();
        int height = Tools.maintainAspectRatio(width, vehicleIcon);
        vehicleWidth = width;
        vehicleHeight = height;
        setSize(width, height);
        this.road = road;
    }

    protected int getHypotenuse(int width, int height) {
        return (int) (Math.sqrt((width * width) + (height * height)));
    }

    protected Dimension getHypotenuseDimensios(int width, int height) {
        return new Dimension(getHypotenuse(width, height), getHypotenuse(width, height));
    }

    @Override
    public void paint(Graphics2D g) {
        drawVehicle(g);
    }

    private void createVehicle() {
        String location = "";
        if (vehicleColor.equals(Color.black)) {
            location = "../images/black.png";
        } else if (vehicleColor.equals(Color.blue)) {
            location = "../images/blue.png";
        } else if (vehicleColor.equals(Color.green)) {
            location = "../images/green.png";
        } else if (vehicleColor.equals(Color.cyan)) {
            location = "../images/cyan.png";
        } else if (vehicleColor.equals(Color.white)) {
            location = "../images/white.png";
        } else if (vehicleColor.equals(Color.yellow)) {
            location = "../images/yellow.png";
        } else {
            location = "../images/red.png";
        }
        vehicleIcon = new ImageIcon(getClass().getResource(location));
    }

    public Color getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(Color color) {
        this.vehicleColor = color;
        createVehicle();
    }

    @Override
    public void setSize(int width, int height) {
        height = Tools.maintainAspectRatio(width, vehicleIcon);
        super.setSize(getHypotenuse(width, height), getHypotenuse(width, height));
        vehicleWidth = width;
        vehicleHeight = height;
    }

    protected void drawVehicle(Graphics2D g) {
        g.drawImage(vehicleIcon.getImage(), vehicleWidth / 2, 0, vehicleWidth, vehicleHeight, this);
    }

    protected static Color getRandomColor() {
        Color colors[] = new Color[]{RED, BLUE, GREEN, YELLOW, WHITE, BLACK, BLUE, CYAN};
        int random=(int) (Math.random() * colors.length);
        return colors[random];
    }
}
