package com.BrickDestroy;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by filippo on 04/09/16.
 */
abstract public class Brick {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;


    public static Random getRnd() {
        return rnd;
    }

    private static Random rnd;

    private Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;


    public Brick(String name, Point pos, Dimension size, Color border, Color inner, int strength) {
        rnd = new Random();
        setBroken(false);

        this.setBorder(border);
        this.setInner(inner);

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

    public abstract Shape getBrick();


    public Color getBorderColor() {
        return getBorder();
    }

    public Color getInnerColor() {
        return getInner();
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

    public final boolean isBroken() {
        return broken;
    }

    public void repair() {
        setBroken(false);
        setStrength(getFullStrength());
    }

    public void impact() {
        setStrength(getStrength() - 1);
        setBroken((getStrength() == 0));
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public Shape getBrickFace() {
        return brickFace;
    }

    public void setBrickFace(Shape brickFace) {
        this.brickFace = brickFace;
    }

    public int getStrength() {
        return strength;
    }

    public int setStrength(int strength) {
        this.strength = strength;
        return strength;
    }

    public int getFullStrength() {
        return fullStrength;
    }

    public void setFullStrength(int fullStrength) {
        this.fullStrength = fullStrength;
    }

    public Color getBorder() {
        return border;
    }

    public void setBorder(Color border) {
        this.border = border;
    }

    public Color getInner() {
        return inner;
    }

    public void setInner(Color inner) {
        this.inner = inner;
    }
}





