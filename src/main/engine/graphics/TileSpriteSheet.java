package main.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * TileSpriteSheet - Extracts individual tiles from a spritesheet.
 */
public class TileSpriteSheet {
    
    private BufferedImage sheet;
    private int tileWidth, tileHeight;
    private int cols, rows;
    
    public TileSpriteSheet(String path, int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        
        try {
            sheet = ImageIO.read(getClass().getResourceAsStream(path));
            cols = sheet.getWidth() / tileWidth;
            rows = sheet.getHeight() / tileHeight;
            System.out.println("TileSpriteSheet loaded: " + path + " (" + cols + "x" + rows + " tiles)");
        } catch (IOException e) {
            System.err.println("Failed to load tile spritesheet: " + path);
            e.printStackTrace();
        }
    }
    
    /**
     * Get tile at grid position (col, row).
     */
    public BufferedImage getTile(int col, int row) {
        if (sheet == null || col < 0 || col >= cols || row < 0 || row >= rows) {
            return null;
        }
        return sheet.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
    }
    
    /**
     * Get tile by index (left-to-right, top-to-bottom).
     */
    public BufferedImage getTile(int index) {
        int col = index % cols;
        int row = index / cols;
        return getTile(col, row);
    }
    
    public int getCols() { return cols; }
    public int getRows() { return rows; }
    public int getTileCount() { return cols * rows; }
}
