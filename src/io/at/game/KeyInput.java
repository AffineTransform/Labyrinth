package io.at.game;

import io.at.game.ErrorCodes;
import io.at.game.Game;
import io.at.game.objects.CharacterConstants;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.BitSet;

/**
 * Keyboard listener class.
 */
class KeyInput implements KeyListener, Runnable {
    private boolean running;
    private BitSet keys;

    /**
     * Constructor.
     */
    KeyInput() {
        running = true;
        keys = new BitSet();
    }

    @Override
    public void keyTyped(final KeyEvent e) {}
    @Override
    public void keyPressed(final KeyEvent e) {
        keys.set(e.getKeyCode(), true);
    }
    @Override
    public void keyReleased(final KeyEvent e) {
        keys.set(e.getKeyCode(), false);
    }

    /**
     * KeyInput processing thread method.
     */
    public void run() {
        final int delay = 1000 / 60;

        while (running) {
            if (keys.get(KeyEvent.VK_ESCAPE)) {
                Game.switchPause();
            }

            try {
                if (!Game.isPaused()) {

                    //Accelerating y-axis
                    if (keys.get(KeyEvent.VK_W) || keys.get(KeyEvent.VK_UP)) {
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
                }
            } catch (NullPointerException e) {
                Game.stop(ErrorCodes.PLAYER_IS_NULL_ERROR);
            }

            //Delay
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Game.stop(ErrorCodes.THREAD_WAS_INTERRUPTED_ERROR);
            }
        }
    }
}
