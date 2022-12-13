package graphvis.group30;

public class SpringForce extends Vector{
    static final double idealLength = 150;
    static final double springConstant = 0.2;

    /**
     * Creates a force vector.
     * @see graphvis.group30.Vector#Vector(double, double)
     * @param x the x component of the force vector
     * @param y the y component of the force vector
     */
    public SpringForce(double x, double y) {
        super(x, y);
    } 

    /**
     * Creates a SpringForce vector object for the spring force between the two vertices.
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @return the spring force between the two vertices
     */
    public static SpringForce getSpringForceBetween(SimBody v1, SimBody v2) {
        Vector diff = Vector.subtract(v2.getPosition(), v1.getPosition()); // used to calculate direction and distance

        Vector direction;
        if (diff.getLength() == 0) { // prevent division by zero
            direction = Vector.normalize(new Vector(Math.random(), Math.random())); // random direction
        } else {
            direction = Vector.normalize(diff); // direction is the normalized vector of the difference between the position vectors of the two vertices
        }

        Vector idealSpringVector = Vector.mult(direction, idealLength); // a vector with length equal to the resting length of the spring
        Vector springVector = Vector.mult(Vector.subtract(diff, idealSpringVector), springConstant); // spring force formula
        SpringForce sf = new SpringForce(springVector.x, springVector.y); // create the spring force object
        return sf;
    }
}
