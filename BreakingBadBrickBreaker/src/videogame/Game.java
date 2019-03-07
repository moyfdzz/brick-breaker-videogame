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
    private int powerChance;
        
        
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
        this.powerChance = 0;
        
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
        paddle = new Paddle(getWidth()/2, getHeight() - 100,PADDLE_WIDTH, PADDLE_HEIGHT,  this);
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
                ball.setVelY(-ball.getVelY());
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
                    //sets the score to the new score
                    paddle.setScore(paddle.getScore()+50);
                    
                }
                // if the bricks is 0 
                if(bricks.get(i).getLives() == 0) {
                    bricks.remove(bricks.get(i));
                    //generates the possibility of a powerup
                    powerChance = (int) (Math.random() * 2);
                }
                
                else {
                    //ticks the bricks
                    bricks.get(i).tick();
                }
            }
            // creates a powerup if there is a chance 
            if(!power.isCreated() && powerChance == 1)
            {
                //generates a random number between 1 and 3
                int select = (int) (Math.random() * 4) + 1;

                switch(select)
                {
                    //creates powerup 1
                    case 1:
                    power = new PowerUp((int) (Math.random() * (getWidth())),0,POWERUP1_WIDTH,POWERUP1_HEIGHT,this,3,1);
                    
                    break;
                    //creates powerup 2
                    case 2:
                    power = new PowerUp((int) (Math.random() * (getWidth())),0,POWERUP1_WIDTH,POWERUP1_HEIGHT,this,3,2);
                        break;
                    //creates powerup 3
                    case 3:
                    power = new PowerUp((int) (Math.random() * (getWidth())),0,POWERUP1_WIDTH,POWERUP1_HEIGHT,this,3,3);    
                        break;
                }  
                    //sets the creator
                    power.setCreated(true);
                    powerChance = 0;
            }

            // if powerup intersects changes the status

            if(power.intersecta(paddle))
            {
                switch(power.getPower())
                {
                    //adds +10 to velocity
                    case 1:
                    paddle.setVelocity(paddle.getVelocity()+10);
                    break;
                    // adds a life to the player
                    case 2:
                    paddle.setLives(paddle.getLives()+1);
                    break;
                    //decreases velocity
                    case 3:
                    paddle.setVelocity(paddle.getVelocity()-5);
                    break;
                }
                //resets the value of power
                power.reset();
            }
            //ticks the objects
            ball.tick();
            paddle.tick();
            power.tick();
        }
        // restarts the game
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
            
            //shows if the game is paused and the current score
            if(isPaused() && !isGameOver())
            {
                g.setFont(new Font("Serif", Font.BOLD, 120));
                g.drawString("Paused", getWidth()/2-200, getHeight()/2);
                g.setFont(new Font("Serif", Font.BOLD, 60));
                g.drawString("Current Score: " + paddle.getScore(), getWidth()/2-200, getHeight()/2+200);
            }
            //shows that the game is over and the instructions to restart the game
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
    //to stop the game
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
    /**
     * To save the game into the text
     */
    private void saveGame() throws IOException {
        // creates a new file or rewrites if already created                                          
        PrintWriter fileOut = new PrintWriter(new FileWriter(lastSave));

        // writes the amount of bricks to the file
        fileOut.println(bricks.size());

        // writes the properties of each of the bricks to the file
        for (int i = 0; i < bricks.size(); i++) {
            fileOut.println(bricks.get(i).getX());
            fileOut.println(bricks.get(i).getY());
            fileOut.println(bricks.get(i).getLives());
        }

        // writes the properties of the ball
        fileOut.println(ball.getX());
        fileOut.println(ball.getY());
        fileOut.println(ball.getVelX());
        fileOut.println(ball.getVelY());

        // writes the properties of the paddle
        fileOut.println(paddle.getX());
        fileOut.println(paddle.getY());
        fileOut.println(paddle.getLives());
        fileOut.println(paddle.getScore());

        //closes the edited file and saves it
        fileOut.close();
    }
    
    /**
     * To load the game from the file
     */
    private void loadGame() throws IOException
    {
        //sets the start position of the ball to false
        setStart(false);

        //clears the linked list in order to add the bricks
        bricks.clear();
        //creates the buffer of the file to be read
        BufferedReader fileIn;
              try {
                  // assigns the file to filein
                      fileIn = new BufferedReader(new FileReader(lastSave));

              } catch (FileNotFoundException e){
                    //if the exception is cateched the recover file writed not saved
                      File recover = new File(lastSave);

                      PrintWriter fileOut = new PrintWriter(recover);

                      fileOut.println("File not saved");
                    //file closes
                      fileOut.close();
                    //tries to create a new file to be read
                      fileIn = new BufferedReader(new FileReader(lastSave));
              }
              //reads the first line which is the amount of bricks to be read
              String dato = fileIn.readLine();
              int num = Integer.parseInt(dato);
              //create variables of the bricks
              int brickX, brickY,brickLives;
              //for a certain amount the buffer reads the properties
              for(int i = 0; i < num ; i++)   
              {
                         brickX = Integer.parseInt(fileIn.readLine());
                         brickY = Integer.parseInt(fileIn.readLine());
                         brickLives = Integer.parseInt(fileIn.readLine());
                    // adds the new bricks to the linked list
                    bricks.add(new Brick(brickX,brickY,70,25,brickLives, this));
              }
              //create variables of the ball
              int bX,bY,bVelX, bVelY;
              //reads the variables of the ball
                bX = Integer.parseInt(fileIn.readLine());
                bY = Integer.parseInt(fileIn.readLine());
                bVelX = Integer.parseInt(fileIn.readLine());
                bVelY = Integer.parseInt(fileIn.readLine());
              //creates a new ball woth the properties above
                ball = new Ball(bX,bY,BALL_DIMENSION,BALL_DIMENSION,this,bVelX,bVelY);
            
            //create variables of the paddle
              int pX,pY,pScore, pLives;

              //reads the variables of the paddle
                pX = Integer.parseInt(fileIn.readLine());
                pY = Integer.parseInt(fileIn.readLine());
                pLives = Integer.parseInt(fileIn.readLine());
                pScore = Integer.parseInt(fileIn.readLine());

              //creates a new paddle
                paddle = new Paddle(PADDLE_WIDTH,PADDLE_HEIGHT,this,10, pLives, pScore, pX,pY);

            //closes the file
              fileIn.close();
    }

    //restarts the game
    private void restartGame() {
    
       //rests the conditions of the game
       setStart(false);
       setPaused(false);
       setGameOver(false);

    //rests the conditions of the paddle
       paddle.setScore(0);
       paddle.setLives(3);
       paddle.setX(getWidth()/2);
       paddle.setY(getHeight() - 100);
       
       //rests the conditions of the ball
       ball.setBottom(false);
       ball.setX(this.getWidth()/2);
       ball.setY(this.getHeight()/2-50);
       
       //resets all the board to new values of the bricks linked list
        bricks.clear();

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

