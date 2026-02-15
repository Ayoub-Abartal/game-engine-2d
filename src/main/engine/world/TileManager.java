package main.engine.world;


import main.engine.graphics.TileSpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * TileManager - Loads and renders the game main.engine.world.
 *
 */
public class TileManager {
    
    private Map<Integer,Tile> tiles;
    private int[][] platformLayer;
    private int[][] backgroundLayer;
    private int mapCols;
    private int mapRows;
    private int tileSize;

    public TileManager(int tileSize) {
        this.tileSize = tileSize;
        tiles = new HashMap<>();
    }

    /**
     * Register a tile with the manager.
     */
    public void registerTile(int id, Tile tile) {
        tiles.put(id, tile);
    }

    /**
     * Register a tile from image and solid flag.
     */
    public void registerTile(int id, BufferedImage image, boolean solid) {
        tiles.put(id, new Tile(image, solid));
    }

    /**
     * LoadMap - two layer map
     *
     * the map is divided into layers
     * we should load each layer
     * @param backgroundPath background layer file path
     * @param platformPath platform layer file path
     */
    public void loadMap(String backgroundPath, String platformPath) {
        backgroundLayer = loadLayer(backgroundPath);
        platformLayer = loadLayer(platformPath);
        System.out.println("Multi-layer map loaded: " + mapCols + "x" + mapRows);
    }

    /**
     * LoadLayer -
     *
     * a layer could be background, platform
     * @param path path of the layer file
     * @return a 2d Array
     */
    private int[][] loadLayer(String path) {
        int[][] layer = null;  // Initialize to null before try block

        // Read the file
        try {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(path))
            );

            // Extract the first line of the txt file
            String firstLine = br.readLine();

            // Extract the number of columns in the row
            String[] firstRow = firstLine.split(",");  // CSV uses commas
            int cols = firstRow.length;

            // Extract the number of rows in the file
            int rowCount = 1;
            while (br.readLine() != null) rowCount++;
            int rows = rowCount;

            // close the buffer reader
            br.close();

            br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(path))
            );
            // initialize the layer array
            layer = new int[rows][cols];

            // Loop through each line to extract numbers of each row
            for (int row = 0; row < rows; row++) {
                String line = br.readLine();
                if (line == null) break;

                String[] numbers = line.split(",");  // CSV uses commas

                for (int col = 0; col < cols && col < numbers.length; col++) {
                    layer[row][col] = Integer.parseInt(numbers[col].trim());
                }
            }

            // close the bufferReader
            br.close();

            mapCols = cols;
            mapRows = rows;

        } catch (IOException e) {
            System.err.println("Error loading layer: " + path);
            e.printStackTrace();
        }

        // return the 2d Array
        return layer;
    }

    public void draw(Graphics2D g2d) {
        // Draw background layer first (behind everything)
        drawLayer(g2d, backgroundLayer);
        
        // Draw platform layer on top (with collision)
        drawLayer(g2d, platformLayer);
    }

    private void drawLayer(Graphics2D g2d, int[][] layer) {
        if (layer == null) return;
        
        for (int row = 0; row < mapRows; row++) {
            for (int col = 0; col < mapCols; col++) {
                int tileIndex = layer[row][col];
                
                // Skip empty tiles (-1 = transparent)
                if (tileIndex == -1) continue;
                
                int x = col * tileSize;
                int y = row * tileSize;

                Tile tile = tiles.get(tileIndex);
                if (tile != null && tile.image != null) {
                    g2d.drawImage(tiles.get(tileIndex).image, x, y,
                            tileSize, tileSize, null);
                }
            }
        }
    }

    public boolean isSolid(int col, int row) {
        // Out of bounds horizontally = solid (walls)
        if (col < 0 || col >= mapCols) {
            return true;
        }
        // Above map = not solid (can jump up)
        if (row < 0) {
            return false;
        }
        // BELOW map = not solid (can fall and die!)
        if (row >= mapRows) {
            return false;
        }
        
        // Only check platform layer for collision (not background!)
        if (platformLayer == null) return false;
        
        int tileIndex = platformLayer[row][col];
        
        // -1 = empty/transparent = not solid
        if (tileIndex == -1) return false;

        Tile tile = tiles.get(tileIndex);
        return tile != null && tile.solid;
    }
}
