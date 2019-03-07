package videogame;

import java.awt.Graphics;

/**
 *
 * @author moisesfernandez
 */
public abstract class Item {
    protected int x;
    protected int y;
    
    /**
     * Constructor to initialize an object of the type Item with its attributes
     * @param x
     * @param y
     */
    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * To get the position x of the item
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * To get the position y of the item
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * To set the position x of the item
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * To set the position y of the item
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
}
