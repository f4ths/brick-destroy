package com.BrickDestroy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


public class Info extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GAME_TITLE = "INSTRUCTIONS:";
    private static final String LINE1 = "SPACE - start/pause";
    private static final String LINE2 = "D - move the paddle to the right";
    private static final String LINE3 = "A - move the paddle to the left";
    private static final String LINE4 = "ESC - enter/exit pause menu";
    private static final String LINE5 = "ALT+SHIFT+F1 open console";
    private static final String BACK_TEXT = "BACK";


    // private static final Color BG_COLOR = Color.GREEN.darker();
    private static final Color BORDER_COLOR = new Color(200, 8, 21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = new Color(255, 253, 121);//gold
    //private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 0;
    private static final float[] DASHES = {12, 6};

    private Rectangle menuFace;
    private Rectangle BackButton;

    private Font instructionFont;
    private Font gameTitleFont;
    private Font buttonFont;

    private GameFrame owner;

    private boolean BackClicked;



    public Info(GameFrame owner, Dimension area) {

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;


        menuFace = new Rectangle(new Point(0, 0), area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 15);
        BackButton = new Rectangle(btnDim);

        instructionFont = new Font("SansSerif", Font.PLAIN, 25);
        gameTitleFont = new Font("SansSerif", Font.BOLD, 40);
        buttonFont = new Font("SansSerif", Font.PLAIN, BackButton.height - 2);

    }


    public void paint(Graphics g) {
        drawMenu((Graphics2D) g);
    }


    public void drawMenu(Graphics2D g2d) {

        drawContainer(g2d);

        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x, y);

        drawText(g2d);
        drawButton(g2d);

        g2d.translate(-x, -y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);

    }

    private void drawContainer(Graphics2D g2d) {
        //add image
        Image picture = Toolkit.getDefaultToolkit().getImage("brick.jpg");

        Color prev = g2d.getColor();
        g2d.fill(menuFace);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(tmp);

        g2d.setColor(prev);

        g2d.drawImage(picture, 0, 0, this);
    }

    private void drawText(Graphics2D g2d) {

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D Title = gameTitleFont.getStringBounds(GAME_TITLE, frc);
        Rectangle2D II1 = instructionFont.getStringBounds(LINE1, frc);
        Rectangle2D II2 = instructionFont.getStringBounds(LINE2, frc);
        Rectangle2D II3 = instructionFont.getStringBounds(LINE3, frc);
        Rectangle2D II4 = instructionFont.getStringBounds(LINE4, frc);
        Rectangle2D II5 = instructionFont.getStringBounds(LINE5, frc);

        int sX, sY;

        sX = (int) (menuFace.getWidth() - Title.getWidth()) /2;
        sY = (int) (menuFace.getHeight() / 6);
        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE, sX, sY);

        sX = (int) (menuFace.getWidth() - II1.getWidth()) / 4;
        sY += (int) II1.getHeight() * 1.1;
        g2d.setFont(instructionFont);
        g2d.drawString(LINE1, sX, sY);

        sX = (int) (menuFace.getWidth() - II1.getWidth()) / 4;
        sY += (int) II2.getHeight() * 1.1;
        g2d.setFont(instructionFont);
        g2d.drawString(LINE2, sX, sY);

        sX = (int) (menuFace.getWidth() - II1.getWidth()) / 4;
        sY += (int) II3.getHeight() * 1.1;
        g2d.setFont(instructionFont);
        g2d.drawString(LINE3, sX, sY);

        sX = (int) (menuFace.getWidth() - II1.getWidth()) / 4;
        sY += (int) II4.getHeight() * 1.1;
        g2d.setFont(instructionFont);
        g2d.drawString(LINE4, sX, sY);

        sX = (int) (menuFace.getWidth() - II1.getWidth()) / 4;
        sY += (int) II5.getHeight() * 1.1;
        g2d.setFont(instructionFont);
        g2d.drawString(LINE5, sX, sY);

    }

    private void drawButton(Graphics2D g2d) {

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(BACK_TEXT, frc);

        g2d.setStroke(new BasicStroke(4));
        g2d.setFont(buttonFont);

        int x = (menuFace.width - BackButton.width) / 2;
        int y = (int) ((menuFace.height - BackButton.height) * 0.9);

        BackButton.setLocation(x, y);
        x = (int) (BackButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int) (BackButton.getHeight() - txtRect.getHeight()) / 2;
        x += BackButton.x;
        y += BackButton.y + (BackButton.height * 0.9);

        if (BackClicked) {
            ClickBack(g2d, x, y);
        } else {
            g2d.draw(BackButton);
            g2d.drawString(BACK_TEXT, x, y);
        }
    }

    //*Made into a function from drawButton*/
    private void ClickBack(Graphics2D g2d, int x, int y) {
        Color tmp = g2d.getColor();
        //g2d.setColor(CLICKED_BUTTON_COLOR);
        g2d.draw(BackButton);
        g2d.setColor(CLICKED_TEXT);
        g2d.drawString(BACK_TEXT, x, y);
        g2d.setColor(tmp);
    }



    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (BackButton.contains(p)) {
            owner.BackHome();

        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (BackButton.contains(p)) {
            BackClicked = true;
            repaint(BackButton.x, BackButton.y, BackButton.width + 1, BackButton.height + 1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if  (BackClicked) {
            BackClicked = false;
            repaint(BackButton.x, BackButton.y, BackButton.width + 1, BackButton.height + 1);
        }
    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (BackButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
