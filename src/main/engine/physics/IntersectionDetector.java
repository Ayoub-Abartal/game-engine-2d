package main.engine.physics;

public class IntersectionDetector {

    private IntersectionDetector(){
        throw new IllegalStateException("Utility class");
    }
    public static double calculateDistance(int objectCenterX, int objectCenterY, int playerCenterX, int playerCenterY) {
        return Math.sqrt(
                Math.pow(objectCenterX - playerCenterX, 2) +
                        Math.pow(objectCenterY - playerCenterY, 2)
        );
    }
}
