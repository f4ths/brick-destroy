/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.BrickDestroy.Model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * DiamondBrick is a subclass of brick that has a probability to be broken when the ball impacts it.
 * This class was originally SteelBrick.
 * DiamondBrick is essentially a recolor of SteelBrick since in the original iteration it was hard to differentiate between cement and steel.
 * The logic and behaviour of DiamondBrick remains the same as SteelBrick.
 *
 * @author Fathan
 * @version 2.0
 * @see Brick
 */
public class DiamondBrick extends Brick {

    private static final Color DEF_INNER = new Color(181, 250, 253, 255);
    private static final Color DEF_BORDER = DEF_INNER.darker();
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private final Random rnd;
    private final Shape brickFace;

    /**
     * This is the constructor for DiamondBrick. It initialises a brick that has a pseudorandom value.
     * @param point Takes in a point coordinate that determines the brick's center.
     * @param size Takes in a size dimension that determines the area of space that the brick occupies.
     */
    public DiamondBrick(Point point, Dimension size) {
        super(point, size, DEF_BORDER, DEF_INNER, STEEL_STRENGTH);
        rnd = new Random();
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
     * Getter for the diamond brick object.
     * @return The face of cement brick, which includes its point and size.
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }
    /**
     * Overrides from Brick. Decides if a specific brick needs to be impacted or not.
     * @param point The position where the impact occurs.
     * @param dir The direction where the impact comes from.
     * @return Calls the impact method if Diamond Brick has not been broken. If Diamond Brick is broken, setImpact returns false.
     */
    public boolean setImpact(Point2D point, int dir) {
        if (super.isBroken())
            return false;
        impact();
        return super.isBroken();
    }

    /**
     * When this method is called, Diamond Brick is only broken if a generated pseudorandom integer is lower than 40.
     * This means that there is a 40% chance that Diamond Brick gets impacted.
     * Since Diamond Brick has a strength of 1, it is broken after a successful impact, meaning there is a 40% chance that the brick breaks after impact.
     */
    public void impact() {
        if (rnd.nextDouble() < STEEL_PROBABILITY) {
            super.impact();
        }
    }

}
