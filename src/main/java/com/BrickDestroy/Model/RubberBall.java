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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * RubberBall is a subclass of Ball. It stores unique properties which includes its radius, inner color, and border color.
 *
 * @author Fathan
 * @version 2.0
 */
public class RubberBall extends Ball {

    private static final int DEF_RADIUS = 10;
    private static final Color DEF_INNER_COLOR = new Color(255, 255, 255);
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();

    /**
     * Constructor for RubberBall.
     * It sets the location, radius, body color, and outer color of the ball.
     * @param center Location of the ball as a coordinate point.
     */
    public RubberBall(Point2D center) {
        super(center, DEF_RADIUS, DEF_RADIUS, DEF_INNER_COLOR, DEF_BORDER_COLOR);
    }

    /**
     * Makes a rubber ball. Logic for its center point.
     * @return an Ellipse2D object representing the rubber ball.
     */
    @Override
    protected Shape makeBall(Point2D center, int radiusA, int radiusB) {

        double x = center.getX() - (radiusA * 0.5);
        double y = center.getY() - (radiusB * 0.5);

        return new Ellipse2D.Double(x, y, radiusA, radiusB);
    }
}
