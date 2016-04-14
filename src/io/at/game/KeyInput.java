package io.at.game;

import io.at.game.objects.ObjectsConstants;

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
            try {
                //Hero sprint
                if (keys.get(KeyEvent.VK_SHIFT)) {
                    Game.player().setMaxMovingSpeed(ObjectsConstants.CHARACTER_RUN_MAX_SPEED);
                } else {
                    Game.player().setMaxMovingSpeed(ObjectsConstants.CHARACTER_WALKING_MAX_SPEED);
                }

                if (Game.player().getZ() == ObjectsConstants.GROUND_LEVEL && keys.get(KeyEvent.VK_SPACE)) {
                    Game.player().setSpeedZ(ObjectsConstants.CHARACTER_JUMP_SPEED);
                }

                //Accelerating y-axis
                if (keys.get(KeyEvent.VK_W) || keys.get(KeyEvent.VK_UP)) {
                    Game.player().setAccelerationY(-ObjectsConstants.CHARACTER_RUN_ACCELERATION);
                } else if (keys.get(KeyEvent.VK_S) || keys.get(KeyEvent.VK_DOWN)) {
                    Game.player().setAccelerationY(ObjectsConstants.CHARACTER_RUN_ACCELERATION);

                //Braking y-axis
                } else if (Math.abs(Game.player().getSpeedY()) < ObjectsConstants.CHARACTER_RUN_BRAKING) {
                    Game.player().setAccelerationY(0);
                    Game.player().setSpeedY(0);
                } else if (Game.player().getSpeedY() > 0) {
                    Game.player().setAccelerationY(-ObjectsConstants.CHARACTER_RUN_BRAKING);
                } else if (Game.player().getSpeedY() < 0) {
                    Game.player().setAccelerationY(ObjectsConstants.CHARACTER_RUN_BRAKING);
                } else if (Game.player().getSpeedY() == 0) {
                    Game.player().setAccelerationY(0);
                }

                //Accelerating x-axis
                if (keys.get(KeyEvent.VK_A) || keys.get(KeyEvent.VK_LEFT)) {
                    Game.player().setAccelerationX(-ObjectsConstants.CHARACTER_RUN_ACCELERATION);
                } else if (keys.get(KeyEvent.VK_D) || keys.get(KeyEvent.VK_RIGHT)) {
                    Game.player().setAccelerationX(ObjectsConstants.CHARACTER_RUN_ACCELERATION);

                //Braking x-axis
                } else if (Math.abs(Game.player().getSpeedX()) < ObjectsConstants.CHARACTER_RUN_BRAKING) {
                    Game.player().setAccelerationX(0);
                    Game.player().setSpeedX(0);
                } else if (Game.player().getSpeedX() > 0) {
                    Game.player().setAccelerationX(-ObjectsConstants.CHARACTER_RUN_BRAKING);
                } else if (Game.player().getSpeedX() < 0) {
                    Game.player().setAccelerationX(ObjectsConstants.CHARACTER_RUN_BRAKING);
                } else if (Game.player().getSpeedX() == 0) {
                    Game.player().setAccelerationX(0);
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
