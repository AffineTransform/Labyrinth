package io.at.game;

import io.at.game.Game;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/**
 * Mouse listener class.
 */
class MouseInput implements MouseListener, MouseMotionListener, Runnable {
    private boolean running;
    private int mouseX, mouseY;

    /**
     * Constructor.
     */
    MouseInput() {
        mouseX = 0;
        mouseY = 0;
        running = true;
    }

    @Override
    public void mouseClicked(final MouseEvent e) {}
    @Override
    public void mousePressed(final MouseEvent e) {}
    @Override
    public void mouseReleased(final MouseEvent e) {}

    @Override
    public void mouseEntered(final MouseEvent e) {}
    @Override
    public void mouseExited(final MouseEvent e) {}
    @Override
    public void mouseDragged(final MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    @Override
    public void mouseMoved(final MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public int getX() {
        return mouseX;
    }
    public int getY() {
        return mouseY;
    }

    /**
     * Mouse processing thread.
     */
    public void run() {
        final int delay = 1000 / 60;

        while (running) {
            //Rotating character
            int heroCenterX = Game.player().getX() + Game.player().getWidth() / 2 + 1;
            int heroCenterY = Game.player().getY() + Game.player().getHeight() / 2 + 1;

            if (mouseY < heroCenterY && mouseX == heroCenterX) {
                Game.player().setAngle(Math.toRadians(0));
            } else if (mouseY < heroCenterY && mouseX > heroCenterX) {
                Game.player().setAngle(Math.PI / 2 - Math.atan2(
                        Math.abs(mouseY - heroCenterY),
                        Math.abs(mouseX - heroCenterX)
                ));
            } else if (mouseY == heroCenterY && mouseX > heroCenterX) {
                Game.player().setAngle(Math.toRadians(90));
            } else if (mouseY > heroCenterY && mouseX > heroCenterX) {
                Game.player().setAngle(Math.PI / 2 + Math.atan2(
                        Math.abs(mouseY - heroCenterY),
                        Math.abs(mouseX - heroCenterX)
                ));
            } else if (mouseY > heroCenterY && mouseX == heroCenterX) {
                Game.player().setAngle(Math.toRadians(180));
            } else if (mouseY > heroCenterY && mouseX < heroCenterX) {
                Game.player().setAngle(Math.PI * 3 / 2 - Math.atan2(
                        Math.abs(mouseY - heroCenterY),
                        Math.abs(mouseX - heroCenterX)
                ));
            } else if (mouseY == heroCenterY && mouseX < heroCenterX) {
                Game.player().setAngle(Math.toRadians(270));
            } else if (mouseY < heroCenterY && mouseX < heroCenterX) {
                Game.player().setAngle(Math.PI * 3 / 2 + Math.atan2(
                        Math.abs(mouseY - heroCenterY),
                        Math.abs(mouseX - heroCenterX)
                ));
            }
        }
    }
}
