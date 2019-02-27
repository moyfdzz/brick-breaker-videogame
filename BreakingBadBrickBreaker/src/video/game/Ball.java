/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package video.game;

import java.awt.Graphics;

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

    
    @Override
    public void tick() {
     
    }

    @Override
    public void render(Graphics g) {
        
    }
    
    
    
    
}
