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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moisesfernandez
 */
public class Game implements Runnable,Constants {

    private BufferStrategy bs;          // to have several buffers when	displaying
    private Graphics g;                 // to paint objects
    private Display display;            // to display in the game
    String title;			// the title of the window												//	title	of	the	window
    private final int width;		// width of the	window
    private final int height;		// height of the window
    private Thread thread;              // thread to create the	game
    private boolean running;            // to set the game
    private Paddle paddle;              // variable for the paddle
    private KeyManager keyManager;      // variable for the key manager
    private LinkedList<Brick> bricks;   // linked list of the bricks of the game
    private Ball ball;
    private boolean gameOver;           // to determine if the game is over
    private boolean paused;             // to determine if the game is paused
    private boolean start;              // to determine if the game is paused
    private String lastSave;            // to determine the name of the file
    private int bricksAmount;           // to determine the bricks remaining
    private PowerUp power;              // to determine the powerup 
    private boolean powerChance;
        
        
    /**
     * To create game with title, width, height and status of running
     * @param title
     * @param width
     * @param height 
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        this.gameOver = false;
        this.paused = false;
        this.start = false;
        this.lastSave = "lastSave.txt";
        this.powerChance = false;
        
    }

    /**
     * Returns a boolean value to know whether the game is paused or not
     * @return paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Sets the status of the game either paused or not paused
     * @param paused 
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    /**
     * Returns the width of the window
     * @return width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the height of the window
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns a boolean value to know whether the game is over or not
     * @return 
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Sets the status of the game either over or not over
     * @param gameOver 
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
    
    /**
     * Returns the buffer strategy
     * @return bs
     */
    public BufferStrategy getBs() {
        return this.bs;
    }

    /**
     * Sets the buffer strategy
     * @param bs 
     */
    public void setBs(BufferStrategy bs) {
        this.bs = bs;
    }

    /**
     * Returns the graphics
     * @return g
     */
    public Graphics getG() {
        return this.g;
    }

    /**
     * Sets the graphics
     * @param g 
     */
    public void setG(Graphics g) {
        this.g = g;
    }

    /**
     * Returns the display
     * @return display
     */
    public Display getDisplay() {
        return this.display;
    }

    /**
     * Sets the display
     * @param display 
     */
    public void setDisplay(Display display) {
        this.display = display;
    }

    /**
     * Returns the title
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the thread
     * @return 
     */
    public Thread getThread() {
        return this.thread;
    }

    /**
     * Sets the thread
     * @param thread 
     */
    public void setThread(Thread thread) {
        this.thread = thread;
    }

    /**
     * Returns the status of running
     * @return running
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Sets the status of running
     * @param running 
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Returns the paddle
     * @return paddle
     */
    public Paddle getpaddle() {
        return this.paddle;
    }

    /**
     * Sets the paddle
     * @param paddle 
     */
    public void setpaddle(Paddle paddle) {
        this.paddle = paddle;
    }
    
