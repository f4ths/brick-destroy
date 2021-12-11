package com.BrickDestroy.Model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * This is a Crack class which is used in CementBrick after it is impacted once.
 * It has been separated from Brick into its own class file to reduce the size of Brick.
 *
 * @author Fathan
 * @version 2.0
 * @see Brick
 * @see CementBrick
 */
public class Crack {

    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;


    private final Brick brick;
    private final GeneralPath crack;

    private int crackDepth;
    private int steps;

    /**
     * This is the constructor for Crack.
     *
     * @param brick Takes in a brick object to add a crack to.
     * @param crackDepth Value for the size of the crack made.
     * @param steps Value that is used in further calculation to determine the steps.
     */
    public Crack(Brick brick, int crackDepth, int steps) {
        this.brick = brick;

        crack = new GeneralPath();
        this.setCrackDepth(crackDepth);
        this.setSteps(steps);
    }

    /**
     * Draws a crack which is defined as a geometric line. Called in CementBrick.
     *
     * @return A crack object.
     */
    public GeneralPath draw() {
        return crack;
    }

    /**
     * Resets the crack path to empty.
     */
    public void reset() {
        crack.reset();
    }

    /**
     * This is a logic method that determines where the crack is made.
     * This method determines the crack starting and end position based on where an impact is made.
     *
     * @param point A coordinate point used to determine which point an impact is made.
     * @param direction A directional value that determines which side of the brick an impact has been made.
     */
    protected void makeCrackLocation(Point2D point, int direction) {
        Rectangle bounds = brick.getBrickFace().getBounds();

        Point impact = new Point((int) point.getX(), (int) point.getY());
        Point start = new Point();
        Point end = new Point();

        switch (direction) {
            case LEFT:
                start.setLocation(bounds.x + bounds.width, bounds.y);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                Point tmp = makeRandomPoint(start, end, VERTICAL);
                makeCrack(impact, tmp);

                break;
            case RIGHT:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x, bounds.y + bounds.height);
                tmp = makeRandomPoint(start, end, VERTICAL);
                makeCrack(impact, tmp);

                break;
            case UP:
                start.setLocation(bounds.x, bounds.y + bounds.height);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                tmp = makeRandomPoint(start, end, HORIZONTAL);
                makeCrack(impact, tmp);
                break;
            case DOWN:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x + bounds.width, bounds.y);
                tmp = makeRandomPoint(start, end, HORIZONTAL);
                makeCrack(impact, tmp);

                break;

        }
    }

    /**
     * This method draws the crack object itself after its start and end position has been determined by makeCrackLocation().
     * It forms a new Crack path on the brick depending on the parameters.
     *
     * @param start Starting point of the Crack GeneralPath
     * @param end Ending point of the Crack GeneralPath
     */
    protected void makeCrack(Point start, Point end) {

        GeneralPath path = new GeneralPath();

        path.moveTo(start.x, start.y);

        double w = (end.x - start.x) / (double) getSteps();
        double h = (end.y - start.y) / (double) getSteps();

        int bound = getCrackDepth();
        int jump = bound * 5;

        double x, y;

        for (int i = 1; i < getSteps(); i++) {

            x = (i * w) + start.x;
            y = (i * h) + start.y + randomInBounds(bound);

            if (inMiddle(i, getSteps()))
                y += jumps(jump);

            path.lineTo(x, y);

        }

        path.lineTo(end.x, end.y);
        crack.append(path, true);
    }

    /**
     * This method is called in makeCrack and jumps.
     * It takes in a bound value and returns a random value of a brick subtracted by the bound value.
     * @param bound Bound value
     * @return The next pseudorandom value of a brick subtracted by the bound value.
     */
    private int randomInBounds(int bound) {
        int n = (bound * 2) + 1;
        return Brick.getRnd().nextInt(n) - bound;
    }

    /**
     * This boolean method determines whether a crack is formed in the middle of the brick or not.
     * I have inlined the steps parameter as it is always Crack.CRACK_SECTIONS.
     */
    private boolean inMiddle(int i, int divisions) {
        int low = (Crack.CRACK_SECTIONS / divisions);
        int up = low * (divisions - 1);

        return (i > low) && (i < up);
    }

    /**
     * This method determines the crack's jump value as a pseudorandom value if the jump probability has been surpassed.
     * @param bound Bounding value
     * @return Pseudorandom bounded value if jump probability has been surpassed, otherwise 0.
     */
    private int jumps(int bound) {

        if (Brick.getRnd().nextDouble() > Crack.JUMP_PROBABILITY)
            return randomInBounds(bound);
        return 0;

    }

    /**
     * This method creates a random coordinate point to determine the crack's points.
     * @param from Starting point.
     * @param to End Point.
     * @param direction Direction value to determine whether horizontal or vertical.
     * @return A new point.
     */
    private Point makeRandomPoint(Point from, Point to, int direction) {

        Point out = new Point();
        int pos;

        switch (direction) {
            case HORIZONTAL -> {
                pos = Brick.getRnd().nextInt(to.x - from.x) + from.x;
                out.setLocation(pos, to.y);
            }
            case VERTICAL -> {
                pos = Brick.getRnd().nextInt(to.y - from.y) + from.y;
                out.setLocation(to.x, pos);
            }
        }
        return out;
    }


    public void setCrackDepth(int crackDepth) {
        this.crackDepth = crackDepth;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getCrackDepth() {
        return crackDepth;
    }

    public int getSteps() {
        return steps;
    }


}
