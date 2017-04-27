package carracing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author Moses Muigai Gitau
 */
public abstract class MComponent extends JComponent {

    protected MainWindow window;

    public MComponent(MainWindow window) {
        this.window = window;
    }

    public MComponent() {
    }

    @Override
    public void paint(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        super.paint(gr);
        paint(g);
    }

    public abstract void paint(Graphics2D g);

}
