package io.at.game.objects;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

/**
 * Game object class.
 */
public class GameObject {
    private String name;
    private int x, y, width, height;
    private double speedX, speedY, accelerationX, accelerationY, angleRadians;
    private Image sprite;

    /**
     * Constructor.
     * @param name - object name.
     * @param x - object coordinate.
     * @param y - object coordinate.
     * @param spritePath - image file directory path.
     * @throws IOException
     */
    public GameObject(final String name, final int x, final int y, final String spritePath) throws IOException {
        this.name = name;
        this.x = x;
        this.y = y;
        speedX = 0;
        speedY = 0;
        accelerationX = 0;
        accelerationY = 0;
        angleRadians = 0;
        /*
        this.sprite = ImageIO.read(new File(spritePath));
        if (sprite != null) {
            width = sprite.getWidth(null);
            height = sprite.getHeight(null);
        }*/
    }

    String getName() {
        return name;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public double getSpeedX() {
        return speedX;
    }
    public double getSpeedY() {
        return speedY;
    }
    public double getAccelerationX() {
        return accelerationX;
    }
    public double getAccelerationY() {
        return accelerationY;
    }

    /**
     * @param vx - x speed.
     * @param vy - y speed.
     */
    public void setSpeed(final double vx, final double vy) {
        speedX = vx;
        speedY = vy;
    }
    /**
     * @param ax - x acceleration.
     * @param ay - y acceleration.
     */
    public void setAcceleration(final double ax, final double ay) {
        accelerationX = ax;
        accelerationY = ay;
    }

    /**
     * @param path - image directory path.
     * @throws IOException
     */
    public void setImage(final String path) throws IOException {
        sprite = ImageIO.read(new File(path));
    }

    /**
     * Set sprite angle of sprite rotation.
     * @param radians - angle which the sprite is rotated relative y-axis clockwise.
     */
    public void setAngle(final double radians) {
        this.angleRadians = radians;
    }

    /**
     * Move object
     */
    void move() {
        x += speedX;
        y += speedY;
    }

    /**
     * Accelerate object.
     */
    void accelerate() {
        if (Math.abs(speedX) < CharacterConstants.CHARACTER_RUN_MAX_SPEED) {
            speedX += accelerationX;
        } else if (speedX > CharacterConstants.CHARACTER_RUN_MAX_SPEED) {
            speedX = CharacterConstants.CHARACTER_RUN_MAX_SPEED;
        } else if (speedX < -CharacterConstants.CHARACTER_RUN_MAX_SPEED) {
            speedX = -CharacterConstants.CHARACTER_RUN_MAX_SPEED;
        }

        if (Math.abs(speedY) < CharacterConstants.CHARACTER_RUN_MAX_SPEED) {
            speedY += accelerationY;
        } else if (speedY > CharacterConstants.CHARACTER_RUN_MAX_SPEED) {
            speedY = CharacterConstants.CHARACTER_RUN_MAX_SPEED;
        } else if (speedY < -CharacterConstants.CHARACTER_RUN_MAX_SPEED) {
            speedY = -CharacterConstants.CHARACTER_RUN_MAX_SPEED;
        }
    }

    /**
     * Drawing object sprite on the screen.
     * @param g - Graphics object.
     */
    public void draw(final Graphics2D g) {
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(angleRadians, width / 2 + 1, height / 2 + 1);
        g.drawImage(sprite, at, null);
    }
}
