package main.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * SpriteSheet - Loads and extracts frames from a sprite sheet.
 * 
 * A sprite sheet is a single image containing multiple animation frames.
 * This class splits it into individual frames.
 */
public class SpriteSheet {
    
    private BufferedImage sheet;
    private int frameWidth;
    private int frameHeight;
    private int columns;
    private int rows;
    
    /**
     * Load a sprite sheet from resources.
     * 
     * @param path Path to sprite sheet (e.g., "/sprites/character.png")
     * @param frameWidth Width of each frame in pixels
     * @param frameHeight Height of each frame in pixels
     */
    public SpriteSheet(String path, int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        
        try {
            sheet = ImageIO.read(getClass().getResourceAsStream(path));
            columns = sheet.getWidth() / frameWidth;
            rows = sheet.getHeight() / frameHeight;
            
            System.out.println("Loaded sprite sheet: " + path);
            System.out.println("  Size: " + sheet.getWidth() + "x" + sheet.getHeight());
            System.out.println("  Frame size: " + frameWidth + "x" + frameHeight);
            System.out.println("  Grid: " + columns + " cols x " + rows + " rows");
            System.out.println("  Total frames: " + (columns * rows));
        } catch (IOException e) {
            System.err.println("Failed to load sprite sheet: " + path);
            e.printStackTrace();
        }
    }
    
    /**
     * Extract a single frame from the sprite sheet.
     * 
     * @param col Column index (0-based, left to right)
     * @param row Row index (0-based, top to bottom)
     * @return BufferedImage of the frame
     */
    public BufferedImage getFrame(int col, int row) {
        if (sheet == null) return null;
        
        int x = col * frameWidth;
        int y = row * frameHeight;
        
        return sheet.getSubimage(x, y, frameWidth, frameHeight);
    }
    
    /**
     * Extract a row of frames (e.g., all walk-down frames).
     * 
     * @param row Row index (0-based)
     * @return Array of frames in that row
     */
    public BufferedImage[] getRow(int row) {
        BufferedImage[] frames = new BufferedImage[columns];
        for (int col = 0; col < columns; col++) {
            frames[col] = getFrame(col, row);
        }
        return frames;
    }
    
    /**
     * Extract all frames as a 2D array.
     * 
     * @return [row][col] array of frames
     */
    public BufferedImage[][] getAllFrames() {
        BufferedImage[][] frames = new BufferedImage[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                frames[row][col] = getFrame(col, row);
            }
        }
        return frames;
    }
    
    public int getColumns() { return columns; }
    public int getRows() { return rows; }
    public int getFrameWidth() { return frameWidth; }
    public int getFrameHeight() { return frameHeight; }
}
