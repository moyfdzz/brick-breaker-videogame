/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/**
 *
 * @author moisesfernandez
 */
public class Game implements Runnable {

    private BufferStrategy bs;	// to have several buffers when	displaying
    private Graphics g;         // to paint objects
    private Display display;	// to display in the game
    String title;															//	title	of	the	window
    private final int width;		// width of the	window
    private final int height;		// height of the window
    private Thread thread;	// thread to create the	game
    private boolean running;    // to set the game
    private Player player;      // variable for the player
    private KeyManager keyManager;
    private LinkedList<Brick> enemies;
    private boolean gameOver;
    private boolean paused;         // to determine if the game is paused
    
    
    /**
     * to	create	title,	width	and	height	and	set	the	game	is	still	not	running
     *
     * @param	title	to	set	the	title	of	the	window
     * @param	width	to	set	the	width	of	the	window
     * @param	height	to	set	the	height	of	the	window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        enemies = new LinkedList<Brick>();
        this.gameOver = false;
        this.paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    public boolean isGameOver() {
        return gameOver;
    }


    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }


    public BufferStrategy getBs() {
        return this.bs;
    }

    public void setBs(BufferStrategy bs) {
        this.bs = bs;
    }

    public Graphics getG() {
        return this.g;
    }

    public void setG(Graphics g) {
        this.g = g;
    }

    public Display getDisplay() {
        return this.display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Thread getThread() {
        return this.thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public boolean isRunning() {
        return this.running;
    }

    public boolean getRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setKeyManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public boolean getPaused() {
        return this.paused;
    }

    
    /**
     * initializing	the	display	window	of	the	game
     */
    private void init() {
        display = new Display(title, width, height);
        Assets.init();
        player = new Player(getWidth()/2, getHeight() , 50, 20, this);
        display.getJframe().addKeyListener(keyManager);
        
        for (int i = 0; i <= 9; i++) {
            enemies.add(new Brick(50*i, 0, 30, 10, this));
        }
        for (int i = 0; i <= 8; i++) {
            enemies.add(new Brick(25+50*i, 50, 30, 10, this));
        }
        for (int i = 0; i <= 9; i++) {
            enemies.add(new Brick(50*i, 100, 30, 10, this));
        }
        for (int i = 0; i <= 8; i++) {
            enemies.add(new Brick(25+50*i, 150, 30, 10, this));
        }
        
    }
    
    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    private void tick() {
        keyManager.tick();
        
        if(keyManager.P == true)
        {
            setPaused(true);
        }
        
        if (player.getLives() == 0) {
            setGameOver(true);
        }

        if(!isGameOver() && !isPaused())
        {
         for(int i = 0;  i <= enemies.size(); i++)
          {
              if(enemies.get(i).getLives() == 0)
                enemies.remove(i);
              
            else enemies.get(i).tick();
          }   
          player.tick();
    
        }
        
    }
    
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one	with 3 buffers to display images of
           the game, if	not null, then we display every	image of the game but
           after clearing the Rectanlge, getting the graphic object from the	
           buffer strategy element. show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            
            g.setColor(Color.black);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            
            if(!isGameOver()) {
                player.render(g);
                for (int i = 0; i < enemies.size(); i++) {
                        enemies.get(i).render(g);
                }
            
                g.drawString( "Score : " + player.getScore(), getWidth() - 100, getHeight());
            }
            else {
                g.drawImage(Assets.gameOver, 0, 0, width, height, null);
            }
            
            bs.show();
            g.dispose();
        }
        
    }
    
    public synchronized void start() {
        if(!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    public synchronized void stop() {
        if(running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        init();
        int fps = 30;
        double timeTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
	while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

}
