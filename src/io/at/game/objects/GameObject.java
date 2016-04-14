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
    private int x, y, width, height, centerX, centerY, currentSpriteIndex;
    private double z, speedX, speedY, speedZ, maxMovingSpeed, accelerationX, accelerationY, accelerationZ, angleRadians;
    private Image[] sprites;

    private AffineTransform at;

    /**
     * Constructor.
     *
     * Object coordinates:
     * @param x - from left to right axis.
     * @param y - from up to down axis.
     * @param z - from screen to viewer axis.
     *
     * Other parameters:
     * @param name - object name.
     * @param spritePaths - array of sprites directory paths.
     * @throws IOException
     */
    public GameObject(final String name, final int x, final int y, final int z, final String[] spritePaths) throws IOException {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = ObjectsConstants.GROUND_LEVEL;
        this.speedX = 0;
        this.speedY = 0;
        this.speedZ = 0;
        this.accelerationX = 0;
        this.accelerationY = 0;
        this.accelerationZ = 0;
        this.angleRadians = 0;

        this.sprites = new Image[spritePaths.length];
        for (int i = 0; i < sprites.length; i++) {
            this.sprites[i] = ImageIO.read(new File(spritePaths[i]));
        }
        this.width = sprites[0].getWidth(null);
        this.height = sprites[0].getHeight(null);
        this.currentSpriteIndex = 0;
    }

    public String getName() {
        return name;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getCenterX() {
        return centerX;
    }
    public int getCenterY() {
        return centerY;
    }
    public double getSpeedX() {
        return speedX;
    }
    public double getSpeedY() {
        return speedY;
    }
    public double getSpeedZ() {
        return speedZ;
    }
    public double getAccelerationX() {
        return accelerationX;
    }
    public double getAccelerationY() {
        return accelerationY;
    }
    public double getAccelerationZ() {
        return accelerationZ;
    }

    /**
     * @param z - coordinate.
     */
    public void setZ(double z) {
        this.z = z;
    }
    /**
     * @param vx - x-axis speed.
     */
    public void setSpeedX(final double vx) {
        speedX = vx;
    }
    /**
     * @param vy - y-axis speed.
     */
    public void setSpeedY(final double vy) {
        speedY = vy;
    }
    /**
     * @param vz - z-axis speed.
     */
    public void setSpeedZ(final double vz) {
        speedZ = vz;
    }
    /**
     * @param maxMovingSpeed - maximal moving speed modulo.
     */
    public void setMaxMovingSpeed(final double maxMovingSpeed) {
        this.maxMovingSpeed = maxMovingSpeed;
    }
    /**
     * @param ax - x-axis acceleration.
     */
    public void setAccelerationX(final double ax) {
        accelerationX = ax;
    }
    /**
     * @param ay - y-axis acceleration.
     */
    public void setAccelerationY(final double ay) {
        accelerationY = ay;
    }
    /**
     * @param az - z-axis acceleration.
     */
    public void setAccelerationZ(final double az) {
        accelerationZ = az;
    }
    /**
     * Set sprite rotation angle.
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
        z += speedZ;
    }
    /**
     * Accelerate object.
     */
    void accelerate() {
        if (Math.abs(speedX + accelerationX) <= maxMovingSpeed) {
            speedX += accelerationX;
        } else if (speedX > maxMovingSpeed) {
            speedX = maxMovingSpeed;
        } else if (speedX < -maxMovingSpeed) {
            speedX = -maxMovingSpeed;
        }

        if (Math.abs(speedY + accelerationY) <= maxMovingSpeed) {
            speedY += accelerationY;
        } else if (speedY > maxMovingSpeed) {
            speedY = maxMovingSpeed;
        } else if (speedY < -maxMovingSpeed) {
            speedY = -maxMovingSpeed;
        }

        speedZ += accelerationZ;
    }

    /**
     * Switch to the next sprite.
     */
    public void nextSprite() {
        if (currentSpriteIndex == sprites.length - 1) {
            currentSpriteIndex = 0;
        } else {
            currentSpriteIndex++;
        }
    }
    /**
     * Draw object sprite on the screen.
     * @param g - Graphics object.
     */
    public void draw(final Graphics2D g) {
        this.centerX = (int) (width * z / 2) + 1;
        this.centerY = (int) (height * z / 2) + 1;

        at = AffineTransform.getTranslateInstance(x - width * (z - 1) / 2, y - height * (z - 1) / 2);
        at.rotate(angleRadians, centerX, centerY);
        at.scale(z, z);
        g.drawImage(sprites[currentSpriteIndex], at, null);
    }
}