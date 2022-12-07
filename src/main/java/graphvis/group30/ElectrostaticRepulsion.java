package graphvis.group30;

public class ElectrostaticRepulsion extends Vector{
    static final double charge = 250000;
    public ElectrostaticRepulsion(double x, double y) {
        super(x, y);
    }

    public static ElectrostaticRepulsion getElectrostaticBetween(SimBody v1, SimBody v2) {
        Vector diff = Vector.subtract(v1.getPosition(), v2.getPosition());
        Vector direction = Vector.normalize(diff);
        Vector eVector = Vector.div(Vector.mult(direction, charge), diff.getLength()*diff.getLength());
        ElectrostaticRepulsion ef = new ElectrostaticRepulsion(eVector.x, eVector.y);
        return ef;
    }
    
}
