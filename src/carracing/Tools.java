package carracing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author Moses Muigai Gitau
 */
public class Tools {

    public static BufferedImage createImage(JComponent component) {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        component.paint(g);
        g.dispose();
        return image;
    }

    public static int maintainAspectRatio(double newWidth, int width, int height) {
        double percentage = newWidth * 100 / width;
        return (int) (percentage * height / 100);
    }

    public static int maintainAspectRatio(double newWidth, ImageIcon image) {
        return maintainAspectRatio(newWidth, image.getIconWidth(), image.getIconHeight());
    }
}
