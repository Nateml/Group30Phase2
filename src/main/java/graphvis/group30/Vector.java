package graphvis.group30;

public class Vector {
    double x, y;

    /**
     * Creates a Vector object.
     * @param x the x component of the vector
     * @param y the y component of the vector
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds the argument vector to the vector which this method is called with.
     * @param v the vector to add
     */
    public void add(Vector v) {
        this.x += v.x;
        this.y += v.y;
    }

    /**
     * Subtracts the argument vector from the vector which this method is called with.
     * @param v
     */
    public void subtract(Vector v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    /**
     * Multiplies the vector by a scalar.
     * @param scalar the value to scale the vector by
     */
    public void mult(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    /**
     * Creates a new vector object which has length 1 and has the same direction as the argument vector.
     * @param v the vector to normalize
     * @return a new vector equal to the normalized vector of V
     */
    public static Vector normalize(Vector v) {
        Vector newV = new Vector(v.x, v.y);
        newV.normalize();
        return newV;
    }

    /**
     * Creates a scaled version of the argument vector.
     * @param v1 the vector to be scaled
     * @param scalar the value to scale the vector by
     * @return a new vector which is equal to v1 * scalar
     */
    public static Vector mult(Vector v1, double scalar) {
        Vector newVector = new Vector(v1.x*scalar, v1.y*scalar);
        return newVector;
    }

    /**
     * Creates a new vector which is the result of dividing each component of the argument vector by a scalar.
     * @param v1 the vector to perform division on
     * @param scalar the value to divide by
     * @return a new vector equal to v1 / scalar
     */
    public static Vector div(Vector v1, double scalar) {
        Vector newVector = new Vector(v1.x/scalar, v1.y/scalar);
        return newVector;
    }

    /**
     * Creates a new vector equal to the sum of the two argument vectors.
     * @param v1 the first vector
     * @param v2 the second vector
     * @return a new vector equal to the sum of v1 and v2
     */
    public static Vector add(Vector v1, Vector v2) {
        Vector newVector = new Vector(v1.x+v2.x, v1.y+v2.y);
        return newVector;
    }

    /**
     * Creates a new vector equal to the differencce between the two argument vectors.
     * @param v1 the first vector
     * @param v2 the second vector
     * @return a new vector equal to v1 - v2
     */
    public static Vector subtract(Vector v1, Vector v2) {
        Vector newVector = new Vector(v1.x-v2.x, v1.y-v2.y);
        return newVector;
    }

    /*
     * Normalises the vector such that it has a length = 1
     */
    public void normalize() {
        double length = this.getLength();
        x = x/length;
        y = y/length;
    } 
    
    /*
     * Returns the euclidean length of the vector
     */
    public double getLength() {
        double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y,2));
        return length;
    }

}