    /**
     * Sets the key manager
     * @param keyManager 
     */
    public void setKeyManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }
    
    /**
     * initializing	the	display	window	of	the	game
     */
    private void init() {
        display = new Display(title, width, height);
        //initialize the assets
        Assets.init();
        //play the theme song of the game
        Assets.theme.play();
        //initialize the objects of the game
        bricks = new LinkedList<Brick>();
        ball = new Ball(getWidth()/2, getHeight()-150, BALL_DIMENSION, BALL_DIMENSION, this, 0, 0);
        paddle = new Paddle(getWidth()/2, getHeight() - 100, PADDLE_HEIGHT, PADDLE_WIDTH, this);
        display.getJframe().addKeyListener(keyManager);

        power = new PowerUp(0,0,0,0,this,0,0);
        
        //add the bricks to the linked lists
        for (int i = 0; i <= 10; i++) {
            bricks.add(new Brick(BRICK_DIFFX*i+BRICK_STARTX1, BRICK_STARTY1, BRICK_HEIGHT, BRICK_WIDTH, this));
        }
        for (int i = 0; i <= 9; i++) {
            bricks.add(new Brick(BRICK_DIFFX*i+BRICK_STARTX2, BRICK_STARTY2, BRICK_HEIGHT, BRICK_WIDTH, this));
        }
        for (int i = 0; i <= 10; i++) {
            bricks.add(new Brick(BRICK_DIFFX*i+BRICK_STARTX1, BRICK_STARTY3, BRICK_HEIGHT, BRICK_WIDTH, this));
        }
        for (int i = 0; i <= 9; i++) {
            bricks.add(new Brick(BRICK_DIFFX*i+BRICK_STARTX2, BRICK_STARTY4, BRICK_HEIGHT, BRICK_WIDTH, this));
        }
        bricksAmount = bricks.size();
    }
    /**
     * To return the keyboard manager
     * @return keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    // ticks the game
    private void tick() throws IOException {
        
        keyManager.tick();
        // sets the pause to true
        if(getKeyManager().isP() == true) {
            setPaused(!isPaused());
            getKeyManager().setP(false);
        }
        // starts the movement of the ball
        if(getKeyManager().isSPACE() == true && !isStart()){
           getKeyManager().setSPACE(false);
           setStart(true);
           int randomVelX = (int) (Math.random() * 20) - 10;
           ball.setVelX(randomVelX);
           ball.setVelY(-10);
        }
        //saves the game
        if(getKeyManager().isG() == true)
        {
            getKeyManager().setG(false);
            saveGame();
        }
        //loads the game
        if(getKeyManager().isC() == true)
        {
            getKeyManager().setC(false);
            loadGame();
        }
        //conditions that ends the game
        if (paddle.getLives() == 0 || bricks.size() == 0) {
            setGameOver(true);
            Assets.theme.stop();
        }
        //while the the game is neither over nor paused
        if(!isGameOver() && !isPaused()){
            
            //checks the collision with the paddle
            if (ball.intersecta(paddle)) {
                int xBall = ball.getX();
                int yBall = ball.getY();
                int wBall = ball.getWidth();
                int hBall = ball.getHeight();
                int xPaddle = paddle.getX();
                int yPaddle = paddle.getY();
                int wPaddle = paddle.getWidth();
                int hPaddle = paddle.getHeight();
                
                if (yBall >= yPaddle - hPaddle * 2) {
                    if (xBall >= xPaddle + wPaddle / 5 && xBall <= xPaddle + (4 * wPaddle / 5)) {
                        ball.setVelY(-ball.getVelY());
                    }
                    else {
                        ball.setVelY(-ball.getVelY());
                        ball.setVelX(-ball.getVelX());
                    }
                }                
                
                /*
                if (yBall >= yPaddle - hPaddle * 2) {
                    ball.setVelY(-ball.getVelY());
                }
                
                */
                
                /*
                
                if (ball.getX() >= paddle.getX() + 50) {
                    ball.setVelX(-ball.getVelX());
                    ball.setVelY(-ball.getVelY());
                }
                */
            }
            if(!power.isCreated())
            {
                if(bricksAmount  == bricks.size())
                {
                    power = new PowerUp((int) (Math.random() * (getWidth())),0,POWERUP1_WIDTH,POWERUP1_HEIGHT,this,3,1);
                    power.setCreated(true);
                }

            }
            
            if(power.intersecta(paddle))
            {
                switch(power.getPower())
                {
                    case 1:
                    paddle.setVelocity(paddle.getVelocity()+10);
                    break;

                    case 2:
                    paddle.setLives(paddle.getLives()+1);
                    break;

                    case 3:
                    paddle.setVelocity(paddle.getVelocity()-5);
                    break;
                }
                power.reset();
            }
            // checks if the ball reaches the bottom and restarts
            if(ball.isBottom())
            {
                paddle.setLives(paddle.getLives()-1);
                setStart(false);
                ball.setBottom(false);
    
            }
            // moves the ball with respect to the paddle if not started
            if(!isStart())
            {
                ball.setVelX(0);
                ball.setVelY(0);
                ball.setX(paddle.getX() + paddle.getWidth() / 3 + 5);
                ball.setY(paddle.getY() - ball.getHeight() * 2);
            }
            
            //checkes the collision of every brick
            for (int i = 0; i < bricks.size(); i++) {
                
                if (ball.intersecta(bricks.get(i))) {
                    //sets the velocity to the other side
                    ball.setVelY(-ball.getVelY());
                    //takes 1 live from the brick and changes the skin color
                    bricks.get(i).setLives(bricks.get(i).getLives() - 1);
                    paddle.setScore(paddle.getScore()+50);
                }

                if(bricks.get(i).getLives() == 0) {
                    bricks.remove(bricks.get(i));
                    
                }
                
                else {
                    bricks.get(i).tick();
                }
            }

            ball.tick();
            paddle.tick();
            power.tick();
        }

        if(isGameOver() && getKeyManager().isR() == true) {
            restartGame();
            getKeyManager().setR(false);   
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
            
            g.drawImage(Assets.background, 0, 0, getWidth(), getHeight(), null);
            
            g.setColor(Color.WHITE);
            
            if(!isGameOver()) {
                
                paddle.render(g);
                power.render(g);
                ball.render(g);
                for (int i = 0; i < bricks.size(); i++) {
                        bricks.get(i).render(g);
                }
                g.setFont(new Font("Serif", Font.BOLD, 15));
                //to show the score of the player
                g.drawString( "Score : " + paddle.getScore(), getWidth() - 100, getHeight());
                //to show the lives of the player
                for (int i = 1; i <= paddle.getLives(); i++) {
                    g.drawImage(Assets.lives, LIVES_DIFFX * i - LIVES_DIFFX+5, getHeight()-LIVES_DIFFX, LIVES_WIDTH, LIVES_HEIGHT, null);
                }
            }
            
            if(isPaused() && !isGameOver())
            {
                g.setFont(new Font("Serif", Font.BOLD, 120));
                g.drawString("Paused", getWidth()/2-200, getHeight()/2);
                g.setFont(new Font("Serif", Font.BOLD, 60));
                g.drawString("Current Score: " + paddle.getScore(), getWidth()/2-200, getHeight()/2+200);
            }
            if(isGameOver())
            {
                g.setFont(new Font("Serif", Font.BOLD, 120));
                g.drawString("Game", getWidth()/2-100, getHeight()/2-100);
                g.drawString("Over", getWidth()/2+50, getHeight()/2+50);
                g.setFont(new Font("Serif", Font.BOLD, 60));
                g.drawString("Press R to Restart Game", getWidth()/2-200, getHeight()/2+200);
                
            }
            bs.show();
            g.dispose();
        }
        
    }
    // to start the game
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
                try {
                    tick();
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                render();
                delta--;
            }
        }
        stop();
    }

    private void saveGame() throws IOException {
                                                          
        PrintWriter fileOut = new PrintWriter(new FileWriter(lastSave));
        
        fileOut.println(bricks.size());
        
        for (int i = 0; i < bricks.size(); i++) {
            fileOut.println(bricks.get(i).getX());
            fileOut.println(bricks.get(i).getY());
            fileOut.println(bricks.get(i).getLives());
        }
        
        fileOut.println(ball.getX());
        fileOut.println(ball.getY());
        fileOut.println(ball.getVelX());
        fileOut.println(ball.getVelY());
        
        fileOut.println(paddle.getX());
        fileOut.println(paddle.getY());
        fileOut.println(paddle.getLives());
        fileOut.println(paddle.getScore());
       
        fileOut.close();
    }
    
    
    private void loadGame() throws IOException
    {
        setStart(false);
        bricks.clear();
        BufferedReader fileIn;
              try {
                      fileIn = new BufferedReader(new FileReader(lastSave));
              } catch (FileNotFoundException e){
                      File puntos = new File(lastSave);
                      PrintWriter fileOut = new PrintWriter(puntos);
                      fileOut.println("100,demo");
                      fileOut.close();
                      fileIn = new BufferedReader(new FileReader(lastSave));
              }
              String dato = fileIn.readLine();
              int num = Integer.parseInt(dato);
              int brickX, brickY,brickLives;
              
              for(int i = 0; i < num ; i++)   
              {
                         brickX = Integer.parseInt(fileIn.readLine());
                         brickY = Integer.parseInt(fileIn.readLine());
                         brickLives = Integer.parseInt(fileIn.readLine());
                         
                    bricks.add(new Brick(brickX,brickY,70,25,brickLives, this));
              }
              
              int bX,bY,bVelX, bVelY;
                bX = Integer.parseInt(fileIn.readLine());
                bY = Integer.parseInt(fileIn.readLine());
                bVelX = Integer.parseInt(fileIn.readLine());
                bVelY = Integer.parseInt(fileIn.readLine());
                ball = new Ball(bX,bY,50,50,this,bVelX,bVelY);
                
              int pX,pY,pScore, pLives;
                pX = Integer.parseInt(fileIn.readLine());
                pY = Integer.parseInt(fileIn.readLine());
                pLives = Integer.parseInt(fileIn.readLine());
                pScore = Integer.parseInt(fileIn.readLine());
                paddle = new Paddle(120,30,this,10, pLives, pScore, pX,pY);
              fileIn.close();
    }

    private void restartGame() {
        
       ball.setCollisions(0);
       ball.setBottom(false);
       setStart(false);
       setPaused(false);
       setGameOver(false);
       
       paddle.setScore(0);
       paddle.setLives(3);
       paddle.setX(getWidth()/2);
       paddle.setY(getHeight() - 100);
       
       ball.setX(this.getWidth()/2);
       ball.setY(this.getHeight()/2-50);
       
       
        for (int i = 0; i <= 10; i++) {
            bricks.add(new Brick(BRICK_DIFFX*i+BRICK_STARTX1, BRICK_STARTY1, BRICK_HEIGHT, BRICK_WIDTH, this));
        }
        for (int i = 0; i <= 9; i++) {
            bricks.add(new Brick(BRICK_DIFFX*i+BRICK_STARTX2, BRICK_STARTY2, BRICK_HEIGHT, BRICK_WIDTH, this));
        }
        for (int i = 0; i <= 10; i++) {
            bricks.add(new Brick(BRICK_DIFFX*i+BRICK_STARTX1, BRICK_STARTY3, BRICK_HEIGHT, BRICK_WIDTH, this));
        }
        for (int i = 0; i <= 9; i++) {
            bricks.add(new Brick(BRICK_DIFFX*i+BRICK_STARTX2, BRICK_STARTY4, BRICK_HEIGHT, BRICK_WIDTH, this));
        }
        
    }
   

}

