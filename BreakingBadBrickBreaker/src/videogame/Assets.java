/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;
import videogame.ImageLoader;

/**
 *
 * @author moisesfernandez
 */
public class Assets {
    public static BufferedImage background;         //to save the image of the background
    public static BufferedImage paddle;             //to save the image of the paddle
    public static BufferedImage enemy;              //to save the image of the enemy
    public static BufferedImage intro;              //to save the image of the intro
    public static BufferedImage lives;              //to save the image of the lives
    public static BufferedImage gameOver;           //to save the image of the gamover
    public static BufferedImage ball;               //to save the image of the ball
    public static BufferedImage UP1;                //to save the image of the POWERUP
    public static BufferedImage lightning;          //to save the image of the POWERUP
    public static BufferedImage slow;          //to save the image of the DEBUFFER
    public static SoundClip death;                  //to save the image of the background
    public static SoundClip theme;                  //to save the image of the background
    public static BufferedImage brickSkins[];       //to save the image of the background
    
    public static void init() {
        
        background = ImageLoader.loadImage("/images/background.jpeg");
        UP1 = ImageLoader.loadImage("/images/1up.png");
        paddle = ImageLoader.loadImage("/images/bar.png");
        intro = ImageLoader.loadImage("/images/intro.jpg");
        ball = ImageLoader.loadImage("/images/ball.png");
        gameOver = ImageLoader.loadImage("/images/game_over.jpg");
        lives = ImageLoader.loadImage("/images/lives.png");
        lightning = ImageLoader.loadImage("/images/lightning.png");
        slow = ImageLoader.loadImage("/images/slow.png");
        death = new SoundClip("/sounds/death.wav"); 
        theme = new SoundClip("/sounds/theme_song.wav");
        brickSkins = new BufferedImage[3];
        brickSkins[0] = ImageLoader.loadImage("/images/brick_hp_1.png");
        brickSkins[1] = ImageLoader.loadImage("/images/brick_hp_2.png");
        brickSkins[2] = ImageLoader.loadImage("/images/brick_hp_3.png");
    }
    
}
