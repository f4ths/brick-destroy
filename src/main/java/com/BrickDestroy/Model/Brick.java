package com.BrickDestroy.Model;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Brick is an abstract class for a brick object that defines concrete methods.
 *
 * @author Fathan
 * @version 2.0
 */
abstract public class Brick {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    private static Random rnd;

    private Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;

    /**
     * This is the constructor for a brick object. It defines the position, size, color, and strength of a brick.
     *
     * @param pos The central coordinate position of the brick.
     * @param size The size of the brick - the area that it occupies.
     * @param border The border color of the brick.
     * @param inner The body color of the brick.
     * @param strength The strength value of the brick.
     */
    public Brick(Point pos, Dimension size, Color border, Color inner, int strength) {
        rnd = new Random();
        setBroken(false);

        this.setBorderColor(border);
        this.setInnerColor(inner);

        setBrickFace(makeBrickFace(pos, size));
        this.setFullStrength(this.setStrength(strength));

    }

    /**
     * This abstract method is overridden in brick subclasses.
     *
     * @see ClayBrick
     * @see CementBrick
     * @see DiamondBrick
     */
    protected abstract Shape makeBrickFace(Point pos, Dimension size);

    /**
     * This method decides that a specific brick needs to be impacted or not.
     * If the brick has already been broken, an impact does not occur.
     * Otherwise, it impacts the ball and reduces its strength.
     *
     * @param point The position where the impact occurs.
     * @param dir The direction where the impact comes from.
     * @return Either the broken status of the ball.
     */
    public boolean setImpact(Point2D point, int dir) {
        if (isBroken())
            return false;
        impact();
        return isBroken();
    }

    /**
     * This method looks for impacts made by a ball on a brick.
     * It does so by looking for whether a point of a ball occupies the same coordinate point of a brick.
     * The point of the ball is specific to its up, down, left, or right point, which decides where the impact comes from.
     *
     * @param b A ball object whose points are looked at.
     * @return The direction that the impact comes from. Otherwise, returns 0.
     */
    public final int findImpact(Ball b) {
        if (isBroken())
            return 0;
        int out = 0;
        if (getBrickFace().contains(b.right))
            out = LEFT_IMPACT;
        else if (getBrickFace().contains(b.left))
            out = RIGHT_IMPACT;
        else if (getBrickFace().contains(b.up))
            out = DOWN_IMPACT;
        else if (getBrickFace().contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    /**
     * This method resets the brick to its original state, before any impacts has been made to it.
     * It sets the strength value of a brick to its original strength, and it sets it as not broken.
     * It is used in the Wall class to reset the strength of bricks when a new wall is generated.
     */
    public void repair() {
        setBroken(false);
        setStrength(getFullStrength());
    }

    /**
     * If a brick has been impacted, this method is called to decrease the strength of the brick.
     * If the strength of the brick reaches 0 after this method, the brick is set as broken.
     */
    public void impact() {
        setStrength(getStrength() - 1);
        setBroken((getStrength() == 0));
    }

    /**
     * Sets the bounding area of the brick. It is the space that the brick occupies.
     * @param brickFace Takes in the space that the brick occupies.
     */
    public void setBrickFace(Shape brickFace) {
        this.brickFace = brickFace;
    }

    /**
     * Sets the border color of the brick.
     * @param border Takes in a color that will be set as the border color.
     */
    public void setBorderColor(Color border) {
        this.border = border;
    }

    /**
     * Sets the body color of the brick.
     * @param inner Takes in a color that will be set as the body color.
     */
    public void setInnerColor(Color inner) {
        this.inner = inner;
    }

    /**
     * This method sets the brick's full strength value.
     * @param fullStrength A brick's original strength value.
     */
    public void setFullStrength(int fullStrength) {
        this.fullStrength = fullStrength;
    }

    /**
     * This method sets a brick's strength value.
     * @param strength A brick's strength value.
     */
    public int setStrength(int strength) {
        this.strength = strength;
        return strength;
    }

    /**
     * This method sets the brick's broken condition.
     */
    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    /**
     * This method gets a brick as an object and is called in GameBoard
     */
    public abstract Shape getBrick();

    public static Random getRnd() {
        return rnd;
    }

    public Shape getBrickFace() {
        return brickFace;
    }

    public Color getBorderColor() {
        return border;
    }

    public Color getInnerColor() {
        return inner;
    }

    public int getFullStrength() {
        return fullStrength;
    }

    public int getStrength() {
        return strength;
    }

    public final boolean isBroken() {
        return broken;
    }



}





