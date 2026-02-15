package main.engine.physics;

/**
 * AABB - Axis-Aligned Bounding Box collision detection
 */
public class AABB {

    private AABB(){
        throw new  IllegalStateException("Utility class");
    }

    /**
     * Check if two axis-aligned bounding boxes overlap.
     *
     * @param pos1 Position of first box (top-left corner)
     * @param w1 Width of first box
     * @param h1 Height of first box
     * @param pos2 Position of second box (top-left corner)
     * @param w2 Width of second box
     * @param h2 Height of second box
     * @return true if boxes overlap, false otherwise
     */
    public static boolean overlap(Vector2D pos1, int w1, int h1,
                                  Vector2D pos2, int w2, int h2)
    {
        return  pos1.x < pos2.x + w2 &&
                pos1.x + w1 > pos2.x &&
                pos1.y < pos2.y + h2 &&
                pos1.y + h1 > pos2.y ;
    }


}
