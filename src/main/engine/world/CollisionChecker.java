package main.engine.world;

import main.game.config.PlateformerConfig;
import main.engine.api.Collidable;
import main.engine.core.Scene;
import main.engine.physics.CollisionContext;
import main.engine.physics.Vector2D;

import java.util.List;

/**
 * CollisionChecker - Handles collision detection.
 * 
 * Checks if player can move to a position without hitting walls.
 */
public class CollisionChecker {
    
    private TileManager tileManager;

    private Scene scene;

    public CollisionChecker(TileManager tileManager,Scene scene) {
        this.tileManager = tileManager;
        this.scene = scene;
    }
    
    /**
     * Check if player can move to new position.
     * 
     * Checks all 4 corners of player hitbox against solid tiles.
     */
    public boolean canMove(Vector2D position, int width, int height, CollisionContext context) {
        // Player hitbox corners
        int left = (int) position.x + 8;      // Small margin
        int right = (int) position.x + width - 8;
        int top = (int) position.y + 16;      // Top margin (head area)
        int bottom = (int) position.y + height - 4;
        
        // Convert to tile coordinates
        int leftCol = left / PlateformerConfig.TILE_SIZE;
        int rightCol = right / PlateformerConfig.TILE_SIZE;
        int topRow = top / PlateformerConfig.TILE_SIZE;
        int bottomRow = bottom / PlateformerConfig.TILE_SIZE;
        
        // Check all 4 corners
        // Checking the collision with the tile
        if (tileManager.isSolid(leftCol, topRow)) return false;
        if (tileManager.isSolid(rightCol, topRow)) return false;
        if (tileManager.isSolid(leftCol, bottomRow)) return false;
        if (tileManager.isSolid(rightCol, bottomRow)) return false;

        // checking the collision with the Collidables objects
        if(scene!= null && scene.getCollidables() != null){
            List<Collidable> collidables = scene.getCollidables();
            for(Collidable collidable: collidables){
                if(collidable.isSolidAt(position,width,height,context)){
                    return false;
                }
            }
        }
        return true;
    }


}
