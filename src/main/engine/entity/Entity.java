package main.engine.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.engine.api.Positioned;

/**
 * Entity - Base class for all game entities (Player, NPCs, etc.)
 * 
 * Contains common properties: position, size, sprite
 */
public abstract class Entity implements Positioned {
    
    protected int x, y;
    protected int width, height;
    protected BufferedImage sprite;
    
    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
   // public abstract void update();
    public abstract void draw(Graphics2D g2d);
    
    // Collision box
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    // Check collision with another main.engine.entity
    public boolean collidesWith(Entity other) {
        return getBounds().intersects(other.getBounds());
    }
    
    // Check if point is inside main.engine.entity
    public boolean contains(int px, int py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    // Setters
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
