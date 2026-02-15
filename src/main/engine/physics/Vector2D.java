package main.engine.physics;

public class Vector2D {

    // POSITION (x,y)
    public float x;
    public float y;

    // Zero Vector
    public static Vector2D zero() {
        return new Vector2D(0, 0);
    }

    // Constructor
    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    // Copy Constructor
    public Vector2D(Vector2D vector2D){
        this.x = vector2D.x;
        this.y = vector2D.y;
    }

    public void add(Vector2D vector2D){
        this.x += vector2D.x;
        this.y += vector2D.y;
    }

    public void subtract(Vector2D vector2D){
        this.x -= vector2D.x;
        this.y -= vector2D.y;
    }

    // Scalar multiplication
    public void multiply(float scalar){
        this.x *= scalar;
        this.y *= scalar;
    }

    /*
    The magnitude (length) of a 2D vector (vec{v}=(x,y) ) represents its size,
     calculated using the Pythagorean theorem as \( |vec{v}| =sqrt{x^{2}+y^{2}} ).
     It is a scalar value (always non-negative) representing the length of the arrow, or hypotenuse,
     formed by the (x) and (y) components.
     */
    public float length(){
        return (float) Math.sqrt(x*x + y*y);
    }

    /**
     * Normalizing a 2D physics vector means scaling it to a length (magnitude) of 1 while preserving its direction, resulting in a unit vector.
     * To normalize a vector \(vec{v}=(x,y)), divide each component by its magnitude: (vec{v}_{norm}=frac{(x,y)}/{sqrt{x^{2}+y^{2}}}).
     * If the magnitude is 0, the vector cannot be normalized.
     */
    public void normalize(){
        float magnitude = length();
        if(magnitude!=0){
            this.x /= magnitude;
            this.y /= magnitude;
        }
    }

    /**
     * The distance between the two points in the example is 5 units.
     * The general formula to calculate the distance \(d\) between two points with coordinates \((x_{1},y_{1})\) and \((x_{2},y_{2})\)
     * is given by the formula \(\mathbf{d=}\sqrt{\mathbf{(x}_{\mathbf{2}}\mathbf{-x}_{\mathbf{1}}\mathbf{)}^{\mathbf{2}}\mathbf{+(y}_{\mathbf{2}}\mathbf{-y}_{\mathbf{1}}\mathbf{)}^{\mathbf{2}}}\).
     * @param vector2D
     * @return float
     */
    public float distanceTo(Vector2D vector2D){

        // displacement vector is calculated by D = VectB - VectA
        float dy = this.y - vector2D.y;
        float dx = this.x- vector2D.x;
        // Distance formula ( Pethagorean theorem )

        return (float) Math.sqrt(dx * dx + dy*dy);
    }

    public float dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    // set values

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    // to Save old position
    public Vector2D copy() {
        return new Vector2D(this.x, this.y);
    }

    @Override
    public String toString() {
        return String.format("Vector2D(%.2f, %.2f)", x, y);
    }





}
