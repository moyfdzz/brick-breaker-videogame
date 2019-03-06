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

    public Ball(int x, int y, int width, int height, Game game, int velX, int velY) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.velX = velX;
        this.velY = velY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    /**
     * To get the perimeter of the rectangle of the ball
     * @return Rectangle
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * To determine if the object is intersecting with another object
     * @param obj
     * @return Rectangle
     */
    public boolean intersecta(Paddle obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }
    
    @Override
    public void tick() {
        

        //right margin
        if( getX() + getWidth() >= game.getWidth()){
            setX(game.getWidth()- getWidth());
            setVelX(-getVelX());
        }
        //left margin
        else if (getX() <= 0){
            setX(0);
            setVelX(-getVelX());
        }
        //top margin
        else if( getY() + getHeight() >= game.getHeight()){
            setY(game.getHeight()- getHeight());
            setVelY(-getVelY());
        }
        //bottom margin
        else if (getY() <= -getHeight()){
            setY(-getHeight());
            setVelY(-getVelY());
        }
        
        setY(getY() + getVelY());  
        setX(getX() + getVelX()); 
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }

    @Override
    public String toString() {
        return x + "," + y + "," + width + "," + height + "," + velX + "," + velY;
    }
    
}
