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
    public static BufferedImage background;
    public static BufferedImage player;
    public static BufferedImage enemy;
    public static BufferedImage lives;
    public static BufferedImage gameOver;
    public static SoundClip death;
    public static BufferedImage brickSkins[];
    
    public static void init() {
        background = ImageLoader.loadImage("/images/mario_bros_background.jpg");
        player = ImageLoader.loadImage("/images/bar.png");
        enemy = ImageLoader.loadImage("/images/goomba.png");
        lives = ImageLoader.loadImage("/images/heart.png");
        gameOver = ImageLoader.loadImage("/images/game_over.jpg");
        death = new SoundClip("/sounds/death.wav"); 

        brickSkins = new BufferedImage[3];
        brickSkins[0] = ImageLoader.loadImage("/images/brick_hp_1.png");
        brickSkins[1] = ImageLoader.loadImage("/images/brick_hp_2.png");
        brickSkins[2] = ImageLoader.loadImage("/images/brick_hp_3.png");
    }
    
}
