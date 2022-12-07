package graphvis.group30;

public class SpringForce extends Vector{
    static final double idealLength = 150;
    static final double springConstant = 0.05;
    public SpringForce(double x, double y) {
        super(x, y);
    } 

    public static SpringForce getSpringForceBetween(SimBody v1, SimBody v2) {
        Vector diff = Vector.subtract(v2.getPosition(), v1.getPosition());
        Vector direction = Vector.normalize(diff);
        Vector idealSpringVector = Vector.mult(direction, idealLength);
        Vector springVector = Vector.mult(Vector.subtract(diff, idealSpringVector), springConstant);
        SpringForce sf = new SpringForce(springVector.x, springVector.y);
        return sf;
    }
}
