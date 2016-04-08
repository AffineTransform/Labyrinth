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
    private Image sprite;

    /**
     * Decoration constructor.
     * @param x - coordinate.
     * @param y - coordinate.
     * @param spritePath - sprite directory path.
     * @throws IOException
     */
    public Decoration(final int x, final int y, final String spritePath) throws IOException {
        this.x = x;
        this.y = y;
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
