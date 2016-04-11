package io.at.game.visual;

import io.at.game.ErrorCodes;
import io.at.game.Game;
import io.at.game.objects.Decoration;
import io.at.game.objects.GameObject;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.util.Vector;

/**
 * Screen class needed for drawing an image on window.
 */
public class Screen extends Canvas {

    /**
     *
     * @param width - screen width.
     * @param height - screen height.
     */
    public Screen(final int width, final int height) {
        setSize(width, height);
    }

    /**
     * Render image on screen.
     * @param mouseX - mouse coordinate.
     * @param mouseY - mouse coordinate.
     * @param decorations - array of game decoration.
     * @param objects - array of game objects.
     */
    public void render(final int mouseX, final int mouseY, final Vector<Decoration> decorations, final Vector<GameObject> objects) {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());

        try {
            for (Decoration d : decorations) {
                d.draw(g);
            }
            for (GameObject o : objects) {
                o.draw(g);
            }
        } catch (NullPointerException e) {
            if (decorations == null) {
                Game.stop(ErrorCodes.DECORATIONS_ARRAY_IS_NULL_ERROR);
            } else if (objects == null) {
                Game.stop(ErrorCodes.OBJECTS_ARRAY_IS_NULL_ERROR);
            }
        }
        g.setColor(Color.darkGray);
        g.fillOval(mouseX - 2, mouseY - 2, 5, 5);
        g.setColor(Color.black);
        g.drawOval(mouseX - 2, mouseY - 2, 5, 5);

        g.dispose();
        bs.show();
    }
}