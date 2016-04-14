package io.at.game.objects;

import io.at.game.ErrorCodes;
import io.at.game.Game;

import java.util.Vector;

/**
 * Class is needed for game objects position and dynamics calculation.
 */
public class DynamicsCalculator {

    /**
     * Calculate game objects position.
     * @param objects - vector of game objects.
     */
    public void calculate(final Vector<GameObject> objects) {
        try {
            for (GameObject o : objects) {
                o.move();
                o.accelerate();

                if (o.getZ() > ObjectsConstants.GROUND_LEVEL) {
                    o.setAccelerationZ(-ObjectsConstants.CHARACTER_FALLING_ACCELERATION);
                } else {
                    o.setAccelerationZ(0);
                }

                if (o.getZ() <= ObjectsConstants.GROUND_LEVEL) {
                    o.setAccelerationZ(0);
                    o.setSpeedZ(0);
                    o.setZ(ObjectsConstants.GROUND_LEVEL);
                }
            }
        } catch (NullPointerException e) {
            Game.stop(ErrorCodes.OBJECTS_ARRAY_IS_NULL_ERROR);
        }
    }
}
