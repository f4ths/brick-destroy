package com.BrickDestroy.Model;

import java.awt.*;

/**
 * This class contains methods that generate levels and how different level types are generated.
 * It was separated from the wall class as it was a bloater class.
 * I have added an additional level that consists of only Diamond Bricks.
 *
 * @author Fathan
 * @version 2.0
 */
public class Levels {

    private static final int LEVELS_COUNT = 5;

    private static final int CLAY = 1;
    private static final int DIAMOND = 2;
    private static final int CEMENT = 3;

    /**
     * This method generates a level that contains only one single type of brick.
     * If the number of bricks is not divisible by the number of lines, brickCount is adjusted tot he biggest multiple of lineCount smaller than brickCount
     * This method calculates and adjusts the number of bricks in each line, the size of the bricks, and the position of each brick
     * The bricks are proportionally identical to one another.
     * The bricks fill up the area uniformly.
     *
     * @param drawArea The area that the wall of bricks occupies. The bricks fill up this area uniformly.
     * @param brickCnt The total number of bricks.
     * @param lineCnt The number of rows of bricks. Used to calculate the width of the bricks.
     * @param brickSizeRatio The ratio of the size of the bricks. Used to calculate the height of the bricks.
     * @param type The subclass of brick that the level is made out of.
     * @return An array of bricks with identical proportions that fill up the space uniformly.
     */
    private static Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type) {

        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < tmp.length; i++) {

            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);

            tmp[i] = makeBrick(p, brickSize, type);

        }

        for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, type);
        }
        return tmp;

    }

    /**
     * This method forms a chessboard level composed of 2 types of bricks.
     * If the number of bricks is not divisible by the number of lines, brickCount is adjusted tot he biggest multiple of lineCount smaller than brickCount.
     * This method adjusts the number of bricks in each line, their size, and their position.
     * The brick types form an alternating pattern - typeA will be next to typeB akin to a chessboard.
     * All bricks have identical proportions and fill up the area uniformly.
     *
     * @param drawArea The area that the wall of bricks occupies. The bricks fill up this area uniformly.
     * @param brickCnt The total number of bricks.
     * @param lineCnt The number of rows of bricks. Used to calculate the width of the bricks.
     * @param brickSizeRatio The ratio of the size of the bricks. Used to calculate the height of the bricks.
     * @param typeA The first type of brick that the level is made out of.
     * @param typeB The second type of brick that the level is made out of.
     * @return An array of bricks that contain two types of bricks arranged in a chessboard pattern. The bricks fill up the area uniformly and proportionately.
     */
    private static Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB) {

        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        int i;
        for (i = 0; i < tmp.length; i++) {

            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ? makeBrick(p, brickSize, typeA) : makeBrick(p, brickSize, typeB);
        }

        for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, typeA);
        }
        return tmp;
    }

    /**
     * This method determines what type of level is generated for each level.
     * It takes in the area, brickCount, lineCount, and brickDimensionRatio and passes them into the makeLevel methods.
     * There are 5 levels. The first 4 levels are the same as the original implementation (DiamondBrick = SteelBrick).
     * The 5th level is a new level that consists of only diamond bricks.
     *
     * @param drawArea The area that the wall of bricks occupies. The bricks fill up this area uniformly.
     * @param brickCount The total number of bricks.
     * @param lineCount The number of rows of bricks. Used to calculate the width of the bricks.
     * @param brickDimensionRatio The ratio of the size of the bricks. Used to calculate the height of the bricks.
     * @return A double array of bricks that stores the current level and each brick of that level.
     */
    public static Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY);
        tmp[1] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, CEMENT);
        tmp[2] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, DIAMOND);
        tmp[3] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, DIAMOND, CEMENT);
        tmp[4] = makeSingleTypeLevel(drawArea, brickCount, lineCount, brickDimensionRatio, DIAMOND);
        return tmp;
    }

    /**
     * Makes a brick depending on what type is required.
     */
    private static Brick makeBrick(Point point, Dimension size, int type) {
        return switch (type) {
            case CLAY -> new ClayBrick(point, size);
            case DIAMOND -> new DiamondBrick(point, size);
            case CEMENT -> new CementBrick(point, size);
            default -> throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        };
    }
}
