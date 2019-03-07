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
public class PowerUp extends Item {
    
    private int width;
    private int height;
    private Game game;
    private int velY;
    private int maxVel;
    private boolean bottom;
    private int power;
    
    public PowerUp(int x, int y, int width, int height, Game game, int velX, int velY) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.velY = velY;
        maxVel = 15;
        this.bottom = false;
        this.power = 0;
    }
    /**
     * to get the power of the ball
     * @return 
     */
    public int getPower() {
        return power;
    }
    /**
     * to set the power of the ball
     * @param power 
     */
    public void setPower(int power) {
        this.power = power;
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
    
    @Override
    public void tick() {
        
        if (getY() <= 0) { // Top margin of window
            setVelY(-getVelY());
        } 
        
        if (getY() + getHeight() >= game.getHeight()) {
            setBottom(true);
        } 

        if (getVelY() > getMaxVel()) {
            setVelY(getMaxVel());
        }
        
        //sets the movement
        setY(getY() + getVelY());  
    }
    
    /**
     * renders the ball
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        switch(power)
        {
            case 1: 
            g.drawImage(Assets.lightning, getX(), getY(), getWidth(), getHeight(), null);
                break;
            case 2: 
            g.drawImage(Assets.UP1, getX(), getY(), getWidth(), getHeight(), null);
                break;
            case 3: 
            g.drawImage(Assets.invisible, getX(), getY(), getWidth(), getHeight(), null);
                break;
        }
    }
    
    
    
}
