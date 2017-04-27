package carracing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;

/**
 *
 * @author Moses Muigai Gitau
 */
public class GreenField extends MotionComponent {

    public static final int RIGHT = 1;
    public static final int LEFT = 2;

    private int side;

    private final ImageIcon[] leftIcons = new ImageIcon[]{
        new ImageIcon(getClass().getResource("../images/side_left_1.png")),
        new ImageIcon(getClass().getResource("../images/side_left_2.png")),
        new ImageIcon(getClass().getResource("../images/side_left_3.png"))
    };
    private final ImageIcon[] rightIcons = new ImageIcon[]{
        new ImageIcon(getClass().getResource("../images/side_right_1.png")),
        new ImageIcon(getClass().getResource("../images/side_right_2.png")),
        new ImageIcon(getClass().getResource("../images/side_right_3.png"))
    };

    public GreenField(MainWindow window, int width, int side) {
        super(window);
        this.side = side;
        setSize(width, window.getHeight());
        setPreferredSize(new Dimension(width, window.getHeight()));
        createBlocks();
    }

    public int getSide() {
        return side;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        for (Block block : blocks) {
            block.setSize(width, Tools.maintainAspectRatio(width, block.icon));
            block.setPreferredSize(getSize());
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        for (Block block : blocks) {
            block.setSize(width, Tools.maintainAspectRatio(width, block.icon));
            block.setPreferredSize(getSize());
        }
    }

    @Override
    public void createBlocks() {
        ImageIcon[] icons;
        if (side == LEFT) {
            icons = leftIcons;
        } else {
            icons = rightIcons;
        }
        ArrayList<ImageIcon> iconsList = new ArrayList();
        for (ImageIcon icon : icons) {
            iconsList.add(icon);
        }
        Collections.shuffle(iconsList);
        for (ImageIcon icon : iconsList) {
            Block block = new Block() {
                @Override
                public void paint(Graphics g) {
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            block.icon = icon;
            block.setSize(getWidth(), Tools.maintainAspectRatio(getWidth(), icon.getIconWidth(), icon.getIconHeight()));
            block.setPreferredSize(getSize());
            addBlock(block);
        }
    }

}
