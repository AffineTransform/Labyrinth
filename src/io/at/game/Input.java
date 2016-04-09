package io.at.game;

import io.at.game.objects.CharacterConstants;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.BitSet;

/**
 * Keyboard and mouse listener class.
 */
class Input implements MouseListener, MouseMotionListener, KeyListener, Runnable {
    private boolean running;
    private int mouseX, mouseY;
    private BitSet keys;

    /**
     * Constructor
     */
    Input() {
        running = true;
        mouseX = 0;
        mouseY = 0;
        keys = new BitSet();
    }

    public void keyTyped(final KeyEvent e) {}
    public void keyPressed(final KeyEvent e) {
        keys.set(e.getKeyCode(), true);
    }
    public void keyReleased(final KeyEvent e) {
        keys.set(e.getKeyCode(), false);
    }

    public void mouseClicked(final MouseEvent e) {}
    public void mousePressed(final MouseEvent e) {}
    public void mouseReleased(final MouseEvent e) {}

    public void mouseEntered(final MouseEvent e) {}
    public void mouseExited(final MouseEvent e) {}
    public void mouseDragged(final MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
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
     * Input processing thread method.
     */
    public void run() {
        final int delay = 1000 / 60;

        while (running) {
            if (keys.get(KeyEvent.VK_ESCAPE)) {
                Game.switchPause();
            }

            try {

                if (Game.isPaused()) {
                    //TODO

                //Accelerating y-axis
                } else if (keys.get(KeyEvent.VK_W) || keys.get(KeyEvent.VK_UP)) {
                    Game.player().setAcceleration(Game.player().getAccelerationX(), -CharacterConstants.CHARACTER_RUN_ACCELERATION);
                } else if (keys.get(KeyEvent.VK_S) || keys.get(KeyEvent.VK_DOWN)) {
                    Game.player().setAcceleration(Game.player().getAccelerationX(), CharacterConstants.CHARACTER_RUN_ACCELERATION);

                //Breaking y-axis
                } else if (Math.abs(Game.player().getSpeedY()) < CharacterConstants.CHARACTER_RUN_BREAKING) {
                    Game.player().setAcceleration(Game.player().getAccelerationX(), 0);
                    Game.player().setSpeed(Game.player().getSpeedX(), 0);
                } else if (Game.player().getSpeedY() > 0) {
                    Game.player().setAcceleration(Game.player().getAccelerationX(), -CharacterConstants.CHARACTER_RUN_BREAKING);
                } else if (Game.player().getSpeedY() < 0) {
                    Game.player().setAcceleration(Game.player().getAccelerationX(), CharacterConstants.CHARACTER_RUN_BREAKING);
                } else if (Game.player().getSpeedY() == 0) {
                    Game.player().setAcceleration(Game.player().getAccelerationX(), 0);
                }

                //Accelerating x-axis
                if (keys.get(KeyEvent.VK_A) || keys.get(KeyEvent.VK_LEFT)) {
                    Game.player().setAcceleration(-CharacterConstants.CHARACTER_RUN_ACCELERATION, Game.player().getAccelerationY());
                } else if (keys.get(KeyEvent.VK_D) || keys.get(KeyEvent.VK_RIGHT)) {
                    Game.player().setAcceleration(CharacterConstants.CHARACTER_RUN_ACCELERATION, Game.player().getAccelerationY());

                //Breaking x-axis
                } else if (Math.abs(Game.player().getSpeedX()) < CharacterConstants.CHARACTER_RUN_BREAKING) {
                    Game.player().setAcceleration(0, Game.player().getAccelerationY());
                    Game.player().setSpeed(0, Game.player().getSpeedY());
                } else if (Game.player().getSpeedX() > 0) {
                    Game.player().setAcceleration(-CharacterConstants.CHARACTER_RUN_BREAKING, Game.player().getAccelerationY());
                } else if (Game.player().getSpeedX() < 0) {
                    Game.player().setAcceleration(CharacterConstants.CHARACTER_RUN_BREAKING, Game.player().getAccelerationY());
                } else if (Game.player().getSpeedX() == 0) {
                    Game.player().setAcceleration(0, Game.player().getAccelerationY());
                }

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

            } catch (NullPointerException e) {
                Game.stop(Game.PLAYER_IS_NULL_ERROR);
            }

            //Delay
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Game.stop(Game.THREAD_WAS_INTERRUPTED_ERROR);
            }
        }
    }
}
