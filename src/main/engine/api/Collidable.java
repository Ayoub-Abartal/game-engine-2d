package main.engine.api;

import main.engine.physics.CollisionContext;
import main.engine.physics.Vector2D;

/**
 * Collidable - Interface for objects that can block movements
 *
 * Objects implementing this interface can be checked for collision
 * by the CollisionChecker without knowing their specific type.
 *
 * Examples : Door , Bridge , MovingPlatform
 */
public interface Collidable {

    /**
     * Check if this object blocks movement at the given position.
     *
     * @param position Position to check (top-left corner)
     * @param width Width of the moving object
     * @param height Height of the moving object
     * @param context Additional context (element, velocity, etc.)
     * @return true if this object blocks movement, false otherwise
     */
    boolean isSolidAt(Vector2D position, int width, int height, CollisionContext context);

    /**
     * Get collision priority (for layered collision).
     * Higher priority = checked first.
     * Default: 0 (normal priority)
     *
     * @return collision priority
     */
    default int getCollisionPriority() {
        return 0;
    }
}
