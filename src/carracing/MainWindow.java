package carracing;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/**
 *
 * @author Moses Muigai Gitau
 */
public class MainWindow extends JFrame implements ComponentListener {

    GreenField left;
    GreenField right;
    Road road;
    JLayeredPane layer;

    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        addComponentListener(this);
        layer = this.getLayeredPane();

        left = new GreenField(this, 350, GreenField.LEFT);
        right = new GreenField(this, 350, GreenField.RIGHT);
        try {
            road = new Road(this, 300);
        } catch (Exception e) {
        }

        left.start(5, MotionComponent.MOVE_DOWN, 2);
        right.start(5, MotionComponent.MOVE_DOWN, 2);
        road.start(5, MotionComponent.MOVE_DOWN, 2);

        right.startMotion();
        left.startMotion();
        road.startMotion();

        left.setBounds(0, 0, left.getWidth(), left.getHeight());
        right.setBounds(getWidth() - right.getWidth(), 0, right.getWidth(), right.getHeight());
        road.setBounds(left.getWidth(), 0, road.getWidth(), road.getHeight());

        layer.add(left);
        layer.add(right);
        layer.add(road);

    }

    @Override
    public void componentResized(ComponentEvent e) {
        int w = getWidth() - road.getWidth();

        left.setBounds(0, 0, w / 2, getHeight());
        right.setBounds((getWidth() + road.getWidth()) / 2, 0, w / 2, getHeight());
        road.setBounds(w / 2, 0, road.getWidth(), getHeight());
        road.setSize(road.getWidth(), getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
