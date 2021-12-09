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
     * @param pos
     * @param size
     * @param border
     * @param inner
     * @param strength
     */
    public Brick(Point pos, Dimension size, Color border, Color inner, int strength) {
        rnd = new Random();
        setBroken(false);

        this.setBorderColor(border);
        this.setInnerColor(inner);

        setBrickFace(makeBrickFace(pos, size));
        this.setFullStrength(this.setStrength(strength));

    }

    protected abstract Shape makeBrickFace(Point pos, Dimension size);

    public boolean setImpact(Point2D point, int dir) {
        if (isBroken())
            return false;
        impact();
        return isBroken();
    }

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

    public void repair() {
        setBroken(false);
        setStrength(getFullStrength());
    }

    public void impact() {
        setStrength(getStrength() - 1);
        setBroken((getStrength() == 0));
    }

    public void setBrickFace(Shape brickFace) {
        this.brickFace = brickFace;
    }

    public void setBorderColor(Color border) {
        this.border = border;
    }

    public void setInnerColor(Color inner) {
        this.inner = inner;
    }

    public void setFullStrength(int fullStrength) {
        this.fullStrength = fullStrength;
    }

    public int setStrength(int strength) {
        this.strength = strength;
        return strength;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }


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





