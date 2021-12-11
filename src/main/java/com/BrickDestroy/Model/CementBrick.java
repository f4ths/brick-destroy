package com.BrickDestroy.Model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * CementBrick is a subclass of Brick that needs to be impacted twice in order to be broken.
 * The first impact cracks CementBrick.
 *
 * @author Fathan
 * @version 2.0
 * @see Brick
 */
public class CementBrick extends Brick {

    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;

    /**
     * This is the constructor for CementBrick. It initialises a grey brick that has a strength of 2.
     * @param point Takes in a point coordinate that determines the brick's center.
     * @param size Takes in a size dimension that determines the area of space that the brick occupies.
     */
    public CementBrick(Point point, Dimension size) {
        super(point, size, DEF_BORDER, DEF_INNER, CEMENT_STRENGTH);
        setCrack(new Crack(CementBrick.this, DEF_CRACK_DEPTH, DEF_STEPS));
        brickFace = super.getBrickFace();
    }

    /**
     * Overrides the makeBrickFace() method from Brick.
     * @param pos Point that determines center.
     * @param size Area of space the brick occupies.
     * @return The face of the brick that is seen.
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    /**
     * Overrides from Brick so that when the cement brick is impacted, a crack is made and shown on the face.
     * @param point The position where the impact occurs.
     * @param dir The direction where the impact comes from.
     * @return If cement brick is not broken, then an impact is made and a crack is made. If cement brick is broken, then setImpact returns false.
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if (super.isBroken())
            return false;
        super.impact();
        if (!super.isBroken()) {
            getCrack().makeCrackLocation(point, dir);
            updateBrick();
            return false;
        }
        return true;
    }

    /**
     * Getter for the cement brick object.
     * @return The face of cement brick, which includes its point and size.
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * Updates cement brick to show a crack that has been formed after first impact with the ball.
     */
    private void updateBrick() {
        if (!super.isBroken()) {
            GeneralPath gp = getCrack().draw();
            gp.append(super.getBrickFace(), false);
            brickFace = gp;
        }
    }

    /**
     * This method removes a crack that has been made on cement brick. It returns the cement brick to its uncracked state.
     */
    public void repair() {
        super.repair();
        getCrack().reset();
        brickFace = super.getBrickFace();
    }

    /**
     * Getter for crack object.
     * @return Crack object.
     */
    public Crack getCrack() {
        return crack;
    }

    /**
     * Setter for crack object.
     */
    public void setCrack(Crack crack) {
        this.crack = crack;
    }
}
