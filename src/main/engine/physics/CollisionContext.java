package main.engine.physics;

/**
 * Collision context - Provides context information for collision checks.
 *
 * This allows collision objects to make decisions based on the entity
 * attempting to move ( e.g Door checks if player has correct key element )
 *
 * Uses Object type for maximum flexibility - different games can pass
 * different context types (ElementType, DamageType, TeamID, etc.).
 */
public class CollisionContext {

    /**
     * Generic attribute that can be checked during collision.
     *
     * Examples:
     * - DamageType (for shields)
     * - TeamID (for team-based barriers)
     * - null (for unconditional collision)
     */
    public Object attribute;

    /**
     * Optional: The entity attempting to move.
     * Useful for more complex collision logic.
     */
    public Object entity;

    public CollisionContext(){
        this.attribute = null;
        this.entity = null;

    }

    public CollisionContext(Object attribute) {
        this.attribute = attribute;
        this.entity = null;
    }

    public CollisionContext(Object attribute, Object entity) {
        this.attribute = attribute;
        this.entity = entity;
    }

    // Future: Add more context as needed
    // public Vector2D velocity;


}
