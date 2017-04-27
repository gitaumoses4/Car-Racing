package carracing;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Moses Muigai Gitau
 */
public class Road extends MotionComponent implements AutomaticVehicleListener {

    private final Lane up;
    private final Lane down;
    private final LaneSeparator separator;
    private Server server;

    private ArrayList<Vehicle> automaticVehicles = new ArrayList();
    private PlayerVehicle player;

    private final AutomaticVehicleGenerator generator;

    public Road(MainWindow mainWindow, int roadWidth) {
        super(mainWindow);
        generator = new AutomaticVehicleGenerator(mainWindow, this, 2000);
        generator.addAutomaticVehicleListener(this);
        Thread generatorThread = new Thread(generator);
        generatorThread.start();

        setSize(roadWidth, mainWindow.getHeight());
        setPreferredSize(getSize());
        up = new Lane(mainWindow, this, roadWidth / 2);
        player = new PlayerVehicle(mainWindow, this, PlayerVehicle.CYAN, roadWidth / 4);

        mainWindow.addKeyListener(player);

        down = new Lane(mainWindow, this, roadWidth / 2);
        separator = new LaneSeparator(mainWindow, this, up, down, 100, 10);
        try {
            server = new Server();
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.start();
        server.addAngleChangeListener(player);
    }

    @Override
    public void start(int speed, int direction, int threshold) {
        separator.start(speed, direction, threshold);
    }

    @Override
    public void paint(Graphics2D g) {
        g.drawImage(Tools.createImage(up), 0, 0, up.getWidth(), window.getHeight(), this);
        g.drawImage(Tools.createImage(down), up.getWidth(), 0, down.getWidth(), window.getHeight(), this);
        g.drawImage(Tools.createImage(separator), up.getWidth() + (separator.getWidth()) / 2, 0, separator.getWidth(), window.getHeight(), this);
        for (Vehicle vehicle : automaticVehicles) {
            g.drawImage(Tools.createImage(vehicle), vehicle.getX(), vehicle.getY(), vehicle.getWidth(), vehicle.getHeight(), vehicle);
        }
        removeVehicles();
        g.drawImage(Tools.createImage(player), player.getX(), player.getY(), player.getWidth(), player.getHeight(), player);
    }

    private void removeVehicles() {
        ArrayList<Vehicle> toRemove = new ArrayList();
        for (Vehicle v : automaticVehicles) {
            if (v.getY() > getHeight()) {
                toRemove.add(v);
            }
        }
        automaticVehicles.removeAll(toRemove);
    }

    @Override
    public void startMotion() {
        separator.startMotion();
    }

    @Override
    public void createBlocks() {
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (up != null) {
            up.setSize(width / 2, height);
            down.setSize(width / 2, height);
            for (Vehicle vehicle : automaticVehicles) {
                vehicle.setSize(width / 3, height);
            }
            player.setSize(width / 3, height);
            player.setLocation(player.getX(), (height - player.getHeight()));
        }
    }

    public void addVehicle(Vehicle vehicle) {
        this.automaticVehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        this.automaticVehicles.remove(vehicle);
    }

    @Override
    public void vehicleAddedOnRoad(AutomaticVehicle vehicle) {
        addVehicle(vehicle);
        vehicle.startMotion(5, 2);
    }

    public int getCarWidth() {
        return getWidth() / 3;
    }

    public Point generateVehicleLocation(int direction, int height) {
        int x;
        int y = -10 - height;
        if (direction == AutomaticVehicle.MOVING_DOWN) {
            x = 0;
        } else {
            int half = getWidth() / 2;
            x = getCarWidth() + ((half - getCarWidth()) / 2);
        }
        return new Point(x, y);
    }
}
