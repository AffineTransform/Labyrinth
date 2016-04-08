package io.at.game.objects;

import io.at.game.Game;

/**
 * This class is needed for calculation of game objects position and dynamics.
 */
public class DynamicsCalculator {

    /**
     * Calculate game objects position.
     * @param objects - array of game objects.
     */
    public void calculate(final GameObject[] objects) {
        try {
            for (GameObject o : objects) {
                o.move();
                o.accelerate();
            }
        } catch (NullPointerException e) {
            Game.stop(Game.OBJECTS_ARRAY_IS_NULL_ERROR);
        }
    }
}
