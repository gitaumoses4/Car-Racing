package carracing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author Moses Muigai Gitau
 */
public class LaneSeparator extends MotionComponent {

    private Color color = Color.white;
    private final Road road;
    private Lane right;
    private Lane left;
    private final int length;
    private final int separatorWidth;

    public LaneSeparator(MainWindow window, Road road, Lane right, Lane left, int length, int width) {
        super(window);
        this.road = road;
        this.right = right;
        this.left = left;
        this.length = length;
        this.separatorWidth = width;
        setSize(separatorWidth, window.getHeight());
        createBlocks();
    }

    @Override
    public void createBlocks() {
        for (int i = 0; i < 2; i++) {
            Block block = new Block() {
                @Override
                public void paint(Graphics gr) {
                    Graphics2D g = (Graphics2D) gr;
                    Stroke dashed = new BasicStroke(separatorWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{length}, 0);
                    g.setStroke(dashed);
                    g.drawLine(0, 0, 0, window.getHeight() + 10);
                }
            };
            block.setSize(separatorWidth, window.getHeight() + 10);
            block.setPreferredSize(getSize());
            addBlock(block);
        }
    }

    public void redraw() {
        road.repaint();
    }
}
