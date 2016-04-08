package io.at.game.visual;

import io.at.game.Game;
import io.at.game.objects.Decoration;
import io.at.game.objects.GameObject;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

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
     * @param decorations - array of game decoration.
     * @param objects - array of game objects.
     */
    public void render(final Decoration[] decorations, final GameObject[] objects) {
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
                Game.stop(Game.DECORATIONS_ARRAY_IS_NULL_ERROR);
            } else if (objects == null) {
                Game.stop(Game.OBJECTS_ARRAY_IS_NULL_ERROR);
            }
        }

        g.dispose();
        bs.show();
    }
}