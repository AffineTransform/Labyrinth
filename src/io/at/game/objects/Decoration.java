package io.at.game.objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Game decoration class.
 */
public class Decoration {
    private int x, y;
    private double angleRadians;
    private Image sprite;

    /**
     * Decoration constructor.
     *
     * Object coordinates:
     * @param x - from left to right axis.
     * @param y - from up to down axis.
     *
     * Other parameters:
     * @param spritePath - sprite directory path.
     * @throws IOException
     */
    public Decoration(final int x, final int y, final String spritePath) throws IOException {
        this.x = x;
        this.y = y;
        this.angleRadians = 0;
        sprite = ImageIO.read(new File(spritePath));
    }

    /**
     * Draw decoration on screen.
     * @param g - graphics object.
     */
    public void draw(final Graphics2D g) {

        g.drawImage(sprite, x , y, null);
    }
}
