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
    private boolean created;
    
    /**
     * Constructor to initialize an object of the type PowerUp with its attributes
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param velY
     * @param power
     */
    public PowerUp(int x, int y, int width, int height, Game game, int velY, int power) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.velY = velY;
        maxVel = 15;
        this.bottom = false;
        this.power = power;
        this.created = false;
    }

    /**
     * To get the power of the ball
     * @return power
     */
    public int getPower() {
        return power;
    }

    /**
     * To set the power of the ball
     * @param power 
     */
    public void setPower(int power) {
        this.power = power;
    }
    
    /**
     * To get the width of the ball
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To set the width of the ball
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * To get the height of the ball
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * To set the height of the ball
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * To get the velY of the ball
     * @return velY
     */
    public int getVelY() {
        return velY;
    }

    /**
     * To set the velY of the ball
     * @param velY 
     */
    public void setVelY(int velY) {
        this.velY = velY;
    }

    /**
     * To get the maxVel of the ball
     * @return 
     */
    public int getMaxVel() {
        return maxVel;
    }

    /**
     * To set the maxVel of the ball
     * @param maxVel 
     */
    public void setMaxVel(int maxVel) {
        this.maxVel = maxVel;
    }

    /**
     * To get the status of bottom of the ball
     * @return 
     */
    public boolean isBottom() {
        return bottom;
    }

    /**
     * To set the status of bottom of the ball
     * @param bottom 
     */
    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    /**
     * To get the status of the power 
     * @return created
     */
    public boolean isCreated() {
        return created;
    }

    /**
     * To set the status of the power up
     * @param created
     */
    public void setCreated(boolean created) {
        this.created = created;
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
        
        if (getY() + getHeight() >= game.getHeight()) {
            setBottom(true);
        } 

        if (getVelY() > getMaxVel()) {
            setVelY(getMaxVel());
        }
        
        //sets the movement
        setY(getY() + getVelY());  

        if(isBottom())
        {
            reset();
        }
        
    }
    public void reset(){
        setBottom(false);
        setX(0);
        setY(0);
        setPower(0);
        setVelY(0);
        setCreated(false);
    }
    /**
     * renders the ball
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        
        switch(getPower())
        {
            case 1: 
            g.drawImage(Assets.lightning, getX(), getY(), getWidth(), getHeight(), null);
                break;
            case 2: 
            g.drawImage(Assets.UP1, getX(), getY(), getWidth(), getHeight(), null);
                break;
            case 3: 
            g.drawImage(Assets.slow, getX(), getY(), getWidth(), getHeight(), null);
                break;
        }
        
    }
}
