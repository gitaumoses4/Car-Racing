package carracing;

import java.awt.EventQueue;

/**
 *
 * @author Moses Muigai Gitau
 */
public class CarRacing {

    public static void main(String args[]) {
        EventQueue.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}
