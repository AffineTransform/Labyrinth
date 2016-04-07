package io.at.game.visual;

import io.at.game.Game;
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
     * @param objects - array of game objects.
     */
    public void render(final GameObject[] objects) {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());

        try {
            /*
            for (GameObject o : objects) {
                o.draw(g);
            }
            */

            g.fillRect(Game.player().getX(), Game.player().getY(), 100, 100);
        } catch (NullPointerException e) {
            Game.stop(Game.OBJECTS_ARRAY_IS_NULL_ERROR);
        }

        g.dispose();
        bs.show();
    }
}