package com.BrickDestroy.Model;

import java.awt.*;
import java.awt.Point;


/**
 * ClayBrick is the most basic subclass of Brick. It has a strength value of 1 meaning it is broken after 1st impact.
 *
 * @author Fathan
 * @version 2.0
 * @see Brick
 */
public class ClayBrick extends Brick {

    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;


    public ClayBrick(Point point, Dimension size) {
        super(point, size, DEF_BORDER, DEF_INNER, CLAY_STRENGTH);
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    @Override
    public Shape getBrick() {
        return super.getBrickFace();
    }


}
