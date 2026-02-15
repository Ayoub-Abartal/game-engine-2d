package main.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    
    // Element switching keys (1-4)
    public boolean key1Pressed, key2Pressed, key3Pressed, key4Pressed;
    
    // Interaction
    public boolean interactPressed;
    public boolean spacePressed;
    public boolean toggleLever;
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        // Movement
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = true;
        
        // Element switching
        if (code == KeyEvent.VK_1) key1Pressed = true;
        if (code == KeyEvent.VK_2) key2Pressed = true;
        if (code == KeyEvent.VK_3) key3Pressed = true;
        if (code == KeyEvent.VK_4) key4Pressed = true;
        
        // Interaction
        if (code == KeyEvent.VK_E) interactPressed = true;
        if (code == KeyEvent.VK_SPACE) spacePressed = true;
        if(code == KeyEvent.VK_F) toggleLever = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = false;
        
        if (code == KeyEvent.VK_1) key1Pressed = false;
        if (code == KeyEvent.VK_2) key2Pressed = false;
        if (code == KeyEvent.VK_3) key3Pressed = false;
        if (code == KeyEvent.VK_4) key4Pressed = false;
        
        if (code == KeyEvent.VK_E) interactPressed = false;
        if (code == KeyEvent.VK_SPACE) spacePressed = false;
        if(code == KeyEvent.VK_F) toggleLever = false;

    }
    
    public boolean isMoving() {
        return upPressed || downPressed || leftPressed || rightPressed;
    }
    
    // One-shot main.engine.input (returns true once, then resets)
    public boolean consumeInteract() {
        if (interactPressed) {
            interactPressed = false;
            return true;
        }
        return false;
    }
    
    public boolean consumeSpace() {
        if (spacePressed) {
            spacePressed = false;
            return true;
        }
        return false;
    }
}
