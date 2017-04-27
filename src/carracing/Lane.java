package carracing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Moses Muigai Gitau
 */
public class Lane extends MotionComponent {

    private Color laneColor = Color.gray;
    private int laneWidth;
    private final Road road;

    public Lane(MainWindow window, Road road, int width) {
        super(window);
        setSize(width, window.getHeight());
        setPreferredSize(getSize());
        this.laneWidth = width;
        createBlocks();
        this.road = road;
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (width != 0) {
            this.laneWidth = width;
            for (Block block : blocks) {
                block.repaint();
                block.setSize(width, height);
            }
        }
    }

    @Override
    public void createBlocks() {
        for (int i = 0; i < 2; i++) {
            Block block = new Block() {
                @Override
                public void paint(Graphics g) {
                    g.setColor(laneColor);
                    g.fillRect(0, 0, laneWidth, Lane.this.window.getHeight() + 10);
                }
            };
            block.setSize(laneWidth, window.getHeight());
            block.setPreferredSize(block.getSize());
            addBlock(block);
        }
    }
}
