package graphvis.group30;

public class ElectrostaticRepulsion extends Vector{
    static final double charge = 450000; // the charge constant

    /**
     * @see graphvis.group30.Vector#Vector(double, double)
     * @param x the x value of the force vector
     * @param y the y value of the force vector
     */
    public ElectrostaticRepulsion(double x, double y) {
        super(x, y);
    }

    /**
     * @param v1 first object to use in force calculation
     * @param v2 second object to use in force calculation
     * @return the electrostatic repulsion force between the two objects
     */
    public static ElectrostaticRepulsion getElectrostaticBetween(SimBody v1, SimBody v2) {
        Vector diff = Vector.subtract(v1.getPosition(), v2.getPosition());
        ElectrostaticRepulsion ef;

        if (diff.getLength() <= 50) { // limits the magnitude of the force from skyrocketing
            Vector direction = new Vector(1, 1); 
            if (diff.getLength() == 0) { // prevents division by zero
                direction = Vector.normalize(new Vector(Math.random(), Math.random())); // random direction
            } else {
                direction = Vector.normalize(diff); // normalized vector pointing from v2 to v1
            }
            Vector eVector = Vector.div(Vector.mult(direction, charge), 50*50); // inverse square law 
            ef = new ElectrostaticRepulsion(eVector.x, eVector.y);
        } else {
            Vector direction = Vector.normalize(diff); // normalized vector pointing from v2 to v1
            Vector eVector = Vector.div(Vector.mult(direction, charge), diff.getLength()*diff.getLength()); // inverse square law
            ef = new ElectrostaticRepulsion(eVector.x, eVector.y);
        }
        return ef;
    }
    
}
