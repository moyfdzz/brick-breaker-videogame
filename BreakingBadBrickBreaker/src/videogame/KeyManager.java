/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author moisesfernandez
 */
public class KeyManager implements KeyListener{
    public boolean RIGHT;
    public boolean LEFT;
    public boolean SPACE;
    public boolean P;
    
    private boolean keys[];
    
    public KeyManager() {
        keys = new boolean[256];
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    public void tick() {
        RIGHT = keys[KeyEvent.VK_RIGHT];
        LEFT = keys[KeyEvent.VK_LEFT];
        SPACE = keys[KeyEvent.VK_SPACE];
        P = keys[KeyEvent.VK_P];
    }
}
