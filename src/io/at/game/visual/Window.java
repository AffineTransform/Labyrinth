package io.at.game.visual;

import javax.swing.JFrame;

import io.at.game.ErrorCodes;
import io.at.game.Game;

/**
 * Window class.
 */
public class Window extends JFrame {

    /**
     * Window constructor.
     * @param screen - screen for drawing on window.
     * @param title - window title.
     */
    public Window(final Screen screen, final String title) {
        if (screen == null) {
            Game.stop(ErrorCodes.SCREEN_IS_NULL_ERROR);
        } else {
            add(screen);
            setSize(screen.getSize());
            setLocationRelativeTo(null);
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setTitle(title);
            setVisible(true);
        }
    }
}
