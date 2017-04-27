package carracing;

import java.util.ArrayList;

/**
 *
 * @author Moses Muigai Gitau
 */
public class AutomaticVehicleGenerator implements Runnable {

    private final ArrayList<AutomaticVehicleListener> listeners = new ArrayList();
    private boolean generate = true;
    private final MainWindow mainWindow;
    private final Road road;

    private int rate = 1000;

    /**
     *
     * @param mainWindow
     * @param road
     * @param rate
     */
    public AutomaticVehicleGenerator(MainWindow mainWindow, Road road, int rate) {
        this.mainWindow = mainWindow;
        this.road = road;
        this.rate = rate;
    }

    public void stopGenerating() {
        generate = false;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    @Override
    public void run() {
        while (true) {
            if (generate) {
                AutomaticVehicle vehicle = new AutomaticVehicle(mainWindow, road, Vehicle.RANDOM, road.getCarWidth(), AutomaticVehicle.RANDOM_DIRECTION);
                vehicle.setLocation(road.generateVehicleLocation(vehicle.getDirection(), vehicle.getHeight()));
                fireAutomaticVehicleAddedToRoad(vehicle);
            }
            try {
                Thread.sleep(rate);
            } catch (Exception e) {
            }
        }
    }

    public void addAutomaticVehicleListener(AutomaticVehicleListener l) {
        this.listeners.add(l);
    }

    public void removeAutomaticVehicleListener(AutomaticVehicleListener l) {
        this.listeners.remove(l);
    }

    public void fireAutomaticVehicleAddedToRoad(AutomaticVehicle vehicle) {
        listeners.stream().forEach((l) -> {
            l.vehicleAddedOnRoad(vehicle);
        });
    }
}
