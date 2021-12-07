package com.BrickDestroy.Model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class CementBrick extends Brick {


    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;


    public CementBrick(Point point, Dimension size) {
        super(NAME, point, size, DEF_BORDER, DEF_INNER, CEMENT_STRENGTH);
        setCrack(new Crack(CementBrick.this, DEF_CRACK_DEPTH, DEF_STEPS));
        brickFace = super.getBrickFace();
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    @Override
    public boolean setImpact(Point2D point, int dir) {
        if (super.isBroken())
            return false;
        super.impact();
        if (!super.isBroken()) {
            getCrack().makeCrack(point, dir);
            updateBrick();
            return false;
        }
        return true;
    }


    @Override
    public Shape getBrick() {
        return brickFace;
    }

    private void updateBrick() {
        if (!super.isBroken()) {
            GeneralPath gp = getCrack().draw();
            gp.append(super.getBrickFace(), false);
            brickFace = gp;
        }
    }

    public void repair() {
        super.repair();
        getCrack().reset();
        brickFace = super.getBrickFace();
    }

    public Crack getCrack() {
        return crack;
    }

    public void setCrack(Crack crack) {
        this.crack = crack;
    }
}
