package main.engine.world;

import java.awt.image.BufferedImage;

/**
 * Tile - A single tile in the game main.engine.world.
 * 
 * Each tile has an image and collision property.
 */
public class Tile {
    public BufferedImage image;
    public boolean solid;  // Can player walk through?
    
    public Tile(BufferedImage image, boolean solid) {
        this.image = image;
        this.solid = solid;
    }
}
