/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author ErickFrank
 */
public class Ball extends Item {
    
    private int width;
    private int height;
    private Game game;
    private int velX;
    private int velY;
    private int maxVel;
    private boolean bottom;
    private int collisions;

    /**
     * Constructor to initialize an object of the type Ball with its attributes
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param velX
     * @param velY
     */
    public Ball(int x, int y, int width, int height, Game game, int velX, int velY) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.velX = velX;
        this.velY = velY;
        maxVel = 15;
        this.bottom = false;
        this.collisions = 0;
    }
    /**
     * to get the width of the ball
     * @return width
     */
    public int getWidth() {
        return width;
    }
    /**
     * to set the width of the ball
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * to get the height of the ball
     * @return height
     */
    public int getHeight() {
        return height;
    }
    /**
     * to set the height of the ball
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * to get the velX of the ball
     * @return velX
     */
    public int getVelX() {
        return velX;
    }
    /**
     * to set the velX of the ball
     * @param velX 
     */
    public void setVelX(int velX) {
        this.velX = velX;
    }
    /**
     * to get the velY of the ball
     * @return velY
     */
    public int getVelY() {
        return velY;
    }
    /**
     * to set the velY of the ball
     * @param velY 
     */
    public void setVelY(int velY) {
        this.velY = velY;
    }
    /**
     * to get the maxVel of the ball
     * @return 
     */
    public int getMaxVel() {
        return maxVel;
    }
    /**
     * to set the maxVel of the ball
     * @param maxVel 
     */
    public void setMaxVel(int maxVel) {
        this.maxVel = maxVel;
    }
    /**
     * to get the status of bottom of the ball
     * @return 
     */
    public boolean isBottom() {
        return bottom;
    }
    /**
     * to set the status of bottom of the ball
     * @param bottom 
     */
    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }
    /**
     * to get the collisions of the ball
     * @return collisions
     */
    public int getCollisions() {
        return collisions;
    }
    /**
     * to set the collision of the ball
     * @param collisions 
     */
    public void setCollisions(int collisions) {
        this.collisions = collisions;
    }
    
    /**
     * To get the perimeter of the rectangle of the ball
     * @return Rectangle
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * To determine if the object is intersecting with the paddle
     * @param obj
     * @return Rectangle
     */
    public boolean intersecta(Paddle obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }

    /**
     * To determine if the object is intersecting with a brick
     * @param obj
     * @return Rectangle
     */
    public boolean intersecta(Brick obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }
    
    @Override
    public void tick() {
        

        if (getX() + getWidth() >= game.getWidth()) { // Right margin of window
            setVelX(-getVelX());
            
        } else if (getX() <= 0) { // Left margin of window
            setVelX(-getVelX());
        }

        if (getY() <= 0) { // Top margin of window
            setVelY(-getVelY());
        } 
        if (getY() + getHeight() >= game.getHeight()) {
            setBottom(true);
        } 

        if (getVelX() > getMaxVel()) {
            setVelX(getMaxVel());
        }

        if (getVelY() > getMaxVel()) {
            setVelY(getMaxVel());
        }
        
        //sets the movement
        setY(getY() + getVelY());  
        setX(getX() + getVelX()); 
    }
    
    /**
     * renders the ball
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
