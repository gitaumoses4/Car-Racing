package carracing;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author Moses Muigai Gitau
 */
public abstract class MotionComponent extends MComponent {

    protected ArrayList<Block> blocks = new ArrayList();
    private boolean moveBlocks = false;
    public static final int MOVE_UP = 1;
    public static final int MOVE_DOWN = 2;

    public MotionComponent(MainWindow window) {
        super(window);
    }

    public MotionComponent() {

    }

    public abstract void createBlocks();

    @Override
    public void paint(Graphics2D g) {
        for (Block block : blocks) {
            Image image = Tools.createImage(block);
            g.drawImage(image, block.getX(), block.getY(), block.getWidth(), block.getHeight(), this);
        }
    }

    public void start(int speed, int direction, int threshold) {
        Timer moveTimer = new Timer();
        moveTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (moveBlocks) {
                    int index = 0;
                    for (Block block : blocks) {
                        Point location = block.getLocation();
                        Point newLocation;
                        if (direction == MOVE_UP) {
                            newLocation = new Point(location.x, location.y - threshold);
                            if (newLocation.getY() + block.getHeight() < 0) {
                                newLocation = new Point(location.x, getNext(index, direction).getY() + getNext(index, direction).getHeight());
                            }
                        } else {
                            newLocation = new Point(location.x, location.y + threshold);
                            if (newLocation.getY() > window.getHeight()) {
                                newLocation = new Point(location.x, getNext(index, direction).getY() - block.getHeight());
                            }
                        }
                        block.setLocation(newLocation);
                        index++;
                    }
                    redraw();
                }
            }
        }, 0, speed);
    }

    public void redraw() {
        Thread paintThread = new Thread() {
            @Override
            public void run() {
                repaint();
            }
        };
        paintThread.start();
    }

    public void addBlock(Block block) {
        if (!blocks.isEmpty()) {
            Block before = blocks.get(blocks.size() - 1);
            block.setLocation(new Point(before.getX(), before.getY() + before.getHeight()));
        } else {
            block.setLocation(0, -block.getHeight());
        }
        this.blocks.add(block);
    }

    public void removeBlock(Block block) {
        this.blocks.remove(block);
    }

    public static class Block extends JComponent {

        public ImageIcon icon = null;
    }

    public void stopMotion() {
        this.moveBlocks = false;
    }

    public void startMotion() {
        this.moveBlocks = true;
    }

    private Block getNext(int i, int direction) {
        if (direction == MOVE_DOWN) {
            if (i == blocks.size() - 1) {
                return blocks.get(0);
            } else {
                return blocks.get(i + 1);
            }
        } else {
            if (i == 0) {
                return blocks.get(blocks.size() - 1);
            } else {
                return blocks.get(i - 1);
            }
        }
    }

}
